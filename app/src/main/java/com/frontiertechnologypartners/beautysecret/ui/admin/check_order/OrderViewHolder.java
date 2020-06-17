package com.frontiertechnologypartners.beautysecret.ui.admin.check_order;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Order;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseViewHolder;

public class OrderViewHolder extends BaseViewHolder<Order, OnRecyclerMultiItemClickListener> {
    private TextView tvOrderUserName, tvOrderPhoneNo, tvOrderTotalPrice, tvOrderDate, tvOrderAddress;
    private Button showOrderProductBtn;

    private Context mContext;

    OrderViewHolder(View itemView, OnRecyclerMultiItemClickListener listener) {
        super(itemView, listener);
        mContext = itemView.getContext();
        tvOrderUserName = itemView.findViewById(R.id.tv_order_user_name);
        tvOrderPhoneNo = itemView.findViewById(R.id.tv_order_ph_no);
        tvOrderTotalPrice = itemView.findViewById(R.id.tv_order_total_price);
        tvOrderDate = itemView.findViewById(R.id.tv_order_date);
        tvOrderAddress = itemView.findViewById(R.id.tv_order_address);
        showOrderProductBtn = itemView.findViewById(R.id.show_order_products_btn);
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
