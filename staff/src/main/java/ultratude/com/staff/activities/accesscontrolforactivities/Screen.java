package ultratude.com.staff.activities.accesscontrolforactivities;

/**
 * Created by James on 02/05/2019.
 */

public class Screen {

    private String screenID;
    private String screenName;



    public Screen(String screenID, String screenName){
        this.screenID = screenID;
        this.screenName = screenName;
    }

    public String getScreenID() {
        return screenID;
    }

    public String getScreenName() {
        return screenName;
    }
}
