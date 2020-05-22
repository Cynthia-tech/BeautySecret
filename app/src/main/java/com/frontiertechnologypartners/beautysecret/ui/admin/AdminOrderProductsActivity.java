package com.frontiertechnologypartners.beautysecret.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.frontiertechnologypartners.beautysecret.util.Constant.CART_LIST;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDERS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDER_KEY_ID;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDER_PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDER_USER;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT_TYPE;

public class AdminOrderProductsActivity extends BaseActivity implements OnRecyclerMultiItemClickListener {
    @BindView(R.id.rv_order_products)
    RecyclerView orderProductsRv;

    private AdminOrderProductsAdapter adminOrderProductsAdapter;
    private List<Cart> orderProductList;
    private String orderUser, orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_products);
        ButterKnife.bind(this);
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //init rv
        orderProductsRv.setLayoutManager(new LinearLayoutManager(this));
        orderProductsRv.setHasFixedSize(true);
        orderProductsRv.setItemAnimator(new DefaultItemAnimator());
        adminOrderProductsAdapter = new AdminOrderProductsAdapter(this, this);
        orderProductsRv.setAdapter(adminOrderProductsAdapter);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderUser = bundle.getString(ORDER_USER);
            orderId = bundle.getString(ORDER_KEY_ID);
        }
        showOrderProducts();
    }

    private void showOrderProducts() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            orderProductList = new ArrayList<>();
            loadingBar.show();
            dbRef.child(ORDER_PRODUCTS).child(orderUser)
                    .child(PRODUCTS).child(orderId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadingBar.dismiss();
                    if (dataSnapshot.exists()) {
                        orderProductList.clear();
                        adminOrderProductsAdapter.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Cart cart = dataSnapshot1.getValue(Cart.class);
                            cart.setPid(dataSnapshot1.getKey());
                            orderProductList.add(cart);
                        }
                        adminOrderProductsAdapter.setItems(orderProductList);
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

    @OnClick(R.id.approve_order_btn)
    void approveOrder() {
        dbRef.child(ORDERS).child(orderUser).child(orderId)
                .child("state")
                .setValue("shipped")
                .addOnCompleteListener(task -> {
                    Intent intent = new Intent(AdminOrderProductsActivity.this, AdminCheckOrdersActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClickAction(int position) {

    }
}
