package com.caio.carteiradeclientes;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class ActCadastroClientes extends AppCompatActivity {

    private EditText getEditNome(){ return (EditText) findViewById(R.id.txtNome);}
    private EditText getEditEndereco() {return (EditText)findViewById(R.id.txtEndereco);}
    private EditText getEditEmail() { return (EditText)findViewById(R.id.txtEmail);}
    private EditText getEditTelefone() {return (EditText)findViewById(R.id.txtTelefone);};

    Cliente cliente;
    Boolean modoAlterar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_clientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //adiciona a seta de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //cria uma instancia e passa o context por parametro
        cliente = new Cliente(this);

        modoAlterar = isModoAlterar();
    }

    //Adicionando o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro_clientes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        try
        {
            switch (id)
            {
                case android.R.id.home:
                    //finish();
                    break;
                case R.id.action_ok:
                    if(!isCamposValidos()) return false;
                    if(modoAlterar)
                        cliente.Alterar();
                    else
                        cliente.Inserir();
                    break;
                case R.id.action_excluir:
                    cliente.Excluir();
                    break;
            }
            finish();
        }catch (SQLException ex)
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isModoAlterar(){
        Bundle bundle = getIntent().getExtras();

        if ( (bundle != null) && (bundle.containsKey("CLIENTE")) ){

            cliente = (Cliente)bundle.getSerializable("CLIENTE");

            getEditNome().setText(cliente.Nome);
            getEditEndereco().setText(cliente.Endereco);
            getEditEmail().setText(cliente.Email);
            getEditTelefone().setText(cliente.Telefone);
            return true;
        }
        return false;
    }

    private boolean isCamposValidos()
    {
        cliente.Nome = getEditNome().getText().toString();
        cliente.Endereco = getEditEndereco().getText().toString();
        cliente.Email = getEditEmail().getText().toString();
        cliente.Telefone = getEditTelefone().getText().toString();

        boolean camposValidos = false;
        if(isCampoVazio(cliente.Nome)){
            getEditNome().requestFocus();
        }else if(isCampoVazio(cliente.Endereco)) {
            getEditEndereco().requestFocus();
        }else if(!isEmailValido(cliente.Email)){
            getEditEmail().requestFocus();
        }else if(isCampoVazio(cliente.Telefone)){
            getEditTelefone().requestFocus();
        }else{
            camposValidos = true;
        }

        if(!camposValidos){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_aviso);
            dlg.setMessage(R.string.message_campos_invalidos_e_brancos);
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();
        }

        return camposValidos;
    }

    private boolean isCampoVazio(String campo)
    {
        return campo.trim().isEmpty();
    }

    private boolean isEmailValido(String email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
