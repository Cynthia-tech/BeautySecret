package com.frontiertechnologypartners.beautysecret.ui.admin;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.frontiertechnologypartners.beautysecret.R;
import com.frontiertechnologypartners.beautysecret.ui.base.BaseActivity;
import com.frontiertechnologypartners.beautysecret.ui.home.AdminCategoryActivity;
import com.frontiertechnologypartners.beautysecret.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.frontiertechnologypartners.beautysecret.util.Constant.GALLERY_IMAGE_PICK;
import static com.frontiertechnologypartners.beautysecret.util.Constant.PRODUCTS;

public class AdminAddNewProductActivity extends BaseActivity {
    @BindView(R.id.spinner_brand_name)
    Spinner brandNameSpinner;

    @BindView(R.id.spinner_category_name)
    Spinner categoryNameSpinner;

    @BindView(R.id.spinner_sub_category_name)
    Spinner subCategoryNameSpinner;

    @BindView(R.id.img_product)
    ImageView imgProduct;

    @BindView(R.id.et_product_name)
    TextInputEditText etProductName;

    @BindView(R.id.et_product_color)
    TextInputEditText etProductColor;

    @BindView(R.id.et_product_price)
    TextInputEditText etProductPrice;

    private String brandName, productCategoryName, productSubCategoryName;
    private Uri imageUri;
    private String productName, productColor, productPrice, downloadImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        ButterKnife.bind(this);

        //brand name spinner
        brandNameSpinner();

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery();
            }
        });
    }

    private void chooseImageFromGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgProduct.setImageURI(imageUri);
        }
    }

    @OnClick(R.id.btn_add_product)
    void addNewProduct() {
        productName = etProductName.getText().toString();
        productColor = etProductColor.getText().toString();
        productPrice = etProductPrice.getText().toString();
        brandName = brandNameSpinner.getSelectedItem().toString();
        productCategoryName = categoryNameSpinner.getSelectedItem().toString();
        productSubCategoryName = subCategoryNameSpinner.getSelectedItem().toString();

        if (imageUri == null) {
            Toast.makeText(this, R.string.require_image, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(productName)) {
            etProductName.setError(getString(R.string.require_product_name));
            Toast.makeText(this, R.string.require_product_name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(productColor)) {
            etProductColor.setError(getString(R.string.require_product_color));
            Toast.makeText(this, R.string.require_product_color, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(productPrice)) {
            etProductPrice.setError(getString(R.string.require_product_price));
            Toast.makeText(this, R.string.require_product_price, Toast.LENGTH_SHORT).show();
        } else {
            if (Util.isNetworkAvailable(getApplicationContext())) {
                loadingBar.show();
                storeProductInformation();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void storeProductInformation() {
        final StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        uploadTask = fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadImageUri = uri.toString();
                    saveProductInfoToDatabase();
                })).addOnFailureListener(e -> loadingBar.dismiss());

    }

    private void saveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("productName", productName);
        productMap.put("productColor", productColor);
        productMap.put("productPrice", "$" + productPrice);
        productMap.put("image", downloadImageUri);
        String keyId = dbRef.push().getKey();
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
                if (selectedName.equals("EYES COSMETICS")) {
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
                if (selectedName.equals("EYES COSMETICS")) {
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
                if (selectedName.equals("EYES COSMETICS")) {
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
}
