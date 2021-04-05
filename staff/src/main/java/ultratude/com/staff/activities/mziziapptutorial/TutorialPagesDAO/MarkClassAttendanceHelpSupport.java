package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import ultratude.com.staff.R;

/**
 * Created by James on 27/05/2019.
 */

public class MarkClassAttendanceHelpSupport {


    private int[] slideDescription;
    private String slideImages;

    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.class_attendance_q_1
        };
        return arr;
    }


    public int[] getSlideDescription() {
        int[] arr  = {
                R.string.at_step_1,
                R.string.at_step_2,
                R.string.at_step_3,
                R.string.at_step_4,
                R.string.at_step_5,
                R.string.at_step_6,
                R.string.at_step_7,
                R.string.at_step_8,
                R.string.at_step_9

        };



        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
                "at_step_1_img_1.PNG",
                "at_step_2_img_2.PNG",
                "at_step_3_img_3.PNG",
                "at_step_4_img_4.PNG",
                "at_step_5_img_5.PNG",
                "at_step_6_img_6.PNG",
                "at_step_7_img_7.PNG",
                "at_step_8_img_8.PNG",
                "at_step_9_img_9.PNG"

        };
        return arr;
    }
}
