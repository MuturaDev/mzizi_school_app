package ultratude.com.mzizi.modelclasses;

import java.io.Serializable;

import ultratude.com.mzizi.BuildConfig;


/**
 * Created by James on 25/06/2018.
 */

//@Entity(tableName = MziziURLConstants.TABLE_NAME_USERCREDENTIALS)
public class UserCredentials implements Serializable{

    private String userName;
    private String password;
    private String appcode;
    private String AppVersion;


    public UserCredentials(){

    }


    @Override
    public String toString() {
        return "UserCredentials{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", appcode='" + appcode + '\'' +
                '}';
    }

    public UserCredentials(String userName, String password, String appcode){
        this.userName = userName;
        this.password = password;
        this.appcode = appcode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getAppcode() {
        return appcode;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof UserCredentials)) return false;

        UserCredentials user = (UserCredentials) obj;

        if(password !=user.password ) return false;

        return userName != null ? userName.equals(user.userName) : user.userName == null;
    }
}
