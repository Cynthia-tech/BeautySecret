package com.frontiertechnologypartners.beautysecret.ui.admin;

import android.content.Context;
import android.view.ViewGroup;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.ui.base.GenericRecyclerViewAdapter;
import com.frontiertechnologypartners.beautysecret.ui.cart.CartViewHolder;

import androidx.annotation.NonNull;

public class AdminOrderProductsAdapter extends GenericRecyclerViewAdapter<Cart, OnRecyclerMultiItemClickListener, AdminOrderProductsViewHolder> {

    AdminOrderProductsAdapter(Context context, OnRecyclerMultiItemClickListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public AdminOrderProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminOrderProductsViewHolder(inflate(R.layout.order_products_item, parent), getListener());
    }
}
