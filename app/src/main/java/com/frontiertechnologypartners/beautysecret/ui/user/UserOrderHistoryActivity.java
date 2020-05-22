package com.frontiertechnologypartners.beautysecret.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.model.Order;
import com.frontiertechnologypartners.beautysecret.model.Users;
import com.frontiertechnologypartners.beautysecret.ui.admin.OrderAdapter;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.NOT_SHIPPED;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDERS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDER_PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;

public class UserOrderHistoryActivity extends BaseActivity implements OnRecyclerMultiItemClickListener {
    @BindView(R.id.rv_orders)
    RecyclerView ordersRv;

    @BindView(R.id.tv_no_orders)
    TextView tvNoOrders;

    private UserOrderAdapter orderAdapter;
    private List<Order> orderList;
    private Users userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_history);
        ButterKnife.bind(this);
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //init rv
        ordersRv.setLayoutManager(new LinearLayoutManager(this));
        ordersRv.setHasFixedSize(true);
        ordersRv.setItemAnimator(new DefaultItemAnimator());
        orderAdapter = new UserOrderAdapter(this, this);
        ordersRv.setAdapter(orderAdapter);
        //login data
        userData = Paper.book().read(LOGIN_USER_DATA);

        showOrders();
    }

    private void showOrders() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            orderList = new ArrayList<>();
            loadingBar.show();
            dbRef.child(ORDERS).child(userData.getName()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadingBar.dismiss();
                    if (dataSnapshot.exists()) {
                        orderList.clear();
                        orderAdapter.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Order order = dataSnapshot1.getValue(Order.class);
                            orderList.add(order);
                        }
                        orderAdapter.setItems(orderList);
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
        Order order = orderAdapter.getItem(position);
        deleteOrder(order);
    }

    private void deleteOrder(Order order) {
        new AlertDialog.Builder(this)
                .setMessage("Are you received this order?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (order.getState().equals(NOT_SHIPPED)) {
                        dialog.dismiss();
                        Toast.makeText(UserOrderHistoryActivity.this, "Sorry, you can't delete not shipped order", Toast.LENGTH_LONG).show();
                    } else {
                        dbRef.child(ORDERS).child(userData.getName()).child(order.getOrderId())
                                .removeValue()
                                .addOnCompleteListener(task -> dbRef.child(ORDER_PRODUCTS).child(userData.getName())
                                        .child(PRODUCTS).child(order.getOrderId())
                                        .removeValue()
                                        .addOnCompleteListener(task1 -> orderAdapter.remove(order)));
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onClickAction(int position) {

    }
}
