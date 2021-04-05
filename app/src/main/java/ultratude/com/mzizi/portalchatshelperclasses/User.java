package ultratude.com.mzizi.portalchatshelperclasses;

/**
 * Created by Admin on 6/2/2018.
 */

public class User{



        //if true, its the school that sent the message to the recipient
    private boolean isSender;
        //each instance will represent the loginUser (the recipient) or the Sender
    public User(boolean isSender){


        this.isSender = isSender;
    }

    @Override
    public String toString() {
        return "Staff{" +
                ", isSender=" + isSender +
                '}';
    }





    public boolean getIsSender(){
        return isSender;
    }

}
