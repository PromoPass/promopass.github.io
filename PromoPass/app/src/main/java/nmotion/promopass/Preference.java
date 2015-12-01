package nmotion.promopass;

/**
 * Created by Anna on 12/1/2015.
 */
public class Preference {

    private String PreferenceID;
    private String BusinessName;

    public Preference(String businessName, String preferenceID){
        BusinessName = businessName;
        PreferenceID = preferenceID;
    }

    @Override
    public String toString(){
        return BusinessName;
    }

    public String getPreferenceID() {
        return PreferenceID;
    }
}
