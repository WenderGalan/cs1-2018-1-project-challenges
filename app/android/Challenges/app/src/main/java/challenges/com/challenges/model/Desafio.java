package challenges.com.challenges.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Desafio implements Serializable {


    private String titulo;
    private String recompensa;
    private String habilidade;
    private int pontos = 0;
    private String observacoes;
    private String responsavel;
    private String crianca;
    private String frequencia;
    private int repeticoes = 1;
    private String hora;
    private String data;




    public Desafio() { }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getRecompensa() {
        return recompensa;
    }
    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }
    public String getHabilidade() {
        return habilidade;
    }
    public void setHabilidade(String habilidade) {
        this.habilidade = habilidade;
    }
    public int getPontos() {
        return pontos;
    }
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public String getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    public String getCrianca() {
        return crianca;
    }
    public void setCrianca(String crianca) {
        this.crianca = crianca;
    }
    public String getFrequencia() {
        return frequencia;
    }
    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }
    public int getRepeticoes() {
        return repeticoes;
    }
    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public Map construirHash(){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        if (getTitulo() != null) hashMap.put("titulo", getTitulo());
        if (getRecompensa() != null) hashMap.put("recompensa", getRecompensa());
        if (getHabilidade() != null) hashMap.put("habilidade", getHabilidade());
        if (getPontos() != 0) hashMap.put("pontos", getPontos());
        if (getObservacoes() != null) hashMap.put("observacoes", getObservacoes());
        if (getCrianca() != null) hashMap.put("crianca", getCrianca());
        if (getResponsavel() != null) hashMap.put("responsavel", getResponsavel());
        if (getFrequencia() != null) hashMap.put("frequencia", getFrequencia());
        if (getRepeticoes() >= 1) hashMap.put("repeticoes", getRepeticoes());
        if (getHora() != null) hashMap.put("hora", getHora());
        if (getData() != null) hashMap.put("data", getData());
        return hashMap;
    }

}
