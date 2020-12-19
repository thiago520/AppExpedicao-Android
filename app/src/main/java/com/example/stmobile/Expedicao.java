package com.example.stmobile;

import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Expedicao extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ArrayList<ModeloExpedicao> arrayList;
    private TextView textViewCod,textViewNome;
    private TabLayout tabLayout;
    private LayoutInflater inflater;
    private ToggleButton toggleButton;
    public static String selectEntregador = null;
   // Timer timer;
   Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedicao);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ListView listView = findViewById(R.id.listViewExpedicao);

        arrayList = new ArrayList<ModeloExpedicao>();
        adapter = new ListaAdapterExpedicao(this,arrayList);
        listView.setAdapter(adapter);

        textViewCod = findViewById(R.id.txtCodigo);
        textViewNome = findViewById(R.id.txtNome);
        toggleButton = findViewById(R.id.togEnviar);
      //
        tabLayout = findViewById(R.id.tabEntregadores);

        selectEntregador = "Caique";


       /* timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Expedicao.this, Expedicao.class);
                startActivity(intent);
                finish();
            }
        },20000); */

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    selectEntregador = "Caique";
                }
                if (position == 1) {
                    selectEntregador = "Igor";
                }
                if (position == 2) {
                    selectEntregador = "Outro";
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,60000);
    }

    @Override
    protected void onStart(){
        super.onStart();
            try {
                    new HTTPList(arrayList).execute().get();
            } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
    }

    public void EnviarPedido(int cod, String nome, String status, String entregador) {
        HTTPEnviaPedido enviaPedido = new HTTPEnviaPedido(this, "http://104.41.0.108:8080/WebServiceERP/webresources/Expedicao/atualizar", cod, nome, status, entregador);
        enviaPedido.execute();
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            startActivity(getIntent());
            finish();
            Toast.makeText(Expedicao.this,"atualizando...",Toast.LENGTH_SHORT).show();
          //  Expedicao.this.mHandler.postDelayed(m_Runnable, 60000);
        }

    };//runnable
}