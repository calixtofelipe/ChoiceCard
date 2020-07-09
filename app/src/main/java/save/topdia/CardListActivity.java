package save.topdia;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.LinkedList;

import Adapters.CardListAdapter;
import Interface.RVHackConsultaCard;
import bancoDados.dao.CardDAO;
import de.greenrobot.event.EventBus;
import eventBus.ListenerEB;
import objetos.Card;

public class CardListActivity extends AppCompatActivity implements RVHackConsultaCard {
    RecyclerView mRecyclerView;
    CardListAdapter mAdapter;
    private LinkedList<Card> mListaux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(CardListActivity.this);
        mListaux = new LinkedList<Card>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_card);
        mRecyclerView.setHasFixedSize(true);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mListaux.clear();
        CardDAO cardDAO = new CardDAO(getApplicationContext());

        mListaux = cardDAO.getCards();
        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        mAdapter = new CardListAdapter(this, mListaux);


        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);
        mAdapter = new CardListAdapter(this, mListaux);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setRVHackConsultaCard(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(CardListActivity.this, AddCardActivity.class);
                startActivity(menu);
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

    public void onEvent(ListenerEB listenerEB) {
        if (!listenerEB.getClasseDestino().equalsIgnoreCase(CardListActivity.class + "")) {
            return;
        } else {
            if (listenerEB.getMensagem().equals("ATUALIZALISTA")) {
                mAdapter.addListItem(listenerEB.getCard(), mAdapter.getItemCount());
            }
        }
    }

    @Override
    public void onClickListener(View view, int position) {
        Log.i("AgendaActivity", "click - posicao" + position);
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        Log.i("AgendaActivity", "click - posicao" + position);
    }


}
