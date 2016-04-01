package User;

import android.graphics.Bitmap;

import java.text.DateFormat;

/**
 * Created by tushar on 1/4/16.
 */
public class UserDetails {
    private String name;
    private String dob;
    private String gender;
    private String fbUserId;
    private Bitmap fbProfilePic;
    private String eMail;

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Bitmap getFbProfilePic() {
        return fbProfilePic;
    }

    public void setFbProfilePic(Bitmap fbProfilePic) {
        this.fbProfilePic = fbProfilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }
}
