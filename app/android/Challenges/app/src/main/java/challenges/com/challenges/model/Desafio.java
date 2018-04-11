package challenges.com.challenges.model;

import java.util.HashMap;
import java.util.Map;

public class Desafio {

    private int id;
    private String titulo;
    private String descricao;
    private int valor;
    private int duracao;
    private String recompensa;

    public Desafio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }

    public Map construirHash(){
        Map<String, Object> hashMap = new HashMap<String, Object>();

        /**
         * IMPLEMENTAR O MÉTODO
         * **/


        return hashMap;
    }

}
