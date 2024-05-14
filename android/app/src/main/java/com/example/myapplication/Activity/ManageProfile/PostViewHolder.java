package com.example.myapplication.Activity.ManageProfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.ManagePost.ButtomSheetFragement;
import com.example.myapplication.Helper;
import com.example.myapplication.Model.Post;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView postView,icone,menu;
    private TextView commentaire,jaime,description,namepost,date;
    private PostResponse post ;

    private ImageSelectionListnner listnner;
    private int position;


    public PostViewHolder(@NonNull View itemView ,ImageSelectionListnner l) {
        super(itemView);
        listnner=l;
        postView = itemView.findViewById(R.id.post);
        commentaire = itemView.findViewById(R.id.commentaireNum);
        jaime = itemView.findViewById(R.id.jaimeNum);
        description = itemView.findViewById(R.id.description);
        icone = itemView.findViewById(R.id.pictureIcone);
        namepost = itemView.findViewById(R.id.namep);
        date = itemView.findViewById(R.id.date);
        menu = itemView.findViewById(R.id.menu);


        postView.setOnClickListener(this);
        menu.setOnClickListener(this);

    }


    public void bind(PostResponse postResponse,int pos) {
        post = postResponse;
        position =pos;

        Bitmap postFile = Helper.parseImage(post.getPostFile());
        Bitmap postIcone = Helper.parseImage(post.getPhotoDeProfile());
        if (postFile != null){
            postView.setImageBitmap(postFile);
        }else {
            postView.setVisibility(View.GONE);
            description.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
        }
        if (postIcone != null){
            icone.setImageBitmap(postIcone);
        }else {
            icone.setImageResource(R.drawable.defaultpicture);
        }
        description.setText(post.getDescription());
        namepost.setText(post.getFirstname()+" "+post.getLastname());
        String date1 = post.getDateCreate();
        String convertedDate = date1.substring(0,10);
        date.setText(Helper.convertDate(convertedDate));


    }

    @Override
    public void onClick(View v) {
        long id = post.getPost_id();
        if (v.getId() == R.id.post){
            listnner.displayPicture(id);
        }else if (v.getId() == R.id.menu){
            listnner.listMenuItem(post.getPost_id(),position);
            Log.d("holderPos",position+" ");
        }

    }
}
