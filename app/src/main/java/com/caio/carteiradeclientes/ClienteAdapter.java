package com.caio.carteiradeclientes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*Classe responsavel por adicionar o layout linha_cliente para a lista recycler view no content_principal*/

class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderCliente>
{

    private List<Cliente> dados;

    public ClienteAdapter(List<Cliente> dados){
        this.dados = dados;
    }

    @Override
    public ViewHolderCliente onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.linha_clientes, parent, false);

        ViewHolderCliente holderCliente = new ViewHolderCliente(view, parent.getContext());

        return holderCliente;
    }

    @Override
    public void onBindViewHolder(ViewHolderCliente holder, int position)
    {
        if ( (dados != null) && (dados.size() > 0 ) ) {

            Cliente cliente = dados.get(position);

            holder.lblNome.setText(cliente.Nome);
            holder.lblTelefone.setText(cliente.Telefone);
        }
    }

    @Override
    public int getItemCount()
    {
        return dados.size();
    }

    public class ViewHolderCliente extends  RecyclerView.ViewHolder
    {
        private TextView lblNome;
        private TextView lblTelefone;

        public ViewHolderCliente(View itemView, final Context context)
        {
            super(itemView);
            lblNome = (TextView) itemView.findViewById(R.id.lblNomeLista);
            lblTelefone = (TextView) itemView.findViewById(R.id.lblTelefoneLista);

            itemView.setOnClickListener(new View.OnClickListener() {
                //quando clicar em algum item da lista
                @Override
                public void onClick(View v) {

                    if (dados.size() > 0) {

                        Cliente cliente = dados.get(getLayoutPosition());

                        Intent it = new Intent(context, ActCadastroClientes.class);
                        it.putExtra("CLIENTE", cliente);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);
                    }
                }

            });
        }
    }
}
