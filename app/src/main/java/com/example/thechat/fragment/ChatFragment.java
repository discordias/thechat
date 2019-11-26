package com.example.thechat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thechat.ChatActivity;
import com.example.thechat.PerfilActivity;
import com.example.thechat.R;
import com.example.thechat.config.Conexao;
import com.example.thechat.models.ChatUsuarios;
import com.example.thechat.models.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private List<ChatUsuarios> listaChat = new ArrayList<>();
    private ChatUsuarios chats;
    private ArrayAdapter<ChatUsuarios> arrayAdapter;
    private ListView listView;
    private DatabaseReference myRef;
    private ValueEventListener valueEventListenerUsers;
    private Query query;
    private FirebaseUser firebaseUser;
    private String idCurrentUser;

    @Override
    public void onStart() {
        super.onStart();
        query.addValueEventListener(valueEventListenerUsers);
    }

    @Override
    public void onStop() {
        super.onStop();
        query.removeEventListener(valueEventListenerUsers);
    }

    @Override
    public void onPause() {
        super.onPause();
        query.removeEventListener(valueEventListenerUsers);
    }

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        firebaseUser = Conexao.getFirebaseUser();
        this.idCurrentUser = firebaseUser.getUid();


        listView = view.findViewById(R.id.lv_list_chat);
        // Para adicionar listagem
        arrayAdapter = new ArrayAdapter<ChatUsuarios>(
                getActivity(), R.layout.lista_chat, listaChat
        );
        listView.setAdapter(arrayAdapter);

        myRef = Conexao.getFirebase();


        query = myRef.child("chats_users").child(this.idCurrentUser).child("chats")
                     .orderByChild("status").equalTo(Boolean.TRUE);
        valueEventListenerUsers = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaChat.clear();

                for(DataSnapshot objChat: dataSnapshot.getChildren()){
                    chats = objChat.getValue(ChatUsuarios.class);
                    listaChat.add(chats);
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        // Ir para tela de chat
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);

                // recupera dados passados
                ChatUsuarios chat = listaChat.get(position);

                // enviar para tela de chat
                intent.putExtra("nome", chat.getNome());
//              intent.putExtra("email",chat.getEmail());
                intent.putExtra("id",chat.getIdUser());


                startActivity(intent);
            }
        });

        // click longo - abrir perfil
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);

                // recupera dados passados
                ChatUsuarios chat = listaChat.get(position);

                Bundle enviarId = new Bundle();
                enviarId.putString("id", chat.getIdUser());
                Intent intentPerfil = new Intent(getContext(), PerfilActivity.class);
                intentPerfil.putExtras(enviarId);
                startActivity(intentPerfil);

                return true;
            }
        });


        return view;
    }

}
