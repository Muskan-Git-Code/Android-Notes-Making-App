package com.example.swq;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class F4Adapter extends RecyclerView.Adapter<F4Adapter.MyViewHolder> {
    ArrayList<String> l1;
    ArrayList<String> l2;
    ArrayList<String> l3;
    ArrayList<String> l4;
    Context context;

    NotesDb helper;

    public F4Adapter(Context context, ArrayList<String> l1, ArrayList<String> l2, ArrayList<String> l3, ArrayList<String> l4) {
        this.context = context;
        this.l1 = l1;
        this.l2 = l2;
        this.l3= l3;
        this.l4= l4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  tv2, tv3, tv4;
        ImageButton ib1, ib2;

        public MyViewHolder(View v) {
            super(v);
//            tv1 = (TextView) v.findViewById(R.id.name);
            tv2 = (TextView) v.findViewById(R.id.id1);
            tv3 = (TextView) v.findViewById(R.id.des1);
            tv4= (TextView)v.findViewById(R.id.date1);

//            ib1= (ImageButton)v.findViewById(R.id.imageButton);
            ib2= (ImageButton)v.findViewById(R.id.imageButton2);
        }
    }

    @Override
    public F4Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_f4_adapter, parent, false);
        return new F4Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(F4Adapter.MyViewHolder holder, final int position) {
//        holder.tv1.setText(l1.get(position));
        holder.tv2.setText(l2.get(position));
        holder.tv3.setText(l3.get(position));
        holder.tv4.setText(l4.get(position));

        helper = new NotesDb(context);

        holder.ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t1 = l2.get(position);
                if(!t1.isEmpty()){
                    int a= helper.delete(t1);
                    if(a>0) {
                        Toast.makeText(context,"DELETED",Toast.LENGTH_LONG).show();
                        l1.remove(position);    l2.remove(position); l3.remove(position);   l4.remove(position);
                        notifyDataSetChanged();
                    }
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, l1.get(position), Toast.LENGTH_SHORT).show();

                Dialog dialog = new Dialog(context);
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

                ed1.setText(l2.get(position));
                ed2.setText(l3.get(position));
                tv1.setText(l4.get(position));

                Button submit = (Button) dialog.findViewById(R.id.submit1);
                submit.setOnClickListener(new View.OnClickListener() {;
                    @Override
                    public void onClick(View v) {


                        long ix = helper.update(ed1.getText().toString(), ed2.getText().toString(), tv1.getText().toString());
                        if(ix>0)
                            Toast.makeText(context, "Insertion Successful", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(context, "Insertion Unsuccessful", Toast.LENGTH_LONG).show();

                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());

                        dialog.cancel();
                    }
                });
            }
        });

    }

    @Override  public int getItemCount() { return l1.size();}
}
