package com.frontiertechnologypartners.beautysecret.ui.user;

import android.content.Context;
import android.view.ViewGroup;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Order;
import com.frontiertechnologypartners.beautysecret.ui.admin.OrderViewHolder;
import com.frontiertechnologypartners.beautysecret.ui.base.GenericRecyclerViewAdapter;

import androidx.annotation.NonNull;

public class UserOrderAdapter extends GenericRecyclerViewAdapter<Order, OnRecyclerMultiItemClickListener, UserOrderViewHolder> {

    public UserOrderAdapter(Context context, OnRecyclerMultiItemClickListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserOrderViewHolder(inflate(R.layout.user_order_item, parent), getListener());
    }
}
