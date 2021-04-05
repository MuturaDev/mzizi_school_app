package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import java.io.Serializable;

import ultratude.com.staff.R;

/**
 * Created by James on 26/05/2019.
 */

public class ApplyLeaveHelpSupport implements Serializable {

    private int[] slideDescription;
    private String slideImages;

    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.leave_q_1
        };
        return arr;
    }


    public int[] getSlideDescription() {
        int[] arr  = {
                R.string.leave_step_1,
                R.string.leave_step_2,
                R.string.leave_step_3,
                R.string.leave_step_4,
                R.string.leave_step_5,
                R.string.leave_step_6,
                R.string.leave_step_7,
                R.string.leave_step_8,
                R.string.leave_step_9
        };



        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
                "leave_step_1_img_1.PNG",
                "leave_step_2_img_2.PNG",
                "leave_step_3_img_3.PNG",
                "leave_step_4_img_4.PNG",
                "leave_step_5_img_5.PNG",
                "leave_step_6_img_6.PNG",
                "leave_step_7_img_7.PNG",
                "leave_step_8_img_8.PNG",
                "leave_step_9_img_9.PNG"
        };
        return arr;
    }
}
