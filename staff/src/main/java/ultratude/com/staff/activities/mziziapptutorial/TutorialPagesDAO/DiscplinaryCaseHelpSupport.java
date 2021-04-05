package ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO;

import java.io.Serializable;

import ultratude.com.staff.R;

/**
 * Created by James on 26/05/2019.
 */

public class DiscplinaryCaseHelpSupport implements Serializable {

    private int[] helpText;

    public int[] getHelpText() {
        int[] arr = {
                R.string.disciplinary_case_q_1
        };
        return arr;
    }

    private int[] slideDescription;
    private String slideImages;


    public int[] getSlideDescription() {
        int[] arr  = {
                R.string.disci_step_1,
            R.string.disci_step_2,
            R.string.disci_step_3,
            R.string.disci_step_4,
            R.string.disci_step_5
        };



        return arr;
    }

    public String[] getSlideImages() {
        String[] arr = {
                "disci_step_1_img_1.PNG",
            "disci_step_2_img_2.PNG",
            "disci_step_3_img_3.PNG",
            "disci_step_4_img_4.PNG",
            "disci_step_5_img_5.PNG"
        };
        return arr;
    }

}
