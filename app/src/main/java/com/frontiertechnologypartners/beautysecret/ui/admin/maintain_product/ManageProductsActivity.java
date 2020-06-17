package com.frontiertechnologypartners.beautysecret.ui.admin.maintain_product;

import androidx.annotation.Nullable;
import id.zelory.compressor.Compressor;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.model.Product;
import com.frontiertechnologypartners.beautysecret.ui.admin.admin_home.AdminHomeActivity;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.frontiertechnologypartners.beautysecret.util.Constant.GALLERY_IMAGE_PICK;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCT_TYPE;

public class ManageProductsActivity extends BaseActivity {
    Spinner brandNameSpinner, categoryNameSpinner, subCategoryNameSpinner;
    ImageView imgProduct;
    TextInputEditText etProductName, etProductColor, etProductPrice;
    Button updateProductBtn, deleteProductBtn;

    private String brandName, productCategoryName, productSubCategoryName;
    private Uri imageUri;
    private String productName, productColor, productPrice, downloadImageUri;
    private Product updateProduct;
    private int productType;
    private String brand, brandCategory, product;
    private int spinnerPosition;
    private File compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        init();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            updateProduct = (Product) bundle.getSerializable(PRODUCT);
            productType = bundle.getInt(PRODUCT_TYPE);
        }
        if (updateProduct != null) {
            etProductName.setText(updateProduct.getProductName());
            etProductColor.setText(updateProduct.getProductColor());
            etProductPrice.setText(updateProduct.getProductPrice());
            Glide.with(this)
                    .load(updateProduct.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.image_background)
                    .into(imgProduct);

            switch (productType) {
                case 1:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.brow_fast_shapes_eyebrow);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 2:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.tattoostudio_brow);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 3:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.tattoostudio_brow_tint_pen);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 4:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.lasting_matte_lacquer_gel_eyeliner);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 5:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.dream_urban_cover_full_coverage_foundation);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 6:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.cheek_heat_gel_cream_blush);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 7:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.fit_me_blush);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 8:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.superstay_full_coverage_powder);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 9:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.color_sensational_matte_lipstick);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 10:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.lipstudio_plumper_lipstick);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 11:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.superstay_ink_crayon_lipstick);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 12:
                    brand = getResources().getString(R.string.maybelline);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.superstay_matte_ink_liquid_lipstick_coffee_edition);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 13:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.original_washable_bold_eye_mascara);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 14:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.longwear_waterproof_brow_gel);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 15:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.micro_ink_pen);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 16:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.liquid_dip_eyeliner_waterproof);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 17:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.infallible_full_wear_concealer);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 18:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.true_match_crayon_concealer);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 19:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.true_match_liquid_concealer);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 20:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.infallible_24_h_fresh_wear_foundation);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 21:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string._8_hr_le_gloss);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 22:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.colour_riche);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 23:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.matte_lip_crayon_lasting_wear);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 24:
                    brand = getResources().getString(R.string.loreal);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.rouge_signature_matte_lip_stain);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 25:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.colorstay_brow_mousse);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 26:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.colorstay_eyeliner);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 27:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.revlon_ultimate_all_in_one_mascara);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 28:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.eye_consmetic_title);
                    product = getResources().getString(R.string.revlon_volumazing_mascara);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 29:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.photoready_cheek_flushing_tint);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 30:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.colorstay_endless_glow_liquid_highlighter);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 31:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.colorstay_concealer);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 32:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.face_consmetic_title);
                    product = getResources().getString(R.string.photoready_concealer);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 33:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.colorstay_ultimate_lipstick);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 34:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.revlon_cushion_lip_tint);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 35:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.revlon_ultra_hd_matte_lipcolor);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                case 36:
                    brand = getResources().getString(R.string.revlon);
                    brandCategory = getResources().getString(R.string.lip_consmetic_title);
                    product = getResources().getString(R.string.super_lustrous_lipstick);
                    //brand name spinner
                    brandNameSpinner();

                    break;
                default:
                    break;
            }
        }

        imgProduct.setOnClickListener(v -> chooseImageFromGallery());
        updateProductBtn.setOnClickListener(v -> {
            productName = etProductName.getText().toString();
            productColor = etProductColor.getText().toString();
            productPrice = etProductPrice.getText().toString();
            brandName = brandNameSpinner.getSelectedItem().toString();
            productCategoryName = categoryNameSpinner.getSelectedItem().toString();
            productSubCategoryName = subCategoryNameSpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(productName)) {
                etProductName.setError(getString(R.string.require_product_name));
                Toast.makeText(getApplicationContext(), R.string.require_product_name, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(productColor)) {
                etProductColor.setError(getString(R.string.require_product_color));
                Toast.makeText(getApplicationContext(), R.string.require_product_color, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(productPrice)) {
                etProductPrice.setError(getString(R.string.require_product_price));
                Toast.makeText(getApplicationContext(), R.string.require_product_price, Toast.LENGTH_SHORT).show();
            } else {
                if (Util.isNetworkAvailable(getApplicationContext())) {
                    loadingBar.show();
                    if (imageUri == null) {
                        saveProductInfoToDatabase();
                    } else {
                        storeProductInformation();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteProductBtn.setOnClickListener(v -> {
            brandName = brandNameSpinner.getSelectedItem().toString();
            productCategoryName = categoryNameSpinner.getSelectedItem().toString();
            productSubCategoryName = subCategoryNameSpinner.getSelectedItem().toString();

            dbRef.child(PRODUCTS).child(brandName).child(productCategoryName)
                    .child(productSubCategoryName).child(updateProduct.getProductId())
                    .removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(ManageProductsActivity.this, AdminHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ManageProductsActivity.this, "product delete is successfully..", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void init() {
        brandNameSpinner = findViewById(R.id.spinner_brand_name);
        categoryNameSpinner = findViewById(R.id.spinner_category_name);
        subCategoryNameSpinner = findViewById(R.id.spinner_sub_category_name);
        imgProduct = findViewById(R.id.img_product);
        etProductName = findViewById(R.id.et_product_name);
        etProductColor = findViewById(R.id.et_product_color);
        etProductPrice = findViewById(R.id.et_product_price);
        updateProductBtn = findViewById(R.id.update_product_btn);
        deleteProductBtn = findViewById(R.id.delete_product_btn);
    }

    private void chooseImageFromGallery() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Intent galleryIntent = new Intent();
                            galleryIntent.setAction(Intent.ACTION_PICK);
                            galleryIntent.setType("image/*");
                            startActivityForResult(galleryIntent, GALLERY_IMAGE_PICK);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            Util.showSettingsDialog(ManageProductsActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(ManageProductsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                String imagePath = Util.getPathFromURI(this, imageUri);
                compressedImageFile = new Compressor(this)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(75)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .compressToFile(new File(imagePath));

            } catch (IOException e) {
                e.printStackTrace();
            }
            imgProduct.setImageURI(imageUri);
        }
    }

    private void storeProductInformation() {
        final StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        uploadTask = fileReference.putFile(Uri.fromFile(compressedImageFile))
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadImageUri = uri.toString();
                    saveProductInfoToDatabase();
                })).addOnFailureListener(e -> loadingBar.dismiss());

    }

    private void saveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("productName", productName);
        productMap.put("productColor", productColor);
        productMap.put("productPrice", productPrice);
        if (imageUri == null) {
            productMap.put("image", updateProduct.getImage());
        } else {
            productMap.put("image", downloadImageUri);
        }
        productMap.put("productId", updateProduct.getProductId());
        dbRef.child(PRODUCTS).child(brandName).child(productCategoryName)
                .child(productSubCategoryName).child(updateProduct.getProductId())
                .updateChildren(productMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Intent intent = new Intent(ManageProductsActivity.this, AdminHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(ManageProductsActivity.this, "update product is successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(ManageProductsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void brandNameSpinner() {
        List<String> brandNameList = Arrays.asList(getResources().getStringArray(R.array.brand_name));
        ArrayAdapter<String> brandNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, brandNameList);
        brandNameSpinner.setAdapter(brandNameAdapter);
        for (int i = 0; i < brandNameList.size(); i++) {
            if (brandNameList.get(i).equals(brand)) {
                spinnerPosition = i;
            }
        }
        brandNameSpinner.setSelection(spinnerPosition);
        brandNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = brandNameSpinner.getSelectedItem().toString();
                if (selectedName.equals("MAYBELLINE")) {
                    maybellineCategory();
                } else if (selectedName.equals("LOREAL")) {
                    lorealCategory();
                } else {
                    revlonCategory();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void revlonCategory() {
        List<String> categoryNameList = Arrays.asList(getResources().getStringArray(R.array.category_name));
        ArrayAdapter<String> categoryNameAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categoryNameList);
        categoryNameSpinner.setAdapter(categoryNameAdapter);
        for (int i = 0; i < categoryNameList.size(); i++) {
            if (categoryNameList.get(i).equals(brandCategory)) {
                spinnerPosition = i;
            }
        }
        categoryNameSpinner.setSelection(spinnerPosition);

        categoryNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = categoryNameSpinner.getSelectedItem().toString();
                if (selectedName.equals("EYE COSMETICS")) {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.revlon_eye_category_name));
                    categorySpinner(subCategoryNameList);
                } else if (selectedName.equals("FACE COSMETICS")) {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.revlon_face_category_name));
                    categorySpinner(subCategoryNameList);
                } else {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.revlon_lip_category_name));
                    categorySpinner(subCategoryNameList);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void lorealCategory() {
        List<String> categoryNameList = Arrays.asList(getResources().getStringArray(R.array.category_name));
        ArrayAdapter<String> categoryNameAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categoryNameList);
        categoryNameSpinner.setAdapter(categoryNameAdapter);
        for (int i = 0; i < categoryNameList.size(); i++) {
            if (categoryNameList.get(i).equals(brandCategory)) {
                spinnerPosition = i;
            }
        }
        categoryNameSpinner.setSelection(spinnerPosition);
        categoryNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = categoryNameSpinner.getSelectedItem().toString();
                if (selectedName.equals("EYE COSMETICS")) {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.loreal_eye_category_name));
                    categorySpinner(subCategoryNameList);
                } else if (selectedName.equals("FACE COSMETICS")) {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.loreal_face_category_name));
                    categorySpinner(subCategoryNameList);
                } else {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.loreal_lip_category_name));
                    categorySpinner(subCategoryNameList);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void maybellineCategory() {
        List<String> categoryNameList = Arrays.asList(getResources().getStringArray(R.array.category_name));
        ArrayAdapter<String> categoryNameAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categoryNameList);
        categoryNameSpinner.setAdapter(categoryNameAdapter);
        for (int i = 0; i < categoryNameList.size(); i++) {
            if (categoryNameList.get(i).equals(brandCategory)) {
                spinnerPosition = i;
            }
        }
        categoryNameSpinner.setSelection(spinnerPosition);
        categoryNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = categoryNameSpinner.getSelectedItem().toString();
                if (selectedName.equals("EYE COSMETICS")) {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.maybelline_eye_category_name));
                    categorySpinner(subCategoryNameList);
                } else if (selectedName.equals("FACE COSMETICS")) {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.maybelline_face_category_name));
                    categorySpinner(subCategoryNameList);
                } else {
                    List<String> subCategoryNameList = Arrays.asList(getResources().getStringArray(R.array.maybelline_lip_category_name));
                    categorySpinner(subCategoryNameList);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void categorySpinner(List<String> subCategoryNameList) {
        ArrayAdapter<String> subCategoryNameAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, subCategoryNameList);
        subCategoryNameSpinner.setAdapter(subCategoryNameAdapter);
        for (int i = 0; i < subCategoryNameList.size(); i++) {
            if (subCategoryNameList.get(i).equals(product)) {
                spinnerPosition = i;
            }
        }
        subCategoryNameSpinner.setSelection(spinnerPosition);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
