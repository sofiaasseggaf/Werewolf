package com.juaracoding.werewolf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.juaracoding.werewolf.APIService.APIClient;
import com.juaracoding.werewolf.APIService.APIInterfacesRest;
import com.juaracoding.werewolf.model.roles.Role;
import com.juaracoding.werewolf.model.roles.RolesAdapter;
import com.juaracoding.werewolf.model.roles.RolesModel;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoRole extends AppCompatActivity {

    RecyclerView rvRoles;
    RolesAdapter itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_role);

        rvRoles = findViewById(R.id.rvRoles);
        getDataRoles();

    }

    private void getDataRoles() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<RolesModel> dataSurvey = apiInterface.getDataRoles( "192865F851A6648AD3DEC578C868F00E");

        dataSurvey.enqueue(new Callback<RolesModel>() {
            @Override
            public void onResponse(Call<RolesModel> call, Response<RolesModel> response) {
                RolesModel dataReview = response.body();
                if (response.body() != null) {

                    for(int i = 0; i<dataReview.getData().getRoles().size(); i++) {
                        dataReview.getData().getRoles().get(i).save();
                    }

                    List<Role> model = SQLite.select()
                            .from(Role.class)
                            .queryList();

                    itemList = new RolesAdapter(model);
                    rvRoles.setLayoutManager(new LinearLayoutManager(InfoRole.this));
                    rvRoles.setAdapter(itemList);

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(InfoRole.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(InfoRole.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RolesModel> call, Throwable t) {

                List<Role> model = SQLite.select()
                        .from(Role.class)
                        .queryList();

                itemList = new RolesAdapter(model);
                rvRoles.setLayoutManager(new LinearLayoutManager(InfoRole.this));
                rvRoles.setAdapter(itemList);

                Toast.makeText(InfoRole.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
}
