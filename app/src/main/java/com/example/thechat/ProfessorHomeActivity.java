package com.example.thechat;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.thechat.adapter.TabAdapterProfessor;
import com.example.thechat.adapter.TabsAdapter;
import com.example.thechat.config.Conexao;
import com.example.thechat.fragment.AlunosFragment;
import com.example.thechat.helper.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ProfessorHomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        mAuth = Conexao.getAuthFirebase();
        myRef = Conexao.getFirebase();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AlunosFragment alunosFragment = new AlunosFragment();
        fragmentTransaction.add(R.id.ll_professor,alunosFragment);
        fragmentTransaction.commit();

//        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slide_tabs);
//        viewPager = (ViewPager) findViewById(R.id.view_pager);
//
//        // configurar slidingTabLayout
//        slidingTabLayout.setDistributeEvenly(true);
//        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorPrimary));
//
//        // configurar adapter
//        TabAdapterProfessor tabsAdapter = new TabAdapterProfessor( getSupportFragmentManager());
//        viewPager.setAdapter(tabsAdapter);
//
//        slidingTabLayout.setViewPager(viewPager);



        Button logout = (Button) findViewById(R.id.idSairProfessor);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfessorHomeActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
