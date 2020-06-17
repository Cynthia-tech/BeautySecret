package com.frontiertechnologypartners.beautysecret.ui.admin.admin_home;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.frontiertechnologypartners.beautysecret.MainActivity;
import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.admin.add_new_product.AdminAddNewProductActivity;
import com.frontiertechnologypartners.beautysecret.ui.admin.check_order.AdminCheckOrdersActivity;
import com.frontiertechnologypartners.beautysecret.ui.admin.maintain_product.AdminMaintainProductActivity;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;

import io.paperdb.Paper;

public class AdminHomeActivity extends BaseActivity {
    private Button btnAddNewProduct, btnMaintainProduct, btnCheckOrders, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        init();
        btnAddNewProduct.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminAddNewProductActivity.class);
            startActivity(intent);
        });
        btnMaintainProduct.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminMaintainProductActivity.class);
            startActivity(intent);
        });
        btnCheckOrders.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, AdminCheckOrdersActivity.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(v -> {
            Paper.book().destroy();
            Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void init() {
        btnAddNewProduct = findViewById(R.id.admin_add_product_btn);
        btnMaintainProduct = findViewById(R.id.admin_maintain_product_btn);
        btnCheckOrders = findViewById(R.id.admin_check_orders);
        btnLogout = findViewById(R.id.admin_logout_btn);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
