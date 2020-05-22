package com.frontiertechnologypartners.beautysecret.ui.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerItemClickListener;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.model.Users;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.order.FinalConfirmOrderActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.frontiertechnologypartners.beautysecret.widget.GridSpacingItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.frontiertechnologypartners.beautysecret.util.Constant.ADMIN_VIEW;
import static com.frontiertechnologypartners.beautysecret.util.Constant.CART_LIST;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.TOTAL_PRICE;
import static com.frontiertechnologypartners.beautysecret.util.Constant.USER_VIEW;

public class CartActivity extends BaseActivity implements OnRecyclerItemClickListener, OnRecyclerMultiItemClickListener {
    @BindView(R.id.rv_cart_list)
    RecyclerView rvCartList;

    @BindView(R.id.tv_no_cart)
    TextView tvNoCart;

    @BindView(R.id.tv_pd_total_price)
    TextView tvTotalPrice;

    @BindView(R.id.btn_next)
    Button btnNext;

    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    private Users userData;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_activiity);
        ButterKnife.bind(this);
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //init rv
        rvCartList.setLayoutManager(new LinearLayoutManager(this));
        rvCartList.setHasFixedSize(true);
        rvCartList.setItemAnimator(new DefaultItemAnimator());
        cartAdapter = new CartAdapter(this, this);
        rvCartList.setAdapter(cartAdapter);
        //login data
        userData = Paper.book().read(LOGIN_USER_DATA);

        retrieveCartList();

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, FinalConfirmOrderActivity.class);
            intent.putExtra(TOTAL_PRICE, String.valueOf(totalPrice));
            intent.putExtra(CART_LIST, (Serializable) cartList);
            startActivity(intent);
        });
    }

    private void retrieveCartList() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            cartList = new ArrayList<>();
            loadingBar.show();
            dbRef.child(CART_LIST).child(userData.getName())
                    .child(PRODUCTS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadingBar.dismiss();
                    if (dataSnapshot.exists()) {
                        cartList.clear();
                        cartAdapter.clear();
                        totalPrice = 0;
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Cart cart = dataSnapshot1.getValue(Cart.class);
                            cart.setPid(dataSnapshot1.getKey());
                            cartList.add(cart);

                            double price = Double.parseDouble(cart.getPrice());
                            int qty = Integer.parseInt(cart.getQuantity());
                            totalPrice += (price * qty);
                        }
                        cartAdapter.setItems(cartList);
                        tvTotalPrice.setText("Total Price = $ " + totalPrice);
                        tvNoCart.setVisibility(View.GONE);
                        btnNext.setVisibility(View.VISIBLE);
                        tvTotalPrice.setVisibility(View.VISIBLE);
                    } else {
                        tvNoCart.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.GONE);
                        tvTotalPrice.setVisibility(View.GONE);
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
        Cart cart = cartAdapter.getItem(position);
        //login data
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference();
        cartListRef.child(CART_LIST).child(userData.getName())
                .child(PRODUCTS).child(cart.getPid()).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Item removed successfully.", Toast.LENGTH_SHORT).show();
                        cartAdapter.remove(cart);
                    }
                });
    }
}
