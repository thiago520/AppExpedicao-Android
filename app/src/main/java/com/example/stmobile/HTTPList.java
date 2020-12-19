package com.example.stmobile;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HTTPList extends AsyncTask<Void, Void, ModeloExpedicao> {
    //ATRIBUTOS
    private List<ModeloExpedicao> listPedidos = new ArrayList<>();
    private ArrayAdapter adapter;
    private ListView listView;

    private ArrayList<ModeloExpedicao> arrayList;

    public HTTPList(ArrayList<ModeloExpedicao> list) {
        this.arrayList = list;

    }

    @Override
    protected ModeloExpedicao doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();

        try{
            //URL QUE SERÁ CONSUMIDA
            URL url = new URL("http://104.41.0.108:8080/WebServiceERP/webresources/Expedicao/get/expedicao");

            //---- ABRINDO A CONEXÃO ---
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("Content-type","application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            //----- LENDO AS INFORMAÇÕES -------
            Scanner scanner = new Scanner(url.openStream());
            arrayList.clear();

            while (scanner.hasNext()){
                resposta.append(scanner.nextLine());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //CONVERTENDO O RETORNO PARA A CLASSE MODELO
        listPedidos = new Gson().fromJson(resposta.toString(), new TypeToken<List<ModeloExpedicao>>(){}.getType() );
        //arrayList.add((Coins) lCoins);
        return listPedidos.size() > 0 ? listPedidos.get(0) : null;
    }

    @Override
    protected void onPostExecute(ModeloExpedicao pedidos) {
        super.onPostExecute(pedidos);

        for (int i = 0; i <= listPedidos.size()-1; i++){
            arrayList.add(listPedidos.get(i));
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

}