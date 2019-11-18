
package com.juaracoding.werewolf.model.roles;

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
public class Role extends BaseModel implements Serializable, Parcelable
{

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
    @SerializedName("skill")
    @Expose
    @Column
    private String skill;
    @SerializedName("image")
    @Expose
    @Column
    private String image;
    public final static Creator<Role> CREATOR = new Creator<Role>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        public Role[] newArray(int size) {
            return (new Role[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8144918907475003034L;

    protected Role(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.skill = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Role() {
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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(skill);
        dest.writeValue(image);
    }

    public int describeContents() {
        return  0;
    }

}
