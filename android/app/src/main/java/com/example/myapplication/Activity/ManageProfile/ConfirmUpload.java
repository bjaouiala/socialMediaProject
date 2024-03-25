package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Model.User;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.ManageProfileService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;

public class ConfirmUpload extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload);
        toolbar = findViewById(R.id.toolbar2);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button3);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        Uri uri = i.getData();
        imageView.setImageURI(uri);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view -> upploadImage());

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void upploadImage() {
        Intent i = getIntent();
        long id = i.getLongExtra("id", 0);
        Uri uri = i.getData();

        if (!checkPermission()) {
            requestPermission();
            return;
        }

        String path = getPathFromUri(uri);
        if (path == null) {
            Toast.makeText(ConfirmUpload.this, "la photo n'existe pas", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        ManageProfileService manageProfileService = RetrofitClient.getRetrofitInsantce().create(ManageProfileService.class);
        Call<ResponseBody> call = manageProfileService.changeProfilePicture(part, id, "photoDeCouverture");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ConfirmUpload.this, "Picture uploaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConfirmUpload.this, "Picture didn't uploaded", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(ConfirmUpload.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getPathFromUri(Uri uri) {
        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private static final int REQUEST_PERMISSION_CODE = 123;

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                upploadImage();
            } else {
                Toast.makeText(this, "Permission denied. Cannot proceed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}