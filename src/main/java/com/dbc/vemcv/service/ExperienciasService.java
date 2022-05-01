package com.dbc.vemcv.service;


import com.dbc.vemcv.common.Utils;
import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.ExperienciasEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.ExperienciasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienciasService {

    private final ExperienciasRepository experienciasRepository;
    private final CandidatoService candidatoService;
    private final ObjectMapper objectMapper;

    @Transactional
    public List<ExperienciasDTO> list(Integer idCandidato) {
        if(!(idCandidato == null)) {
            return findByIdCandidato(idCandidato);
        }
        return experienciasRepository.findAll()
                .stream()
                .map(experiencia -> objectMapper.convertValue(experiencia, ExperienciasDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public ExperienciasDTO create(Integer idCandidato, ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        //validacao das datas
        Utils.validacaoDeTempo(experienciasCreateDTO.getDataInicio().atStartOfDay(),experienciasCreateDTO.getDataFim().atStartOfDay(),0L,"Data final deve ser após a Data de início");

        ExperienciasEntity entity = objectMapper.convertValue(experienciasCreateDTO, ExperienciasEntity.class);
        CandidatoEntity candidato = candidatoService.findById(idCandidato);
        entity.setCandidato(candidato);
        ExperienciasEntity save = experienciasRepository.save(entity);
        return objectMapper.convertValue(save, ExperienciasDTO.class);
    }

    @Transactional
    public ExperienciasDTO update(Integer idExperiencia, ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        //validacao das datas
        Utils.validacaoDeTempo(experienciasCreateDTO.getDataInicio().atStartOfDay(),experienciasCreateDTO.getDataFim().atStartOfDay(),0L,"Data final deve ser após a Data de início");

        ExperienciasEntity entity = this.findById(idExperiencia);
        BeanUtils.copyProperties(experienciasCreateDTO, entity);
        ExperienciasEntity update = experienciasRepository.save(entity);
        return objectMapper.convertValue(update, ExperienciasDTO.class);
    }

    public void delete(Integer idExperiencia) throws RegraDeNegocioException {
        ExperienciasEntity experiencias = this.findById(idExperiencia);
        experienciasRepository.delete(experiencias);
    }

    public List<ExperienciasDTO> findByIdCandidato(Integer idCandidato){
        return experienciasRepository.findByIdCandidato(idCandidato).stream()
                .map(e->objectMapper.convertValue(e,ExperienciasDTO.class))
                .collect(Collectors.toList());
    }

    private ExperienciasEntity findById(Integer idExperiencia) throws RegraDeNegocioException {
        return experienciasRepository.findById(idExperiencia).orElseThrow(() -> new RegraDeNegocioException("Experiência não encontrada"));
    }

}