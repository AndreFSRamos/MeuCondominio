package br.com.appmeucondominio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

import br.com.ferramentas.Temporizador;
import br.com.model.UsuarioAdm;

public class CadastroUsuarioAdm extends AppCompatActivity {

    //==============================================================================================
    private FirebaseAuth mAuth;
    Intent intent;// Intent explicita para auxiliar na  navegabilidade.
    TextView nome;
    TextView email;
    TextView senha;
    TextView confSenha;
    ProgressBar progressBar;
    TextView textView;
    String TAG;
    Temporizador temporizador;

    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// comando para deixar a tela com FULLSCRENN.
        setContentView(R.layout.activity_cadastro_usuario_adm);
        mAuth = FirebaseAuth.getInstance();
        InciaComponentes();
    }
    //==============================================================================================
    @SuppressLint("SetTextI18n")
    public void NavMainCadastrar(View view) {// Função para concluir o cadastro e validar  os campos.
        progressBar.setVisibility(View.VISIBLE);// Comando para deixar icone de processamento Visivel.
        Log.w(TAG, "debug 01");
        if (ValidaCadastro(progressBar, nome, email, senha, confSenha)) {// Esse IF chama a função
            //ValidaCadastro e caso seja TRUE ele finaliza o cadastro, SENÃO exibi uma messagem de
            // erro  e apaga os dados dos campos.
            Log.w(TAG, "debug 02");
            // Criação da autenticação de email e senha.
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), senha.getText()
                    .toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){// Verifica de a tarefa foi concluida com sucesso.
                        UsuarioAdm usuario = new UsuarioAdm(nome.getText().toString(), email.getText()
                                .toString(), senha.getText().toString());
                        Log.w(TAG, "debug 02.1");
                        Log.w(TAG, "debug 02.2");

                        if (task.isSuccessful()) {
                            usuario.setId(mAuth.getUid());
                            usuario.Salvar();
                            Log.w(TAG, "debug 03");
                            progressBar.setVisibility(View.VISIBLE);
                            textView.setText("Cadastro efetuado com sucesso!");
                            textView.setVisibility(View.VISIBLE);//Torna o campão visivel para o usuario.
                            Log.w(TAG, "debug 04");
                            Timer timer = new Timer();//instaciemnto da ferramenta TIMER.
                            timer.schedule(new TimerTask() {// temporizador para executar a troca de tela em 3 segundos,
                                // deixando a messagem de sucesso visivel por esse tempo.
                                @Override
                                public void run() {
                                    textView.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }, 2000);
                            Log.w(TAG, "debug 05");
                            intent = new Intent(CadastroUsuarioAdm.this, PaginaInicial.class);//Indica a direção para a MAINACTIVITY.
                            startActivity(intent);//execulta a troca de tela.
                        } else {
                            nome.setText(" ");
                            email.setText(" ");
                            senha.setText("");
                            confSenha.setText("");
                            textView.setVisibility(View.VISIBLE);//Torna o campão visivel para o usuario.
                            textView.setText("Algo deu errado, tente novamente!");
                            Log.w(TAG, "debug 06");
                            Timer timer = new Timer();//instaciemnto da ferramenta TIMER.
                            timer.schedule(new TimerTask() {// temporizador para executar a troca de tela em 3 segundos,
                                // deixando a messagem de sucesso visivel por esse tempo.
                                @Override
                                public void run() {
                                    textView.setVisibility(View.INVISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }, 2000);
                        }
                    }
                }
            });
        } else {
            nome.setText(" ");
            email.setText(" ");
            senha.setText("");
            confSenha.setText("");
            textView.setVisibility(View.VISIBLE);//Torna o campão visivel para o usuario.
            textView.setText("Algo deu errado, tente novamente!");
            Log.w(TAG, "debug 07");
            Timer timer = new Timer();//instaciemnto da ferramenta TIMER.
            timer.schedule(new TimerTask() {// temporizador para executar a troca de tela em 3 segundos,
                // deixando a messagem de sucesso visivel por esse tempo.
                @Override
                public void run() {
                    textView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 2000);
        }
    }
    //==============================================================================================
    //Função do tipo Boolean pra validar dos os campos da tela de cadastro, caso uma campo está
    // invalido será exibida a messagem correspondente ao compo para o usuario.
    public boolean ValidaCadastro(ProgressBar progressBar, TextView nome,
                                  TextView email, TextView senha, TextView confSenha){

        // Converções dos dados recebidos por parametro para o tipo STRING.
        String textNome = nome.getText().toString();
        String textEmail = email.getText().toString();
        String textSenha = senha.getText().toString();
        String textConfSenha = confSenha.getText().toString();
        Log.i(TAG, "debug 03 - invicio da validaçõa");

        if (textNome.isEmpty()) {// valida se o campo NOME está vazio.
            progressBar.setVisibility(View.INVISIBLE);// Comando para deixar o icone de processamento oculto.
            nome.setError("O campo 'Nome' é obrigatório!");
            nome.requestFocus();
            return false;
        }

        if (textEmail.isEmpty()) {// Valida se o campo EMAIL está vazio.
            progressBar.setVisibility(View.INVISIBLE);
            email.setError("O campo 'Email' é obrigatório!");
            email.requestFocus();
            return false;
        }

        if (textSenha.length() < 6) { // Valida se o campo SENHA tem menos que 6 digitos.
            progressBar.setVisibility(View.INVISIBLE);
            senha.setError("Senha digitada é menor que 6 digitos!");
            senha.requestFocus();
            return false;
        }

        if (textSenha.length() > 8) {// VAlida se o campo SENHA Tem mais que 8 digitos.
            progressBar.setVisibility(View.INVISIBLE);
            senha.setError("Senha digitada é maior que 8 digitos!");
            senha.requestFocus();
            return false;
        }

        if (textConfSenha.length() < 6) { // Valida se o campoCONFIRMAR SENHA tem menos que 6 digitos.
            progressBar.setVisibility(View.INVISIBLE);
            confSenha.setError("Senha digitada é menor que 6 digitos!");
            confSenha.requestFocus();
            return false;
        }

        if (textConfSenha.length() > 8) {// VAlida se o campo CONFIRMAR SENHA Tem mais que 8 digitos.
            progressBar.setVisibility(View.INVISIBLE);
            confSenha.setError("Senha digitada é maior que 8 digitos!");
            confSenha.requestFocus();
            return false;
        }

        if(!textConfSenha.equals(textSenha)) {// Valida se SENHA digitada ´é igual a SENHA COMFIRMADA.
            progressBar.setVisibility(View.INVISIBLE);
            confSenha.setError("As senha digitadas são diferentes!.");
            confSenha.requestFocus();
            return false;
        }
        Log.i(TAG, "debug 01 - validação");
        return true;
    }
    //==============================================================================================

    public void NavMainCancelar(View view){// Função limpa os todos os campos preechidos
        // e retorna a tela de login quando o usuario clica no botão "CANCELAR".
        nome.setText(" ");
        email.setText(" ");
        senha.setText("");
        confSenha.setText("");
        intent = new Intent(CadastroUsuarioAdm.this, MainActivity.class);
        startActivity(intent);
    }
    //==============================================================================================
    public void InciaComponentes(){
        nome = findViewById(R.id.IdCadastroNome);
        email = findViewById(R.id.idCadastroEmail);
        senha = findViewById(R.id.idCadastroSenha);
        confSenha = findViewById(R.id.IdCadastroConfSenha);
        progressBar = findViewById(R.id.IdIconeCadastro);// Incone para indicar processamento
        textView = findViewById(R.id.idTextSucessoCadastro);//Campo para informar o resultado do cadasdtro.
    }
    //==============================================================================================
}