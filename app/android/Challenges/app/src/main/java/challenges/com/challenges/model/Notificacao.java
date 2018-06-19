package challenges.com.challenges.model;


import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class Notificacao {

    private DocumentReference crianca;
    private DocumentReference desafio;
    private String responsavel;
    private String id;
    private Desafio desafioObject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Notificacao() {

    }

    public DocumentReference getCrianca() {
        return crianca;
    }

    public void setCrianca(DocumentReference crianca) {
        this.crianca = crianca;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public Desafio getDesafioObject() {
        return desafioObject;
    }

    public void setDesafioObject(Desafio desafioObject) {
        this.desafioObject = desafioObject;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public DocumentReference getDesafio() {
        return desafio;
    }

    public void setDesafio(DocumentReference desafio) {
        this.desafio = desafio;
    }

    public Map construirHash() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crianca",getCrianca());
        hashMap.put("desafio", getDesafio());
        hashMap.put("responsavel",getResponsavel());
        return hashMap;
    }

}