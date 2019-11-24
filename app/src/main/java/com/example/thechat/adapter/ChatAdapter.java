package com.example.thechat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thechat.R;
import com.example.thechat.models.Mensagem;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList mensagens;
    private String idUserRemetente;
    private String currentMensagemUserId = "";

    public ChatAdapter(Context context, ArrayList<Mensagem> mensagens, String idUserRemetente) {
        super(context, 0, mensagens);
        this.context = context;
        this.mensagens = mensagens;
        this.idUserRemetente = idUserRemetente;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(mensagens != null){

            // diferencia quem envia a mensagem

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            Mensagem mensagem = (Mensagem) mensagens.get(position);

            if(this.currentMensagemUserId.equals(mensagem.getIdUsuario())){


                // Montar a view a partir do xml
                if(idUserRemetente.equals(mensagem.getIdUsuario())){
                    view = inflater.inflate(R.layout.mensagem_remetente, parent, false);
                }else{
                    view = inflater.inflate(R.layout.mensagem_destino, parent, false);
                }

            }else{

                this.currentMensagemUserId = mensagem.getIdUsuario();

                // Montar a view a partir do xml
                if(idUserRemetente.equals(mensagem.getIdUsuario())){
                    view = inflater.inflate(R.layout.mensagem_remetente, parent, false);
                }else{
                    view = inflater.inflate(R.layout.mensagem_destino, parent, false);
                }
            }



            TextView texto = (TextView) view.findViewById(R.id.tv_mensagem);
            texto.setText(mensagem.getMensagem());

        }

        return view;
    }
}
