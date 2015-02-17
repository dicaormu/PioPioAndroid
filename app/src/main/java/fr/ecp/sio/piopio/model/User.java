package fr.ecp.sio.piopio.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Diana on 05/12/2014.
 */
public class User implements Parcelable {
    private String id;
    private String user;
    private String status="offline";
    private String image;


    public String getId() {
        return id;
    }

    public void setId(String mId) {
        this.id = mId;
    }

    public String getHandle() {
        return user;
    }

    public void setHandle(String mHandle) {
        this.user = mHandle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String mStatus) {
        this.status = mStatus;
    }

    public String getProfilePicture() {
        return image;
    }

    public void setProfilePicture(String mProfilePicture) {
        this.image = mProfilePicture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {//orden de envio es muy importante, ya que no hay clave/valor
        dest.writeString(id);
        dest.writeString(user);
        dest.writeString(status);
        dest.writeString(image);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            User user=new User();
            user.id =source.readString();
            user.user =source.readString();
            user.status =source.readString();
            user.image =source.readString();
            return user;
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };

    @Override
    public String toString(){
        return this.user;
    }

}
