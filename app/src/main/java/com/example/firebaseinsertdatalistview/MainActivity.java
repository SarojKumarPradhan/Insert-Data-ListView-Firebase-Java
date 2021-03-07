package com.example.firebaseinsertdatalistview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private Button btnAdd;
    private EditText etText;
    private ListView listView;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        etText = (EditText)findViewById(R.id.etDatabase);
        listView = (ListView)findViewById(R.id.database_list_view);

        listView.setAdapter(adapter);
        listView.setStackFromBottom(true);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                mDatabase.push().setValue(etText.getText().toString());
                //clear text after click button ......
                etText.getText().clear();


            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                //store data reverse order in listView
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String string = dataSnapshot.getValue(String.class);
                arrayList.remove(string);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}