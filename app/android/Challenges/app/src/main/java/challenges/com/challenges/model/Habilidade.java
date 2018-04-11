package challenges.com.challenges.model;

import java.util.HashMap;
import java.util.Map;

public class Habilidade {

    private int id;
    private String nome;
    private String descricao;

    public Habilidade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Map construirHash(){
        Map<String, Object> hashMap = new HashMap<String, Object>();

        /**
         * IMPLEMENTAR O MÉTODO
         * **/


        return hashMap;
    }
}
