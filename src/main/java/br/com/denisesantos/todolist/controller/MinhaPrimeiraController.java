package br.com.denisesantos.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota/")
public class MinhaPrimeiraController {

    /*
     * Métodos de acesso do http
     * GET - Buscar uma informação
     * POST - Adicionar um dado/informação
     * PUT - Alterar um dado/informação
     * DELETE - Remover um dado
     * PATCH - Alterar somente uma parte do dado
     */

    @GetMapping("/")
    // Método de uma classe
    public String primeiraMensagem() {
        return "Funcionou";
    }
}
