package com.frontiertechnologypartners.beautysecret.ui.cart;

import android.content.Context;
import android.view.ViewGroup;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerItemClickListener;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.ui.base.GenericRecyclerViewAdapter;
import com.frontiertechnologypartners.beautysecret.ui.common.ProductViewHolder;

import androidx.annotation.NonNull;

public class CartAdapter extends GenericRecyclerViewAdapter<Cart, OnRecyclerMultiItemClickListener, CartViewHolder> {

    CartAdapter(Context context, OnRecyclerMultiItemClickListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(inflate(R.layout.cart_item, parent), getListener());
    }
}
