package com.frontiertechnologypartners.beautysecret.ui.common;

import android.content.Context;
import android.view.ViewGroup;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.ui.base.GenericRecyclerViewAdapter;

import androidx.annotation.NonNull;

public class ProductsAdapter extends GenericRecyclerViewAdapter<Product, OnRecyclerItemClickListener, ProductViewHolder> {

    ProductsAdapter(Context context, OnRecyclerItemClickListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(inflate(R.layout.product_item, parent), getListener());
    }
}
