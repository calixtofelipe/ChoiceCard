package objetos;

import java.util.Date;

/**
 * Created by ADMIN on 28/11/2017.
 */

public class Card {
    String numero;
    String apelido;
    Integer  diaCompra;
    Integer _id;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Integer getDiaCompra() {
        return diaCompra;
    }

    public void setDiaCompra(Integer diaCompra) {
        this.diaCompra = diaCompra;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }
}
