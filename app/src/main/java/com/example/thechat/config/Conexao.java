package com.example.thechat.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Conexao {

    private static DatabaseReference myRef;
    private static FirebaseAuth mAuth;

    public static DatabaseReference getFirebase(){
        if(myRef == null){
            myRef = FirebaseDatabase.getInstance().getReference();
        }
        return myRef;
    }

    public static FirebaseAuth getAuthFirebase(){
        if( mAuth == null){
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

}
