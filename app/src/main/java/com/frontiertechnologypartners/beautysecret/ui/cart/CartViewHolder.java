package com.frontiertechnologypartners.beautysecret.ui.cart;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerItemClickListener;
import com.frontiertechnologypartners.beautysecret.delegate.OnRecyclerMultiItemClickListener;
import com.frontiertechnologypartners.beautysecret.model.Cart;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.model.Users;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import butterknife.BindView;
import io.paperdb.Paper;

import static com.frontiertechnologypartners.beautysecret.util.Constant.CART_LIST;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOGIN_USER_DATA;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.USER_VIEW;

public class CartViewHolder extends BaseViewHolder<Cart, OnRecyclerMultiItemClickListener> {

    @BindView(R.id.tv_cart_pd_name)
    TextView tvCardProductName;

    @BindView(R.id.tv_cart_pd_quantity)
    TextView tvCartProductQty;

    @BindView(R.id.tv_cart_pd_price)
    TextView tvCartProductPrice;

    @BindView(R.id.img_delete_cart)
    ImageView imgCartDelete;
    private Context mContext;

    CartViewHolder(View itemView, OnRecyclerMultiItemClickListener listener) {
        super(itemView, listener);
        mContext = itemView.getContext();
    }

    @Override
    protected void onBind(Cart cart) {
        tvCardProductName.setText("Name : " + cart.getPname());
        tvCartProductQty.setText("Quantity : " + cart.getQuantity());
        tvCartProductPrice.setText("Price : " + cart.getPrice());
        imgCartDelete.setOnClickListener(v -> {
            getListener().onDeleteItemClick(getAdapterPosition());
        });
        itemView.setOnClickListener(view -> getListener().onItemClick(getAdapterPosition()));
    }
}
