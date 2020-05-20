package com.frontiertechnologypartners.beautysecret.ui.home;

import android.view.View;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HeaderViewHolder {

    @BindView(R.id.user_profile_name)
    TextView tvUserName;

    @BindView(R.id.user_profile_image)
    CircleImageView imgUserProfileImage;

    HeaderViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}