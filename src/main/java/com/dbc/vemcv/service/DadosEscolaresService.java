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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DadosEscolaresService {

    private final DadosEscolaresRepository dadosEscolaresRepository;
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    public List<DadosEscolaresDTO> list(Integer idCandidato) {
        if(!(idCandidato == null)) {
            return findByIdCandidato(idCandidato);
        }
        return dadosEscolaresRepository.findAll()
                .stream()
                .map(dadosEscolares -> objectMapper.convertValue(dadosEscolares, DadosEscolaresDTO.class))
                .collect(Collectors.toList());
    }

    public DadosEscolaresDTO create(Integer idCandidato, DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        DadosEscolaresEntity entity = objectMapper.convertValue(dadosEscolaresCreateDTO, DadosEscolaresEntity.class);
        CandidatoEntity candidato = candidatoRepository.findById(idCandidato). orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        entity.setCandidato(candidato);
        return objectMapper.convertValue(dadosEscolaresRepository.save(entity), DadosEscolaresDTO.class);
    }

    public List<DadosEscolaresDTO> create(CandidatoEntity candidatoEntity, List<DadosEscolaresCreateDTO> dadosEscolaresCreateDTOS) throws RegraDeNegocioException {
        dadosEscolaresCreateDTOS.forEach(dadosEscolaresCreateDTO -> {
            DadosEscolaresEntity entity = objectMapper.convertValue(dadosEscolaresCreateDTO, DadosEscolaresEntity.class);
            entity.setCandidato(candidatoEntity);
            dadosEscolaresRepository.save(entity);
        });
        return findByIdCandidato(candidatoEntity.getIdCandidato());
    }

    public DadosEscolaresDTO update(Integer idDadosEscolares, DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        DadosEscolaresEntity entity = dadosEscolaresRepository.findById(idDadosEscolares).orElseThrow(() -> new RegraDeNegocioException("Dado escolar não encontrado"));
        BeanUtils.copyProperties(dadosEscolaresCreateDTO, entity);
        return objectMapper.convertValue(dadosEscolaresRepository.save(entity), DadosEscolaresDTO.class);
    }

    public List<DadosEscolaresDTO> update(CandidatoEntity candidatoEntity, List<DadosEscolaresCreateDTO> dadosEscolaresCreateDTOS) throws RegraDeNegocioException {
        candidatoEntity.getDadosEscolares().forEach(dadosEscolaresEntity -> {
            try {
                delete(dadosEscolaresEntity.getIdDadosEscolares());
            } catch (RegraDeNegocioException e) {
                e.printStackTrace();
            }
        });
        return create(candidatoEntity, dadosEscolaresCreateDTOS);
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