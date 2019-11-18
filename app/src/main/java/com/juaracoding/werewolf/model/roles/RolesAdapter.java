package com.juaracoding.werewolf.model.roles;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juaracoding.werewolf.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RolesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Role> dataItemList;
    public RolesAdapter(List<Role> dataItemList) {
        this.dataItemList = dataItemList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roles, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).nameRole.setText("Name      : " + dataItemList.get(position).getName());
        ((Penampung)holder).skillRole.setText("Skill        : " + dataItemList.get(position).getSkill());
        ((Penampung)holder).descriptionRole.setText("Description : " + dataItemList.get(position).getDescription());
        ImageView imageRole = ((Penampung)holder).imageRole;
        Picasso.get().load(dataItemList.get(position).getImage()).into(imageRole);
    }
    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }
    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameRole, skillRole, descriptionRole;
        public ImageView imageRole;
        public Penampung(View itemView) {
            super(itemView);
            nameRole = itemView.findViewById(R.id.nameRole);
            skillRole = itemView.findViewById(R.id.skillRole);
            descriptionRole = itemView.findViewById(R.id.descriptionRole);
            imageRole = itemView.findViewById(R.id.imageRole);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + nameRole.getText());
        }
    }
}
