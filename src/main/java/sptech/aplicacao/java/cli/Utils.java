/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sptech.aplicacao.java.cli;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author samuc
 */
public class Utils {
        public static String obterDataFormatada() {
        // Obter a data e hora atuais
        LocalDateTime agora = LocalDateTime.now();

        // Definir o padrão de formatação com localização em português do Brasil
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss", new Locale("pt", "BR"));

        // Formatar a data e hora atual
        String dataFormatada = agora.format(formato);

        // Retornar a data formatada
        return dataFormatada;
    }
}
