package br.com.appmeucondominio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PaginaInicial extends AppCompatActivity {

    Intent intent;
   /* TextView nomeUsuario;
    FirebaseAuth mAuth;
    String teste;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// Comando para deixa a tela em FullScreen.
        setContentView(R.layout.activity_pagina_inicial);
       //teste = mAuth.getUid().toString();
        CarregaDadosUserLogado();


    }


    public void Sair(View view){
        intent = new Intent( PaginaInicial.this, MainActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(intent);
    }

    public void NavFinanceiro (View view){
        intent = new Intent(PaginaInicial.this, Financeiro.class);
        startActivity(intent);
    }

    public void CarregaDadosUserLogado(){ ;
    }
}