package ultratude.com.mzizi.retrofitokhttp.Pojo;


import ultratude.com.mzizi.BuildConfig;

/**
 * Created by James on 30/05/2019.
 */

public class Test {
    private boolean test;
    private String AppVersion;

    public Test(){
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public boolean getTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
}
