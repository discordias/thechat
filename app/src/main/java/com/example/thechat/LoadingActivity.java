package com.example.thechat;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.thechat.config.Conexao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoadingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

//        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

                            Toast.makeText(LoadingActivity.this, tipo + " testando",Toast.LENGTH_LONG).show();

                            if(tipo != null && tipo.equals("PROFESSOR")){
                                startActivity(new Intent(LoadingActivity.this, ProfessorHomeActivity.class));
                                finish();
                            }else if(tipo != null && tipo.equals("ADMIN")){
                                startActivity(new Intent(LoadingActivity.this, AdminHomeActivity.class));
                                finish();
                            }else if(tipo != null && tipo.equals("ALUNO")){
                                startActivity(new Intent(LoadingActivity.this, AlunoHomeActivity.class));
                                finish();
                            }else{
                                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
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
                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                    finish();
                    Log.d("AUTH", "onAuthStateChanged:signed_out");
                }

            }
        };

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(getBaseContext(), MainActivity.class));
//                finish();
//            }
//        }, 5000);
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

//    private void iniciarFirebase(){
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference();
//    }
}
