package com.dbc.vemcv.service;


import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.ExperienciasEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.dbc.vemcv.repository.ExperienciasRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienciasService {

    private final ExperienciasRepository experienciasRepository;
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    public List<ExperienciasDTO> list() {
        return experienciasRepository.findAll()
                .stream()
                .map(experiencia -> objectMapper.convertValue(experiencia, ExperienciasDTO.class))
                .collect(Collectors.toList());
    }

    public ExperienciasDTO create(Integer idCandidato, ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        ExperienciasEntity entity = objectMapper.convertValue(experienciasCreateDTO, ExperienciasEntity.class);
        CandidatoEntity candidato = candidatoRepository.findById(idCandidato). orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        entity.setCandidato(candidato);
        ExperienciasEntity save = experienciasRepository.save(entity);
        return objectMapper.convertValue(save, ExperienciasDTO.class);
    }

    public ExperienciasDTO update(Integer idExperiencia, ExperienciasCreateDTO experienciasCreateDTO) throws RegraDeNegocioException {
        ExperienciasEntity entity = experienciasRepository.findById(idExperiencia).orElseThrow(() -> new RegraDeNegocioException("Experiência não encontrada"));
        entity.setNomeEmpresa(experienciasCreateDTO.getNomeEmpresa());
        entity.setDataInicio(experienciasCreateDTO.getDataInicio());
        entity.setDataFim(experienciasCreateDTO.getDataFim());
        entity.setDescricao(experienciasCreateDTO.getDescricao());
        ExperienciasEntity update = experienciasRepository.save(entity);
        return objectMapper.convertValue(update, ExperienciasDTO.class);
    }

    public void delete(Integer idExperiencia) throws RegraDeNegocioException {
        ExperienciasEntity experiencias = experienciasRepository.findById(idExperiencia).orElseThrow(() -> new RegraDeNegocioException("Experiência não encontrada"));
        experienciasRepository.delete(experiencias);
    }

    public List<ExperienciasDTO> findByIdCandidato(Integer idCandidato){
        return experienciasRepository.findByIdCandidato(idCandidato).stream()
                .map(e->objectMapper.convertValue(e,ExperienciasDTO.class))
                .collect(Collectors.toList());
    }

}