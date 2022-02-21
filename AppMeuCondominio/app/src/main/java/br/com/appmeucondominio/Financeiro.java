package br.com.appmeucondominio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

public class Financeiro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// Comando para deixa a tela em FullScreen.
        setContentView(R.layout.activity_financeiro);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}