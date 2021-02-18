package com.caio.carteiradeclientes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by caios on 29/06/2017.
 */

public class DadosOpenHelper extends SQLiteOpenHelper {

    private static final String NOME_BD = "dados";
    private static final int VERSAO_BD = 1;

    public DadosOpenHelper(Context context) {
        super(context,  NOME_BD, null, VERSAO_BD);

        getWritableDatabase();
    }

    //Se o banco nao existir chama o onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS CLIENTE(");
        sql.append("    CODIGO      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,");
        sql.append("    NOME        VARCHAR(100) NOT NULL DEFAULT (''),");
        sql.append("    ENDERECO    VARCHAR(100) NOT NULL DEFAULT (''),");
        sql.append("    EMAIL       VARCHAR(100) NOT NULL DEFAULT (''),");
        sql.append("    TELEFONE    VARCHAR(20) NOT NULL DEFAULT ('') )");


        db.execSQL(sql.toString());
    }

    //Se o banco ja existir e a versão mudar chama o onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Fazer backupd dos dados antes
        //code

        db.execSQL("DROP TABLE IF EXISTS CLIENTE");
        onCreate(db);

        //Reinserir os dados
        //code
    }
}
