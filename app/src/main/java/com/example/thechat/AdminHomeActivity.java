package com.example.thechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.thechat.config.Conexao;
import com.example.thechat.fragment.ProfessoresFragment;
import com.example.thechat.models.TipoUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Conexão
        mAuth = Conexao.getAuthFirebase();
        myRef = Conexao.getFirebase();
        firebaseUser = Conexao.getFirebaseUser();

        // Menu e barra superior
        Toolbar toolbar = findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



//        Button cadAluno = (Button) findViewById(R.id.idCadAluno);
//        cadAluno.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle enviarTipo = new Bundle();
//                enviarTipo.putString("tipo", TipoUsuario.ALUNO.getTipo());
//                Intent intent = new Intent(AdminHomeActivity.this, AdminCadastroActivity.class);
//                intent.putExtras(enviarTipo);
//                startActivity(intent);
//            }
//        });
//
//        Button cadProfessor = (Button) findViewById(R.id.idCadProfessor);
//        cadProfessor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle enviarTipo = new Bundle();
//                enviarTipo.putString("tipo", TipoUsuario.PROFESSOR.getTipo());
//                Intent intent = new Intent(AdminHomeActivity.this, AdminCadastroActivity.class);
//                intent.putExtras(enviarTipo);
//                startActivity(intent);
//            }
//        });


    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_logout_admin:
                this.logout();
                break;
            case R.id.nav_admin_alunos:
                Bundle enviarTipoAluno = new Bundle();
                enviarTipoAluno.putString("tipo", TipoUsuario.ALUNO.getTipo());
                Intent intentAluno = new Intent(AdminHomeActivity.this, AdminCadastroActivity.class);
                intentAluno.putExtras(enviarTipoAluno);
                startActivity(intentAluno);
                break;
            case R.id.nav_admin_professores:
                Bundle enviarTipoProfessor = new Bundle();
                enviarTipoProfessor.putString("tipo", TipoUsuario.PROFESSOR.getTipo());
                Intent intentProfessor = new Intent(AdminHomeActivity.this, AdminCadastroActivity.class);
                intentProfessor.putExtras(enviarTipoProfessor);
                startActivity(intentProfessor);
                break;
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

}
