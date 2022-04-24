package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.curriculo.CurriculoDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.CurriculoEntity;
import com.dbc.vemcv.exceptions.FileStorageException;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.dbc.vemcv.repository.CurriculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;
    private final CandidatoRepository candidatoRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public CurriculoDTO uploadCurriculoCandidato(MultipartFile curriculo, Integer idCandidato) throws RegraDeNegocioException {

        CandidatoEntity candidato = candidatoRepository.findById(idCandidato)
                .orElseThrow(() -> new RegraDeNegocioException("Candidato não encontrado"));

        String nomeDoArquivo = StringUtils.cleanPath(new BCryptPasswordEncoder()
                .encode(idCandidato + "_" + candidato.getNome()) + ".pdf");

        if(!curriculo.getContentType().equalsIgnoreCase("application/pdf")){
            throw new RegraDeNegocioException("O arquivo não está no formato pdf");
        }

        try {
            if (nomeDoArquivo.contains("..")) {
                throw new FileStorageException("O arquivo tem um nome inválido" + nomeDoArquivo); }

            CurriculoEntity curriculoCandidato = curriculoRepository.getCurriculoByIdCandidato(idCandidato);

            if (curriculoCandidato == null) {
                curriculoCandidato = new CurriculoEntity(); }

            curriculoCandidato.setSize(curriculo.getSize());
            curriculoCandidato.setFileName(nomeDoArquivo);
            curriculoCandidato.setFileType(curriculo.getContentType());
            curriculoCandidato.setData(curriculo.getBytes());
            curriculoCandidato.setCandidato(candidato);

            CurriculoEntity save = curriculoRepository.save(curriculoCandidato);
            CurriculoDTO curriculoDTO = objectMapper.convertValue(save, CurriculoDTO.class);

            curriculoDTO.setFileDownloadUri(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/download-curriculo/")
                            .path(save.getIdCurriculo().toString())
                            .toUriString() );

            return curriculoDTO;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + nomeDoArquivo + ". Please try again!", ex); }
    }

    @Transactional
    public ResponseEntity<Resource> downloadCurriculo(Integer idCandidato) {
        CurriculoEntity curriculoCandidato = curriculoRepository.getCurriculoByIdCandidato(idCandidato);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(curriculoCandidato.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + curriculoCandidato.getFileName() + "\"")
                .body(new ByteArrayResource(curriculoCandidato.getData()));
    }

}