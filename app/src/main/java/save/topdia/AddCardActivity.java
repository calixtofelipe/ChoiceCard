package save.topdia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import bancoDados.dao.CardDAO;
import de.greenrobot.event.EventBus;
import eventBus.ListenerEB;
import objetos.Card;

public class AddCardActivity extends AppCompatActivity {
    EditText etNumber;
    EditText etApelido;
    EditText etDtCompra;
    Button btnConfirmaCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNumber = (EditText) findViewById(R.id.etNumberCard);
        etApelido = (EditText) findViewById(R.id.etApelido);
        etApelido.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        etDtCompra = (EditText) findViewById(R.id.etDtCompra);
        btnConfirmaCard = (Button) findViewById(R.id.btnConfirmaCard);

        btnConfirmaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject json = new JSONObject();
                try {
                    json.put("diacompra",etDtCompra.getText().toString());
                    json.put("apelido",etApelido.getText().toString());
                    json.put("number",etNumber.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CardDAO cardDAO = new CardDAO(getApplicationContext());
                boolean retorno = cardDAO.insert(json);
                if(retorno) {
                    EventBus eventBus = EventBus.getDefault();
                    ListenerEB msg = new ListenerEB();
                    msg.setClasseDestino(CardListActivity.class + "");
                    msg.setMensagem("ATUALIZALISTA");
                    Card ag = new Card();
                    ag.setApelido(etApelido.getText().toString());
                    ag.setNumero(etNumber.getText().toString());
                    ag.setDiaCompra(new Integer(etDtCompra.getText().toString()));
                    msg.setCard(ag);
                    eventBus.post(msg);
                    onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(),"Erro ao Inserir",Toast.LENGTH_LONG);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
