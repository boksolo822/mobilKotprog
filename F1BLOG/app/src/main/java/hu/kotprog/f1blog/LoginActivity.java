package hu.kotprog.f1blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText logEmail;
    private EditText logPass;
    private Button logButton;
    private Button newAccButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        logEmail = (EditText) findViewById(R.id.loginEmail);
        logPass = (EditText) findViewById(R.id.loginPassword);
        logButton = (Button) findViewById(R.id.loginButton);
        newAccButton=(Button)findViewById(R.id.toRegButton);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String email=logEmail.getText().toString();
            String password=logPass.getText().toString();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent toBlogs=new Intent(LoginActivity.this,ListBlogsActivity.class);
                        startActivity(toBlogs);
                    }
                    else{
                        System.out.println(task.getException().getMessage());
                    }
                }
            });
            }
        });


        newAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toReg();
            }
        });
    }


    public void toReg(){
        Intent toRegAct=new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(toRegAct);
    }

}