package com.frontiertechnologypartners.beautysecret.ui.admin;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Order;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseViewHolder;

import butterknife.BindView;

public class OrderViewHolder extends BaseViewHolder<Order, OnRecyclerMultiItemClickListener> {

    @BindView(R.id.tv_order_user_name)
    TextView tvOrderUserName;

    @BindView(R.id.tv_order_ph_no)
    TextView tvOrderPhoneNo;

    @BindView(R.id.tv_order_total_price)
    TextView tvOrderTotalPrice;

    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;

    @BindView(R.id.tv_order_address)
    TextView tvOrderAddress;

    @BindView(R.id.show_order_products_btn)
    Button showOrderProductBtn;

    private Context mContext;

    OrderViewHolder(View itemView, OnRecyclerMultiItemClickListener listener) {
        super(itemView, listener);
        mContext = itemView.getContext();
    }

    @Override
    protected void onBind(Order order) {
        tvOrderUserName.setText("Name: " + order.getName());
        tvOrderPhoneNo.setText("Phone: " + order.getPhone());
        tvOrderTotalPrice.setText("Total Amount :  $" + order.getTotalAmount());
        tvOrderDate.setText("Order at: " + order.getDate() + "  " + order.getTime());
        tvOrderAddress.setText("Shipping Address: " + order.getAddress() + ", " + order.getCity());
        showOrderProductBtn.setOnClickListener(v -> {
            getListener().onClickAction(getAdapterPosition());
        });
        itemView.setOnClickListener(view -> getListener().onItemClick(getAdapterPosition()));
    }
}
