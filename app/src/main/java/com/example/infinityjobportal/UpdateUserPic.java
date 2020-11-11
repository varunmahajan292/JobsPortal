package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserPic extends AppCompatActivity {
    TextView save;
    ImageView back, userPic, gallery,delete;
    ProgressBar progressBar;

    StorageReference mstorageRef;
    String a;
    String b;
    String userpic;
    public Uri imguri;
    private StorageTask uploadTask;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_pic);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressBar);
        userPic  = findViewById(R.id.userPic);
        gallery = findViewById(R.id.gallery);
        delete = findViewById(R.id.delete);

        save = findViewById(R.id.save);
        mAuth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        mstorageRef = FirebaseStorage.getInstance().getReference("user");


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             FileChooser();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_LONG).show();

                } else {
                    Fileuploader();
                }

            }
        });


        loadInfo();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());

                Map<String,Object> updates = new HashMap<>();
                updates.put("userProfilePic", "user.png");

                docRef.update((Map<String, Object>) updates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUserPic.this);
                                builder.setMessage("Profile Pic Deleted")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();  progressBar.setVisibility(View.GONE);
                                Resources res = getResources(); /** from an Activity */
                                userPic.setImageDrawable(res.getDrawable(R.drawable.images));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Data Not Updated. Try Again..,",Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }


    private void Fileuploader() {

        //StorageReference ref = mstorageRef.child(System.currentTimeMillis()
        b=getExtension(imguri);
        a= mAuth.getCurrentUser().getEmail()+"_profilePic";//System.currentTimeMillis();
        StorageReference ref = mstorageRef.child(a
                + "." + getExtension(imguri) );
        uploadTask = ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        // Uri downloadUrl = taskSnapshot.getUploadSessionUri();  //getDownloadUrl();
//                        Toast.makeText(getApplicationContext(),
//                                "Image uploaded", Toast.LENGTH_LONG).show();
                        updateDatabase();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }




    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == RESULT_OK && data !=null && data.getData()!= null)
        {
            imguri = data.getData();
            userPic.setImageURI(imguri);

        }
    }











    private void updateDatabase() {
        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());

        Map<String,Object> updates = new HashMap<>();
        updates.put("userProfilePic", a+"."+b);

        docRef.update((Map<String, Object>) updates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      //  Toast.makeText(getApplicationContext(),"Data Updated...",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUserPic.this);
                        builder.setMessage("Profile Pic Updated")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressBar.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateUserPic.this);
                        builder.setMessage("Profile Pic Not updated. Please Try Again...")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
    }










    private void loadInfo() {
        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        User user = document.toObject(User.class);

                        if(user.getUserProfilePic().equals(""))
                        {
                           // userPic.setHint("Write here about your self.");
                            //Toast.makeText(getApplicationContext(),user.getUserProfilePic(),Toast.LENGTH_SHORT).show();
                        }else if(user.getUserProfilePic().equals("user.png"))
                        {
                        }
                        else
                        {
                           // userPic.setText(user.getAbout());
                           // Toast.makeText(getApplicationContext(),user.getUserProfilePic(),Toast.LENGTH_SHORT).show();

                            FirebaseStorage firebaseStorage;
                            StorageReference storageReference;

                            firebaseStorage = FirebaseStorage.getInstance();
                            storageReference = firebaseStorage.getReference();

//        StorageReference imageRef = storageReference.child("Images/my.png");
                            StorageReference imageRef = storageReference.child("user/"+user.getUserProfilePic());

                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(getApplicationContext()).load(uri).into(userPic);

                                    //Toast.makeText(getApplicationContext(),"Success.",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Toast.makeText(getApplicationContext(),"fail.",Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    } else {

                    }
                } else {

                }
            }
        });

    }


}