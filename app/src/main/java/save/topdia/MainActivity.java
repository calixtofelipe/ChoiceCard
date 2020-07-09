package save.topdia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import bancoDados.dao.CardDAO;
import objetos.Card;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        TextView tvMelhorCard = (TextView) findViewById(R.id.tvMelhorCard);
        TextView tvDtmax = (TextView) findViewById(R.id.tvDtmax);

        CardDAO cardDAO = new CardDAO(getApplicationContext());
        LinkedList<Card> cards =  cardDAO.getCards();
        LinkedList<Integer> calcDia = new LinkedList<Integer>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Integer melhorDia = null;
        String melhorCartao = null;
        Integer nextfechamento= null;
        Integer ultimoDiaMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Integer dtCorte = null;
        if(cards.size() > 0) {

            for (int i = 0; i < cards.size(); i++) {
                Card cartao = cards.get(i);
                //todos os loops inicio

                Integer diasAteFechamentoAtual = (cartao.getDiaCompra() > day) ? cartao.getDiaCompra() - day : ultimoDiaMes - day + cartao.getDiaCompra()  ;
                Log.d("MainActivityX!", "diasAteFechamento: "+diasAteFechamentoAtual+"MELHOR CARTAO: "+melhorCartao
                +"melhordia: "+melhorDia+"hoje: "+day+"melhor dia atual:"+cartao.getDiaCompra());
                //primeiro loop
                if (melhorDia == null) {
                    melhorDia = diasAteFechamentoAtual;
                    melhorCartao = cartao.getApelido();
                    dtCorte = cartao.getDiaCompra();
                    nextfechamento = diasAteFechamentoAtual;
                }

                //segundo loop em diante
                if (melhorDia != null) {

                    if((diasAteFechamentoAtual > melhorDia)) {
                        melhorDia = diasAteFechamentoAtual;
                        melhorCartao = cartao.getApelido();
                        dtCorte = cartao.getDiaCompra();
                    }
                    if((diasAteFechamentoAtual < nextfechamento)) {
                        nextfechamento = diasAteFechamentoAtual;
                    }

                }

                //ATE QUE DIA UTILIZARÁ O CARTAO
                int mes = calendar.get(Calendar.MONTH)+1;
                int ano = calendar.get(Calendar.YEAR);


                String datafinal = null;
                Calendar cal = Calendar.getInstance();
                if((day+nextfechamento) <= ultimoDiaMes) {
                    datafinal =   day+nextfechamento+ "/" + mes + "/" + ano;
                    try {
                        Date data = new SimpleDateFormat("dd/MM/yyyy").parse(datafinal);

                        cal.setTime(data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {

                    datafinal = (day+nextfechamento)-ultimoDiaMes+ "/" + mes + "/" + ano;
                    try {
                        Date data = new SimpleDateFormat("dd/MM/yyyy").parse(datafinal);

                        cal.setTime(data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cal.add(Calendar.MONTH, 1);

                }
                Log.d("MainActivityX!","NEXTFECHAMENTO"+nextfechamento+ "ANO"+ano+"MES"+mes+"DATA INFORMADA"+cal.getTime());
                tvDtmax.setText(dtFormat.format(cal.getTime()));



            }
            //INFORMA O CARTAO QUE DEVE SER USADO
            tvMelhorCard.setText(melhorCartao);





        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(MainActivity.this, CardListActivity.class);
                startActivity(menu);
            }
        });
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
