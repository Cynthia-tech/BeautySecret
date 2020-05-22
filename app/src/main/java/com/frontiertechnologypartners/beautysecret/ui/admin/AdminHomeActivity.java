package com.frontiertechnologypartners.beautysecret.ui.admin;


import android.content.Intent;
import android.os.Bundle;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        ButterKnife.bind(this);
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.admin_add_product_btn)
    void addNewProduct() {
        Intent intent = new Intent(AdminHomeActivity.this, AdminAddNewProductActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.admin_check_orders)
    void checkOrders() {
        Intent intent = new Intent(AdminHomeActivity.this, AdminCheckOrdersActivity.class);
        startActivity(intent);
    }
}
