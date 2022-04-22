package com.dbc.vemcv.service;


import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.DadosEscolaresEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.dbc.vemcv.repository.DadosEscolaresRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DadosEscolaresService {

    private final DadosEscolaresRepository dadosEscolaresRepository;
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    public List<DadosEscolaresDTO> list() {
        return dadosEscolaresRepository.findAll()
                .stream()
                .map(dadosEscolares -> objectMapper.convertValue(dadosEscolares, DadosEscolaresDTO.class))
                .collect(Collectors.toList());
    }

    public DadosEscolaresDTO create(Integer idCandidato, DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        DadosEscolaresEntity entity = objectMapper.convertValue(dadosEscolaresCreateDTO, DadosEscolaresEntity.class);
        CandidatoEntity candidato = candidatoRepository.findById(idCandidato). orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        entity.setCandidato(candidato);
        DadosEscolaresEntity save = dadosEscolaresRepository.save(entity);
        return objectMapper.convertValue(save, DadosEscolaresDTO.class);
    }

    public DadosEscolaresDTO update(Integer idDadosEscolares, DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        DadosEscolaresEntity entity = dadosEscolaresRepository.findById(idDadosEscolares).orElseThrow(() -> new RegraDeNegocioException("Dado escolar não encontrado"));
        entity.setInstituicao(dadosEscolaresCreateDTO.getInstituicao());
        entity.setDataInicio(dadosEscolaresCreateDTO.getDataInicio());
        entity.setDataFim(dadosEscolaresCreateDTO.getDataFim());
        entity.setDescricao(dadosEscolaresCreateDTO.getDescricao());
        DadosEscolaresEntity update = dadosEscolaresRepository.save(entity);
        return objectMapper.convertValue(update, DadosEscolaresDTO.class);
    }

    public void delete(Integer idDadosEscolares) throws RegraDeNegocioException {
        DadosEscolaresEntity dadosEscolares = dadosEscolaresRepository.findById(idDadosEscolares).orElseThrow(() -> new RegraDeNegocioException("Dado Escolar não encontrado"));
        dadosEscolaresRepository.delete(dadosEscolares);
    }

    public List<DadosEscolaresDTO> findByIdCandidato(Integer idCandidato){
        return dadosEscolaresRepository.findByIdCandidato(idCandidato).stream()
                .map(de->objectMapper.convertValue(de,DadosEscolaresDTO.class))
                .collect(Collectors.toList());
    }

}