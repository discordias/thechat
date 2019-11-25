package com.example.thechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.thechat.config.Conexao;
import com.example.thechat.fragment.ChatFragment;
import com.example.thechat.fragment.ProfessoresFragment;
import com.example.thechat.helper.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AlunoHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private DrawerLayout drawer;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        // Conexão
        mAuth = Conexao.getAuthFirebase();
        myRef = Conexao.getFirebase();

        // Menu e barra superior
        Toolbar toolbar = findViewById(R.id.toolbarAluno);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        // Fragment lista professores
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        ProfessoresFragment professoresFragment= new ProfessoresFragment();
//        fragmentTransaction.add(R.id.fragment_container_aluno,professoresFragment);
//        fragmentTransaction.commit();


        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_aluno,
                    new ChatFragment()).commit();
        }
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


    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AlunoHomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_logout:
                this.logout();
                break;
            case R.id.nav_professores:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_aluno,
                        new ProfessoresFragment()).commit();
                break;
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_aluno,
                        new ChatFragment()).commit();

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(Gravity.START)){
            drawer.closeDrawer(Gravity.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

