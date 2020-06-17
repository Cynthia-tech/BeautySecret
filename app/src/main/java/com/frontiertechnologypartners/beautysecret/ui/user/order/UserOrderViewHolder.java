package com.frontiertechnologypartners.beautysecret.ui.user.order;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Order;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseViewHolder;


public class UserOrderViewHolder extends BaseViewHolder<Order, OnRecyclerMultiItemClickListener> {
    private TextView tvOrderUserName;
    private TextView tvOrderPhoneNo;
    private TextView tvOrderTotalPrice;
    private TextView tvOrderDate;
    private TextView tvOrderAddress;
    private Button userOrderShippedBtn;

    private Context mContext;

    UserOrderViewHolder(View itemView, OnRecyclerMultiItemClickListener listener) {
        super(itemView, listener);
        mContext = itemView.getContext();
        tvOrderUserName = itemView.findViewById(R.id.tv_order_user_name);
        tvOrderPhoneNo = itemView.findViewById(R.id.tv_order_ph_no);
        tvOrderTotalPrice = itemView.findViewById(R.id.tv_order_total_price);
        tvOrderDate = itemView.findViewById(R.id.tv_order_date);
        tvOrderAddress = itemView.findViewById(R.id.tv_order_address);
        userOrderShippedBtn = itemView.findViewById(R.id.order_shipped_btn);
    }

    @Override
    protected void onBind(Order order) {
        tvOrderUserName.setText("Name: " + order.getName());
        tvOrderPhoneNo.setText("Phone: " + order.getPhone());
        tvOrderTotalPrice.setText("Total Amount :  $" + order.getTotalAmount());
        tvOrderDate.setText("Order at: " + order.getDate() + "  " + order.getTime());
        tvOrderAddress.setText("Shipping Address: " + order.getAddress() + ", " + order.getCity());
        userOrderShippedBtn.setText(order.getState());
        itemView.setOnClickListener(view -> getListener().onItemClick(getAdapterPosition()));
        userOrderShippedBtn.setOnClickListener(v -> getListener().onItemClick(getAdapterPosition()));
    }
}
