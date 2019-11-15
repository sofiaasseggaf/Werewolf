
package com.juaracoding.werewolf.model.room;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("room")
    @Expose
    private List<Room> room = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8729326695966049430L;

    protected Data(Parcel in) {
        in.readList(this.room, (com.juaracoding.werewolf.model.room.Room.class.getClassLoader()));
    }

    public Data() {
    }

    public List<Room> getRoom() {
        return room;
    }

    public void setRoom(List<Room> room) {
        this.room = room;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(room);
    }

    public int describeContents() {
        return  0;
    }

}
