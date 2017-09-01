package br.edu.ifpb.pdm.liguei.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "emergencia";
    public static final String TB_NAME = "telefones";
    public static final int VERSAO = 1;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TB_NAME+"(" +
                "numero VARCHAR PRIMARY KEY NOT NULL," +
                "nome VARCHAR," +
                "descricao VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
        this.onCreate(db);
    }
}
