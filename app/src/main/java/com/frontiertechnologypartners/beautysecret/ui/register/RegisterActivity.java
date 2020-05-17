package com.frontiertechnologypartners.beautysecret.ui.register;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.login.LoginActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.frontiertechnologypartners.beautysecret.util.Constant.USER;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_user_name)
    EditText etUserName;

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    private String name, phone, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        // making notification bar transparent
        Util.changeStatusBarColor(this);
    }

    @OnClick(R.id.btn_create_account)
    void createAccount() {
        name = etUserName.getText().toString();
        phone = etPhoneNumber.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            etUserName.setError(getString(R.string.require_name));
        } else if (TextUtils.isEmpty(phone)) {
            etPhoneNumber.setError(getString(R.string.require_phone));
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.require_password));
        } else if (TextUtils.isEmpty(password)) {
            etConfirmPassword.setError(getString(R.string.require_confirm_password));
        } else if (!password.equals(confirmPassword)) {
            etPassword.setError(getString(R.string.password_matcher));
            etConfirmPassword.setError(getString(R.string.password_matcher));
        } else {
            if (Util.isNetworkAvailable(getApplicationContext())) {
                loadingBar.show();
                validateRegisterName();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validateRegisterName() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child(USER).child(name).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", name);
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);

                    dbRef.child(USER).child(name).updateChildren(userdataMap)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Something wrong. Please try again later..", Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "This username already exists. Please try again using another name", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingBar.dismiss();
            }
        });
    }
}