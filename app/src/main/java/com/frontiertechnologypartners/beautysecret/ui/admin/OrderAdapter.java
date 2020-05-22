package com.frontiertechnologypartners.beautysecret.ui.admin;

import android.content.Context;
import android.view.ViewGroup;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.model.Order;
import com.frontiertechnologypartners.beautysecret.ui.base.GenericRecyclerViewAdapter;
import com.frontiertechnologypartners.beautysecret.ui.cart.CartViewHolder;

import androidx.annotation.NonNull;

public class OrderAdapter extends GenericRecyclerViewAdapter<Order, OnRecyclerMultiItemClickListener, OrderViewHolder> {

    public OrderAdapter(Context context, OnRecyclerMultiItemClickListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(inflate(R.layout.admin_check_order_item, parent), getListener());
    }
}
