package com.example.infinityjobportal;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadResumeFromSavedJobs extends AppCompatActivity {
    ImageView back;
    TextView message;
    Button apply, uploadPdf;
ArrayList<String> deleteList = new ArrayList<>();
    StorageReference mstorageRef;
    String a;
    String b;
    String id;
    public Uri imguri;
    private StorageTask uploadTask;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resume);
        back = findViewById(R.id.back);
        apply = findViewById(R.id.apply);
        uploadPdf = findViewById(R.id.uploadPdf);
        message = findViewById(R.id.message);

        db = FirebaseFirestore.getInstance();
        mstorageRef = FirebaseStorage.getInstance().getReference("resume");
        mAuth = FirebaseAuth.getInstance();
        id = getIntent().getStringExtra("jobId");


       // Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();



        uploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooserPdf();
            }
        });


checkSave();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_LONG).show();

                } else {
                    Fileuploader();
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Fileuploader() {
        //StorageReference ref = mstorageRef.child(System.currentTimeMillis()
        b=getExtension(imguri);
        a= mAuth.getCurrentUser().getEmail()+"_"+id+"_resume";//System.currentTimeMillis();

        StorageReference ref = mstorageRef.child(a
                + "." + getExtension(imguri) );
        uploadTask = ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        HashMap apllication = new HashMap();
                        apllication.put("uid",mAuth.getCurrentUser().getEmail());
                        apllication.put("jobId",id);
                        apllication.put("type","application");
                        apllication.put("resume",a+"."+b);

                        db.collection("MyJobs").add(apllication)
                                .addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {


                                       // checkSave();

                                        if(!deleteList.isEmpty()) {
                                            deleteJobMyjobs();
                                        }

                                        Intent i = new Intent(getApplicationContext(),Successful.class);
                                        startActivity(i);
                                        JobDetails.getInstance().finish();
                                        finish();


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadResumeFromSavedJobs.this);
                                        builder.setMessage("Something went wrong. Please try again...")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        finish();

                                                        message.setText("");
                                                        apply.setVisibility(View.INVISIBLE);
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                });



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadResumeFromSavedJobs.this);
                        builder.setMessage("Something went wrong. Please try again...")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();

                                        message.setText("");
                                        apply.setVisibility(View.INVISIBLE);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();


                    }
                });

    }

    private void deleteJobMyjobs() {
for(int i = 0;i<deleteList.size();i++) {

    db.collection("MyJobs").document(deleteList.get(i)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });

}
    }

    private void checkSave() {


        db.collection("MyJobs").whereEqualTo("jobId",id).whereEqualTo("uid",mAuth.getCurrentUser().getEmail()).whereEqualTo("type","save")
             .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                for (DocumentSnapshot d : list1) {

                    // PostJobPojo p = d.toObject(PostJobPojo.class); :
                    //  p.setJobTitle(d.getString("jobTitle"));
                    // p.setCompanyName(d.getString("companyName"));
                    //p.setCityAddress(d.getString("cityAddress"));
                    //p.setId(d.getId());

                    deleteList.add(d.getId());

                   // Toast.makeText(UploadResume.this, " delete : "+d.getId(), Toast.LENGTH_SHORT).show();


                    // saveIdList.add(d.getId());
                    // Toast.makeText(getContext(),d.getString("jobId"),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(),saveIdList,Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }



    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void FileChooserPdf() {
        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/pdf"
                };

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == RESULT_OK && data !=null && data.getData()!= null)
        {
            imguri = data.getData();
            message.setText("File Selected Successfully...");
            apply.setVisibility(View.VISIBLE);

        }
    }



}