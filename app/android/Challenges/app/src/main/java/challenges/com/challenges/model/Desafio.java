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

    public Desafio() {
    }
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

    public Map construirHash(){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        if (getTitulo() != null) hashMap.put("titulo", getTitulo());
        if (getRecompensa() != null) hashMap.put("recompensa", getRecompensa());
        if (getHabilidade() != null) hashMap.put("habilidade", getHabilidade());
        if (getPontos() != 0) hashMap.put("pontos", getPontos());
        if (getObservacoes() != null) hashMap.put("observacoes", getObservacoes());
        return hashMap;
    }

}
