package com.frontiertechnologypartners.beautysecret.ui.admin.add_new_product;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
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

import androidx.annotation.Nullable;
import id.zelory.compressor.Compressor;

import static com.frontiertechnologypartners.beautysecret.util.Constant.GALLERY_IMAGE_PICK;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;

public class AdminAddNewProductActivity extends BaseActivity {
    private Spinner brandNameSpinner, categoryNameSpinner, subCategoryNameSpinner;
    private TextInputEditText etProductName, etProductColor, etProductPrice;
    private ImageView imgProduct;
    private Button btnAddNewProduct;

    private String brandName, productCategoryName, productSubCategoryName;
    private Uri imageUri;
    private String productName, productColor, productPrice, downloadImageUri;
    private File compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        init();
        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //brand name spinner
        brandNameSpinner();

        imgProduct.setOnClickListener(v -> chooseImageFromGallery());
        btnAddNewProduct.setOnClickListener(v -> {
            productName = etProductName.getText().toString();
            productColor = etProductColor.getText().toString();
            productPrice = etProductPrice.getText().toString();
            brandName = brandNameSpinner.getSelectedItem().toString();
            productCategoryName = categoryNameSpinner.getSelectedItem().toString();
            productSubCategoryName = subCategoryNameSpinner.getSelectedItem().toString();

            if (imageUri == null) {
                Toast.makeText(AdminAddNewProductActivity.this, R.string.require_image, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(productName)) {
                etProductName.setError(getString(R.string.require_product_name));
                Toast.makeText(AdminAddNewProductActivity.this, R.string.require_product_name, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(productColor)) {
                etProductColor.setError(getString(R.string.require_product_color));
                Toast.makeText(AdminAddNewProductActivity.this, R.string.require_product_color, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(productPrice)) {
                etProductPrice.setError(getString(R.string.require_product_price));
                Toast.makeText(AdminAddNewProductActivity.this, R.string.require_product_price, Toast.LENGTH_SHORT).show();
            } else {
                if (Util.isNetworkAvailable(getApplicationContext())) {
                    loadingBar.show();
                    storeProductInformation();
                } else {
                    Toast.makeText(AdminAddNewProductActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        brandNameSpinner = findViewById(R.id.spinner_brand_name);
        categoryNameSpinner = findViewById(R.id.spinner_category_name);
        subCategoryNameSpinner = findViewById(R.id.spinner_sub_category_name);
        etProductName = findViewById(R.id.et_product_name);
        etProductColor = findViewById(R.id.et_product_color);
        etProductPrice = findViewById(R.id.et_product_price);
        imgProduct = findViewById(R.id.img_product);
        btnAddNewProduct = findViewById(R.id.btn_add_product);
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
                            Util.showSettingsDialog(AdminAddNewProductActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(AdminAddNewProductActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show())
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
        String keyId = dbRef.push().getKey();
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("productName", productName);
        productMap.put("productColor", productColor);
        productMap.put("productPrice", productPrice);
        productMap.put("image", downloadImageUri);
        productMap.put("productId", keyId);
        dbRef.child(PRODUCTS).child(brandName).child(productCategoryName).child(productSubCategoryName).child(keyId).setValue(productMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(AdminAddNewProductActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void brandNameSpinner() {
        List<String> brandNameList = Arrays.asList(getResources().getStringArray(R.array.brand_name));
        ArrayAdapter<String> brandNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, brandNameList);
        brandNameSpinner.setAdapter(brandNameAdapter);
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
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
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
