package com.dbc.vemcv.service;

import com.dbc.vemcv.common.Utils;
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
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CandidatoCompletoService {
    private final CandidatoService candidatoService;
    private final DadosEscolaresService dadosEscolaresService;
    private final ExperienciasService experienciasService;

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

    @Transactional
    public CandidatoCompletoDTO create(CandidatoCompletoCreateDTO candidatoCompletoCreateDTO) throws RegraDeNegocioException {
        log.info("CANDIDATO ENVIADO:  "+candidatoCompletoCreateDTO);
        CandidatoCreateDTO candidatoCreateDTO = objectMapper.convertValue(candidatoCompletoCreateDTO,CandidatoCreateDTO.class);

        //hotfix: mesmo como transação, não está acontecendo o rollback em caso de excessão nos dados e experiências então o teste foi feito novamente aqui como horfix
        if(candidatoCompletoCreateDTO.getDadosEscolares()!=null&&!candidatoCompletoCreateDTO.getDadosEscolares().isEmpty()) {
            for (DadosEscolaresCreateDTO dadosEscolares : candidatoCompletoCreateDTO.getDadosEscolares()) {
                Utils.validacaoDeTempo(dadosEscolares.getDataInicio().atStartOfDay(),dadosEscolares.getDataFim().atStartOfDay(),0L,"Data final deve ser após a Data de início");
            }
        }
        if(candidatoCompletoCreateDTO.getExperiencias()!=null&&!candidatoCompletoCreateDTO.getExperiencias().isEmpty()) {
            for (ExperienciasCreateDTO experiencia : candidatoCompletoCreateDTO.getExperiencias()) {
                Utils.validacaoDeTempo(experiencia.getDataInicio().atStartOfDay(),experiencia.getDataFim().atStartOfDay(),0L,"Data final deve ser após a Data de início");
            }
        }


        CandidatoDTO candidatoCriado = candidatoService.create(candidatoCreateDTO);

        List<DadosEscolaresDTO> dadosEscolaresDTOList = new ArrayList<>();
        List<ExperienciasDTO> experienciasDTOList = new ArrayList<>();
        if(candidatoCompletoCreateDTO.getDadosEscolares()!=null&&!candidatoCompletoCreateDTO.getDadosEscolares().isEmpty()){
            for(DadosEscolaresCreateDTO dadosEscolares: candidatoCompletoCreateDTO.getDadosEscolares()){
                dadosEscolaresDTOList.add(dadosEscolaresService.create(candidatoCriado.getIdCandidato(),dadosEscolares));
            }
        }
        if(candidatoCompletoCreateDTO.getExperiencias()!=null&&!candidatoCompletoCreateDTO.getExperiencias().isEmpty()) {
            for (ExperienciasCreateDTO experiencia : candidatoCompletoCreateDTO.getExperiencias()) {
                experienciasDTOList.add(experienciasService.create(candidatoCriado.getIdCandidato(), experiencia));
            }
        }
        CandidatoCompletoDTO candidatoCompletoDTO = objectMapper.convertValue(candidatoCriado,CandidatoCompletoDTO.class);
        candidatoCompletoDTO.setDadosEscolares(dadosEscolaresDTOList);
        candidatoCompletoDTO.setExperiencias(experienciasDTOList);

        return candidatoCompletoDTO;
    }

    @Transactional
    public CandidatoCompletoDTO update(Integer idCandidato, CandidatoCompletoCreateDTO candidatoCompletoCreateDTO) throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDTO = objectMapper.convertValue(candidatoCompletoCreateDTO,CandidatoCreateDTO.class);

        //hotfix: mesmo como transação, não está acontecendo o rollback em caso de excessão nos dados e experiências então o teste foi feito novamente aqui como horfix
        if(candidatoCompletoCreateDTO.getDadosEscolares()!=null&&!candidatoCompletoCreateDTO.getDadosEscolares().isEmpty()) {
            for (DadosEscolaresCreateDTO dadosEscolares : candidatoCompletoCreateDTO.getDadosEscolares()) {
                Utils.validacaoDeTempo(dadosEscolares.getDataInicio().atStartOfDay(),dadosEscolares.getDataFim().atStartOfDay(),0L,"Data final deve ser após a Data de início");
            }
        }
        if(candidatoCompletoCreateDTO.getExperiencias()!=null&&!candidatoCompletoCreateDTO.getExperiencias().isEmpty()) {
            for (ExperienciasCreateDTO experiencia : candidatoCompletoCreateDTO.getExperiencias()) {
                Utils.validacaoDeTempo(experiencia.getDataInicio().atStartOfDay(),experiencia.getDataFim().atStartOfDay(),0L,"Data final deve ser após a Data de início");
            }
        }

        CandidatoDTO candidatoAtualizado = candidatoService.update(idCandidato, candidatoCreateDTO);
        List<DadosEscolaresDTO> dadosEscolaresList = dadosEscolaresService.findByIdCandidato(idCandidato);
        List<ExperienciasDTO> experienciasList = experienciasService.findByIdCandidato(idCandidato);

        //primeiro e feita uma remocao de todos os dados e experiencias, pois e mais performatico do que verificar de 1 em 1 se precisa ser atualizada
        if(candidatoCompletoCreateDTO.getDadosEscolares()!=null&&!candidatoCompletoCreateDTO.getDadosEscolares().isEmpty()){
            for (DadosEscolaresDTO dadosEscolaresDTO : dadosEscolaresList) {
                dadosEscolaresService.delete(dadosEscolaresDTO.getIdDadosEscolares());
            }
        }
        if(candidatoCompletoCreateDTO.getExperiencias()!=null&&!candidatoCompletoCreateDTO.getExperiencias().isEmpty()) {
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
