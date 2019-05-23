package br.edu.ifsul.firebasedemoesp8266.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifsul.firebasedemoesp8266.R;
import br.edu.ifsul.firebasedemoesp8266.setup.AppSetup;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "loginActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //obtém a referência para o serviço de autenticação (por Singleton)
        mAuth = AppSetup.getAuthInstace();

        //Sign in
        findViewById(R.id.btSignInLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.etEmailLogin)).getText().toString();
                String senha = ((EditText) findViewById(R.id.etSenhaLogin)).getText().toString();
                if(!email.isEmpty() && !senha.isEmpty()){
                    signIn(email, senha);
                }else{
                    Toast.makeText(LoginActivity.this, "Digite os campos email e senha.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Sign up
        findViewById(R.id.btSignUpLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.etEmailLogin)).getText().toString();
                String senha = ((EditText) findViewById(R.id.etSenhaLogin)).getText().toString();
                if(!email.isEmpty() && !senha.isEmpty()){
                    signUp(email, senha);
                }else{
                    Toast.makeText(LoginActivity.this, "Digite os campos email e senha.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w(TAG, "createUserWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Usuário logado.", Toast.LENGTH_SHORT).show();
                            AppSetup.user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, ArCondicioandoActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure" + task.getException());
                            Toast.makeText(LoginActivity.this, "Usuário ou senha incorreto.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Não cadastrou. Email já cadastrado.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

}
