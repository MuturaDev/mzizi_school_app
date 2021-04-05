package ultratude.com.mzizi.modelclasses;

/**
 * Created by James on 21/05/2019.
 */

public class ReadNotReadNotification {

    private String msgID;
    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public ReadNotReadNotification(String msgID) {
        this.msgID = msgID;
    }

    public String getMsgID() {
        return msgID;
    }
}
