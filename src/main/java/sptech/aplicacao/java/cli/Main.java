/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sptech.aplicacao.java.cli;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.rede.RedeInterfaceGroup;
import static com.github.britooo.looca.api.util.Conversor.formatarBytes;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sptech.aplicacao.java.cli.slacks.Slack;

/**
 *
 * @author samuc
 */
public class Main {

    private static String idTotem;

    public static void setIdTotem(String id) {
        idTotem = id;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        JSONObject json = new JSONObject();

        // Gatilho para pegar quando ele encerrar o diacho do runTime
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                json.put("text", "%s - Programa foi encerrado no totem %s.".formatted(Utils.obterDataFormatada(), idTotem));
                try {
                    Slack.sendMessage(json);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("Encerrando programa");
            }
        });

        // Conexão Sql
        SqlServer conexao = new SqlServer();
        JdbcTemplate conNuvem = conexao.getConexaoDoBanco();

        // Conexão Mysql
        MySql conexaoLocal = new MySql();
        JdbcTemplate conLocal = conexaoLocal.getConexaoDoBanco();

        // Regras para Select 
        Boolean conectou = false;;
        Boolean selecionouTotem = false;

        Scanner leitor = new Scanner(System.in);

        String email = null;
        String senha = null;

        // Fazendo Login do usuário
        do {
            System.out.println("\nDigite seu email:");
            email = leitor.next();
            System.out.println("\nDigite sua senha:");
            senha = leitor.next();

            try {
                Usuario usuario = conNuvem.queryForObject("SELECT * FROM usuario WHERE email = ?  AND senha = ?",
                        new BeanPropertyRowMapper<>(Usuario.class), email, senha);
                System.out.println("\nLogin realizado! \nOlá %s :)".formatted(usuario.getNomeUsuario()));
                conectou = true;

            } catch (Exception e) {
                System.out.println("\nLogin inválido! Tente novamente");
            }
        } while (!conectou);

        // Selecionando Totem
        do {
            System.out.println("\nDigite ID do totem");
            final String idTotem = leitor.next();
            try {
                conNuvem.queryForObject("SELECT * FROM [dbo].[Totem] where idTotem = ?;",
                        new BeanPropertyRowMapper<>(Totem.class), idTotem);
                System.out.println("\nTotem selecionado!");
                selecionouTotem = true;
                setIdTotem(idTotem);
            } catch (Exception e) {
                System.out.println("\nID do totem inválido! Tente novamente");
            }
        } while (!selecionouTotem);

        Totem totem = conNuvem.queryForObject("SELECT * FROM [dbo].[Totem] where idTotem = ?;",
                new BeanPropertyRowMapper<>(Totem.class), idTotem);

        // Iniciando aplicação
        json.put("text", "%s - Programa foi iniciado no totem %s.".formatted(Utils.obterDataFormatada(), idTotem));
        conNuvem.update("update [dbo].[Totem] set ativo = 'true' where idTotem = ?;", idTotem);
        Slack.sendMessage(json);

        // Criando meu mano Looca
        Looca looca = new Looca();

        // pegando os dados
        Memoria memoria = looca.getMemoria();

        DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
        List<Disco> discos = grupoDeDiscos.getDiscos();
        Disco disco = discos.get(0);
        List<Volume> volumes = grupoDeDiscos.getVolumes();
        Volume volume = volumes.get(0);

        Processador processador = looca.getProcessador();

        RedeInterfaceGroup redes = looca.getRede().getGrupoDeInterfaces();
        List<RedeInterface> redeInterfaces = redes.getInterfaces();

        RedeInterface rede = redeInterfaces.get(0);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            Integer qtdAlertaCpu = 0;
            String id;

            {
                this.id = idTotem;
            }

            @Override
            public void run() {
                // RAM Falta Porcentagem
                Double ramEmUso = Totem.formatar(formatarBytes(memoria.getEmUso()));

                // Disco Falta Porcentagem // armazenamento usando
                Double volumeEmUso = Totem.formatar(formatarBytes(volume.getTotal())) - Totem.formatar(formatarBytes(volume.getDisponivel()));

                // Processador Já vem em porcentagem
                Double cpuEmUso = processador.getUso();

                long totalBytesRecebidos = redeInterfaces.stream()
                        .mapToLong(RedeInterface::getBytesRecebidos)
                        .sum();

                long totalBytesEnviados = redeInterfaces.stream()
                        .mapToLong(RedeInterface::getBytesEnviados)
                        .sum();

                // Rede 
                //SLACK
                try {

                    Double ramPorcentagem = ramEmUso * 100 / 8.0;
                    Double volumePorcentagem = volumeEmUso * 100 / 30.0;

                    System.out.println("recebido byte " + formatarBytes(totalBytesRecebidos) + " pacote " + formatarBytes(totalBytesEnviados));
                    System.out.println("Ram: %.2f porcentagem: %.2f , Disco: %.2f , CPU: %.2f".formatted(ramEmUso, ramPorcentagem, volumeEmUso, cpuEmUso));

                    if (ramPorcentagem > totem.getAlertaRam()) {
                        json.put("text", "%s - Totem %s - A porcentagem da RAM antigiu %.2f %%".formatted(LocalDateTime.now(), idTotem, ramPorcentagem));
                        Slack.sendMessage(json);
                    }
                    if (volumePorcentagem > totem.getAlertaDisco()) {
                        json.put("text", "%s - Totem %s - A porcentagem do volume do disco antigiu %.2f %%".formatted(LocalDateTime.now(), idTotem, volumePorcentagem));
                        Slack.sendMessage(json);
                    }

                    if (cpuEmUso > totem.getAlertaProcessador()) {
                        qtdAlertaCpu += 1;

                        if (qtdAlertaCpu == 5) {
                            json.put("text", "%s - Totem %s - Por 25s a porcentagem da cpu passou do limite de %d %%".formatted(Utils.obterDataFormatada(), idTotem, totem.getAlertaProcessador()));
                            Slack.sendMessage(json);
                            qtdAlertaCpu = 0;
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

                conNuvem.update("insert into [dbo].[DadoTotem] values (?, FORMAT(GETDATE(), 'HH:mm:ss') , ?, ?, ?)",
                        idTotem, ramEmUso, volumeEmUso, String.format("%.2f", cpuEmUso));
                
                // insert local
                
//                conNuvem.update("insert into [dbo].[teste] values (CONVERT(VARCHAR(19), GETDATE(), 120))");
//1
//                conNuvem.update("insert into [dbo].[DadoTotem] values (1, CONVERT(VARCHAR(19), GETDATE(), 120) , ?, ?, ?)",
//                        formatarBytes(memoria.getEmUso()), formatarBytes(volume.getDisponivel()), String.format("%.2f", processador.getUso()));
//
//                //2
//                conNuvem.update("insert into [dbo].[DadoTotem] values (2, CONVERT(VARCHAR(19), GETDATE(), 120) , ?, ?, ?)",
//                        formatarBytes(memoria.getEmUso() * 2), formatarBytes(volume.getDisponivel() * 2), String.format("%.2f", processador.getUso() * 2));
//
//                //3
//                conNuvem.update("insert into [dbo].[DadoTotem] values (3, CONVERT(VARCHAR(19), GETDATE(), 120) , ?, ?, ?)",
//                        formatarBytes(memoria.getEmUso() * 3), formatarBytes(volume.getDisponivel() * 3), String.format("%.2f", processador.getUso() * 3));
//
//                //4 
//                conNuvem.update("insert into [dbo].[DadoTotem] values (4, CONVERT(VARCHAR(19), GETDATE(), 120) , ?, ?, ?)",
//                        formatarBytes(memoria.getEmUso() * 4), formatarBytes(volume.getDisponivel() * 4), String.format("%.2f", processador.getUso() * 4));
//
//                //5
//                conNuvem.update("insert into [dbo].[DadoTotem] values (5, CONVERT(VARCHAR(19), GETDATE(), 120) , ?, ?, ?)",
//                        formatarBytes(memoria.getEmUso() * 5), formatarBytes(volume.getDisponivel() * 5), String.format("%.2f", processador.getUso() * 5));
//
//                //6
//                conNuvem.update("insert into [dbo].[DadoTotem] values (6, CONVERT(VARCHAR(19), GETDATE(), 120) , ?, ?, ?)",
//                        formatarBytes(memoria.getEmUso() * 6), formatarBytes(volume.getDisponivel() * 6), String.format("%.2f", processador.getUso() * 6));

//conLocal.update("insert into DadoTotem(dataHora, qtdRam, qtdTotalDisco, qtdProcessador, qtdFaltaDisco, qtdLeituraDisco, qtdPacoteEnviado,";
//                + " qtdPacoteRecebido) values (now() , ?, ?, ?, ?, ?, ?, ?)",
//                formatarBytes(memoria.getEmUso()), formatarBytes(disco.getTamanho()), String.format("%.2f", processador.getUso()),
//                formatarBytes(volume.getDisponivel()), formatarBytes(disco.getLeituras()), formatarBytes(rede.getBytesEnviados()), 0);
            }
        },
                0, 5000);
    }
}
