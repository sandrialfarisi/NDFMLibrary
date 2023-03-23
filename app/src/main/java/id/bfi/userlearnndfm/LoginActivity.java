package id.bfi.userlearnndfm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.bfi.userlearnndfm.MateriNDFM.MateriActivity;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin;
    int setVisible = 1;
    ImageView ivVisible;
    SweetAlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        ivVisible = findViewById(R.id.ivVisible);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });

        ivVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setVisible == 1){
                    setVisible = 0;
                    etPass.setTransformationMethod(null);
                    if (etPass.getText().length()>0)
                        etPass.setSelection(etPass.getText().length());
                    ivVisible.setBackgroundResource(R.drawable.ic_remove_red_eye_black_24dp);
                } else {
                    setVisible = 1;
                    etPass.setTransformationMethod(new PasswordTransformationMethod());
                    if (etPass.getText().length()>0)
                        etPass.setSelection(etPass.getText().length());
                    ivVisible.setBackgroundResource(R.drawable.ic_remove_eye_black_24dp);
                }
            }
        });
    }

    private void loginUserAccount(){
        String email, password;

        email = etEmail.getText().toString();
        password = etPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please input Email...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please input Password...", Toast.LENGTH_SHORT).show();
            return;
        }
        pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(true);
        pDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pDialog.dismissWithAnimation();
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(LoginActivity.this, MateriActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    pDialog.setCancelable(true);
                    Toast.makeText(LoginActivity.this, "Login failed! Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    pDialog.dismissWithAnimation();
                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseAuthInvalidUserException) {
                    pDialog.dismissWithAnimation();
                    Toast.makeText(LoginActivity.this, "Incorrect Email Address", Toast.LENGTH_SHORT).show();
                } else {
                    pDialog.dismissWithAnimation();
                    Toast.makeText(LoginActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
