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
import com.dbc.vemcv.entity.DadosEscolaresEntity;
import com.dbc.vemcv.entity.ExperienciasEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatoCompletoService {
    private final CandidatoService candidatoService;
    private final DadosEscolaresService dadosEscolaresService;
    private final ExperienciasService experienciasService;

    private final ObjectMapper objectMapper;

    /*public CandidatoCompletoDTO create(CandidatoCompletoCreateDTO candidatoCompletoCreateDTO) throws RegraDeNegocioException {
        CandidatoEntity candidato = objectMapper.convertValue(candidatoCompletoCreateDTO,CandidatoEntity.class);
        candidato.setDadosEscolares(new HashSet<>());
        candidato.setExperiencias(new HashSet<>());
        for(DadosEscolaresCreateDTO dadosEscolares: candidatoCompletoCreateDTO.getDadosEscolares()){
            DadosEscolaresEntity dadosEscolaresEntity = objectMapper.convertValue(dadosEscolares, DadosEscolaresEntity.class);
            dadosEscolaresEntity.setCandidato(candidato);
            candidato.getDadosEscolares().add(dadosEscolaresEntity);
        }
        for(ExperienciasCreateDTO experiencias: candidatoCompletoCreateDTO.getExperiencias()){
            ExperienciasEntity experienciasEntity = objectMapper.convertValue(experiencias, ExperienciasEntity.class);
            experienciasEntity.setCandidato(candidato);
            candidato.getExperiencias().add(experienciasEntity);
        }
        CandidatoEntity candidatoSalvo = candidatoService.save(candidato);

        CandidatoCompletoDTO candidatoCompletoDTO = objectMapper.convertValue(candidatoSalvo,CandidatoCompletoDTO.class);
        candidatoCompletoDTO.setDadosEscolares(new ArrayList<>());
        candidatoCompletoDTO.setExperiencias(new ArrayList<>());
        for(DadosEscolaresEntity dadosEscolares: candidatoSalvo.getDadosEscolares()){
            candidatoCompletoDTO.getDadosEscolares().add(objectMapper.convertValue(dadosEscolares, DadosEscolaresDTO.class));
        }
        for(ExperienciasEntity experiencias: candidatoSalvo.getExperiencias()){
            candidatoCompletoDTO.getExperiencias().add(objectMapper.convertValue(experiencias, ExperienciasDTO.class));
        }

        return candidatoCompletoDTO;
    }*/

    public CandidatoCompletoDTO create(CandidatoCompletoCreateDTO candidatoCompletoCreateDTO) throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDTO = objectMapper.convertValue(candidatoCompletoCreateDTO,CandidatoCreateDTO.class);

        //saves
        CandidatoDTO candidatoCriado = candidatoService.create(candidatoCreateDTO);
        List<DadosEscolaresDTO> dadosEscolaresDTOList = new ArrayList<>();
        List<ExperienciasDTO> experienciasDTOList = new ArrayList<>();
        for(DadosEscolaresCreateDTO dadosEscolares: candidatoCompletoCreateDTO.getDadosEscolares()){
            dadosEscolaresDTOList.add(dadosEscolaresService.create(candidatoCriado.getIdCandidato(),dadosEscolares));
        }
        for(ExperienciasCreateDTO experiencia:candidatoCompletoCreateDTO.getExperiencias()){
            experienciasDTOList.add(experienciasService.create(candidatoCriado.getIdCandidato(),experiencia));
        }

        CandidatoCompletoDTO candidatoCompletoDTO = objectMapper.convertValue(candidatoCriado,CandidatoCompletoDTO.class);
        candidatoCompletoDTO.setDadosEscolares(dadosEscolaresDTOList);
        candidatoCompletoDTO.setExperiencias(experienciasDTOList);

        return candidatoCompletoDTO;
    }

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

    /*public List<CandidatoDadosExperienciasDTO> listCandidatosDadosExperiencias(Integer idCandidato) throws RegraDeNegocioException {
        List<CandidatoDadosExperienciasDTO> candidatoById = new ArrayList<>();
        if(idCandidato == null) {
            return candidatoRepository.findAll()
                    .stream()
                    .map(this::setCandidatoDadosExperienciasDTO)
                    .collect(Collectors.toList());
        }
        CandidatoEntity candidatoEntity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        candidatoById.add(setCandidatoDadosExperienciasDTO(candidatoEntity));
        return candidatoById;
    }*/
}
