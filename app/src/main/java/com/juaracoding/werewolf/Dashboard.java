package com.juaracoding.werewolf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.juaracoding.werewolf.APIService.APIClient;
import com.juaracoding.werewolf.APIService.APIInterfacesRest;
import com.juaracoding.werewolf.APIService.AppUtil;
import com.juaracoding.werewolf.model.player.Player;
import com.juaracoding.werewolf.model.player.PlayerModel;
import com.juaracoding.werewolf.model.roles.Role;
import com.juaracoding.werewolf.model.roles.RolesAdapter;
import com.juaracoding.werewolf.model.roles.RolesModel;
import com.juaracoding.werewolf.model.room.ModelPost;
import com.juaracoding.werewolf.model.room.Room;
import com.juaracoding.werewolf.model.room.RoomAdapter;
import com.juaracoding.werewolf.model.room.RoomModel;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    RecyclerView rvRoom;
    RoomAdapter itemList;
    Button btnCreateRoom, btnInfoRole;
    String roomname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        rvRoom = findViewById(R.id.rvRoom);
        getDataRoom();

        btnCreateRoom = findViewById(R.id.btnCreateRoom);
        btnInfoRole = findViewById(R.id.btnInfoRole);

        btnInfoRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Dashboard.this, InfoRole.class);
                startActivity(a);
            }
        });

        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dashboard.this);
                alertDialog.setTitle("INSERT ROOM");
                alertDialog.setMessage("Insert Room Name :");

                final EditText input = new EditText(Dashboard.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                //alertDialog.setIcon(R.drawable.key);

                alertDialog.setPositiveButton("SAVE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                roomname = input.getText().toString();
                                sendDataRoom();
                            }
                });

                alertDialog.setNegativeButton("BACK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void getDataRoom() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<RoomModel> dataSurvey = apiInterface.getDataRoom( "192865F851A6648AD3DEC578C868F00E");

        dataSurvey.enqueue(new Callback<RoomModel>() {
            @Override
            public void onResponse(Call<RoomModel> call, Response<RoomModel> response) {
                RoomModel dataReview = response.body();
                if (response.body() != null) {

                    for(int i = 0; i<dataReview.getData().getRoom().size(); i++) {
                        dataReview.getData().getRoom().get(i).save();
                    }

                    List<Room> model = SQLite.select()
                            .from(Room.class)
                            .queryList();

                    itemList = new RoomAdapter(model);
                    rvRoom.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                    rvRoom.setAdapter(itemList);

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Dashboard.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Dashboard.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RoomModel> call, Throwable t) {

                List<Room> model = SQLite.select()
                        .from(Room.class)
                        .queryList();

                itemList = new RoomAdapter(model);
                rvRoom.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                rvRoom.setAdapter(itemList);

                Toast.makeText(Dashboard.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    APIInterfacesRest apiInterface;

    private void sendDataRoom() {
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<RoomModel> postAdd = apiInterface.sendDataRoom(
                toRequestBody(AppUtil.replaceNull(roomname)),
                toRequestBody(AppUtil.replaceNull("0")),
                toRequestBody(AppUtil.replaceNull("waiting"))
        );
        postAdd.enqueue(new Callback<RoomModel>() {
            @Override
            public void onResponse(Call<RoomModel> call, Response<RoomModel> response) {
                RoomModel responServer = response.body();
                if (responServer != null) {
                    Toast.makeText(Dashboard.this,responServer.getMessage(),Toast.LENGTH_LONG).show();
                    getDataRoom();
                }
            }
            @Override
            public void onFailure(Call<RoomModel> call, Throwable t) {
                //MASUKIN KE DATABASE
                Room room = new Room();
                room.setName(roomname);
                room.setJmlPlayer("0");
                room.setStatus("waiting");
                room.save();
                Toast.makeText(Dashboard.this, "Maaf koneksi bermasalah, data tetap terinput, roomname : " + roomname, Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
    //change string to requestbody
    public RequestBody toRequestBody(String value) {
        if (value == null) {
            value = "";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

}
