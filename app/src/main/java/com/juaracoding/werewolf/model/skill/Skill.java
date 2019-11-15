
package com.juaracoding.werewolf.model.skill;

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
public class Skill extends BaseModel implements Serializable, Parcelable{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @Column
    private String id;
    @SerializedName("name")
    @Expose
    @Column
    private String name;
    @SerializedName("description")
    @Expose
    @Column
    private String description;
    @SerializedName("type")
    @Expose
    @Column
    private String type;
    public final static Creator<Skill> CREATOR = new Creator<Skill>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        public Skill[] newArray(int size) {
            return (new Skill[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5454028048376820238L;

    protected Skill(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Skill() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(type);
    }

    public int describeContents() {
        return  0;
    }

}
