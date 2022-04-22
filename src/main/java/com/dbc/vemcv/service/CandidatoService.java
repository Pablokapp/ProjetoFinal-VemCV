package com.dbc.vemcv.service;


import com.dbc.vemcv.dto.candidato.CandidatoCreateDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDTO;
import com.dbc.vemcv.dto.candidato.CandidatoDadosExperienciasDTO;
import com.dbc.vemcv.dto.candidato.PaginaCandidatoDTO;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
        CandidatoEntity candidatoAtual = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        BeanUtils.copyProperties(candidatoCreateDTO, candidatoAtual, "id", "cpf");

        if (!candidatoCreateDTO.getCpf().equals(candidatoAtual.getCpf())) {
            if (candidatoRepository.existsByCpf(candidatoCreateDTO.getCpf())) {
                throw new RegraDeNegocioException("CPF já cadastrado");
            }
            candidatoAtual.setCpf(candidatoCreateDTO.getCpf());
        }
        CandidatoDTO candidatoAtualizado = objectMapper.convertValue((candidatoRepository.save(candidatoAtual)), CandidatoDTO.class);
        return candidatoAtualizado;
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

    private CandidatoDadosExperienciasDTO setCandidatoDadosExperienciasDTO(CandidatoEntity candidato) {
        CandidatoDadosExperienciasDTO candidatoDadosExperienciasDTO = new CandidatoDadosExperienciasDTO();
        candidatoDadosExperienciasDTO.setCandidato(objectMapper.convertValue(candidato, CandidatoDTO.class));

        candidatoDadosExperienciasDTO.setDadosEscolares(candidato.getDadosEscolares()==null?new ArrayList<>():
                candidato.getDadosEscolares()
                        .stream()
                        .map(dadoEscolar -> objectMapper.convertValue(dadoEscolar, DadosEscolaresDTO.class))
                        .collect(Collectors.toList())
        );
        candidatoDadosExperienciasDTO.setExperiencias(candidato.getExperiencias()==null?new ArrayList<>():
                candidato.getExperiencias()
                        .stream()
                        .map(experiencia -> objectMapper.convertValue(experiencia, ExperienciasDTO.class))
                        .collect(Collectors.toList())
        );
        return candidatoDadosExperienciasDTO;
    }

    public PaginaCandidatoDTO listPaginado(Integer idCandidato, Integer pagina, Integer quantidadePorPagina) throws RegraDeNegocioException {
        List<CandidatoDTO>  candidatoByID = new ArrayList<>();
        Pageable pageable = PageRequest.of(pagina==null?0:pagina, quantidadePorPagina==null?10:quantidadePorPagina, Sort.by("idCandidato").ascending());
        if (idCandidato == null) {
            Page<CandidatoEntity> paginaCandidatos = candidatoRepository.findAll(pageable);
            return PaginaCandidatoDTO.builder()
                    .candidatos(paginaCandidatos.stream().map(c->objectMapper.convertValue(c,CandidatoDTO.class)).collect(Collectors.toList()))
                    .totalDeElementos(paginaCandidatos.getTotalElements())
                    .totalDePaginas(paginaCandidatos.getTotalPages())
                    .build();
        }
        CandidatoEntity candidatoEntity = candidatoRepository.findById(idCandidato).orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));
        candidatoByID.add(objectMapper.convertValue(candidatoEntity, CandidatoDTO.class));
        return PaginaCandidatoDTO.builder()
                .candidatos(candidatoByID)
                .totalDeElementos(1L)
                .totalDePaginas(1)
                .build();
    }

}