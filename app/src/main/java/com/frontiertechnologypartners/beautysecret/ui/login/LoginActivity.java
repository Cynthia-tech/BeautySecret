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
import com.frontiertechnologypartners.beautysecret.ui.admin.AdminAddNewProductActivity;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.home.HomeActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

import static com.frontiertechnologypartners.beautysecret.util.Constant.ADMIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_ADMIN_LOGIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_FIRST_TIME_LUNCH;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.USER;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_user_name)
    EditText etUserName;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.tv_not_admin_panel_link)
    TextView tvNotAdminPanelLink;

    @BindView(R.id.admin_panel_link)
    TextView tvAdminPanelLink;

    @BindView(R.id.btn_login)
    Button btnLogin;

    private String name, password;
    private String parentDbName = USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
    }

    @OnClick(R.id.btn_login)
    void login() {
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
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
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
                                    Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Paper.book().write(IS_FIRST_TIME_LUNCH, false);
                                    Paper.book().write(IS_ADMIN_LOGIN, true);
                                    Intent intent = new Intent(LoginActivity.this, AdminAddNewProductActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Paper.book().write(IS_FIRST_TIME_LUNCH, false);
                                    Paper.book().write(IS_ADMIN_LOGIN, false);
                                    Paper.book().write(LOGIN_USER_DATA, usersData);
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Username is invalid", Toast.LENGTH_SHORT).show();
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
