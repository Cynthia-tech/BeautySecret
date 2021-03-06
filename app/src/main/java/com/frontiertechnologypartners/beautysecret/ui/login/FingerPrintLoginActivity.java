package com.frontiertechnologypartners.beautysecret.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.model.Users;
import com.frontiertechnologypartners.beautysecret.ui.admin.admin_home.AdminHomeActivity;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.user.home.HomeActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import io.paperdb.Paper;

import static com.frontiertechnologypartners.beautysecret.util.Constant.ADMIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.FINGER_PRINT_LOGIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_ADMIN_LOGIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_FIRST_TIME_LUNCH;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.USER;


public class FingerPrintLoginActivity extends BaseActivity {
    private EditText etUserName, etPassword;
    private TextView tvNotAdminPanelLink, tvAdminPanelLink;
    private Button btnLogin;

    private String name, password;
    private String parentDbName = USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_finger_print);
        init();
        // making notification bar transparent
        Util.changeStatusBarColor(this);

        tvNotAdminPanelLink.setOnClickListener(v -> {
            btnLogin.setText(R.string.login);
            tvAdminPanelLink.setVisibility(View.VISIBLE);
            tvNotAdminPanelLink.setVisibility(View.INVISIBLE);
            parentDbName = USER;
        });
        tvAdminPanelLink.setOnClickListener(v -> {
            btnLogin.setText(R.string.login_admin);
            tvAdminPanelLink.setVisibility(View.INVISIBLE);
            tvNotAdminPanelLink.setVisibility(View.VISIBLE);
            parentDbName = ADMIN;
        });
        btnLogin.setOnClickListener(v -> {
            name = etUserName.getText().toString();
            password = etPassword.getText().toString();

            if (TextUtils.isEmpty(name)) {
                etUserName.setError(getString(R.string.require_name));
            } else if (TextUtils.isEmpty(password)) {
                etPassword.setError(getString(R.string.require_password));
            } else {
                if (Util.isNetworkAvailable(getApplicationContext())) {
                    loadingBar.show();
                    allowAccessToAccount();
                } else {
                    Toast.makeText(FingerPrintLoginActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
        tvNotAdminPanelLink = findViewById(R.id.tv_not_admin_panel_link);
        tvAdminPanelLink = findViewById(R.id.admin_panel_link);
        btnLogin = findViewById(R.id.btn_login);
    }

    private void allowAccessToAccount() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(name).exists()) {
                    Users usersData = dataSnapshot.child(parentDbName).child(name).getValue(Users.class);
                    if (usersData != null) {
                        if (usersData.getName().equals(name)) {
                            if (usersData.getPassword().equals(password)) {
                                if (parentDbName.equals(ADMIN)) {
                                    Toast.makeText(FingerPrintLoginActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Paper.book().write(IS_FIRST_TIME_LUNCH, false);
                                    Paper.book().write(IS_ADMIN_LOGIN, true);
                                    Paper.book().write(LOGIN_USER_DATA, usersData);
                                    Paper.book().write(FINGER_PRINT_LOGIN, true);
                                    Intent intent = new Intent(FingerPrintLoginActivity.this, AdminHomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(FingerPrintLoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Paper.book().write(IS_FIRST_TIME_LUNCH, false);
                                    Paper.book().write(IS_ADMIN_LOGIN, false);
                                    Paper.book().write(LOGIN_USER_DATA, usersData);
                                    Paper.book().write(FINGER_PRINT_LOGIN, true);
                                    Intent intent = new Intent(FingerPrintLoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(FingerPrintLoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(FingerPrintLoginActivity.this, "Username is invalid", Toast.LENGTH_SHORT).show();
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
