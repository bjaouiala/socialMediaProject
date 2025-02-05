package com.example.myapplication.Activity.ManageHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.Activity.FriendRequest.FriendRequestFragment;
import com.example.myapplication.Activity.FriendSearch.FriendSearchFragment;
import com.example.myapplication.Activity.ManageAccount.ManageAccountFragment;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
   private FriendSearchFragment friendSearchFragment = new FriendSearchFragment();
    WelcomeFragment welcomeFragment = new WelcomeFragment();
    private FriendRequestFragment friendRequestFragment = new FriendRequestFragment();
    ManageAccountFragment manageAccountFragment = new ManageAccountFragment();
    FragmentManager fragmentManager;
    private long userid;
    private Bundle bundle;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.navigation_bar);
        Intent intent = getIntent();
        userid = intent.getLongExtra("id",0);

        bundle = new Bundle();
        bundle.putLong("id",userid);

        welcomeFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,welcomeFragment);
        fragmentTransaction.commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (R.id.welcome == id){
                    welcomeFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container,welcomeFragment);
                    fragmentTransaction.commit();
                    return true;
                } else if (R.id.friendrequest == id) {

                    friendRequestFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container,friendRequestFragment);
                    fragmentTransaction.commit();
                    return true;

                } else if (R.id.notification == id) {
                    return true;

                } else if (R.id.search == id) {

                    friendSearchFragment.setArguments(bundle);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,friendSearchFragment);
                    fragmentTransaction.commit();
                    return true;

                } else if (R.id.menu == id) {
                    manageAccountFragment.setArguments(bundle);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,manageAccountFragment);
                    fragmentTransaction.commit();
                    return true;


                } else {
                    return true;
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        return true;
    }



}