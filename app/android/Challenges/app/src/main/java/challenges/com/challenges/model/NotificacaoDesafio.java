//package challenges.com.challenges.model;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class NotificacaoDesafio extends Notificacao {
//
//    private String desafio;
//    private String habilidade;
//
//    public String getHabilidade() {
//        return habilidade;
//    }
//
//    public void setHabilidade(String habilidade) {
//        this.habilidade = habilidade;
//    }
//
//    public NotificacaoDesafio() {
//
//    }
//
//    public String getDesafio() {
//        return desafio;
//    }
//
//    public void setDesafio(String desafio) {
//        this.desafio = desafio;
//    }
//
//    public Map construirHash() {
//        Map<String, String> hashMap = new HashMap<>();
//        hashMap.put("responsavel",getResponsavel());
//        hashMap.put("crianca",getCrianca());
//        hashMap.put("desafio", getDesafio());
//        hashMap.put("habilidade", getHabilidade());
//        return hashMap;
//    }
//}