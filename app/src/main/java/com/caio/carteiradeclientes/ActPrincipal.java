package com.caio.carteiradeclientes;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class ActPrincipal extends AppCompatActivity {
    private ConstraintLayout layoutPrincipal;

    private RecyclerView lista;
    private ClienteAdapter clienteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        layoutPrincipal = (ConstraintLayout) findViewById(R.id.layoutContentMain);

        lista = (RecyclerView) findViewById(R.id.rvDados);
        lista.setHasFixedSize(true);

        //adicionando a lista de cliente no layout linha_cliente
        LinearLayoutManager layoutLinha = new LinearLayoutManager(this);
        lista.setLayoutManager(layoutLinha);
        ListarClientes();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itCadastroClientes = new Intent(ActPrincipal.this,  ActCadastroClientes.class);
                //inicia a activity aguardando resultado
                startActivityForResult(itCadastroClientes, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //quando a activity que foi passado parametro 0 for retornada, atualizar lista clientes
        if(requestCode == 0 )
        {
            ListarClientes();
        }
    }

    private void ListarClientes(){
        List<Cliente> listaCliente = Cliente.BuscarTodosClientes(this);
        clienteAdapter = new ClienteAdapter(listaCliente);
        lista.setAdapter(clienteAdapter);
    }
}
