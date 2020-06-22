package com.example.tsone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>  implements  OnPersonItemClickListener {

    ArrayList<PersonInfo> items=new ArrayList<PersonInfo>();
    OnPersonItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View itemView=inflater.inflate(R.layout.layout1,viewGroup,false);

        return new ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        PersonInfo item=items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public void addItem(PersonInfo item){
        items.add(item);
    }

    public void setItems(ArrayList<PersonInfo> items){
        this.items=items;
    }

    public PersonInfo getItem(int position){
        return items.get(position);
    }

    public void setItem(int position,PersonInfo item){
        items.set(position,item);
    }

    public void setOnItemClickListener(OnPersonItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position){
        if(listener!=null){
            listener.onItemClick(holder,view,position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;

        public ViewHolder(@NonNull View itemView,final OnPersonItemClickListener listener) {
            super(itemView);

            textView=itemView.findViewById(R.id.textView);
            textView2=itemView.findViewById(R.id.textView2);


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){

                    int position=getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this,v,position);
                    }

                }

            });


        }

        public void setItem(PersonInfo item){
            textView.setText(item.getName());
            textView2.setText(item.getMac());
        }

    }

}
