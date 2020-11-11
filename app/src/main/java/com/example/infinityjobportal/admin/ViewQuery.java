package com.example.infinityjobportal.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinityjobportal.ClientLogin;
import com.example.infinityjobportal.ClientSignUp;
import com.example.infinityjobportal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewQuery extends AppCompatActivity {
TextView firstname, subject, description;
    Button delete, reply;
ImageView back;

String id;

FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_view_query);
        firstname=findViewById(R.id.firstname);
        subject=findViewById(R.id.subject);
        description=findViewById(R.id.description);
        delete=findViewById(R.id.deleteButton);
        reply=findViewById(R.id.replyButton);
        back=findViewById(R.id.back);

       // firstname.setText(" ");
      // subject.setText(" ");
       // description.setText(" ");
        //= getIntent().getStringExtra("sub");

        id  = getIntent().getStringExtra("id");


       // Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        firstname.setText( getIntent().getStringExtra("usname"));
        subject.setText( getIntent().getStringExtra("sub"));
        description.setText( getIntent().getStringExtra("maincontent"));



        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={getIntent().getStringExtra("email")};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Re : "+ getIntent().getStringExtra("sub"));
                intent.putExtra(Intent.EXTRA_TEXT,"Hello, dear \n Your query is valuable for us.");
                intent.putExtra(Intent.EXTRA_CC,"");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                view.getContext().startActivity(Intent.createChooser(intent, "Send mail"));

            }
        });





        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("Query").document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ViewQuery.this);
                                builder.setMessage("Query deleted successfully.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                               finish();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewQuery.this);
                        builder.setMessage("Query not deleted. Please try again...")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

            }
        });





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });






    }
}