package challenges.com.challenges.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by matheus on 28/05/18.
 */

public class DesafioApp {

    private String id;
    private Date dataCriacao;
    private Date dataFim;
    private String frequencia;
    private String habilidade;
    private String habilidadeID;
    private int pontos;
    private int repeticoes;
    private String titulo;

    private DocumentReference crianca;
    private DocumentReference desafio;
    private String responsavel;

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(String habilidade) {
        this.habilidade = habilidade;
    }

    public String getHabilidadeID() {
        return habilidadeID;
    }

    public void setHabilidadeID(String habilidadeID) {
        this.habilidadeID = habilidadeID;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public DocumentReference getCrianca() {
        return crianca;
    }

    public void setCrianca(DocumentReference crianca) {
        this.crianca = crianca;
    }

    public DocumentReference getDesafio() {
        return desafio;
    }

    public void setDesafio(DocumentReference desafio) {
        this.desafio = desafio;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map construirHash() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crianca",getCrianca());
        hashMap.put("desafio", getDesafio());
        hashMap.put("responsavel",getResponsavel());
        return hashMap;
    }
}
