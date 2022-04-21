package com.dbc.vemcv.service;


import com.dbc.vemcv.dto.*;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CandidatoService {


    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;


    public List<CandidatoDTO> list(Integer idCandidato) throws RegraDeNegocioException {
        List<CandidatoDTO>  candidatoByID = new ArrayList<>();
        if (idCandidato == null) {
            return candidatoRepository.findAll()
                    .stream()
                    .map(candidato -> objectMapper.convertValue(candidato, CandidatoDTO.class))
                    .collect(Collectors.toList());
        }
        CandidatoEntity candidatoEntity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        candidatoByID.add(objectMapper.convertValue(candidatoEntity, CandidatoDTO.class));
        return candidatoByID;
    }

    public CandidatoDTO create(CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        if (candidatoRepository.existsByCpf(candidatoCreateDTO.getCpf())) {
            throw new RegraDeNegocioException("CPF já cadastrado");
        }
        CandidatoEntity entity = objectMapper.convertValue(candidatoCreateDTO, CandidatoEntity.class);
        CandidatoEntity save = candidatoRepository.save(entity);
        return objectMapper.convertValue(save, CandidatoDTO.class);
    }

    public CandidatoDTO update(Integer idCandidato, CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        CandidatoEntity entity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        entity.setNome(candidatoCreateDTO.getNome());
        if (!candidatoCreateDTO.getCpf().equals(entity.getCpf())) {
            if (candidatoRepository.existsByCpf(candidatoCreateDTO.getCpf())) {
                throw new RegraDeNegocioException("CPF já cadastrado");
            }
            entity.setCpf(candidatoCreateDTO.getCpf());
        }
        entity.setLogradouro(candidatoCreateDTO.getLogradouro());
        entity.setComplemento(candidatoCreateDTO.getComplemento());
        entity.setDataNascimento(candidatoCreateDTO.getDataNascimento());
        entity.setNumero(candidatoCreateDTO.getNumero());
        entity.setTelefone(candidatoCreateDTO.getTelefone());
        entity.setSenioridade(candidatoCreateDTO.getSenioridade());
        entity.setCargo(candidatoCreateDTO.getCargo());
        CandidatoEntity update = candidatoRepository.save(entity);
        return objectMapper.convertValue(update, CandidatoDTO.class);
    }

    public void delete(Integer idCandidato) throws RegraDeNegocioException {
        CandidatoEntity candidato = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        candidatoRepository.delete(candidato);
    }

    public List<CandidatoDadosExperienciasDTO> listCandidatosDadosExperiencias(Integer idCandidato) throws RegraDeNegocioException {
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
    }



    public CandidatoDadosExperienciasDTO setCandidatoDadosExperienciasDTO(CandidatoEntity candidato) {
        CandidatoDadosExperienciasDTO candidatoDadosExperienciasDTO = new CandidatoDadosExperienciasDTO();
        candidatoDadosExperienciasDTO.setCandidato(objectMapper.convertValue(candidato, CandidatoDTO.class));
        candidatoDadosExperienciasDTO.setDadosEscolares(
                candidato.getDadosEscolares()
                        .stream()
                        .map(dadoEscolar -> objectMapper.convertValue(dadoEscolar, DadosEscolaresDTO.class))
                        .collect(Collectors.toList())
        );
        candidatoDadosExperienciasDTO.setExperiencias(
                candidato.getExperiencias()
                        .stream()
                        .map(experiencia -> objectMapper.convertValue(experiencia, ExperienciasDTO.class))
                        .collect(Collectors.toList())
        );
        return candidatoDadosExperienciasDTO;
    }




}