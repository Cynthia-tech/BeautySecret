package com.frontiertechnologypartners.beautysecret.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.model.Order;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.frontiertechnologypartners.beautysecret.util.Constant.NOT_SHIPPED;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDERS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDER_KEY_ID;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDER_USER;

public class AdminCheckOrdersActivity extends BaseActivity implements OnRecyclerMultiItemClickListener {
    @BindView(R.id.rv_orders)
    RecyclerView ordersRv;

    @BindView(R.id.tv_no_orders)
    TextView tvNoOrders;

    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_orders_actiivity);
        ButterKnife.bind(this);
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //init rv
        ordersRv.setLayoutManager(new LinearLayoutManager(this));
        ordersRv.setHasFixedSize(true);
        ordersRv.setItemAnimator(new DefaultItemAnimator());
        orderAdapter = new OrderAdapter(this, this);
        ordersRv.setAdapter(orderAdapter);

        showOrders();

    }

    private void showOrders() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            orderList = new ArrayList<>();
            loadingBar.show();
            dbRef.child(ORDERS).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadingBar.dismiss();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DatabaseReference orderRef = dbRef.child(ORDERS).child(dataSnapshot1.getKey());
                            orderRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                        Order order = dataSnapshot2.getValue(Order.class);
                                        if (order.getState().equals(NOT_SHIPPED)) {
                                            orderList.add(order);
                                        }
                                    }
                                    if (orderList != null && orderList.size() > 0) {
                                        tvNoOrders.setVisibility(View.GONE);
                                    } else {
                                        tvNoOrders.setVisibility(View.VISIBLE);
                                    }
                                    orderAdapter.setItems(orderList);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        tvNoOrders.setVisibility(View.GONE);
                    } else {
                        tvNoOrders.setVisibility(View.VISIBLE);
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
        Order order = orderAdapter.getItem(position);
        Intent intent = new Intent(AdminCheckOrdersActivity.this, AdminOrderProductsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ORDER_KEY_ID, order.getOrderId());
        bundle.putString(ORDER_USER, order.getOrderUser());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
