package com.example.thechat.fragment;


import android.content.Intent;
import android.os.Bundle;
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
import com.example.thechat.R;
import com.example.thechat.config.Conexao;
import com.example.thechat.models.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlunosFragment extends Fragment {

    private List<Usuario> listaUsuario = new ArrayList<>();
    private ArrayAdapter<Usuario> arrayAdapter;
    private ListView listView;
    private DatabaseReference myRef;
    private ValueEventListener valueEventListenerUsers;
    private Query query;

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

    public AlunosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_alunos, container, false);

        myRef = Conexao.getFirebase();

        listView = view.findViewById(R.id.lv_aluno);
        // Para adicionar listagem
        arrayAdapter = new ArrayAdapter<Usuario>(
                getActivity(), R.layout.lista_aluno, listaUsuario
        );
        listView.setAdapter(arrayAdapter);


        query = myRef.child("users").orderByChild("tipo").equalTo("ALUNO");

        valueEventListenerUsers = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaUsuario.clear();

                for(DataSnapshot objUsers: dataSnapshot.getChildren()){
                    Usuario user = objUsers.getValue(Usuario.class);
                    listaUsuario.add(user);
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
                Usuario user = listaUsuario.get(position);

                // enviar para tela de chat
                intent.putExtra("nome", user.getNome());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("id",user.getId());

                startActivity(intent);
            }
        });


        return view;
    }

}
