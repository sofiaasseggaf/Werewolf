
package com.juaracoding.werewolf.model.roles;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("roles")
    @Expose
    private List<Role> roles = null;
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
    private final static long serialVersionUID = 1981999943327671288L;

    protected Data(Parcel in) {
        in.readList(this.roles, (com.juaracoding.werewolf.model.roles.Role.class.getClassLoader()));
    }

    public Data() {
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(roles);
    }

    public int describeContents() {
        return  0;
    }

}
