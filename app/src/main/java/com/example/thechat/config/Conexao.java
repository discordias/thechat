package com.example.thechat.config;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Conexao {

    private static DatabaseReference myRef;
    private static FirebaseAuth mAuth;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static FirebaseUser firebaseUser;
    private static String userLogado;

    public static DatabaseReference getFirebase(){
        if(myRef == null){
            myRef = FirebaseDatabase.getInstance().getReference();

        }
        return myRef;
    }

    public static String getUserLogado(){
        if(userLogado != null){
            return userLogado;
        }else{
            return "";
        }
    }

    public static void setUserLogado(String nome){
        userLogado = nome;
    }

    public static FirebaseAuth getAuthFirebase(){
        if( mAuth == null){
            inicializarFirebaseAuth();
        }
        return mAuth;
    }

    private static void inicializarFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = firebaseAuth.getCurrentUser();
                if(usuario != null){
                    firebaseUser = usuario;
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    public static FirebaseAuth.AuthStateListener getmAuthListener(){
        return mAuthListener;
    }

    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public static void logout(){
        mAuth.signOut();
    }
}
