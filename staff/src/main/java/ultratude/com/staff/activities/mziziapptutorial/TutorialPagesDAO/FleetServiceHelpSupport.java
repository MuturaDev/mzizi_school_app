package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import java.io.Serializable;

import ultratude.com.staff.R;

/**
 * Created by James on 26/05/2019.
 */

public class FleetServiceHelpSupport implements Serializable {

    private int[] slideDescription;
    private String slideImages;

    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.fleet_servicing_q_1
        };
        return arr;
    }


    public int[] getSlideDescription() {
        int[] arr  = {
                R.string.service_step_1,
            R.string.service_step_2,
            R.string.service_step_3,
            R.string.service_step_4,
            R.string.service_step_5,
            R.string.service_step_6
        };



        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
                "service_step_1_img_1.PNG",
            "service_step_2_img_2.PNG",
            "service_step_3_img_3.PNG",
            "service_step_4_img_4.PNG",
            "service_step_5_img_5.PNG",
            "service_step_6_img_6.PNG"
        };
        return arr;
    }
}
