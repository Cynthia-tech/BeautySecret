package com.frontiertechnologypartners.beautysecret.ui.user.cart;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseViewHolder;

public class CartViewHolder extends BaseViewHolder<Cart, OnRecyclerMultiItemClickListener> {
    private TextView tvCardProductName;
    private TextView tvCartProductQty;
    private TextView tvCartProductPrice;
    private ImageView imgCartDelete;

    private Context mContext;

    CartViewHolder(View itemView, OnRecyclerMultiItemClickListener listener) {
        super(itemView, listener);
        mContext = itemView.getContext();
        tvCardProductName = itemView.findViewById(R.id.tv_cart_pd_name);
        tvCartProductQty = itemView.findViewById(R.id.tv_cart_pd_quantity);
        tvCartProductPrice = itemView.findViewById(R.id.tv_cart_pd_price);
        imgCartDelete = itemView.findViewById(R.id.img_delete_cart);
    }

    @Override
    protected void onBind(Cart cart) {
        tvCardProductName.setText("Name : " + cart.getPname());
        tvCartProductQty.setText("Quantity : " + cart.getQuantity());
        tvCartProductPrice.setText("Price : " + "$ " + cart.getPrice());
        imgCartDelete.setOnClickListener(v -> {
            getListener().onClickAction(getAdapterPosition());
        });
        itemView.setOnClickListener(view -> getListener().onItemClick(getAdapterPosition()));
    }
}
