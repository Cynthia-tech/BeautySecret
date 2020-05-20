package com.frontiertechnologypartners.beautysecret.ui.common;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.cart.CartActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.frontiertechnologypartners.beautysecret.widget.GridSpacingItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.frontiertechnologypartners.beautysecret.util.Constant.BRAND_NAME;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOREAL_BRANDS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT_TYPE;

public class ProductsActivity extends BaseActivity implements OnRecyclerItemClickListener {
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;

    @BindView(R.id.tv_no_product)
    TextView tvNoProduct;

    private List<Product> products;
    private String brand, brandCategory, product;
    private ProductsAdapter productsAdapter;
    private int productType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //init rv
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        rvProducts.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(this, 5), true));
        rvProducts.setHasFixedSize(true);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        productsAdapter = new ProductsAdapter(this, this);
        rvProducts.setAdapter(productsAdapter);

        productType = getIntent().getIntExtra(PRODUCT_TYPE, 1);

        switch (productType) {
            case 1:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.brow_fast_shapes_eyebrow);
                retrieveProducts();
                break;
            case 2:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.tattoostudio_brow);
                retrieveProducts();
                break;
            case 3:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.tattoostudio_brow_tint_pen);
                retrieveProducts();
                break;
            case 4:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.lasting_matte_lacquer_gel_eyeliner);
                retrieveProducts();
                break;
            case 5:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.dream_urban_cover_full_coverage_foundation);
                retrieveProducts();
                break;
            case 6:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.cheek_heat_gel_cream_blush);
                retrieveProducts();
                break;
            case 7:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.fit_me_blush);
                retrieveProducts();
                break;
            case 8:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.superstay_full_coverage_powder);
                retrieveProducts();
                break;
            case 9:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.color_sensational_matte_lipstick);
                retrieveProducts();
                break;
            case 10:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.lipstudio_plumper_lipstick);
                retrieveProducts();
                break;
            case 11:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.superstay_ink_crayon_lipstick);
                retrieveProducts();
                break;
            case 12:
                brand = getResources().getString(R.string.maybelline);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.superstay_matte_ink_liquid_lipstick_coffee_edition);
                retrieveProducts();
                break;
            case 13:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.original_washable_bold_eye_mascara);
                retrieveProducts();
                break;
            case 14:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.longwear_waterproof_brow_gel);
                retrieveProducts();
                break;
            case 15:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.micro_ink_pen);
                retrieveProducts();
                break;
            case 16:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.liquid_dip_eyeliner_waterproof);
                retrieveProducts();
                break;
            case 17:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.infallible_full_wear_concealer);
                retrieveProducts();
                break;
            case 18:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.true_match_crayon_concealer);
                retrieveProducts();
                break;
            case 19:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.true_match_liquid_concealer);
                retrieveProducts();
                break;
            case 20:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.infallible_24_h_fresh_wear_foundation);
                retrieveProducts();
                break;
            case 21:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string._8_hr_le_gloss);
                retrieveProducts();
                break;
            case 22:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.colour_riche);
                retrieveProducts();
                break;
            case 23:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.matte_lip_crayon_lasting_wear);
                retrieveProducts();
                break;
            case 24:
                brand = getResources().getString(R.string.loreal);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.rouge_signature_matte_lip_stain);
                retrieveProducts();
                break;
            case 25:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.colorstay_brow_mousse);
                retrieveProducts();
                break;
            case 26:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.colorstay_eyeliner);
                retrieveProducts();
                break;
            case 27:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.revlon_ultimate_all_in_one_mascara);
                retrieveProducts();
                break;
            case 28:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.eye_consmetic_title);
                product = getResources().getString(R.string.photoready_cheek_flushing_tint);
                retrieveProducts();
                break;
            case 29:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.photoready_cheek_flushing_tint);
                retrieveProducts();
                break;
            case 30:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.colorstay_endless_glow_liquid_highlighter);
                retrieveProducts();
                break;
            case 31:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.colorstay_concealer);
                retrieveProducts();
                break;
            case 32:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.face_consmetic_title);
                product = getResources().getString(R.string.photoready_concealer);
                retrieveProducts();
                break;
            case 33:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.colorstay_ultimate_lipstick);
                retrieveProducts();
                break;
            case 34:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.revlon_cushion_lip_tint);
                retrieveProducts();
                break;
            case 35:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.revlon_ultra_hd_matte_lipcolor);
                retrieveProducts();
                break;
            case 36:
                brand = getResources().getString(R.string.revlon);
                brandCategory = getResources().getString(R.string.lip_consmetic_title);
                product = getResources().getString(R.string.super_lustrous_lipstick);
                retrieveProducts();
                break;
            default:
                break;
        }

    }

    private void retrieveProducts() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            products = new ArrayList<>();
            loadingBar.show();
            dbRef.child(PRODUCTS).child(brand).child(brandCategory).child(product).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadingBar.dismiss();
                    if (dataSnapshot.exists()) {
                        products.clear();
                        productsAdapter.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Product product = dataSnapshot1.getValue(Product.class);
                            products.add(product);
                            productsAdapter.setItems(products);
                        }
                        tvNoProduct.setVisibility(View.GONE);
                    } else {
                        tvNoProduct.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    loadingBar.dismiss();
                }
            });
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fab_cart)
    void productCart() {
        Intent intent = new Intent(ProductsActivity.this, CartActivity.class);
        startActivity(intent);
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

    @Override
    public void onItemClick(int position) {
        Product product = productsAdapter.getItem(position);
        Intent productDetailIntent = new Intent(ProductsActivity.this, ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCT, product);
        bundle.putInt(PRODUCT_TYPE, productType);
        productDetailIntent.putExtras(bundle);
        startActivity(productDetailIntent);
        finish();
    }
}
