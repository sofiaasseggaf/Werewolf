
package com.juaracoding.werewolf.model.skill;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("skill")
    @Expose
    private List<Skill> skill = null;
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
    private final static long serialVersionUID = 1644971541519438110L;

    protected Data(Parcel in) {
        in.readList(this.skill, (com.juaracoding.werewolf.model.skill.Skill.class.getClassLoader()));
    }

    public Data() {
    }

    public List<Skill> getSkill() {
        return skill;
    }

    public void setSkill(List<Skill> skill) {
        this.skill = skill;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(skill);
    }

    public int describeContents() {
        return  0;
    }

}
