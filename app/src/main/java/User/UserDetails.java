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
    private String uuid;
    private String status;
    private String token;
    private String mobile;
    static UserDetails _instance;

    public static UserDetails getinstance() {
        if(_instance==null){
            _instance= new UserDetails();
        }
        return _instance;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        _instance.mobile = mobile;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        _instance.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        _instance.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        _instance.token = token;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        _instance.eMail = eMail;
    }

    public Bitmap getFbProfilePic() {
        return fbProfilePic;
    }

    public void setFbProfilePic(Bitmap fbProfilePic) {
        _instance.fbProfilePic = fbProfilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        _instance.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        _instance.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        _instance.gender = gender;
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        _instance.fbUserId = fbUserId;
    }
}
