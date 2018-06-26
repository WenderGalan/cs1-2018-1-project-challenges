package challenges.com.challenges.model;

import com.google.firebase.firestore.DocumentReference;

/**
 * Created by matheus on 20/06/18.
 */

public class NotificacaoDesafioApps {

    private DocumentReference crianca;
    private DocumentReference desafio;
    private String responsavel;
    private String id;
    private DesafioApp desafioObject;

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

    public DesafioApp getDesafioObject() {
        return desafioObject;
    }

    public void setDesafioObject(DesafioApp desafioObject) {
        this.desafioObject = desafioObject;
    }

    
}
