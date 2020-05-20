package com.frontiertechnologypartners.beautysecret.ui.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.model.Users;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.home.HomeActivity;
import com.frontiertechnologypartners.beautysecret.ui.maybelline.MaybellineFaceCosmeticActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.frontiertechnologypartners.beautysecret.util.Constant.BRAND_NAME;
import static com.frontiertechnologypartners.beautysecret.util.Constant.CART_LIST;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT_TYPE;
import static com.frontiertechnologypartners.beautysecret.util.Constant.USER_VIEW;

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.product_image_details)
    ImageView productImageDetail;

    @BindView(R.id.number_btn)
    ElegantNumberButton numberButton;

    @BindView(R.id.product_name_details)
    TextView tvProductNameDetail;

    @BindView(R.id.product_color_details)
    TextView tvProductColorDetail;

    @BindView(R.id.product_price_details)
    TextView tvProductPriceDetail;
    private Product product;
    String saveCurrentTime, saveCurrentDate;
    private int productType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product = (Product) bundle.getSerializable(PRODUCT);
            productType = bundle.getInt(PRODUCT_TYPE);
        }
        if (product != null) {
            tvProductNameDetail.setText(product.getProductName());
            tvProductColorDetail.setText(product.getProductColor());
            tvProductPriceDetail.setText(product.getProductPrice());
            Glide.with(this)
                    .load(product.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.image_background)
                    .into(productImageDetail);
        }
    }

    @OnClick(R.id.pd_add_to_cart_btn)
    void productAddToCart() {

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        String keyId = dbRef.push().getKey();

        //login data
        Users userData = Paper.book().read(LOGIN_USER_DATA);

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", keyId);
        cartMap.put("pname", tvProductNameDetail.getText().toString());
        cartMap.put("price", tvProductPriceDetail.getText().toString());
        cartMap.put("color", tvProductColorDetail.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount", "");

        dbRef.child(CART_LIST).child(USER_VIEW).child(userData.getName())
                .child(PRODUCTS).child(keyId)
                .updateChildren(cartMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProductDetailActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ProductDetailActivity.this, ProductsActivity.class);
                        intent.putExtra(PRODUCT_TYPE, productType);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProductDetailActivity.this, ProductsActivity.class);
        intent.putExtra(PRODUCT_TYPE, productType);
        startActivity(intent);
        finish();
    }
}
