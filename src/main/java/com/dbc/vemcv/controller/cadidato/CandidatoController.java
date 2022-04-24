package com.dbc.vemcv.controller.cadidato;

import com.dbc.vemcv.dto.candidato.*;
import com.dbc.vemcv.dto.dadosescolares.DadosEscolaresCreateDTO;
import com.dbc.vemcv.dto.experiencias.ExperienciasCreateDTO;
import com.dbc.vemcv.exceptions.RegraDeNegocioException;
import com.dbc.vemcv.service.CandidatoService;
import com.dbc.vemcv.service.DadosEscolaresService;
import com.dbc.vemcv.service.ExperienciasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/candidato")
@Validated
@RequiredArgsConstructor
public class CandidatoController implements CandidatoAPI {


    private final CandidatoService candidatoService;
    private final DadosEscolaresService dadosEscolaresService;
    private final ExperienciasService experienciasService;


    @GetMapping
    public ResponseEntity<List<CandidatoDTO>> list(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.list(idCandidato));
    }


    @GetMapping("/dados-completos")
    public ResponseEntity<List<CandidatoDadosExperienciasDTO>> listCandidatosDadosExperiencias(@RequestParam(value = "idCandidato", required = false) Integer idCandidato) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.listCandidatosDadosExperiencias(idCandidato));
    }

    @PostMapping
    public ResponseEntity<CandidatoDTO> create(@RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.create(candidatoCreateDTO));
    }


    @PostMapping("/candidato-completo")
        public ResponseEntity<Integer> createCompleto(@RequestBody @Valid CandidatoCompletoPostDTO candidatoCompletoPostDTO) throws RegraDeNegocioException {
        CandidatoCreateDTO candidatoCreateDTO = new CandidatoCreateDTO();
        candidatoCreateDTO.setNome(candidatoCompletoPostDTO.getNome());
        candidatoCreateDTO.setCpf(candidatoCompletoPostDTO.getCpf());
        candidatoCreateDTO.setDataNascimento(candidatoCompletoPostDTO.getDataNascimento());
        candidatoCreateDTO.setLogradouro(candidatoCompletoPostDTO.getLogradouro());
        candidatoCreateDTO.setNumero(candidatoCompletoPostDTO.getNumero());
        candidatoCreateDTO.setBairro(candidatoCompletoPostDTO.getBairro());
        candidatoCreateDTO.setCidade(candidatoCompletoPostDTO.getCidade());
        candidatoCreateDTO.setTelefone(candidatoCompletoPostDTO.getTelefone());
        candidatoCreateDTO.setCargo(candidatoCompletoPostDTO.getCargo());
        candidatoCreateDTO.setSenioridade(candidatoCompletoPostDTO.getSenioridade());

        DadosEscolaresCreateDTO dadosEscolaresCreateDTO = new DadosEscolaresCreateDTO();
        dadosEscolaresCreateDTO.setInstituicao(candidatoCompletoPostDTO.getInstituicao());
        dadosEscolaresCreateDTO.setDataInicio(candidatoCompletoPostDTO.getDataInicioCurso());
        dadosEscolaresCreateDTO.setDataFim(candidatoCompletoPostDTO.getDataFimCurso());
        dadosEscolaresCreateDTO.setDescricao(candidatoCompletoPostDTO.getDescricaoDoCurso());

        ExperienciasCreateDTO experienciasCreateDTO = new ExperienciasCreateDTO();
        experienciasCreateDTO.setNomeEmpresa(candidatoCompletoPostDTO.getNomeEmpresa());
        experienciasCreateDTO.setDataInicio(candidatoCompletoPostDTO.getDataInicioExperiencia());
        experienciasCreateDTO.setDataFim(candidatoCompletoPostDTO.getDataFimExperiencia());
        experienciasCreateDTO.setDescricao(candidatoCompletoPostDTO.getDescricaoDoCargo());

        CandidatoDTO candidatoDTO = candidatoService.create(candidatoCreateDTO);

        dadosEscolaresService.create(candidatoDTO.getIdCandidato(), dadosEscolaresCreateDTO);
        experienciasService.create(candidatoDTO.getIdCandidato(), experienciasCreateDTO);




        candidatoService.listCandidatosDadosExperiencias(candidatoDTO.getIdCandidato());

        return ResponseEntity.ok(candidatoDTO.getIdCandidato());

    }


    @PutMapping
    public ResponseEntity<CandidatoDTO> update(@RequestParam Integer idCandidato, @RequestBody @Valid CandidatoCreateDTO candidatoCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.update(idCandidato, candidatoCreateDTO));
    }


    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Integer idCandidato) throws RegraDeNegocioException {
        candidatoService.delete(idCandidato);
        return ResponseEntity.ok("Candidato deletado com sucesso");
    }

    @GetMapping("/candidato-completo-formato-de-entrada")
    public ResponseEntity<CandidatoCompletoPostComIdDTO> getCandidatoCompletoFormatoNovo(@RequestParam("id-candidato") Integer idCandidato){
        CandidatoCompletoPostComIdDTO candidatoCompletoDTO = CandidatoCompletoPostComIdDTO.builder()
                .idCandidato(1)
                .cpf("12345678910")
                .nome("nome1")
                .dataNascimento(LocalDate.now().minusYears(1))
                .logradouro("logradouro1")
                .cidade("cidade1")
                .bairro("bairro1")
                .telefone("telefone1")
                .numero(1)
                .instituicao("instituicao1")
                .descricao("descricao1")
                .dataInicioCurso(LocalDate.now().minusYears(1))
                .dataFimCurso(LocalDate.now().minusDays(1))
                .nomeEmpresa("nomeEmpresa1")
                .cargo("cargo1")
                .dataInicioExperiencia(LocalDate.now().minusYears(1))
                .dataFimExperiencia(LocalDate.now().minusDays(1))
                .senioridade("senioridade1")
                .build();

        return ResponseEntity.ok(candidatoCompletoDTO);
    }

    @GetMapping("/get-paginado")
    public ResponseEntity<PaginaCandidatoDTO> listPaginado(@RequestParam(value = "idCandidato", required = false) Integer idCandidato,
                                                           @RequestParam(value = "pagina", required = false, defaultValue = "0") Integer pagina,
                                                           @RequestParam(value = "quantidadePorPagina", required = false, defaultValue = "10") Integer quantidadePorPagina) throws RegraDeNegocioException {
        return ResponseEntity.ok(candidatoService.listPaginado(idCandidato,pagina,quantidadePorPagina));
    }

}