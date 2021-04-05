package ultratude.com.staff.webservice.RequestModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by James on 29/04/2019.
 */

public class PortalStudentRequest  implements Serializable {

        @SerializedName("studentID")
        private String studentID;
        @SerializedName("appcode")
        private String appcode;

        public PortalStudentRequest(String studentID, String appcode){
            this.studentID = studentID;
            this.appcode = appcode;
        }

        @Override
        public String toString() {
            return "StudentMR{" +
                    "studentID='" + studentID + '\'' +
                    ", appcode='" + appcode + '\'' +
                    '}';
        }

        public String getStudentID() {
            return studentID;
        }



        public String getAppcode() {
            return appcode;
        }



}
