package com.frontiertechnologypartners.beautysecret.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.MainActivity;
import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.model.Users;
import com.frontiertechnologypartners.beautysecret.ui.admin.AdminHomeActivity;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.user.HomeActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

import static com.frontiertechnologypartners.beautysecret.util.Constant.ADMIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.FINGER_PRINT_LOGIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_ADMIN_LOGIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_FIRST_TIME_LUNCH;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.USER;


public class LoginActivity extends BaseActivity {
    private EditText etUserName, etPassword;
    private TextView tvNotAdminPanelLink, tvAdminPanelLink;
    private Button btnLogin;
    private ImageView fingerPrintLogin;

    private String name, password;
    private String parentDbName = USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        fingerPrintLogin.setOnClickListener(v -> {
            boolean firstTimeFingerPrintLogin = Paper.book().read(FINGER_PRINT_LOGIN, false);
            if (firstTimeFingerPrintLogin) {
                showFingerPrintDialog();
            } else {
                startActivity(new Intent(LoginActivity.this, FingerPrintLoginActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Toast.makeText(LoginActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void init() {
        etUserName=findViewById(R.id.et_user_name);
        etPassword=findViewById(R.id.et_password);
        tvNotAdminPanelLink=findViewById(R.id.tv_not_admin_panel_link);
        tvAdminPanelLink=findViewById(R.id.admin_panel_link);
        btnLogin=findViewById(R.id.btn_login);
        fingerPrintLogin=findViewById(R.id.iv_finger_print_login);
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
                                    Paper.book().write(LOGIN_USER_DATA, usersData);
                                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
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

    private void showFingerPrintDialog() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
//                Toast.makeText(getApplicationContext(),
//                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
//                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Login successfully", Toast.LENGTH_SHORT).show();
                //login data
                Users userData = Paper.book().read(LOGIN_USER_DATA);
                if (userData.getLoginType().equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
//                Toast.makeText(getApplicationContext(), "Authentication failed",
//                        Toast.LENGTH_SHORT)
//                        .show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Fingerprint Login")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);


    }
}
