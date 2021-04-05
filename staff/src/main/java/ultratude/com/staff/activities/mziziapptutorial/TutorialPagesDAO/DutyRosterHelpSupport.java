package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import java.io.Serializable;

import ultratude.com.staff.R;

/**
 * Created by James on 26/05/2019.
 */

public class DutyRosterHelpSupport implements Serializable {

    private int[] slideDescription;
    private String slideImages;
    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.dutyroster_q_1
        };
        return arr;
    }


    public int[] getSlideDescription() {
        int[] arr = {
                R.string.duty_step_1,
                R.string.duty_step_2
        };


        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
                "duty_step_1_img_1.PNG",
                "duty_step_2_img_2.PNG"
        };
        return arr;
    }
}
