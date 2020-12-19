package com.example.stmobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ListaAdapterExpedicao extends ArrayAdapter<ModeloExpedicao> {

    private Context context;
    private ArrayList<ModeloExpedicao> arrayList;
    private LayoutInflater inflater;
    private TextView textViewCod;
    private TextView textViewNome;
    private ToggleButton toggleButton;
    Expedicao exp = new Expedicao();

    public ListaAdapterExpedicao(Context c, ArrayList<ModeloExpedicao> objects) {
        super(c, 0, objects);
        this.context = c;
        this.arrayList = objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
       // ModeloExpedicao itemPosicao = this.lista.get(position);

        if (arrayList != null) {
            // LayoutInflater inflater = LayoutInflater.from(context);
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.item_lista, parent, false);

            textViewCod = view.findViewById(R.id.txtCodigo);
            textViewNome = view.findViewById(R.id.txtNome);
            toggleButton = view.findViewById(R.id.togEnviar);

            final ModeloExpedicao mod = this.arrayList.get(position);
            textViewCod.setText(String.valueOf(mod.getCod_pedido()));
            textViewNome.setText(mod.getNome_cliente());

            if (mod.getStatus() == "Enviado") {
                toggleButton.setText("Enviado");
                toggleButton.setChecked(true);
            } else {
                toggleButton.setText("Enviar");
                toggleButton.setChecked(false);
            }

            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                       mod.setStatus("Enviado");
                    } else {
                       mod.setStatus("Em Preparo");
                    }
                }
            });

            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exp.EnviarPedido(mod.getCod_pedido(), mod.getNome_cliente(), mod.getStatus(), Expedicao.selectEntregador);
                }
            });

        }

        return view;
    }

}