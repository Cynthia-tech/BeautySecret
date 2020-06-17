package com.frontiertechnologypartners.beautysecret.ui.user.home;

import android.view.View;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class HeaderViewHolder {
    TextView tvUserName;
    CircleImageView imgUserProfileImage;

    HeaderViewHolder(View view) {
        tvUserName = view.findViewById(R.id.user_profile_name);
        imgUserProfileImage = view.findViewById(R.id.user_profile_image);
    }
}