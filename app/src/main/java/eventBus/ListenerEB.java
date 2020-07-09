package eventBus;


import objetos.Card;

/**
 * Created by ADMIN on 18/04/2017.
 */

public class ListenerEB {

    private String ClasseDestino;
    private String mensagem;
    private Card card;
    private String comando;
    private String retorno;

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getClasseDestino() {
        return ClasseDestino;
    }

    public void setClasseDestino(String classeDestino) {
        ClasseDestino = classeDestino;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
