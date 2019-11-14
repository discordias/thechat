package com.example.thechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thechat.config.Conexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = Conexao.getAuthFirebase();
        myRef = Conexao.getFirebase();



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String id = user.getUid();
                    final Query myUser = myRef.child("users").child(id);

                    myUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String tipo = (String) dataSnapshot.child("tipo").getValue();

                            if(tipo.equals("professor")){
                                startActivity(new Intent(MainActivity.this, ProfessorHomeActivity.class));
                                finish();
                            }else if(tipo.equals("admin")){
                                startActivity(new Intent(MainActivity.this, AdminHomeActivity.class));
                                finish();
                            }else if(tipo.equals("aluno")){
                                startActivity(new Intent(MainActivity.this, AlunoHomeActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

//                    FirebaseAuth.getInstance().signOut();
//                    Toast.makeText(getApplicationContext(), "Não foi possivel identificar tipo de usuario", Toast.LENGTH_SHORT).show();

//                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    Log.d("AUTH", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("AUTH", "onAuthStateChanged:signed_out");
                }

            }
        };






    }


    public void realizarLogin(View view){
        TextView email = findViewById(R.id.idEmail);
        TextView password = findViewById(R.id.idPassword);

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Falha ao realizar login", Toast.LENGTH_SHORT).show();
//                    Log.w("AUTH", "Falha ao efetuar o Login: ", task.getException());
                }else{
                    String txt = "Bem Vindo " + FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
                    //                    Log.d("AUTH", "Login Efetuado com sucesso!!!");
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
