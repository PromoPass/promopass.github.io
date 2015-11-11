package nmotion.promopass;

/**
 * Created by Anna on 11/10/2015.
 */
public class ReceivedAd {

    private String ReceivedAdID;
    private String AdID;
    private String BusinessID;
    private String BusinessName;

    public ReceivedAd(String receivedAdID, String adID, String businessID, String businessName){
        super();
        ReceivedAdID = receivedAdID;
        AdID = adID;
        BusinessID = businessID;
        BusinessName = businessName;
    }

    @Override
    public String toString(){
        return BusinessName;
    }

    public String getReceivedAdID(){
        return ReceivedAdID;
    }

    public String getAdID(){
        return AdID;
    }

    public String getBusinessID(){
        return BusinessID;
    }

}
