package com.frontiertechnologypartners.beautysecret.ui.common;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.loreal.LorealEyeCosmeticActivity;
import com.frontiertechnologypartners.beautysecret.ui.loreal.LorealFaceCosmeticActivity;
import com.frontiertechnologypartners.beautysecret.ui.loreal.LorealLipCosmeticActivity;
import com.frontiertechnologypartners.beautysecret.ui.maybelline.MaybellineEyeCosmeticActivity;
import com.frontiertechnologypartners.beautysecret.ui.maybelline.MaybellineFaceCosmeticActivity;
import com.frontiertechnologypartners.beautysecret.ui.maybelline.MaybellineLipCosmeticActivity;
import com.frontiertechnologypartners.beautysecret.ui.revlon.RevlonEyeCosmeticActivity;
import com.frontiertechnologypartners.beautysecret.ui.revlon.RevlonFaceCosmeticActivity;
import com.frontiertechnologypartners.beautysecret.ui.revlon.RevlonLipCosmeticActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.frontiertechnologypartners.beautysecret.util.Constant.BRAND_NAME;
import static com.frontiertechnologypartners.beautysecret.util.Constant.LOREAL_BRANDS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.MAYBELLINE_BRANDS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.REVLON_BRANDS;

public class BrandCategoryActivity extends BaseActivity {
    @BindView(R.id.eye_layout)
    RelativeLayout eyeCosmeticLayout;

    @BindView(R.id.face_layout)
    RelativeLayout faceCosmeticLayout;

    @BindView(R.id.lip_layout)
    RelativeLayout lipCosmeticLayout;

    private String brandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_category);
        ButterKnife.bind(this);
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            brandName = bundle.getString(BRAND_NAME);
            setTitle(brandName);
        }

        eyeCosmeticLayout.setOnClickListener(v -> {
            switch (brandName) {
                case MAYBELLINE_BRANDS:
                    Intent maybellineEyeIntent = new Intent(BrandCategoryActivity.this, MaybellineEyeCosmeticActivity.class);
                    startActivity(maybellineEyeIntent);
                    break;
                case LOREAL_BRANDS:
                    Intent lorealEyeIntent = new Intent(BrandCategoryActivity.this, LorealEyeCosmeticActivity.class);
                    startActivity(lorealEyeIntent);
                    break;
                case REVLON_BRANDS:
                    Intent revlonEyeIntent = new Intent(BrandCategoryActivity.this, RevlonEyeCosmeticActivity.class);
                    startActivity(revlonEyeIntent);
                    break;
            }

        });

        faceCosmeticLayout.setOnClickListener(v -> {
            switch (brandName) {
                case MAYBELLINE_BRANDS:
                    Intent maybellineFaceIntent = new Intent(BrandCategoryActivity.this, MaybellineFaceCosmeticActivity.class);
                    startActivity(maybellineFaceIntent);
                    break;
                case LOREAL_BRANDS:
                    Intent lorealFaceIntent = new Intent(BrandCategoryActivity.this, LorealFaceCosmeticActivity.class);
                    startActivity(lorealFaceIntent);
                    break;
                case REVLON_BRANDS:
                    Intent revlonFaceIntent = new Intent(BrandCategoryActivity.this, RevlonFaceCosmeticActivity.class);
                    startActivity(revlonFaceIntent);
                    break;
            }
        });

        lipCosmeticLayout.setOnClickListener(v -> {
            switch (brandName) {
                case MAYBELLINE_BRANDS:
                    Intent maybellineLipIntent = new Intent(BrandCategoryActivity.this, MaybellineLipCosmeticActivity.class);
                    startActivity(maybellineLipIntent);
                    break;
                case LOREAL_BRANDS:
                    Intent lorealLipIntent = new Intent(BrandCategoryActivity.this, LorealLipCosmeticActivity.class);
                    startActivity(lorealLipIntent);
                    break;
                case REVLON_BRANDS:
                    Intent revlonLipIntent = new Intent(BrandCategoryActivity.this, RevlonLipCosmeticActivity.class);
                    startActivity(revlonLipIntent);
                    break;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
