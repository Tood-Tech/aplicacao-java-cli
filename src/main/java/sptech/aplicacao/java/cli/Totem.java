/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sptech.aplicacao.java.cli;

/**
 *
 * @author samuc
 */
public class Totem {
    private String numeroSocial;
    private String processador;
    private Integer alertaProcessador;
    private String ram;
    private Integer alertaRam;
    private String disco;
    private Integer alertaDisco;
    private Integer rebootProcessador;
    private Integer rebootRam;
    
    public static Double formatar(String teste) {
        String numberString = teste.replaceAll("[^\\d,.]", "");
        Double number = Double.parseDouble(numberString.replace(",", "."));
        return number;
    }
  
    
    public String getNumeroSocial() {
        return numeroSocial;
    }

    public void setNumeroSocial(String numeroSocial) {
        this.numeroSocial = numeroSocial;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public Integer getAlertaProcessador() {
        return alertaProcessador;
    }

    public void setAlertaProcessador(Integer alertaProcessador) {
        this.alertaProcessador = alertaProcessador;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Integer getAlertaRam() {
        return alertaRam;
    }

    public void setAlertaRam(Integer alertaRam) {
        this.alertaRam = alertaRam;
    }

    public String getDisco() {
        return disco;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public Integer getAlertaDisco() {
        return alertaDisco;
    }

    public void setAlertaDisco(Integer alertaDisco) {
        this.alertaDisco = alertaDisco;
    }

    public Integer getRebootProcessador() {
        return rebootProcessador;
    }

    public void setRebootProcessador(Integer rebootProcessador) {
        this.rebootProcessador = rebootProcessador;
    }

    public Integer getRebootRam() {
        return rebootRam;
    }

    public void setRebootRam(Integer rebootRam) {
        this.rebootRam = rebootRam;
    }

    @Override
    public String toString() {
        return "Totem{" + "numeroSocial=" + numeroSocial + ", processador=" + processador + ", alertaProcessador=" + alertaProcessador + ", ram=" + ram + ", alertaRam=" + alertaRam + ", disco=" + disco + ", alertaDisco=" + alertaDisco + ", rebootProcessador=" + rebootProcessador + ", rebootRam=" + rebootRam + '}';
    }
}
