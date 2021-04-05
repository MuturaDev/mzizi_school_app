package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import java.io.Serializable;

import ultratude.com.staff.R;

/**
 * Created by James on 26/05/2019.
 */


public class FuelConsumptionHelpSupport implements Serializable {
    private int[] slideDescription;
    private String slideImages;
    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.fuel_consumption_q_1
        };
        return arr;
    }

    public int[] getSlideDescription() {
        int[] arr  = {
            R.string.consump_step_1,
            R.string.consump_step_2,
            R.string.consump_step_3,
            R.string.consump_step_4,
            R.string.consump_step_5
        };

        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
            "consump_step_1_img_1.PNG",
            "consump_step_2_img_2.PNG",
            "consump_step_3_img_3.PNG",
            "consump_step_4_img_4.PNG",
            "consump_step_5_img_5.PNG"

    };
        return arr;
    }
}
