package ultratude.com.mzizi.modelclasses;

import java.util.HashMap;
import java.util.Map;

public class StudentBioData {

    //Father/Mother/Guardian Details
    private String First_Name;
    private String Middle_Name;
    private String Second_Name;
    private String Phone_Number;
    private String Email_Address;
    private String National_ID;
    private String Occupation;




    private Map<String, String> fatherDetailsList;


    public StudentBioData(String first_Name, String middle_Name, String second_Name, String phone_Number, String email_Address, String national_ID, String occupation) {
        First_Name = first_Name;
        Middle_Name = middle_Name;
        Second_Name = second_Name;
        Phone_Number = phone_Number;
        Email_Address = email_Address;
        National_ID = national_ID;
        Occupation = occupation;


    }


    public String getFirst_Name() {
        return First_Name;
    }

    public String getMiddle_Name() {
        return Middle_Name;
    }

    public String getSecond_Name() {
        return Second_Name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public String getNational_ID() {
        return National_ID;
    }

    public String getOccupation() {
        return Occupation;
    }



    public Map<String, String> getFatherDetailsList() {
        return fatherDetailsList;
    }

    public void setFatherDetailsList(Map<String,String> map){



        this.fatherDetailsList = map;
    }
}
