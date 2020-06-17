package com.frontiertechnologypartners.beautysecret.ui.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseViewHolder;

public class ProductViewHolder extends BaseViewHolder<Product, OnRecyclerItemClickListener> {
    ImageView imgProduct;
    TextView tvProductColor;
    TextView tvProductPrice;

    private Context mContext;

    ProductViewHolder(View itemView, OnRecyclerItemClickListener listener) {
        super(itemView, listener);
        mContext = itemView.getContext();
        imgProduct = itemView.findViewById(R.id.img_product);
        tvProductColor = itemView.findViewById(R.id.tv_product_color);
        tvProductPrice = itemView.findViewById(R.id.tv_product_price);
    }

    @Override
    protected void onBind(Product product) {
        Glide.with(mContext)
                .load(product.getImage())
                .centerCrop()
                .placeholder(R.drawable.image_background)
                .into(imgProduct);

        tvProductColor.setText(product.getProductColor());
        tvProductPrice.setText("$ " + product.getProductPrice());
        itemView.setOnClickListener(view -> getListener().onItemClick(getAdapterPosition()));
    }
}
