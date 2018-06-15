package com.example.barperetz.petfinder;

/**
 * Created by Bar Peretz on 5/24/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AdapterShelter extends RecyclerView.Adapter<AdapterShelter.ViewHolder> {

    public Context context;
    private List<DataShelter> dataShelter;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }



    public AdapterShelter(Context context, List dataShelter) {
        this.context = context;
        this.dataShelter = dataShelter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelterlistitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setTag(dataShelter.get(position));

        DataShelter pu = dataShelter.get(position);

        holder.pName.setText(pu.getShelterName());

        holder.pName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShelterDetail.class);
                intent.putExtra("nameforid", holder.pName.getText().toString());
                context.startActivity(intent);
                Log.d("clickSuccess", holder.pName.getText().toString());
            }
        });


    }

    @Override
    public int getItemCount() {
        if(dataShelter.size()>25) {
            return 25;
        } else {
            return dataShelter.size();
        }}



    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView pName;
        private View title;

        private ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            pName = (TextView) itemView.findViewById(R.id.pName);

                }


            }

}