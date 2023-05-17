/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sptech.aplicacao.java.cli;

import com.github.britooo.looca.api.core.Looca;
import java.util.Scanner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author samuc
 */
public class Main {

    public static void main(String[] args) {

        // Conexão Sql
        SqlServer conexao = new SqlServer();
        JdbcTemplate conNuvem = conexao.getConexaoDoBanco();

        // Conexão Mysql
        MySql conexaoLocal = new MySql();
        JdbcTemplate conLocal = conexaoLocal.getConexaoDoBanco();

        Boolean conectou = false;

        Scanner leitor = new Scanner(System.in);

        String email = null;
        String senha = null;
        do {
            System.out.println("\nDigite seu email:");
            email = leitor.next();
            System.out.println("\nDigite sua senha:");
            senha = leitor.next();

            try {
                Usuario usuario = conNuvem.queryForObject("SELECT * FROM usuario WHERE email = ?  AND senha = ?",
                        new BeanPropertyRowMapper<>(Usuario.class), email, senha);
                System.out.println("\nLogin realizado! \nOlá %s".formatted(usuario.getNomeUsuario()));
                conectou = true;
            } catch (Exception e) {
                System.out.println("\nLogin inválido! Tente novamente");
            }
        } while (!conectou);

        Looca locoa = new Looca();

        // Falta processos
        // Dados capturados:
        // qtdRam
        // qtdTotalDisco
        // qtdProcessador
        // qtdFaltaDisco
        // qtdLeituraDisco
        // qtdPacoteEnviado
        // qtdPacoteRecebido
        System.out.println("");
    }
}
