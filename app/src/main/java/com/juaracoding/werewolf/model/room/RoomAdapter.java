package com.juaracoding.werewolf.model.room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juaracoding.werewolf.R;
import com.juaracoding.werewolf.model.roles.Role;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Room> dataItemList;

    public RoomAdapter(List<Room> dataItemList) {
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).nameRoom.setText("Name Room   : " + dataItemList.get(position).getName());
        ((Penampung)holder).jmlPlayer.setText("Jml Pemain  : " + dataItemList.get(position).getJmlPlayer());
        ((Penampung)holder).statusRoom.setText("Status Room : " + dataItemList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameRoom, jmlPlayer, statusRoom;
        public Penampung(View itemView) {
            super(itemView);
            nameRoom = itemView.findViewById(R.id.nameRoom);
            jmlPlayer = itemView.findViewById(R.id.jml_Player);
            statusRoom = itemView.findViewById(R.id.statusRoom);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + nameRoom.getText());
        }
    }
}
