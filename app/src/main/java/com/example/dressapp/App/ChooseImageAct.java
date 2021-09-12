package com.example.dressapp.App;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dressapp.R;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class ChooseImageAct extends AppCompatActivity
{

    private static final int GALLERY_PERMISSION_REQUESET_CODE = 1;
    private static final int SELECT_PHOTO = 2;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        initView();
        choseImageFromGallery();
    }

    @AfterPermissionGranted(GALLERY_PERMISSION_REQUESET_CODE)
    private void choseImageFromGallery() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "Permission",
                    GALLERY_PERMISSION_REQUESET_CODE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void btnChooseImage(View view) {
        choseImageFromGallery();
    }

    public void initView() {

        mIv = findViewById(R.id.iv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO) {
            Uri selectedImage = data.getData();
            mIv.setImageURI(selectedImage);
//            Picasso.get()
//                    .load(selectedImage)
//                    .resize(50, 50)
//                    .centerCrop()
//                    .into(mIv);
           // Glide.with(getApplicationContext()).load(selectedImage).into(mIv);
        }
    }
}