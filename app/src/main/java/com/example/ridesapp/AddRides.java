package com.example.ridesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddRides extends AppCompatActivity {

    EditText name,from,to,number,participants;
    Button submit,back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rides);

        name=(EditText)findViewById(R.id.add_name);
        from=(EditText)findViewById(R.id.add_from);
        to=(EditText)findViewById(R.id.add_to);
        number=(EditText)findViewById(R.id.add_number);
        participants=(EditText)findViewById(R.id.add_participants);

        back=(Button)findViewById(R.id.add_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        submit=(Button)findViewById(R.id.add_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessInsert();
            }
        });
    }

    private void ProcessInsert()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("from",from.getText().toString());
        map.put("to",to.getText().toString());
        map.put("number",number.getText().toString());
        map.put("participants",participants.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("rides").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        name.setText("");
                        from.setText("");
                        to.setText("");
                        number.setText("");
                        participants.setText("");
                        Toast.makeText(getApplicationContext(),"Created ride successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Could not create ride",Toast.LENGTH_LONG).show();
                    }
                });

    }
}