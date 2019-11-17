package com.example.thechat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thechat.config.Conexao;
import com.example.thechat.models.ChatUsuarios;
import com.example.thechat.models.Mensagem;
import com.example.thechat.models.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private String nomeUser;
    private String idUserDestinatario;
    private EditText msg;
    private Button btnEnviar;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter adapterMsg;
    private ValueEventListener valueEventListenerMsg;
    private Query query;

    private DatabaseReference myRef;

    private String idChat;

    // dados de quem envia
    private String idUserRemetente;
    private FirebaseUser firebaseUser;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        query.removeEventListener(valueEventListenerMsg);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        msg = (EditText) findViewById(R.id.idMsg);
        btnEnviar = (Button) findViewById(R.id.idMsgEnviar);
        listView = (ListView) findViewById(R.id.lv_chat);


        // dados usuario logado
        firebaseUser = Conexao.getFirebaseUser();
        idUserRemetente = firebaseUser.getUid();
        myRef = Conexao.getFirebase();

        Bundle extra = getIntent().getExtras();
        idUserDestinatario = extra.getString("id");

        if(extra != null){
            nomeUser = extra.getString("nome");

        }
        verificaIdChat();

        // list view

        mensagens = new ArrayList<>();
        adapterMsg = new ArrayAdapter(
          ChatActivity.this,
          android.R.layout.simple_list_item_1,
          mensagens
        );

        listView.setAdapter( adapterMsg );



        valueEventListenerMsg = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mensagens.clear();

                for(DataSnapshot objMensagens: dataSnapshot.getChildren()){
                    Mensagem msgs = objMensagens.getValue(Mensagem.class);
                    mensagens.add(msgs);
                }

                adapterMsg.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        // enviar msg
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtMsg = msg.getText().toString();

                if(txtMsg.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Digite alguma coisa", Toast.LENGTH_LONG).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUserRemetente);
                    mensagem.setMensagem(txtMsg);

                    salvarMsg(idUserRemetente,idUserDestinatario, mensagem );

                    msg.setText("");
                }

            }
        });

    }

    private void verificaIdChat(){
        Query mChat = myRef.child("chats_users").child(idUserRemetente).child("chats");

        mChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                idChat = (String) dataSnapshot.child(idUserDestinatario).child("idChat").getValue();
                Toast.makeText(ChatActivity.this, idChat + " aqui", Toast.LENGTH_LONG).show();

                if(idChat == null){

                   setIdChat(idUserRemetente, idUserDestinatario);
                }

                if(idChat != null ){
                    query = myRef.child("chat").child(idChat);
                    query.addValueEventListener(valueEventListenerMsg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void setIdChat(String idUserRemetente, String idUserDestinatatio){
        if(this.idChat == null){

            this.idChat = myRef.child("chat").push().getKey();
            ChatUsuarios chatUsuarios = new ChatUsuarios();
            chatUsuarios.setIdChat(this.idChat);

            myRef.child("chats_users").child(idUserDestinatatio).child("chats").child(idUserRemetente).setValue(chatUsuarios);
            myRef.child("chats_users").child(idUserRemetente).child("chats").child(idUserDestinatatio).setValue(chatUsuarios);

        }
    }


    private void salvarMsg(String idUserRemetente, String idUserDestinatatio, Mensagem msg){

        if(idChat != null ) {
            myRef.child("chat").child(idChat).push().setValue(msg);
        }
    }

}

