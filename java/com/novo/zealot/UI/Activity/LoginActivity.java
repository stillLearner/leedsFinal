package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.novo.zealot.DB.SPSaveLogin;
import com.novo.zealot.R;

import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText login_ed_num, login_ed_pwd;
    private Button login_btn_load;
    private TextView login_tv_register, login_tv_xieyi, login_tv_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        retrieveSavedUserInfo();
    }

    private void initViews() {
        // Registration key
        login_tv_register = findViewById(R.id.login_tv_register);
        login_tv_register.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Welcome to Register", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Related agreement window
        login_tv_xieyi = findViewById(R.id.login_tv_xieyi);
        login_tv_xieyi.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Prompt")
                    .setMessage("The 'User Service Agreement', 'Privacy Agreement', and 'Carrier Agreement' are being fully revised. Please be patient.")
                    .setPositiveButton("OK", null);
            builder.create().show();
        });

        login_ed_num = findViewById(R.id.login_et_number);
        login_ed_pwd = findViewById(R.id.login_et_password);
        login_btn_load = findViewById(R.id.login_btn_load);
        login_btn_load.setOnClickListener(this);
    }

    private void retrieveSavedUserInfo() {
        Map<String, String> userInfo = SPSaveLogin.getUserInfo(this);
        if (userInfo != null) {
            login_ed_num.setText(userInfo.get("number"));
            login_ed_pwd.setText(userInfo.get("password"));
        }
    }

    @Override
    public void onClick(View view) {
        String username = login_ed_num.getText().toString().trim();
        String password = login_ed_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        String savedUsername = SPSaveLogin.getUserInfo(this).get(SPSaveLogin.KEY_USERNAME);
        String savedPassword = SPSaveLogin.getUserInfo(this).get(SPSaveLogin.KEY_PASSWORD);

        if (username.equals(savedUsername) && password.equals(savedPassword)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
        }
    }
}



