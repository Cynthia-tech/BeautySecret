package com.frontiertechnologypartners.beautysecret.ui.loreal;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.common.ProductsActivity;
import com.frontiertechnologypartners.beautysecret.ui.maybelline.MaybellineLipCosmeticActivity;


import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT_TYPE;

public class LorealEyeCosmeticActivity extends BaseActivity {
    private LinearLayout product1Layout, product2Layout, product3Layout, product4Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loreal_eye_cosmetic);
        init();
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        product1Layout.setOnClickListener(v -> {
            Intent intent = new Intent(LorealEyeCosmeticActivity.this, ProductsActivity.class);
            intent.putExtra(PRODUCT_TYPE, 13);
            startActivity(intent);
        });

        product2Layout.setOnClickListener(v -> {
            Intent intent = new Intent(LorealEyeCosmeticActivity.this, ProductsActivity.class);
            intent.putExtra(PRODUCT_TYPE, 14);
            startActivity(intent);
        });

        product3Layout.setOnClickListener(v -> {
            Intent intent = new Intent(LorealEyeCosmeticActivity.this, ProductsActivity.class);
            intent.putExtra(PRODUCT_TYPE, 15);
            startActivity(intent);
        });

        product4Layout.setOnClickListener(v -> {
            Intent intent = new Intent(LorealEyeCosmeticActivity.this, ProductsActivity.class);
            intent.putExtra(PRODUCT_TYPE, 16);
            startActivity(intent);
        });
    }

    private void init() {
        product1Layout = findViewById(R.id.product1_layout);
        product2Layout = findViewById(R.id.product2_layout);
        product3Layout = findViewById(R.id.product3_layout);
        product4Layout = findViewById(R.id.product4_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
