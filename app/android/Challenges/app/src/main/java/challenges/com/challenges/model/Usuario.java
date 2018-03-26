package challenges.com.challenges.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Wender Galan Gamer on 25/03/2018.
 */

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String responsavel;
    private String habilidade;
    private int pontos;
    private int tipo; // 1 => é criança  e 0 => é responsável
    private String amigos[];
    private String senha;
    private String foto;


    public Usuario() {
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String[] getAmigos() {
        return amigos;
    }

    public void setAmigos(String[] amigos) {
        this.amigos = amigos;
    }

    public void salvar(){

    }

}
