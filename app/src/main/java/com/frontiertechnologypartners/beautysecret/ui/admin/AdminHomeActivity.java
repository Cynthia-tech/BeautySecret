package com.frontiertechnologypartners.beautysecret.ui.admin;


import android.content.Intent;
import android.os.Bundle;

import com.frontiertechnologypartners.beautysecret.MainActivity;
import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class AdminHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.admin_add_product_btn)
    void addNewProduct() {
        Intent intent = new Intent(AdminHomeActivity.this, AdminAddNewProductActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.admin_maintain_product_btn)
    void maintainProduct() {
        Intent intent = new Intent(AdminHomeActivity.this, AdminMaintainProductActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.admin_check_orders)
    void checkOrders() {
        Intent intent = new Intent(AdminHomeActivity.this, AdminCheckOrdersActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.admin_logout_btn)
    void adminLogout() {
        Paper.book().destroy();
        Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
