package com.example.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtName, txtQty, txtPrice;
    Button btnPost, btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtQty = findViewById(R.id.txtQty);
        txtPrice = findViewById(R.id.txtPrice);

        btnPost = findViewById(R.id.btnPost);
        btnGet = findViewById(R.id.btnGet);

        btnPost.setOnClickListener(this);
        btnGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGet) {
            btnGetClick();
        } else if (v.getId() == R.id.btnPost) {
            btnPostClick();
        }
    }

    private void btnGetClick() {
        GetData();
    }

    private void btnPostClick() {
        AddDataFireStore();
    }


    public void GetData() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                System.out.println(documentSnapshot.getData().toString());
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void AddDataFireStore() {
        String name = txtName.getText().toString();
        String qty = txtQty.getText().toString();
        String price = txtPrice.getText().toString();

        HashMap<String, String> order = new HashMap<>();

        order.put("name", name);
        order.put("qty", qty);
        order.put("price", price);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Orders")
                .add(order)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failure - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
    }
}
