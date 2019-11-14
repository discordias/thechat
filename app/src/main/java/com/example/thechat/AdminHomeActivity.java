package com.example.thechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thechat.config.Conexao;
import com.example.thechat.models.TipoUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminHomeActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        firebaseUser = Conexao.getFirebaseUser();
        Toast.makeText(getApplicationContext(), "texto" + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();

        Button cadAluno = (Button) findViewById(R.id.idCadAluno);
        cadAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle enviarTipo = new Bundle();
                enviarTipo.putString("tipo", TipoUsuario.ALUNO.getTipo());
                Intent intent = new Intent(AdminHomeActivity.this, AdminCadastroActivity.class);
                intent.putExtras(enviarTipo);
                startActivity(intent);
            }
        });

        Button cadProfessor = (Button) findViewById(R.id.idCadProfessor);
        cadProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle enviarTipo = new Bundle();
                enviarTipo.putString("tipo", TipoUsuario.PROFESSOR.getTipo());
                Intent intent = new Intent(AdminHomeActivity.this, AdminCadastroActivity.class);
                intent.putExtras(enviarTipo);
                startActivity(intent);
            }
        });

        Button logout = (Button) findViewById(R.id.idLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexao.logout();
//                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
