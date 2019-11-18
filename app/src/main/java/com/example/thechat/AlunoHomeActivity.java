package com.example.thechat;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.thechat.adapter.TabsAdapter;
import com.example.thechat.config.Conexao;
import com.example.thechat.fragment.AlunosFragment;
import com.example.thechat.fragment.ProfessoresFragment;
import com.example.thechat.helper.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlunoHomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        mAuth = Conexao.getAuthFirebase();
        myRef = Conexao.getFirebase();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ProfessoresFragment professoresFragment= new ProfessoresFragment();
        fragmentTransaction.add(R.id.ll_aluno,professoresFragment);
        fragmentTransaction.commit();
//
//        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slide_tabs);
//        viewPager = (ViewPager) findViewById(R.id.view_pager);
//
//        // configurar slidingTabLayout
//        slidingTabLayout.setDistributeEvenly(true);
//        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorPrimary));
//
//        // configurar adapter
//        TabsAdapter tabsAdapter = new TabsAdapter( getSupportFragmentManager());
//        viewPager.setAdapter(tabsAdapter);
//
//        slidingTabLayout.setViewPager(viewPager);

        Button logout = (Button) findViewById(R.id.idSairAluno);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AlunoHomeActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
