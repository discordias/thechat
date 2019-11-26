package com.example.thechat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thechat.config.Conexao;
import com.example.thechat.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PerfilActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String idUser;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = Conexao.getAuthFirebase();
        myRef = Conexao.getFirebase();

        Bundle enviarId = getIntent().getExtras();
        idUser = enviarId.get("id").toString();

//        Toast.makeText(PerfilActivity.this, idUser, Toast.LENGTH_LONG).show();

        Query userQuery = myRef.child("users").child(idUser);


        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = (Usuario) dataSnapshot.getValue(Usuario.class);
                Toast.makeText(PerfilActivity.this, user.getNome(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
