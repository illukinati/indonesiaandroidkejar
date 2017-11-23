package id.ignitech.iak.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.ignitech.iak.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail, edtPassword;
    Button btnMasuk;
    ProgressBar pgBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnMasuk = (Button) findViewById(R.id.btn_masuk);
        pgBar = (ProgressBar) findViewById(R.id.pg_loading);
        pgBar.setVisibility(View.GONE);

        btnMasuk.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view == btnMasuk) {
            pgBar.setVisibility(View.VISIBLE);
            btnMasuk.setVisibility(View.GONE);
            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, FasilActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                pgBar.setVisibility(View.GONE);
                                btnMasuk.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}