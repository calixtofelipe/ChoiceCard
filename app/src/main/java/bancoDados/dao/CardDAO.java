package bancoDados.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import bancoDados.DB;
import objetos.Card;


/**
 * Created by ADMIN on 20/10/2015.
 */
public class CardDAO {

    private static String table_name = "TABCARD";
    private static Context ctx;
    private static String[] columns = {"NUMBER", "APELIDO", "DIACOMPRA", "DHINTEGRACAO", "_id"};
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");

    public CardDAO(Context ctx) {
        this.ctx = ctx;
    }

    public boolean insert(JSONObject jsonObject) {
        boolean retorno = false;
        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        db.execSQL("VACUUM");
        db.beginTransaction();
        try {
            ContentValues ctv = new ContentValues();
            ctv.put("NUMBER", jsonObject.getString("number"));
            ctv.put("APELIDO", jsonObject.getString("apelido"));
            ctv.put("DIACOMPRA", jsonObject.getString("diacompra"));
            ctv.put("DHINTEGRACAO", dateFormat.format(Calendar.getInstance().getTime()));
            retorno = (db.insert(table_name, null, ctv) > 0);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        return retorno;

    }

    public boolean deleteItem(Integer id) {
        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        db.execSQL("VACUUM");
        db.beginTransaction();
        try {
            Cursor cr = db.rawQuery("SELECT * FROM TABCARD", null);
            Integer newseq = 0;
            if (cr.move(id+1)) {
                newseq = cr.getInt(cr.getColumnIndex("_id"));
            }
            cr.close();
            return (db.delete(table_name, "_id=?", new String[]{newseq.toString()}) > 0);
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    public boolean update(JSONObject jo) {
        SQLiteDatabase db = new DB(ctx).getWritableDatabase();
        db.beginTransaction();
        boolean retorno = false;
        try {
            ContentValues ctv2 = new ContentValues();
            ctv2.put("NUMBER", jo.getString("number"));
            ctv2.put("APELIDO", jo.getString("apelido"));
            ctv2.put("DTCOMPRA", jo.getString("dtcompra"));
            ctv2.put("DHINTEGRACAO", dateFormat.format(Calendar.getInstance().getTime()));
            retorno = (db.update(table_name, ctv2, "_id=?", new String[]{jo.getString("_id")}) > 0);
        } catch (JSONException e) {
            e.printStackTrace();

        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        return retorno;
    }


    public Card getCard(Integer id) {
        SQLiteDatabase db = new DB(ctx).getReadableDatabase();
        db.beginTransaction();
        try {
            Cursor rs = db.query(table_name, columns, "_id=?", new String[]{id.toString()}, null, null, null);

            Card vo = new Card();

            if (rs.moveToNext()) {
                vo.setApelido(rs.getString(rs.getColumnIndex("APELIDO")));
                vo.setNumero(rs.getString(rs.getColumnIndex("NUMBER")));
                vo.setDiaCompra(rs.getInt(rs.getColumnIndex("DIACOMPRA")));

            }
            rs.close();
            return vo;
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }


    public Integer count() {
        SQLiteDatabase db = new DB(ctx).getReadableDatabase();
        db.beginTransaction();
        try {
            Cursor c = db.query(table_name, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                c.getCount();
            }
            c.close();
            return c.getCount();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    public LinkedList<Card> getCards() {
        SQLiteDatabase db = new DB(ctx).getReadableDatabase();
        db.beginTransaction();
        try {
            Cursor rs = db.query(table_name, columns, null, null, null, null, null);
            LinkedList<Card> lista = new LinkedList<Card>();
            while (rs.moveToNext()) {
                Card vo = new Card();
                vo.set_id(rs.getInt(rs.getColumnIndex("_id")));
                vo.setNumero(rs.getString(rs.getColumnIndex("NUMBER")));
                vo.setApelido(rs.getString(rs.getColumnIndex("APELIDO")));
                vo.setDiaCompra(rs.getInt(rs.getColumnIndex("DIACOMPRA")));
                lista.add(vo);
            }
            rs.close();
            return lista;
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }

    }


}