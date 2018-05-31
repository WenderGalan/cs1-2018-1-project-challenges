package challenges.com.challenges.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Desafio implements Serializable {

    private String id;
    private String titulo;
    private String recompensa;
    private String habilidade;
    private int pontos = 0;
    private String observacoes;
    private String responsavel;
    private String crianca;
    private String frequencia;
    private int repeticoes = 1;
    private boolean completado = false;
    private boolean checado = false;
    private Date dataCriacao;
    private Date dataUpdate;
    private Timestamp dataCriacaoTimestamp;
    private Timestamp dataUpdateTimestamp;


    public boolean isCompletado() {
        return completado;
    }
    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
    public boolean isChecado() {
        return checado;
    }
    public void setChecado(boolean checado) {
        this.checado = checado;
    }
    public Desafio() { }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public Date getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public Date getDataUpdate() {
        return dataUpdate;
    }
    public void setDataUpdate(Date dataUpdate) {
        this.dataUpdate = dataUpdate;
    }
    public Timestamp getDataCriacaoTimestamp() {
        return dataCriacaoTimestamp;
    }
    public void setDataCriacaoTimestamp(Timestamp dataCriacaoTimestamp) {
        this.dataCriacaoTimestamp = dataCriacaoTimestamp;
    }
    public Timestamp getDataUpdateTimestamp() {
        return dataUpdateTimestamp;
    }
    public void setDataUpdateTimestamp(Timestamp dataUpdateTimestamp) {
        this.dataUpdateTimestamp = dataUpdateTimestamp;
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
        hashMap.put("completado", isCompletado());
        hashMap.put("checado", isChecado());
        if (getDataCriacaoTimestamp() != null) hashMap.put("dataCriacao", getDataCriacaoTimestamp());
        if (getDataUpdateTimestamp() != null) hashMap.put("dataUpdate", getDataUpdateTimestamp());
        return hashMap;
    }

}
