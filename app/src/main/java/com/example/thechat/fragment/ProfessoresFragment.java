package com.example.thechat.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thechat.R;
import com.example.thechat.config.Conexao;
import com.example.thechat.models.Usuario;
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
public class ProfessoresFragment extends Fragment {

    private List<Usuario> listaUsuario = new ArrayList<>();
    private ArrayAdapter<Usuario> arrayAdapter;
    private ListView listView;
    private DatabaseReference myRef;
    private ValueEventListener valueEventListenerUsers;
    private Query query;

    public ProfessoresFragment() {
        // Required empty public constructor
    }


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_professores, container, false);

        listView = view.findViewById(R.id.lv_professor);

        arrayAdapter = new ArrayAdapter<Usuario>(
                getActivity(), R.layout.lista_professor, listaUsuario
        );

        listView.setAdapter(arrayAdapter);

        myRef = Conexao.getFirebase();

        query = myRef.child("users").orderByChild("tipo").equalTo("PROFESSOR");

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

        return view;
    }

}
