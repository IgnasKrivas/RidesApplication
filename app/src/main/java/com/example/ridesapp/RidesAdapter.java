package com.example.ridesapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RidesAdapter extends FirebaseRecyclerAdapter<Rides,RidesAdapter.ViewHolderd> {
    public RidesAdapter(@NonNull FirebaseRecyclerOptions<Rides> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolderd holder,final int position, @NonNull final Rides model) {
        holder.name.setText(model.getName());
        holder.from.setText(model.getFrom());
        holder.to.setText(model.getTo());
        holder.number.setText(model.getNumber());
        holder.participants.setText(model.getParticipants());

        holder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.name.getContext()).setContentHolder(new ViewHolder(R.layout.update_window))
                        .setExpanded(true,1600)
                        .create();

                View myview=dialogPlus.getHolderView();
//                final EditText name =myview.findViewById(R.id.uname);
//                final EditText from=myview.findViewById(R.id.ufrom);
//                final EditText to=myview.findViewById(R.id.uto);
//                final EditText number=myview.findViewById(R.id.unumber);
                final EditText participants=myview.findViewById(R.id.uparticipants);
                Button submit=myview.findViewById(R.id.usubmit);

//                name.setText(model.getName());
//                from.setText(model.getFrom());
//                to.setText(model.getTo());
//                number.setText(model.getNumber());
                participants.setText(model.getParticipants());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();

//                        map.put("name",name.getText().toString());
//                        map.put("from",from.getText().toString());
//                        map.put("to",to.getText().toString());
//                        map.put("number",number.getText().toString());
                        map.put("participants",participants.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("rides")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delete Ride");
                builder.setMessage("Delete");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("rides")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public ViewHolderd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride,parent,false);
        return new ViewHolderd(view);
    }

    class ViewHolderd extends RecyclerView.ViewHolder{
        TextView name,from,to,number,participants;
        ImageView edit, delete;
        public ViewHolderd(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            from=(TextView)itemView.findViewById(R.id.fromtext);
            to=(TextView)itemView.findViewById(R.id.totext);
            number=(TextView)itemView.findViewById(R.id.numbertext);
            participants=(TextView)itemView.findViewById(R.id.participantstext);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}
