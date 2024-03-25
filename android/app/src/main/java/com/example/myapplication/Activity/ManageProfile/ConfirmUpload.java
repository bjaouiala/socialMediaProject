package com.example.myapplication.Activity.ManageProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.PostService;
import com.example.myapplication.services.UserService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload);
        toolbar = findViewById(R.id.toolbar2);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button3);
        editText = findViewById(R.id.editTextTextMultiLine);
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

    public String createNewDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }

    public void upploadImage() {
        String descriptin = editText.getText().toString();
        String dateString = createNewDate();
        Intent i = getIntent();
        long id = i.getLongExtra("id", 0);
        String picture = i.getStringExtra("picture");
        Uri uri = i.getData();
        String path = getPathFromUri(uri);

        if (!checkPermission()) {
            requestPermission();
            return;
        }

        if (path == null) {
            Toast.makeText(ConfirmUpload.this, "la photo n'existe pas", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody postdescription = RequestBody.create(MediaType.parse("text/plain"),descriptin);
        RequestBody dateCreate = RequestBody.create(MediaType.parse("text/plain"),dateString);

        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<ResponseBody> call = postService.savePost( part,postdescription,dateCreate, id, picture);
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