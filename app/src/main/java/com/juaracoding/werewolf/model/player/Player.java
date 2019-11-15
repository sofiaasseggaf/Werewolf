
package com.juaracoding.werewolf.model.player;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.juaracoding.werewolf.AppController;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppController.class)
public class Player extends BaseModel implements Serializable, Parcelable
{
    @PrimaryKey
    @Column
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    @Column
    private String name;
    @SerializedName("password")
    @Expose
    @Column
    private String password;
    @SerializedName("photo")
    @Expose
    @Column
    private String photo;

    public Player() {
    }

    public final static Creator<Player> CREATOR = new Creator<Player>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return (new Player[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6528043785784495247L;

    protected Player(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.photo = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Player(String string, String cursorString, String s, String string1) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(password);
        dest.writeValue(photo);
    }

    public int describeContents() {
        return  0;
    }

}
