package com.frontiertechnologypartners.beautysecret.ui.admin.check_order;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseViewHolder;


public class AdminOrderProductsViewHolder extends BaseViewHolder<Cart, OnRecyclerMultiItemClickListener> {

    private TextView tvOrderProductName, tvOrderProductColor, tvOrderProductPrice, tvOrderProductQty;

    private Context mContext;

    AdminOrderProductsViewHolder(View itemView, OnRecyclerMultiItemClickListener listener) {
        super(itemView, listener);
        mContext = itemView.getContext();
        tvOrderProductName = itemView.findViewById(R.id.tv_order_pd_name);
        tvOrderProductColor = itemView.findViewById(R.id.tv_order_pd_color);
        tvOrderProductPrice = itemView.findViewById(R.id.tv_order_pd_price);
        tvOrderProductQty = itemView.findViewById(R.id.tv_order_pd_quantity);
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
