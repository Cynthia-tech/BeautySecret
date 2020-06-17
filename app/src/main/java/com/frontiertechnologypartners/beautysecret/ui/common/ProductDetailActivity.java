package com.frontiertechnologypartners.beautysecret.ui.common;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.model.Users;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.frontiertechnologypartners.beautysecret.util.Constant.CART_LIST;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT_TYPE;

public class ProductDetailActivity extends BaseActivity {
    private ImageView productImageDetail;
    private ElegantNumberButton numberButton;
    private TextView tvProductNameDetail;
    private TextView tvProductColorDetail;
    private TextView tvProductPriceDetail;
    private Button btnProductAddToCart;

    private Product product;
    String saveCurrentTime, saveCurrentDate;
    private int productType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        init();

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
            tvProductPriceDetail.setText("$ " + product.getProductPrice());
            Glide.with(this)
                    .load(product.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.image_background)
                    .into(productImageDetail);
        }
        btnProductAddToCart.setOnClickListener(v -> {

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());
            String keyId = dbRef.push().getKey();

            //login data
            Users userData = Paper.book().read(LOGIN_USER_DATA);

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("pname", tvProductNameDetail.getText().toString());
            cartMap.put("price", product.getProductPrice());
            cartMap.put("color", tvProductColorDetail.getText().toString());
            cartMap.put("date", saveCurrentDate);
            cartMap.put("time", saveCurrentTime);
            cartMap.put("quantity", numberButton.getNumber());
//        String productRandomKey = saveCurrentDate + saveCurrentTime;

            dbRef.child(CART_LIST).child(userData.getName())
                    .child(PRODUCTS).child(keyId)
                    .updateChildren(cartMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProductDetailActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProductDetailActivity.this, ProductsActivity.class);
                            intent.putExtra(PRODUCT_TYPE, productType);
                            startActivity(intent);
                            finish();
//                        dbRef.child(CART_LIST).child(userData.getName())
//                                .child(PRODUCTS).child(keyId)
//                                .updateChildren(cartMap)
//                                .addOnCompleteListener(task1 -> {
//                                    if (task1.isSuccessful()) {
//                                        Toast.makeText(ProductDetailActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();
//
//                                        Intent intent = new Intent(ProductDetailActivity.this, ProductsActivity.class);
//                                        intent.putExtra(PRODUCT_TYPE, productType);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                });
                        }
                    });
        });
    }

    private void init() {
        productImageDetail = findViewById(R.id.product_image_details);
        numberButton = findViewById(R.id.number_btn);
        tvProductNameDetail = findViewById(R.id.product_name_details);
        tvProductColorDetail = findViewById(R.id.product_color_details);
        tvProductPriceDetail = findViewById(R.id.product_price_details);
        btnProductAddToCart = findViewById(R.id.pd_add_to_cart_btn);
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
