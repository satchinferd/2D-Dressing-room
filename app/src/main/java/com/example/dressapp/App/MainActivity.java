package com.example.dressapp.App;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dressapp.BitmapUtility;
import com.example.dressapp.CutOut;
import com.example.dressapp.R;

import com.example.dressapp.SaveDrawingTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pub.devrel.easypermissions.EasyPermissions;

import android.widget.Button;

import android.os.StrictMode;


public class MainActivity extends AppCompatActivity {
     ImageView imageView;
    //private com.github.gabrielbb.cutout.test.CustomImageVIew ivTop;
    private CustomImageVIewCombine ivTop;
    private static final int GALLERY_PERMISSION_REQUESET_CODE = 1;
    private static final int SELECT_DRESS_PHOTO = 2;
    private static final int SELECT_PERSON_PHOTO = 3;
    private ImageView mIv;
    Button saveimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActivityCompat.requestPermissions(MainActivity.this,
//                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        ActivityCompat.requestPermissions(MainActivity.this,
//                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        imageView = findViewById(R.id.imageView);
        ivTop = findViewById(R.id.ivTop);
        //saveimage = (Button)findViewById(R.id.savegallery);


        final Uri imageIconUri = getUriFromDrawable(R.drawable.bg);
        //imageView.setImageURI(imageIconUri);
        imageView.setTag(imageIconUri);

        FloatingActionButton fab = findViewById(R.id.fab);
//        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
//        saveimage.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//
//                                        saveToGallery();
//                                    }
//                                    });


//        fab.setOnClickListener(view ->
//        {
//
//
//            //startActivity(new Intent(MainActivity.this, MainActivity.class));
//            //final Uri testImageUri = getUriFromDrawable(R.drawable.download);
//
////            CutOut.activity()
////                    .src(testImageUri)
////                    .bordered()
////                    .noCrop()
////                    .start(this);
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE) {

            switch (resultCode) {
                case Activity.RESULT_OK:
                    Uri imageUri = CutOut.getUri(data);

                    //imageView.setImageURI(imageUri);
                    //imageView.setTag(imageUri);
                    ivTop.setImageURI(imageUri);
                    ivTop.setVisibility(View.VISIBLE);
                    break;
                case CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE:
                    Exception ex = CutOut.getError(data);
                    break;
                default:
                    System.out.print("User cancelled the CutOut screen");
            }
        } else if (requestCode == SELECT_DRESS_PHOTO) {
            Uri selectedImage = data.getData();
            Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show();
            //imageView.setImageURI(selectedImage);

            //final Uri testImageUri = getUriFromDrawable(R.drawable.download);

            CutOut.activity()
                    .src(selectedImage)
                    .bordered()
                    .noCrop()
                    .start(this);
        } else if (requestCode == SELECT_PERSON_PHOTO) {
            Uri selectedImage = data.getData();
            Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show();
            imageView.setImageURI(selectedImage);
        }
    }

    public Uri getUriFromDrawable(int drawableId) {
        return Uri.parse("android.resource://" + getPackageName() + "/drawable/" + getApplicationContext().getResources().getResourceEntryName(drawableId));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnChooseDress(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_DRESS_PHOTO);
    }

    public void btnChooseImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PERSON_PHOTO);
    }

    public void goToActivity2 (View view){
        Intent intent = new Intent (this, StartActivity.class);
        startActivity(intent);
    }



//    public void ScreenshotButton(View view){
//        View view1 = getWindow().getDecorView().getRootView();
//        view1.setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
//        view1.setDrawingCacheEnabled(false);
//
//        String filePath = Environment.getExternalStorageDirectory()+"/Download/"+ Calendar.getInstance().getTime().toString()+".jpg";
//        File fileScreenshot = new File(filePath);
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream(fileScreenshot);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(fileScreenshot);
//        intent.setDataAndType(uri,"image/jpeg");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        this.startActivity(intent);
//    }



//
//    private void saveToGallery(){
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//
//        FileOutputStream outputStream = null;
//        File file = Environment.getExternalStorageDirectory();
//        File dir = new File(file.getAbsolutePath() + "/MyPics");
//        dir.mkdirs();
//
//        String filename = String.format("%d.png",System.currentTimeMillis());
//        File outFile = new File(dir,filename);
//        try{
//            outputStream = new FileOutputStream(outFile);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
//        try{
//            outputStream.flush();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        try{
//            outputStream.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }



}


