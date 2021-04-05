package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import ultratude.com.staff.R;

/**
 * Created by James on 27/05/2019.
 */

public class EnqAttendanceHelpSupport {


    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.view_attendance_q_1
        };
        return arr;
    }

    private int[] slideDescription;
    private String slideImages;


    public int[] getSlideDescription() {
        int[] arr  = {
                R.string.enq_atte_step_1,
                R.string.enq_atte_step_2,
                R.string.enq_atte_step_3,
                R.string.enq_atte_step_4,
                R.string.enq_atte_step_5,
                R.string.enq_atte_step_6,
                R.string.enq_atte_step_7,
                R.string.enq_atte_step_8,
                R.string.enq_atte_step_9



        };



        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
                "enq_atte_step_1_img_1.PNG",
                "enq_atte_step_2_img_2.PNG",
                "enq_atte_step_3_img_3.PNG",
                "enq_atte_step_4_img_4.PNG",
                "enq_atte_step_5_img_5.PNG",
                "enq_atte_step_6_img_6.PNG",
                "enq_atte_step_7_img_7.PNG",
                "enq_atte_step_8_img_8.PNG",
                "enq_atte_step_9_img_9.PNG"
        };
        return arr;
    }

}
