package com.frontiertechnologypartners.beautysecret.ui.home;

import android.view.View;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderViewHolder {

    @BindView(R.id.user_profile_name)
    TextView tvUserName;

    HeaderViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}