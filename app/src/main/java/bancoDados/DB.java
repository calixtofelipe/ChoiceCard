package bancoDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by ADMIN on 20/10/2015.
 */
public class DB extends SQLiteOpenHelper {
    private static int version = 1;
    private static String dbName = "TopDiadb.db";
    private static String createage = "CREATE TABLE IF NOT EXISTS [TABCARD]  ([_id] integer PRIMARY KEY ON CONFLICT FAIL AUTOINCREMENT," +
            "  [NUMBER] VARCHAR2(100), " +
            "  [APELIDO] VARCHAR2(2), " +
            "  [DIACOMPRA] INTEGER, "+
            "  [DHINTEGRACAO] DATETIME);";


    public DB(Context ctx) {
        super(ctx, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createage);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("LOGCALIXTO", "VERSAO NOVA" + newVersion + "VERSAO ANTIGA" + oldVersion);
        //       db.execSQL(sqldelete);
        //     db.execSQL(sql);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("LOGCALIXTO", "DOWNVERSAO NOVA" + newVersion + "DONWVERSAO ANTIGA" + oldVersion);
    }
}