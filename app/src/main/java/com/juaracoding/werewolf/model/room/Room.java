
package com.juaracoding.werewolf.model.room;

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
public class Room extends BaseModel implements Serializable, Parcelable{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @Column
    private String id;
    @SerializedName("name")
    @Expose
    @Column
    private String name;
    @SerializedName("status")
    @Expose
    @Column
    private String status;
    public final static Creator<Room> CREATOR = new Creator<Room>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        public Room[] newArray(int size) {
            return (new Room[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6570097882008827194L;

    protected Room(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Room() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
