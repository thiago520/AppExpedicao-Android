package com.example.stmobile;


import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/*
public class HTTPAlterar {

    private static final Logger LOG = Logger.getLogger(HTTPAlterar.class.getName());

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) {

        String uri = "http://ec2-18-228-15-250.sa-east-1.compute.amazonaws.com:8080/WebService/webresources/Expedicao/atualizar";

        try {
            ModeloExpedicao modeloExpedicao = new ModeloExpedicao();
            modeloExpedicao.setCod_pedido(125);
            modeloExpedicao.setNome_cliente("Teste");
            modeloExpedicao.setStatus("Enviado");

            Gson gson = new Gson();
            String modeloExpedicaoJson = gson.toJson(modeloExpedicao);

            System.out.println("Produto serializado (json):");
            System.out.println(modeloExpedicaoJson);

            try {
                URL url = new URL(uri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(modeloExpedicaoJson);
                out.close();

                int http_status = connection.getResponseCode();
                if (http_status / 100 != 2) {
                    LOG.log(Level.SEVERE, "Ocorreu algum erro. Código de resposta: {0}", http_status);
                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } catch (Exception e) {
                LOG.log(Level.SEVERE, null, e);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
}

*/


public class HTTPAlterar extends AsyncTask<Void, Void, ModeloExpedicao> {

    private ArrayList<ModeloExpedicao> arrayList;

    public HTTPAlterar(ArrayList<ModeloExpedicao> list) {
        this.arrayList = list;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected ModeloExpedicao doInBackground(Void... voids) {

        try {
            //URL QUE SERÁ CONSUMIDA
            URL url = new URL("http://localhost:8080/WebServiceERP/webresources/Expedicao/atualizar");

            //---- ABRINDO A CONEXÃO ---
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-type","application/json; utf-8");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);
            conexao.setConnectTimeout(5000);
            // conexao.connect();

            String jsonInputString = "{'cod_pedido':4748,'nome_cliente':'Camilo Aniceto Ferracioli Junior','status':'Enviado'}";

            try(OutputStream os = conexao.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conexao.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

         /*   OutputStream outputStream = conexao.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(new Gson().toJson(arrayList));
            outputStream.flush();
            writer.close();
            outputStream.close(); */

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

}