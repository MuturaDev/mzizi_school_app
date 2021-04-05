package ultratude.com.staff.customer_support.util;

public class UtilityFunctions {

    public static String encrypt( String plainText){
        String[] numo = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9","-"};
        String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y","z"};
        for(String chara : plainText.split("")){
            int count = 0;
            for(String nim : numo){
                if(chara.equalsIgnoreCase(nim)){
                    plainText = plainText.replace(chara, alpha[count+5]);//5 is the private key
                }
                count++;
            }
        }

        return  plainText;


    }

}
