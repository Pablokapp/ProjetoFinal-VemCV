package com.dbc.vemcv.service;


import com.dbc.vemcv.common.Utils;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.DadosEscolaresEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.dbc.vemcv.repository.DadosEscolaresRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosEscolaresService {

    private final DadosEscolaresRepository dadosEscolaresRepository;
    private final CandidatoService candidatoService;
    private final ObjectMapper objectMapper;

    @Transactional
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
        //validacao das datas
        Utils.validacaoDeTempo(dadosEscolaresCreateDTO.getDataInicio().atStartOfDay(),dadosEscolaresCreateDTO.getDataFim().atStartOfDay(),0L,"Datas não correspondem");

        DadosEscolaresEntity entity = objectMapper.convertValue(dadosEscolaresCreateDTO, DadosEscolaresEntity.class);
        CandidatoEntity candidato = candidatoService.findById(idCandidato);
        entity.setCandidato(candidato);
        return objectMapper.convertValue(dadosEscolaresRepository.save(entity), DadosEscolaresDTO.class);
    }

    public DadosEscolaresDTO update(Integer idDadosEscolares, DadosEscolaresCreateDTO dadosEscolaresCreateDTO) throws RegraDeNegocioException {
        //validacao das datas
        Utils.validacaoDeTempo(dadosEscolaresCreateDTO.getDataInicio().atStartOfDay(),dadosEscolaresCreateDTO.getDataFim().atStartOfDay(),0L,"Datas não correspondem");

        DadosEscolaresEntity entity = this.findById(idDadosEscolares);
        BeanUtils.copyProperties(dadosEscolaresCreateDTO, entity);
        return objectMapper.convertValue(dadosEscolaresRepository.save(entity), DadosEscolaresDTO.class);
    }

    public void delete(Integer idDadosEscolares) throws RegraDeNegocioException {
        DadosEscolaresEntity dadosEscolares = this.findById(idDadosEscolares);
        dadosEscolaresRepository.delete(dadosEscolares);
    }

    public List<DadosEscolaresDTO> findByIdCandidato(Integer idCandidato){
        return dadosEscolaresRepository.findByIdCandidato(idCandidato).stream()
                .map(de->objectMapper.convertValue(de,DadosEscolaresDTO.class))
                .collect(Collectors.toList());
    }

    private DadosEscolaresEntity findById(Integer idDadosEscolares) throws RegraDeNegocioException {
        return dadosEscolaresRepository.findById(idDadosEscolares).orElseThrow(() -> new RegraDeNegocioException("Dado Escolar não encontrado"));
    }

}