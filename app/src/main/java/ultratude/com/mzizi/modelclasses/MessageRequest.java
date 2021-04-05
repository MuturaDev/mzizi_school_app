package ultratude.com.mzizi.modelclasses;

import java.io.Serializable;

import ultratude.com.mzizi.BuildConfig;

/**
 * Created by James on 05/07/2018.
 */

public class MessageRequest implements Serializable{

    private String studentID;
    private String msgID;
    private  String appcode;
    private String AppVersion;


    public MessageRequest(String studentID, String msgID, String appcode){
        this.studentID = studentID;
        this.msgID = msgID;
        this.appcode = appcode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "studentID='" + studentID + '\'' +
                ", msgID='" + msgID + '\'' +
                ", appcode='" + appcode + '\'' +
                '}';
    }

    public String getStudentID() {
        return studentID;
    }

    public String getMsgID() {
        return msgID;
    }

    public String getAppcode() {
        return appcode;
    }
}
