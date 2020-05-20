package com.frontiertechnologypartners.beautysecret.ui.revlon;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.common.ProductsActivity;
import com.frontiertechnologypartners.beautysecret.ui.loreal.LorealLipCosmeticActivity;

import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT_TYPE;

public class RevlonEyeCosmeticActivity extends BaseActivity {
    @BindView(R.id.product1_layout)
    LinearLayout product1Layout;

    @BindView(R.id.product2_layout)
    LinearLayout product2Layout;

    @BindView(R.id.product3_layout)
    LinearLayout product3Layout;

    @BindView(R.id.product4_layout)
    LinearLayout product4Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revlon_eye_cosmetic);
        ButterKnife.bind(this);
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        product1Layout.setOnClickListener(v -> {
            Intent intent = new Intent(RevlonEyeCosmeticActivity.this, ProductsActivity.class);
            intent.putExtra(PRODUCT_TYPE, 25);
            startActivity(intent);
        });

        product2Layout.setOnClickListener(v -> {
            Intent intent = new Intent(RevlonEyeCosmeticActivity.this, ProductsActivity.class);
            intent.putExtra(PRODUCT_TYPE, 26);
            startActivity(intent);
        });

        product3Layout.setOnClickListener(v -> {
            Intent intent = new Intent(RevlonEyeCosmeticActivity.this, ProductsActivity.class);
            intent.putExtra(PRODUCT_TYPE, 27);
            startActivity(intent);
        });

        product4Layout.setOnClickListener(v -> {
            Intent intent = new Intent(RevlonEyeCosmeticActivity.this, ProductsActivity.class);
            intent.putExtra(PRODUCT_TYPE, 28);
            startActivity(intent);
        });
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
