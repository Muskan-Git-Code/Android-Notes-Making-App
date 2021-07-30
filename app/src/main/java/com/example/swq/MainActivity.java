package com.example.swq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    NotesDb helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new NotesDb(this);


        view();


        FloatingActionButton fb= (FloatingActionButton)findViewById(R.id.floatingActionButton4);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                dialog.setContentView(R.layout.note_dialog);
                params.copyFrom(dialog.getWindow().getAttributes());
                dialog.getWindow().setAttributes(params);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText ed1= (EditText)dialog.findViewById(R.id.title1);
                EditText ed2 = (EditText) dialog.findViewById(R.id.description1);
                TextView tv1= (TextView)dialog.findViewById(R.id.date1);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String cd = sdf.format(new Date());
                tv1.setText(cd);

                Button submit = (Button) dialog.findViewById(R.id.submit1);
                submit.setOnClickListener(new View.OnClickListener() {;
                    @Override
                    public void onClick(View v) {
                        ;
                        long ix = helper.insert(ed1.getText().toString(), ed2.getText().toString(), tv1.getText().toString());
                        if(ix>0)
                            Toast.makeText(getApplicationContext(), "Insertion Successful", Toast.LENGTH_LONG).show();

                        view();

                        dialog.cancel();
                    }
                });


            }
        });


        view();

    }

    private void view() {
        ArrayList<String> l1 = new ArrayList<>(), l2=new ArrayList<>(), l3=new ArrayList<>(), l4=new ArrayList<>();
        Cursor c= helper.viewData();
        while(c.moveToNext()){
            l1.add(String.valueOf(c.getInt(0)));
            l2.add(c.getString(1));
            l3.add(c.getString(2));
            l4.add(c.getString(3));
        }

        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView3);

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(lm);

        F4Adapter ca = new F4Adapter(MainActivity.this, l1,l2, l3, l4);
        rv.setAdapter(ca);
    }


}