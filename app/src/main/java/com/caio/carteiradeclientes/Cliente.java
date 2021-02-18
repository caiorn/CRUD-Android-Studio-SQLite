package com.caio.carteiradeclientes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.caio.carteiradeclientes.database.DadosOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caios on 30/06/2017.
 * classe ClienteRepository
 */

public class Cliente implements Serializable
{
    private static SQLiteDatabase db;
    private static Context context;

    public int Codigo;
    public String Nome;
    public String Endereco;
    public String Email;
    public String Telefone;

    //Construtor 1
    public Cliente() { }

    //Construtor 2
    public Cliente(Context context){
        this.context = context;
        //abre a conexao
        db = new DadosOpenHelper(context).getWritableDatabase();
    }

    private ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put("NOME", this.Nome);
        cv.put("ENDERECO", this.Endereco);
        cv.put("EMAIL", this.Email);
        cv.put("TELEFONE", this.Telefone);
        return cv;
    }

    public void Inserir()
    {
        long primaryKey = db.insertOrThrow("CLIENTE", null, getContentValues());
        Toast.makeText(context, "Cadastrado", Toast.LENGTH_SHORT).show();

    }

    public void Alterar()
    {
        db.update("CLIENTE", getContentValues(), "CODIGO = ?", new String[]{this.Codigo +""});
        Toast.makeText(context, "Alterado", Toast.LENGTH_SHORT).show();
    }

    public void Excluir()
    {
        int result = db.delete("CLIENTE", "CODIGO = " + this.Codigo, null);
        if(result == 1)
            Toast.makeText(context, "Excluido", Toast.LENGTH_SHORT).show();
    }

    /**
     * Busca todos os clientes ou um cliente especifico por ID, Nome, Telefone etc.
     * @param context this
     * @param condicao Ex: NOME = ? OR TELEFONE = ?
     * @param argsCondicao Parametros do ?, Ex: "Caio", "9999-9999"
     * @return
     */
    private static List<Cliente> BuscarCliente(Context context, String condicao, String... argsCondicao)
    {
        String whereClause = condicao;
        String[] whereArgs = argsCondicao;

        db = new DadosOpenHelper(context).getReadableDatabase();
        List<Cliente> clientes = new ArrayList<Cliente>();

        //Colunas que desejo buscar
        String[] columns =  {"CODIGO","NOME", "ENDERECO","EMAIL", "TELEFONE"};
        Cursor cursor = db.query("CLIENTE", columns, whereClause, argsCondicao , null, null, null);
        //Cursor cursor = db.rawQuery("SELECT CODIGO, NOME, ENDERECO, EMAIL, TELEFONE FROM CLIENTE", null);

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do
            {
                Cliente c = new Cliente();
                c.Codigo = cursor.getInt(0);
                c.Nome =   cursor.getString(1);
                c.Endereco = cursor.getString(2);
                c.Email =   cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));// Alternativa
                c.Telefone =                         cursor.getString(4);

                clientes.add(c);
            }while(cursor.moveToNext());

        }
        return clientes;
    }

    public static List<Cliente> BuscarTodosClientes(Context context){
        return BuscarCliente(context, null, null);
    }

    public static List<Cliente> BuscarClientePorNome(Context context, String nome){
        return BuscarCliente(context, "NOME = ?", nome);
    }

    public static List<Cliente> BuscarClientePorTelefone(Context context, String telefone){
        return BuscarCliente(context, "TELEFONE = ?", telefone);
    }
}
