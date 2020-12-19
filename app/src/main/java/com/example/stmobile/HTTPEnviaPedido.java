package com.example.stmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPEnviaPedido extends AsyncTask<Void, Void, String> {
    private Context httpContext;
    ProgressDialog progressDialog;
    public String resultadoApi = "";
    public String linkRequestAPI = "";
    public int cod = 0;
    public String nome = "";
    public String status = "";
    public String entregador = "";


    public HTTPEnviaPedido(Expedicao ctx, String linkAPI, int cod, String nome, String status, String entregador) {
        this.httpContext = ctx;
        this.linkRequestAPI = linkAPI;
        this.cod = cod;
        this.nome = nome;
        this.status = status;
        this.entregador = entregador;
    }

    /*@Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(httpContext, "Processando...", "Espere");
    } */

    @Override
    protected String doInBackground(Void... params) {
        String result = null;

        String wsURL = linkRequestAPI;

        URL url = null;
        try {
            url = new URL(wsURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            ModeloExpedicao mod = new ModeloExpedicao();

            mod.setCod_pedido(cod);
            mod.setNome_cliente(nome);
            mod.setStatus(status);
            mod.setEntregador(entregador);
            Gson gson = new Gson();
            String modJson = gson.toJson(mod);

            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-type","application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(modJson);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                result = sb.toString();
            } else {
                result = new String("Erro: " + responseCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


 /*   @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        progressDialog.dismiss();
        resultadoApi = s;
        Toast.makeText(httpContext,resultadoApi,Toast.LENGTH_LONG).show();
    } */


 /*   public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    } */


}







