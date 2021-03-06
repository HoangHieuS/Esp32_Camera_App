package com.example.esp32app.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esp32app.EnrollActivity;
import com.example.esp32app.User.ProfileActivity;
import com.example.esp32app.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;


    TextView name;
    ImageView imgView, menuIcon;
    Button btn, addFace, profile;
    Uri image_uri;
    DatabaseReference rff = FirebaseDatabase.getInstance().getReference();
//    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.txtName);
        //photo = findViewById(R.id.txtPhoto);
        imgView = findViewById(R.id.imgView);
        btn = findViewById(R.id.btn);
        menuIcon = findViewById(R.id.menu_icon);
//        contentView = findViewById(R.id.content);
//        addFace = findViewById(R.id.btnEnroll);
//        profile = findViewById(R.id.btnProfile);
        //takePhoto = findViewById(R.id.btnTakePhoto);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);


        navigationDrawer();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rff.child("controlStatus").child("takePhotoBtn").setValue("on");
            }
        });
//        addFace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, EnrollActivity.class));
//            }
//        });
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
//            }
//        });
    }

    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

//        animateNavigationDrawer();
    }


//    private void animateNavigationDrawer() {
//
//        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//                final float diffScaleOffset = slideOffset * (1 - END_SCALE);
//                final float offsetScale = 1 - diffScaleOffset;
//                contentView.setScaleX(offsetScale);
//                contentView.setScaleY(offsetScale);
//
//                final float xOffset = drawerView.getWidth() * slideOffset;
//                final float xOffsetDiff = contentView.getWidth() * diffScaleOffset / 2;
//                final float xTranslation = xOffset - xOffsetDiff;
//                contentView.setTranslationX(xTranslation);
//
//
//            }
//        });
//    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();

        super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        break;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        rff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String faceName = snapshot.child("esp32-cam").child("name").getValue().toString().toUpperCase();
                String photoBase64 = snapshot.child("esp32-cam").child("photo").getValue().toString();
                String data = photoBase64.substring(photoBase64.indexOf(",") + 1);
                name.setText(faceName);
                //photo.setText(photoBase64);

                //byte[] decod = com.example.esp32app.Base64.decode(photoBase64, 4);
                byte[] decoderString = Base64.decode(data.getBytes(), Base64.DEFAULT);
                Bitmap decoded = BitmapFactory.decodeByteArray(decoderString, 0, decoderString.length);

                imgView.setImageBitmap(decoded);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}