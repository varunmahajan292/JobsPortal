package com.example.infinityjobportal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class CompanyProfileActivity extends AppCompatActivity {

    AppCompatImageView ivEdit;
    ImageView ivCompanyLogo, back;
    AppCompatTextView tvCompanyName, tvIndustry, tvLocation, tvWeb, tvAbout, tvDescription, tvEmail, tvPhone;
    String companyId;
    TextView edit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
     /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);
       getSupportActionBar().setTitle("Company Information");*/
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvIndustry = findViewById(R.id.tvIndustry);
        tvLocation = findViewById(R.id.tvLocation);
        tvWeb = findViewById(R.id.tvWeb);
        tvAbout = findViewById(R.id.tvAbout);
        tvDescription = findViewById(R.id.tvDescription);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        ivCompanyLogo = findViewById(R.id.ivCompanyLogo);

        companyId = getIntent().getStringExtra("id");
        final String name = getIntent().getStringExtra("name");
        final String industry = getIntent().getStringExtra("industry");
        final String country = getIntent().getStringExtra("country");
        final String email = getIntent().getStringExtra("email");
        final String contact = getIntent().getStringExtra("contact");
        final String desc = getIntent().getStringExtra("desc");
        final String about = getIntent().getStringExtra("about");
        final String web = getIntent().getStringExtra("web");
        final String city = getIntent().getStringExtra("city");
        final String state = getIntent().getStringExtra("state");
        final String company_image = getIntent().getStringExtra("company_image");

        tvCompanyName.setText(name);
        tvIndustry.setText(industry);
        tvLocation.setText(city + ", " + state + ", " + country);
        tvWeb.setText(web);
        tvAbout.setText(about);
        tvDescription.setText(desc);
        tvEmail.setText(email);
        tvPhone.setText(contact);

        edit=findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CompanyProfileActivity.this, EditCompanyActivity.class);
                i.putExtra("id", companyId);
                i.putExtra("name", name);
                i.putExtra("industry", industry);
                i.putExtra("country", country);
                i.putExtra("email", email);
                i.putExtra("contact", contact);
                i.putExtra("desc", desc);
                i.putExtra("about", about);
                i.putExtra("web", web);
                i.putExtra("city", city);
                i.putExtra("state", state);
                i.putExtra("company_image", company_image);
                startActivity(i);
                finish();
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference imageRef = storageReference.child("company/" + company_image);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(CompanyProfileActivity.this).load(uri).into(ivCompanyLogo);

                //Toast.makeText(getApplicationContext(),"Success.",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(),"fail.",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here


                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

