package com.banco.controller;

import com.banco.domain.Transferencia;
import com.banco.service.BancoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/banco/transfer")
@Tag(name = "banco-api" )
public class BancoController {
    private BancoService bancoService;

    @Autowired
    public BancoController(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @Operation(summary = "Obter uma lista de tranferencias", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busaca por ID com sucesso"),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar busca de dados sem passar o token ou passando token errado"),
    })
    @GetMapping("/all")
    public List < Transferencia> listTransfer(){
        return bancoService.findall();
    }

    @Operation(summary = "Obter uma lista de transferencias por Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busaca por ID com sucesso"),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar busca de dados sem passar o token ou passando token errado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca de dados passando letra ou numero de ID inexistente no banco")
    })
    @GetMapping("/findId")
    public List<Transferencia> findById(@Valid @RequestParam("id") String contaId) {
        return bancoService.getTransferenciasByIdConta(contaId);
    }

    @Operation(summary = "Obter uma lista de transferencias por data", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busaca por data com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao passar a data fora do padrão \"dd-MM-yyyy HH:mm:ss\""),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar busca de dados sem passar o token ou passando token errado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca retornando datas não encontradas no banco"),
    })
    @GetMapping("/date")
    public List<Transferencia> buscarTransferenciasPorData(
            @Valid @RequestParam(required = true) @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataFim) {
        return bancoService.buscarTransferenciasPorData(dataInicio, dataFim);
    }
    @Operation(summary = "Obter uma lista de transferencias por tipo", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busaca por tipo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao chamar o endpoint sem o parametro \"tipo\" requerido."),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar busca de dados sem passar o token ou passando token errado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca retornando um tipo não encontrado no banco"),
    })
    @GetMapping("/tipo")
    public List<Transferencia> findByTipo(@Valid @RequestParam String tipo) {
        return bancoService.findByTipo(tipo);
    }

    @Operation(summary = "Obter uma lista de transferencias por nome", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busaca por nome com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao chamar o endpoint sem o parametro  \"name\" requerido."),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar busca de dados sem passar o token ou passando token errado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca retornando um nome não encontrado no banco"),
    })
    @GetMapping("/name")
    public List <Transferencia> nomeOperadorTransacao(@Valid @RequestParam("name") String nomeOperador){
        return bancoService.nomeOperadorTransacao(nomeOperador);
    }

    @Operation(summary = "Obter uma lista de transferencias por filtros opcionais", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busaca por nome com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao passar a data fora do padrão \"dd-MM-yyyy HH:mm:ss\""),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar busca de dados sem passar o token ou passando token errado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca retornando um nome não encontrado no banco"),
    })
    @GetMapping("/filters")
    public List<Transferencia> obterTransferencias(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataFim,
            @RequestParam(required = false) String nomeOperador) {
        return bancoService.buscarTransferenciasPorNomeEData(nomeOperador,dataInicio, dataFim );
    }

}

