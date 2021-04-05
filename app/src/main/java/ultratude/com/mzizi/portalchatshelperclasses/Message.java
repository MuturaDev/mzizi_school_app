package ultratude.com.mzizi.portalchatshelperclasses;

/**
 * Created by Admin on 6/2/2018.
 */

public class Message {

    private String message;//Msg
    private User sender;//Actor
    private String createdAt;//datePosted
    private boolean showLoading;
    private boolean showCanceled;
    private boolean showsuccess;


    public Message() {
    }

    public Message(String message, User sender, String createdAt, boolean showLoading, boolean showCanceled, boolean showsuccess) {
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
        this.showLoading = showLoading;
        this.showCanceled = showCanceled;
        this.showsuccess = showsuccess;
    }

    public boolean isShowCanceled() {
        return showCanceled;
    }

    public boolean isShowsuccess() {
        return showsuccess;
    }

    public boolean isShowLoading() {
        return showLoading;
    }

    public String getMessage() {
        return message;
    }


    public User getSender() {
        return sender;
    }


    public String getCreatedAt() {
        return createdAt;
    }





}
