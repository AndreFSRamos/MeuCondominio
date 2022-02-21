package br.com.appmeucondominio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Intent intent;
    Button btLogar;
    EditText txtEmail;
    EditText txtPassword;
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// Comando para deixa a tela em FullScreen.
        setContentView(R.layout.activity_main);

       mAuth = FirebaseAuth.getInstance();
        IniciarComponentes();

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  txtEmail.getText().toString();
                String senha = txtPassword.getText().toString();

                if(email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(MainActivity.this, "Email ou senha invalidos!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    ValidarUsuario();
                }
            }
        });
    }
    //==============================================================================================
   @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser userLogado = FirebaseAuth.getInstance().getCurrentUser();

        if(userLogado != null){
            NavPageHome();
        }
    }
    //==============================================================================================
    private void ValidarUsuario(){

        String email =  txtEmail.getText().toString();
        String senha = txtPassword.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "SUCESSO!",
                                    Toast.LENGTH_SHORT).show();
                            NavPageHome();
                        }else{
                            Toast.makeText(MainActivity.this, "Email ou senha invalidos!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //==============================================================================================
    public void NavPageCadastrar(View view){ // Função para navegar até a pagina de cadastro.
        intent = new Intent(MainActivity.this, CadastroUsuarioAdm.class);
        startActivity(intent);
    }
    //==============================================================================================
    public void NavPageHome(){
        intent = new Intent( MainActivity.this, PaginaInicial.class);
        startActivity(intent);
    }
    //==============================================================================================
    public void IniciarComponentes(){
        txtEmail = findViewById(R.id.idEditTextLogin);
        txtPassword = findViewById(R.id.idEditTextSenha);
        btLogar = findViewById(R.id.idBtnEntrar);
    };
    //==============================================================================================
}