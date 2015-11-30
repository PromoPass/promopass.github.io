package nmotion.promopass;

/**
 * Created by Anna on 11/10/2015.
 */
public class ReceivedAd {

    private String ReceivedAdID;
    private String AdID;
    private String BusinessID;
    private String BusinessName;
    private String ConsumerID;

    public ReceivedAd(String receivedAdID, String adID, String businessID, String businessName, String consumerID){
        super();
        ReceivedAdID = receivedAdID;
        AdID = adID;
        BusinessID = businessID;
        BusinessName = businessName;
        ConsumerID = consumerID;
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

    public String getConsumerID(){
        return ConsumerID;
    }

}
