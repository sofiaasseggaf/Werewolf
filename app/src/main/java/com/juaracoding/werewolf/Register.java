package com.juaracoding.werewolf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.juaracoding.werewolf.APIService.APIClient;
import com.juaracoding.werewolf.APIService.APIInterfacesRest;
import com.juaracoding.werewolf.APIService.AppUtil;
import com.juaracoding.werewolf.model.player.Player;
import com.juaracoding.werewolf.model.player.PlayerModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText txtPlayerName, txtPlayerPassword;
    Button btnPlayerPhoto, btnSavePlayer;
    ImageView inputPlayerPhoto;
    public String tempFoto;
    Bitmap photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        txtPlayerName = findViewById(R.id.txtPlayerName);
        txtPlayerPassword = findViewById(R.id.txtPlayerPassword);
        btnPlayerPhoto = findViewById(R.id.btnPlayerPhoto);
        btnSavePlayer = findViewById(R.id.btnSavePlayer);
        inputPlayerPhoto = findViewById(R.id.inputPlayerPhoto);

        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;

        final FilePickerDialog dialogPicker1 = new FilePickerDialog(Register.this, properties);
        dialogPicker1.setTitle("Pilih File Foto");
        dialogPicker1.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                tempFoto = "file://" + files[0];
                Picasso.get().load(tempFoto).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        inputPlayerPhoto.setImageBitmap(bitmap);
                        photo = bitmap;
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(Register.this, "Maaf gambar gagal diload", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            }
        });

        btnPlayerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPicker1.show();
            }
        });


        btnSavePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataPlayer(photo);
                Login.l.getDataPlayer();
                finish();
            }
        });
    }

    APIInterfacesRest apiInterface;
    //send post data with image
    private void sendDataPlayer(Bitmap bitmap) {
        File foto = createTempFile(bitmap);
        byte[] bImg = AppUtil.FiletoByteArray(foto);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"),bImg);
        final MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", foto.getName() + ".jpg", requestFile);

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Call<PlayerModel> postAdd = apiInterface.sendDataPlayer(

                toRequestBody(AppUtil.replaceNull(txtPlayerName.getText().toString())),
                toRequestBody(AppUtil.replaceNull(txtPlayerPassword.getText().toString())),
                photo
        );
        postAdd.enqueue(new Callback<PlayerModel>() {
            @Override
            public void onResponse(Call<PlayerModel> call, Response<PlayerModel> response) {
                PlayerModel responServer = response.body();
                if (responServer != null) {
                    Toast.makeText(Register.this,responServer.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<PlayerModel> call, Throwable t) {
                //MASUKIN KE DATABASE
                Player player = new Player();
                player.setName(txtPlayerName.getText().toString());
                player.setPassword(txtPlayerPassword.getText().toString());
                player.setPhoto(tempFoto);
                player.save();
                Toast.makeText(Register.this, "Maaf koneksi bermasalah, data tetap terinput, username : " + txtPlayerName.getText(), Toast.LENGTH_LONG).show();
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


    //compress picture
    public static byte[] compressCapture(byte[] capture, int maxSizeKB) {

        // This should be different based on the original capture size
        int compression = 12;

        Bitmap bitmap = BitmapFactory.decodeByteArray(capture, 0, capture.length);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, compression, outputStream);
        return outputStream.toByteArray();
    }

    //bitmap to file
    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
