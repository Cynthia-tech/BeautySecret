package com.frontiertechnologypartners.beautysecret;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.ui.admin.AdminHomeActivity;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.user.HomeActivity;
import com.frontiertechnologypartners.beautysecret.ui.login.LoginActivity;
import com.frontiertechnologypartners.beautysecret.ui.register.RegisterActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.FireworksPainter;

import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_ADMIN_LOGIN;
import static com.frontiertechnologypartners.beautysecret.util.Constant.IS_FIRST_TIME_LUNCH;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_app_title)
    SyncTextPathView tvTitle;

    @BindView(R.id.tv_for_ladies)
    TextView tvForLadies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        boolean isFirstTimeLunch = Paper.book().read(IS_FIRST_TIME_LUNCH, true);
//        if (!isFirstTimeLunch) {
//            lunchHomeScreen();
//            finish();
//        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // making notification bar transparent
        Util.changeStatusBarColor(this);

        tvTitle.setPathPainter(new FireworksPainter());
        tvTitle.drawPath(1000f);
        tvTitle.setDuration(3000);
        tvTitle.startAnimation(0, 1);

        Animation shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        tvForLadies.startAnimation(shakeAnimation);
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


    @OnClick(R.id.btn_login)
    void accountLoginPage() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_join_now)
    void accountCreatePage() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
