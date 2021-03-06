package com.frontiertechnologypartners.beautysecret.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseFragment;
import com.frontiertechnologypartners.beautysecret.ui.common.BrandCategoryActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.frontiertechnologypartners.beautysecret.util.Constant.BRAND_NAME;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOREAL_BRANDS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.MAYBELLINE_BRANDS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.REVLON_BRANDS;


public class HomeFragment extends BaseFragment {
    RelativeLayout maybellineLayout, lorealLayout, revlonLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getActivity().setTitle(getResources().getString(R.string.menu_home));
    }

    private void init(View view) {
        maybellineLayout = view.findViewById(R.id.maybelline_layout);
        lorealLayout = view.findViewById(R.id.loreal_layout);
        revlonLayout = view.findViewById(R.id.revlon_layout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        maybellineLayout.setOnClickListener(v -> {
            Intent brandCategoryIntent = new Intent(getContext(), BrandCategoryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(BRAND_NAME, MAYBELLINE_BRANDS);
            brandCategoryIntent.putExtras(bundle);
            startActivity(brandCategoryIntent);
        });

        lorealLayout.setOnClickListener(v -> {
            Intent brandCategoryIntent = new Intent(getContext(), BrandCategoryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(BRAND_NAME, LOREAL_BRANDS);
            brandCategoryIntent.putExtras(bundle);
            startActivity(brandCategoryIntent);
        });

        revlonLayout.setOnClickListener(v -> {
            Intent brandCategoryIntent = new Intent(getContext(), BrandCategoryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(BRAND_NAME, REVLON_BRANDS);
            brandCategoryIntent.putExtras(bundle);
            startActivity(brandCategoryIntent);
        });
    }
}
