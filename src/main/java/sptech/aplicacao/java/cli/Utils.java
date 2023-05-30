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

    public static double formatarRam(String memoriaString) {
        int indiceEspaco = memoriaString.indexOf(" ");
        memoriaString = memoriaString.replace(",", ".");

        if (indiceEspaco == -1 || indiceEspaco == 0 || indiceEspaco == memoriaString.length() - 1) {
            throw new IllegalArgumentException("Formato inválido para a string de memória.");
        }

        // Extrai a parte numérica e a unidade da string
        double memoriaUtilizada = Double.parseDouble(memoriaString.substring(0, indiceEspaco));
        String unidade = memoriaString.substring(indiceEspaco + 1);

        // Verifica a unidade e realiza o cálculo correspondente
        double memoriaTotal;
        if (unidade.equalsIgnoreCase("MiB")) {
            memoriaTotal = 1024 * 8; // Total de memória em MiB
        } else if (unidade.equalsIgnoreCase("GiB")) {
            memoriaTotal = 8.0; // Total de memória em GiB
        } else {
            throw new IllegalArgumentException("Unidade de memória inválida. Utilize 'MiB' ou 'GiB'.");
        }

        return (memoriaUtilizada / memoriaTotal) * 100;
    }

    public static double formatarRamMibEmGib(String armazenamentoString) {
        int indiceEspaco = armazenamentoString.indexOf(" ");
        armazenamentoString = armazenamentoString.replace(",", ".");

        if (indiceEspaco == -1 || indiceEspaco == 0 || indiceEspaco == armazenamentoString.length() - 1) {
            throw new IllegalArgumentException("Formato inválido para a string de armazenamento.");
        }

        double armazenamentoUtilizado = Double.parseDouble(armazenamentoString.substring(0, indiceEspaco));
        String unidade = armazenamentoString.substring(indiceEspaco + 1);

        double armazenamentoConvertido;
        if (unidade.equalsIgnoreCase("MiB")) {
            armazenamentoConvertido = armazenamentoUtilizado / 1024;
        } else if (unidade.equalsIgnoreCase("GiB")) {
            armazenamentoConvertido = armazenamentoUtilizado;
        } else {
            throw new IllegalArgumentException("Unidade de armazenamento inválida. Utilize 'MiB' ou 'GiB'.");
        }

        return armazenamentoConvertido;
    }

    public static double formatarArmazenamento(String armazenamentoString) {
        int indiceEspaco = armazenamentoString.indexOf(" ");
        armazenamentoString = armazenamentoString.replace(",", ".");

        if (indiceEspaco == -1 || indiceEspaco == 0 || indiceEspaco == armazenamentoString.length() - 1) {
            throw new IllegalArgumentException("Formato inválido para a string de armazenamento.");
        }

        // Extrai a parte numérica e a unidade da string
        double armazenamentoUtilizado = Double.parseDouble(armazenamentoString.substring(0, indiceEspaco));
        String unidade = armazenamentoString.substring(indiceEspaco + 1);

        // Verifica a unidade e realiza o cálculo correspondente
        double armazenamentoTotal;
        if (unidade.equalsIgnoreCase("MiB")) {
            armazenamentoTotal = 30 * 1024; // Total de armazenamento em MiB (30 GiB)
        } else if (unidade.equalsIgnoreCase("GiB")) {
            armazenamentoTotal = 30; // Total de armazenamento em GiB
        } else {
            throw new IllegalArgumentException("Unidade de armazenamento inválida. Utilize 'MiB' ou 'GiB'.");
        }

        return (armazenamentoUtilizado / armazenamentoTotal) * 100;
    }
}
