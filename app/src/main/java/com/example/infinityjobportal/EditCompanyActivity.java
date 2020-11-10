package com.example.infinityjobportal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class EditCompanyActivity extends AppCompatActivity {

    AppCompatEditText tvCompanyName, tvLocation, tvLine1, tvLine2, tvCity, tvState, tvCountry, tvAbout, tvDesc, tvWeb, tvEmail, tvContact;
    Spinner spnIndustry;
    AppCompatButton btnSubmit;
    ImageView ivLogo, back;
    AppCompatImageView ivCamera;
    TextView save;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;

    String userId;

    FirebaseStorage storage;
    StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 10;
    private final int PICK_IMAGE_PERMISSION = 100;
    String companyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_edit);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        userId = mAuth.getCurrentUser().getUid();
        ivLogo = findViewById(R.id.ivLogo);


        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvLocation = findViewById(R.id.tvLocation);
        tvLine1 = findViewById(R.id.tvLine1);
        tvLine2 = findViewById(R.id.tvLine2);
        tvCity = findViewById(R.id.tvCity);
        tvState = findViewById(R.id.tvState);
        tvCountry = findViewById(R.id.tvCountry);
        tvAbout = findViewById(R.id.tvAbout);
        tvDesc = findViewById(R.id.tvDesc);
        tvWeb = findViewById(R.id.tvWeb);
        tvEmail = findViewById(R.id.tvEmail);
        tvContact = findViewById(R.id.tvContact);
        spnIndustry = findViewById(R.id.tvIndustry);

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
        spnIndustry.setPrompt(industry);
        tvCity.setText(city);
        tvState.setText(state);
        tvCountry.setText(country);
        tvAbout.setText(about);
        tvDesc.setText(desc);
        tvWeb.setText(web);
        tvEmail.setText(email);
        tvContact.setText(contact);

        save = findViewById(R.id.save);

        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(EditCompanyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditCompanyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_PERMISSION);
                } else {
                    Intent i = new Intent(EditCompanyActivity.this, UpdateCompanyPicActivity.class);
                    i.putExtra("companyid", companyId);
                    startActivity(i);
                    finish();
                }
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
                Glide.with(EditCompanyActivity.this).load(uri).into(ivLogo);

                //Toast.makeText(getApplicationContext(),"Success.",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(),"fail.",Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName = tvCompanyName.getText().toString();
                String location = tvLocation.getText().toString();
                String line1 = tvLine1.getText().toString();
                String line2 = tvLine2.getText().toString();
                String city = tvCity.getText().toString();
                String state = tvState.getText().toString();
                String country = tvCountry.getText().toString();
                String about = tvAbout.getText().toString();
                String desc = tvDesc.getText().toString();
                String web = tvWeb.getText().toString();
                String email = tvEmail.getText().toString();
                String contact = tvContact.getText().toString();


                if (TextUtils.isEmpty(companyName)) {
                    tvCompanyName.setError("Invalid");
                    return;
                }

                if (TextUtils.isEmpty(location)) {
                    tvLocation.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(line1)) {
                    tvLine1.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(line2)) {
                    tvLine2.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    tvCity.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(state)) {
                    tvState.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(country)) {
                    tvCountry.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(about)) {
                    tvAbout.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(desc)) {
                    tvDesc.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(web)) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(tvWeb);
                    tvWeb.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    tvEmail.setError("Invalid");
                    return;
                }

                if (TextUtils.isEmpty(contact)) {
                    tvContact.setError("Invalid");
                    return;
                }
                Log.e("here", "here");


                Map<String, Object> order = new HashMap<>();

                order.put("name", companyName);
                order.put("location", location);
                order.put("line1", line1);
                order.put("line2", line2);
                order.put("city", city);
                order.put("state", state);
                order.put("country", country);
                order.put("about", about);
                order.put("desc", desc);
                order.put("web", web);
                order.put("email", email);
                order.put("contact", contact);
                order.put("userId", userId);
                order.put("industry", spnIndustry.getSelectedItem().toString());


                firebaseFirestore.collection("mycompanies").document(companyId)
                        .update(order)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(EditCompanyActivity.this, "Company Updated Successfully", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivLogo.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_PERMISSION && requestCode == RESULT_OK) {
            if (resultCode == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            } else {
                //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(EditCompanyActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            Log.e("path", taskSnapshot.getUploadSessionUri().getPath());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditCompanyActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
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

