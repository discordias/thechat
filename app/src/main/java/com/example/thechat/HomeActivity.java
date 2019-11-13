package com.example.thechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    // monitorar mudanças no estado de autenticação
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ArrayAdapter<Usuario> arrayAdapter;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Button logout = (Button) findViewById(R.id.idLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void iniciarFirebase(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void usuario(){
        String userId = "sdf";
        final Query myUser = myRef.child("users").child("bYP3UAp0QTbqPXha6iRal37ONTD3");
        myUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String nome = (String) dataSnapshot.child("nome").getValue();
               String tipo = (String) dataSnapshot.child("tipo").getValue();
                TextView texto = findViewById(R.id.idMeuEmail);
                texto.append(nome + " - " + tipo);
                Toast.makeText(getApplicationContext(), nome, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        iniciarFirebase();
        usuario();
    }

}
