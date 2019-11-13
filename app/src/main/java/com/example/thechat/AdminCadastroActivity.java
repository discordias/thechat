package com.example.thechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AdminCadastroActivity extends AppCompatActivity {

    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastro);

        Bundle enviarTipo = getIntent().getExtras();
        tipo = enviarTipo.get("tipo").toString();
        TextView msg = findViewById(R.id.idTipo);
        msg.setText(tipo);
    }
}
