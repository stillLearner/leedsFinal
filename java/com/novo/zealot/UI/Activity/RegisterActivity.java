package com.novo.zealot.UI.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.novo.zealot.DB.SPSaveLogin;
import com.novo.zealot.R;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button register_btn_return, register_btn_register;
    private EditText register_et_number, register_et_password, register_et_again;
    private EditText edage1, edsg1, edtz1, edgy1, eddy1, edxt1;
    private boolean isInfoChecked = false;
    private String number, password, again;
    private String age1, sg1, tz1, gy1, dy1, xt1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
    }

    private void initViews() {
        register_btn_return = findViewById(R.id.register_btn_return);
        register_btn_return.setOnClickListener(v -> {
            Toast.makeText(RegisterActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        register_et_number = findViewById(R.id.register_et_number);
        register_et_password = findViewById(R.id.register_et_password);
        register_et_again = findViewById(R.id.register_et_again);
        edage1 = findViewById(R.id.age1);
        eddy1 = findViewById(R.id.dy1);
        edgy1 = findViewById(R.id.gy1);
        edtz1 = findViewById(R.id.tz1);
        edxt1 = findViewById(R.id.xt1);
        edsg1 = findViewById(R.id.sg1);
        register_btn_register = findViewById(R.id.register_btn_register);
        register_btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_btn_register) {
            gatherInputData();

            if (validateInput()) {
                if (!isInfoChecked) {
                    promptUserConfirmation();
                } else {
                    completeRegistration();
                }
            }
        }
    }

    private void gatherInputData() {
        number = register_et_number.getText().toString().trim();
        password = register_et_password.getText().toString();
        again = register_et_again.getText().toString().trim();
        age1 = edage1.getText().toString().trim();
        sg1 = edsg1.getText().toString().trim();
        tz1 = edtz1.getText().toString().trim();
        gy1 = edgy1.getText().toString().trim();
        dy1 = eddy1.getText().toString().trim();
        xt1 = edxt1.getText().toString().trim();
    }

    private boolean validateInput() {
        if (number.length() == 0) {
            Toast.makeText(this, "Wrong username format！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() == 0) {
            Toast.makeText(this, "Wrong password format", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(again)) {
            Toast.makeText(this, "Different！" + "\n" + "First time：" + password + "\n" + "Second time：" + again, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void promptUserConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Information")
                .setMessage("Username： " + number + "\npassword： " + again +
                        "\nPlease click the register to finish registration")
                .setPositiveButton("Yes", null);
        builder.create().show();

        boolean isSaveSuccess = SPSaveLogin.saveUserInfo(this, number, password, again, gy1, dy1, age1, sg1, tz1, xt1);
        if (isSaveSuccess) {
            Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
        }
        isInfoChecked = true;
    }

    private void completeRegistration() {
        Intent intent = getIntent();
        setResult(0, intent);
        finish();
    }
}
