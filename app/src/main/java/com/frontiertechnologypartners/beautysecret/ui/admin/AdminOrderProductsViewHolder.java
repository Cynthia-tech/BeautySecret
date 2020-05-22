package com.frontiertechnologypartners.beautysecret.ui.admin;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseViewHolder;

import butterknife.BindView;

public class AdminOrderProductsViewHolder extends BaseViewHolder<Cart, OnRecyclerMultiItemClickListener> {

    @BindView(R.id.tv_order_pd_name)
    TextView tvOrderProductName;

    @BindView(R.id.tv_order_pd_color)
    TextView tvOrderProductColor;

    @BindView(R.id.tv_order_pd_price)
    TextView tvOrderProductPrice;

    @BindView(R.id.tv_order_pd_quantity)
    TextView tvOrderProductQty;

    private Context mContext;

    AdminOrderProductsViewHolder(View itemView, OnRecyclerMultiItemClickListener listener) {
        super(itemView, listener);
        mContext = itemView.getContext();
    }

    @Override
    protected void onBind(Cart cart) {
        tvOrderProductName.setText("Name : " + cart.getPname());
        tvOrderProductColor.setText("Color : " + cart.getColor());
        tvOrderProductQty.setText("Quantity : " + cart.getQuantity());
        tvOrderProductPrice.setText("Price : " + "$ " + cart.getPrice());
        itemView.setOnClickListener(view -> getListener().onItemClick(getAdapterPosition()));
    }
}
