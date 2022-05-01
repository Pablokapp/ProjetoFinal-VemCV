package com.dbc.vemcv.service;

import com.dbc.vemcv.dto.curriculo.CurriculoDTO;
import com.dbc.vemcv.entity.CandidatoEntity;
import com.dbc.vemcv.entity.CurriculoEntity;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.repository.CandidatoRepository;
import com.dbc.vemcv.repository.CurriculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurriculoServiceTest {

    @InjectMocks
    private CurriculoService curriculoService;

    @Mock
    private CurriculoRepository curriculoRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private MockHttpServletRequest mockRequest;

    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();


    private CurriculoEntity curriculoEntity;
    private CandidatoEntity candidatoEntity;
    private CurriculoDTO curriculoDTO;
    private MultipartFile file;

    @Before
    public void BeforeEach() {

        ReflectionTestUtils.setField(curriculoService, "objectMapper", objectMapper);
        mockRequest = new MockHttpServletRequest();
        mockRequest.setContextPath("/download-curriculo/");
        candidatoEntity = CandidatoEntity.builder()
                .idCandidato(1)
                .nome("nome")
                .cpf("123.456.789-09")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .logradouro("logradouro")
                .numero(111)
                .bairro("bairro")
                .cidade("cidade")
                .cargo("cargo")
                .senioridade("senioridade")
                .build();

        curriculoEntity = CurriculoEntity.builder()
                .idCurriculo(1)
                .fileName("test.pdf")
                .fileType("application/pdf")
                .size(100)
                .data(new byte[100])
                .candidato(candidatoEntity)
                .build();

        file = new MultipartFile() {

            @Override
            public String getName() { return curriculoEntity.getFileName(); }

            @Override
            public String getOriginalFilename() { return curriculoEntity.getFileName(); }

            @Override
            public String getContentType() { return curriculoEntity.getFileType();}

            @Override
            public boolean isEmpty() { return false; }

            @Override
            public long getSize() { return curriculoEntity.getSize(); }

            @Override
            public byte[] getBytes() throws IOException { return new byte[0]; }

            @Override
            public InputStream getInputStream() throws IOException { return null; }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException { } };

        curriculoDTO = CurriculoDTO.builder().build();
        BeanUtils.copyProperties(curriculoEntity, curriculoDTO);
        curriculoDTO.setIdCurriculo(curriculoEntity.getIdCurriculo());
    }

    @Test
    @DisplayName("deve fazer o upload do curriculo")
    public void uploadCurriculoCandidato() {

        ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attrs);

        try {
            when(curriculoRepository.save(any(CurriculoEntity.class))).thenReturn(curriculoEntity);
            when(candidatoRepository.findById(any(Integer.class))).thenReturn(Optional.of(candidatoEntity));
            when(objectMapper.convertValue(curriculoEntity, CurriculoDTO.class)).thenReturn(curriculoDTO);
            curriculoService.uploadCurriculoCandidato(file, candidatoEntity.getIdCandidato());

            verify(curriculoRepository).save(any(CurriculoEntity.class));
            verify(curriculoRepository, times(1)).save(any(CurriculoEntity.class));
            assertNotNull(curriculoDTO);
            assertEquals(file.getName(),curriculoDTO.getFileName());
            assertEquals(file.getContentType(),curriculoDTO.getFileType());
            assertEquals(file.getSize(),curriculoDTO.getSize());

        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadCurriculo() {

        when(curriculoRepository.getCurriculoByIdCandidato(any(Integer.class))).thenReturn(curriculoEntity);
        curriculoService.downloadCurriculo(candidatoEntity.getIdCandidato());

        verify(curriculoRepository).getCurriculoByIdCandidato(any(Integer.class));
    }
}