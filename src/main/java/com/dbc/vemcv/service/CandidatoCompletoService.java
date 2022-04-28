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
import org.springframework.web.multipart.MultipartFile;

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
        return candidatoCompletoCreateDTOToCandidatoCompletoDTO(null, candidatoCompletoCreateDTO);
    }

    public CandidatoCompletoDTO update(Integer idCandidato, CandidatoCompletoCreateDTO candidatoCompletoCreateDTO) throws RegraDeNegocioException {
        return candidatoCompletoCreateDTOToCandidatoCompletoDTO(idCandidato, candidatoCompletoCreateDTO);
    }

    private CandidatoCompletoDTO candidatoCompletoCreateDTOToCandidatoCompletoDTO(Integer idCandidato, CandidatoCompletoCreateDTO candidatoCompletoCreateDTO) throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDTO = objectMapper.convertValue(candidatoCompletoCreateDTO,CandidatoCreateDTO.class);
        CandidatoDTO candidatoDTO = objectMapper.convertValue(candidatoCompletoCreateDTO,CandidatoDTO.class);
        CandidatoCompletoDTO candidatoCompletoDTO = objectMapper.convertValue(candidatoDTO,CandidatoCompletoDTO.class);;
        CandidatoEntity candidatoEntity = objectMapper.convertValue(candidatoDTO,CandidatoEntity.class);

        if(idCandidato != null){
            candidatoCompletoDTO.setDadosEscolares(dadosEscolaresService.update(candidatoEntity, candidatoCompletoCreateDTO.getDadosEscolares()));
            candidatoCompletoDTO.setExperiencias(experienciasService.update(candidatoEntity, candidatoCompletoCreateDTO.getExperiencias()));
            return candidatoCompletoDTO;
        }
        //saves
        candidatoEntity = objectMapper.convertValue((candidatoService.create(candidatoCreateDTO)), CandidatoEntity.class);
        candidatoCompletoDTO.setDadosEscolares(dadosEscolaresService.create(candidatoEntity, candidatoCompletoCreateDTO.getDadosEscolares()));
        candidatoCompletoDTO.setExperiencias(experienciasService.create(candidatoEntity, candidatoCompletoCreateDTO.getExperiencias()));
        return candidatoCompletoDTO;
    }





    }
