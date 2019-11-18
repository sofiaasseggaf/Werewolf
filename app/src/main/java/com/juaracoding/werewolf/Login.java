package com.juaracoding.werewolf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.material.snackbar.Snackbar;
import com.juaracoding.werewolf.APIService.APIClient;
import com.juaracoding.werewolf.APIService.APIInterfacesRest;
import com.juaracoding.werewolf.model.player.Player;
import com.juaracoding.werewolf.model.player.PlayerModel;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText txtPlayerName, txtPlayerPassword;
    Button btnLogin, btnRegister;
    String name, pw;
    PlayerModel dataModel;
    List<Player> players;

    public static Login l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtPlayerName = findViewById(R.id.txtPlayerName);
        txtPlayerPassword = findViewById(R.id.txtPlayerPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        l=this;

        getDataPlayer();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Login.this, Register.class);
                startActivity(a);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < dataModel.getData().getPlayer().size(); i++) {
                    name = dataModel.getData().getPlayer().get(i).getName();
                    pw = dataModel.getData().getPlayer().get(i).getPassword();

                    if (name.equalsIgnoreCase(txtPlayerName.getText().toString()) && pw.equalsIgnoreCase(txtPlayerPassword.getText().toString())) {
                        Toast.makeText(Login.this, "Selamat Anda Berhasil Login !", Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(Login.this, Dashboard.class);
                        startActivity(a);
                    } else {
                        Toast.makeText(Login.this, "Username & Password Salah !", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });
    }

        public void getDataPlayer() {
            final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
            final Call<PlayerModel> dataSurvey = apiInterface.getDataPlayer( "192865F851A6648AD3DEC578C868F00E");

            dataSurvey.enqueue(new Callback<PlayerModel>() {
                @Override
                public void onResponse(Call<PlayerModel> call, Response<PlayerModel> response) {
                    dataModel = response.body();
                    if (response.body() != null) {
                        for(int i = 0; i<dataModel.getData().getPlayer().size(); i++) {
                            dataModel.getData().getPlayer().get(i).save();
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Login.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<PlayerModel> call, Throwable t) {

                    List<Player> model = SQLite.select()
                            .from(Player.class)
                            .queryList();


                    Toast.makeText(Login.this, "Terjadi gangguan koneksi", Toast.LENGTH_LONG).show();
                    call.cancel();
                }
            });
        }
}
