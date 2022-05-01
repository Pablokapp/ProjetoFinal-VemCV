package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.candidato.CandidatoCreateDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDTO;
import com.dbc.vemcv.dto.candidato.PaginaCandidatoDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoCreateDTO;
import com.dbc.vemcv.dto.candidatocompleto.CandidatoCompletoDTO;
import com.dbc.vemcv.dto.candidatocompleto.PaginaCandidatoCompletoDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatoCompletoService {
    private final CandidatoService candidatoService;
    private final DadosEscolaresService dadosEscolaresService;
    private final ExperienciasService experienciasService;
    private final CurriculoService curriculoService;

    private final ObjectMapper objectMapper;

    public PaginaCandidatoCompletoDTO listPaginado(Integer idCandidato, Integer pagina, Integer quantidadePorPagina) throws RegraDeNegocioException {
        PaginaCandidatoDTO paginaCandidatoDTO = candidatoService.listPaginado(idCandidato, pagina, quantidadePorPagina);
        List<CandidatoCompletoDTO> candidatoCompletoDTOList = paginaCandidatoDTO.getCandidatos().stream()
                .map(c->{
                    CandidatoCompletoDTO candidatoCompletoDTO = objectMapper.convertValue(c,CandidatoCompletoDTO.class);
                    candidatoCompletoDTO.setDadosEscolares(dadosEscolaresService.findByIdCandidato(c.getIdCandidato()));
                    candidatoCompletoDTO.setExperiencias(experienciasService.findByIdCandidato(c.getIdCandidato()));
                    return candidatoCompletoDTO;
                })
                .collect(Collectors.toList());

        return PaginaCandidatoCompletoDTO.builder()
                .candidatosCompletos(candidatoCompletoDTOList)
                .totalDeElementos(paginaCandidatoDTO.getTotalDeElementos())
                .totalDePaginas(paginaCandidatoDTO.getTotalDePaginas())
                .build();
    }

    public CandidatoCompletoDTO create(CandidatoCompletoCreateDTO candidatoCompletoCreateDTO) throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDTO = objectMapper.convertValue(candidatoCompletoCreateDTO,CandidatoCreateDTO.class);

        //saves

        CandidatoDTO candidatoCriado = candidatoService.create(candidatoCreateDTO);

        List<DadosEscolaresDTO> dadosEscolaresDTOList = new ArrayList<>();
        List<ExperienciasDTO> experienciasDTOList = new ArrayList<>();
        if(candidatoCompletoCreateDTO.getDadosEscolares()!=null){
            for(DadosEscolaresCreateDTO dadosEscolares: candidatoCompletoCreateDTO.getDadosEscolares()){
                dadosEscolaresDTOList.add(dadosEscolaresService.create(candidatoCriado.getIdCandidato(),dadosEscolares));
            }
        }
        if(candidatoCompletoCreateDTO.getExperiencias()!=null) {
            for (ExperienciasCreateDTO experiencia : candidatoCompletoCreateDTO.getExperiencias()) {
                experienciasDTOList.add(experienciasService.create(candidatoCriado.getIdCandidato(), experiencia));
            }
        }
        CandidatoCompletoDTO candidatoCompletoDTO = objectMapper.convertValue(candidatoCriado,CandidatoCompletoDTO.class);
        candidatoCompletoDTO.setDadosEscolares(dadosEscolaresDTOList);
        candidatoCompletoDTO.setExperiencias(experienciasDTOList);

        return candidatoCompletoDTO;
    }


    public CandidatoCompletoDTO update(Integer idCandidato, CandidatoCompletoCreateDTO candidatoCompletoCreateDTO) throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDTO = objectMapper.convertValue(candidatoCompletoCreateDTO,CandidatoCreateDTO.class);

        CandidatoDTO candidatoAtualizado = candidatoService.update(idCandidato, candidatoCreateDTO);
        List<DadosEscolaresDTO> dadosEscolaresList = dadosEscolaresService.findByIdCandidato(idCandidato);
        List<ExperienciasDTO> experienciasList = experienciasService.findByIdCandidato(idCandidato);
        if(candidatoCompletoCreateDTO.getDadosEscolares()!=null) {
            for (DadosEscolaresDTO dadosEscolaresDTO : dadosEscolaresList) {
                dadosEscolaresService.delete(dadosEscolaresDTO.getIdDadosEscolares());
            }
        }
        if(candidatoCompletoCreateDTO.getExperiencias()!=null) {
            for (ExperienciasDTO experienciasDTO : experienciasList) {
                experienciasService.delete(experienciasDTO.getIdExperiencia());
            }
        }

        List<DadosEscolaresDTO> dadosEscolaresDTOList = new ArrayList<>();
        List<ExperienciasDTO> experienciasDTOList = new ArrayList<>();
        for(DadosEscolaresCreateDTO dadosEscolares: candidatoCompletoCreateDTO.getDadosEscolares()){
            dadosEscolaresDTOList.add(dadosEscolaresService.create(idCandidato,dadosEscolares));
        }
        for(ExperienciasCreateDTO experiencia:candidatoCompletoCreateDTO.getExperiencias()){
            experienciasDTOList.add(experienciasService.create(idCandidato,experiencia));
        }

        CandidatoCompletoDTO candidatoCompletoDTO = objectMapper.convertValue(candidatoAtualizado,CandidatoCompletoDTO.class);
        candidatoCompletoDTO.setDadosEscolares(dadosEscolaresDTOList);
        candidatoCompletoDTO.setExperiencias(experienciasDTOList);

        return candidatoCompletoDTO;
    }


}
