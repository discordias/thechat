package com.example.thechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thechat.config.Conexao;
import com.example.thechat.models.TipoUsuario;
import com.example.thechat.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AdminCadastroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String tipo;
    private Usuario usuario;
    private FirebaseAuth mAuth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastro);

        mAuth = Conexao.getAuthFirebase();
        myRef = Conexao.getFirebase();


        Bundle enviarTipo = getIntent().getExtras();
        tipo = enviarTipo.get("tipo").toString();
        TextView msg = findViewById(R.id.idCadTitulo);
        msg.setText(Usuario.tituloCadastro(tipo));
//        msg.setText("Novo cadastro de usuarios");

        Toast.makeText(AdminCadastroActivity.this, TipoUsuario.ALUNO.getTipo(),Toast.LENGTH_LONG).show();


        Button btCadastrar = (Button) findViewById(R.id.idCadBotao);
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nome = (EditText) findViewById(R.id.idCadNome);
                EditText email = (EditText) findViewById(R.id.idCadEmail);
                EditText senha = (EditText) findViewById(R.id.idCadSenha);

                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setNome(nome.getText().toString());
                usuario.setSenha(senha.getText().toString());
                String tipoU = tipo.toUpperCase();
                usuario.setTipo(TipoUsuario.valueOf(tipoU));

                novoUsuario();



            }
        });
    }


    private void novoUsuario(){


        mAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(
                AdminCadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(AdminCadastroActivity.this, "Usuário salvo",Toast.LENGTH_LONG).show();

                            FirebaseUser user = task.getResult().getUser();
                            usuario.setId(user.getUid());
                            usuario.salvarUsuario();
                            mAuth.signOut();

                            mAuth.signInWithEmailAndPassword("admin@email.com", "654321").addOnCompleteListener(AdminCadastroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task2) {
                                    //admin sign in again

//                                    if(task2.isSuccessful()){
//
//                                    }
                                }
                            });

                        }else{
                            Toast.makeText(AdminCadastroActivity.this, "Não foi possivel salvar usuario",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

}
