package com.frontiertechnologypartners.beautysecret;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.ui.admin.admin_home.AdminHomeActivity;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.user.home.HomeActivity;
import com.frontiertechnologypartners.beautysecret.ui.login.LoginActivity;
import com.frontiertechnologypartners.beautysecret.ui.user.register.RegisterActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;

import io.paperdb.Paper;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.FireworksPainter;

import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_ADMIN_LOGIN;

public class MainActivity extends BaseActivity {
    private SyncTextPathView tvTitle;
    private TextView tvForLadies;
    private Button btnJoinNow, btnAlreadyLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        boolean isFirstTimeLunch = Paper.book().read(IS_FIRST_TIME_LUNCH, true);
//        if (!isFirstTimeLunch) {
//            lunchHomeScreen();
//            finish();
//        }

        setContentView(R.layout.activity_main);
        init();

        // making notification bar transparent
        Util.changeStatusBarColor(this);

        tvTitle.setPathPainter(new FireworksPainter());
        tvTitle.drawPath(1000f);
        tvTitle.setDuration(3000);
        tvTitle.startAnimation(0, 1);

        Animation shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        tvForLadies.startAnimation(shakeAnimation);
        tvForLadies.setText(getText(R.string.for_ladies));

        btnJoinNow.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnAlreadyLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        tvTitle = findViewById(R.id.tv_app_title);
        tvForLadies = findViewById(R.id.tv_for_ladies);
        btnJoinNow = findViewById(R.id.btn_join_now);
        btnAlreadyLogin = findViewById(R.id.btn_login);
    }

    private void lunchHomeScreen() {
        boolean isAdminLogin = Paper.book().read(IS_ADMIN_LOGIN, false);
        if (isAdminLogin) {
            startActivity(new Intent(MainActivity.this, AdminHomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }

    }
}
