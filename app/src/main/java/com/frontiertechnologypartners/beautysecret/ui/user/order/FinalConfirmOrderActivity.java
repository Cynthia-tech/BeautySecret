package com.frontiertechnologypartners.beautysecret.ui.user.order;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.model.Users;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.user.home.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import io.paperdb.Paper;

import static com.frontiertechnologypartners.beautysecret.util.Constant.CART_LIST;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDERS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.ORDER_PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.TOTAL_PRICE;

public class FinalConfirmOrderActivity extends BaseActivity {
    EditText etName;
    EditText etPhoneNumber;
    EditText etAddress;
    EditText etCity;
    Button btnOrderConfirm;

    private String totalPrice;
    private List<Cart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_confirm_order);
        init();
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        totalPrice = getIntent().getStringExtra(TOTAL_PRICE);
        cartList = (ArrayList<Cart>) getIntent().getSerializableExtra(CART_LIST);

        btnOrderConfirm.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etName.getText().toString())) {
                Toast.makeText(FinalConfirmOrderActivity.this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etPhoneNumber.getText().toString())) {
                Toast.makeText(FinalConfirmOrderActivity.this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etAddress.getText().toString())) {
                Toast.makeText(FinalConfirmOrderActivity.this, "Please provide your address.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etCity.getText().toString())) {
                Toast.makeText(FinalConfirmOrderActivity.this, "Please provide your city name.", Toast.LENGTH_SHORT).show();
            } else {
                order();
            }
        });
    }

    private void init() {
        etName = findViewById(R.id.shippment_name);
        etPhoneNumber = findViewById(R.id.shippment_phone_number);
        etAddress = findViewById(R.id.shippment_address);
        etCity = findViewById(R.id.shippment_city);
        btnOrderConfirm = findViewById(R.id.confirm_final_order_btn);
    }

    private void order() {
        //login data
        Users userData = Paper.book().read(LOGIN_USER_DATA);

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        String saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calForDate.getTime());
        String keyId = dbRef.push().getKey();

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalPrice);
        ordersMap.put("name", etName.getText().toString());
        ordersMap.put("phone", etPhoneNumber.getText().toString());
        ordersMap.put("address", etAddress.getText().toString());
        ordersMap.put("city", etCity.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "not shipped");
        ordersMap.put("orderUser", userData.getName());
        ordersMap.put("orderId", keyId);

        dbRef.child(ORDERS).child(userData.getName()).child(keyId).updateChildren(ordersMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (int i = 0; i < cartList.size(); i++) {
                    final HashMap<String, Object> cartMap = new HashMap<>();
                    cartMap.put("pname", cartList.get(i).getPname());
                    cartMap.put("price", cartList.get(i).getPrice());
                    cartMap.put("color", cartList.get(i).getColor());
                    cartMap.put("date", cartList.get(i).getDate());
                    cartMap.put("time", cartList.get(i).getTime());
                    cartMap.put("quantity", cartList.get(i).getQuantity());
                    dbRef.child(ORDER_PRODUCTS).child(userData.getName())
                            .child(PRODUCTS).child(keyId).child(String.valueOf(i + 1)).setValue(cartMap);
                }

                dbRef.child(CART_LIST).child(userData.getName())
                        .removeValue()
                        .addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                Toast.makeText(FinalConfirmOrderActivity.this, "your final order has been placed successfully.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(FinalConfirmOrderActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
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
