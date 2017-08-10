package myapplication.busat;

import android.util.Log;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.GetSMSAttributesRequest;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.SetSMSAttributesRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KKM on 11/24/2016.
 */

public class SNSModel {

    private AmazonSNSClient _snsClient;

    // for SNS service
    private Map<String, MessageAttributeValue> _attributes;
    private String _msg;
    private String _phoneNum;

    // For Credential info.
    private Credential _myCred;
    private String _securityKey = "+V13d2ym5muojG1PX7Y62JQ4jGWadYKqfcbBVw8N0";
    private String _accessKey = "AKIAJ7XRF5IDCMOMYH2A";

    // Arn info
    private String _arn;

    // Loc info
    private double _lon;
    private double _lat;

    public SNSModel(double lon, double lat){

        String _messageToPhone = null;

        _lon = lon;
        _lat = lat;


        // inint credential info
        _myCred = new Credential(_securityKey, _accessKey);
        _snsClient = new AmazonSNSClient(_myCred.getCredentials());

        // init topic
        initTopic();


        // init subs
        subscribeTopic();

        // setDefaultSmsAttributes(_snsClient);
        _attributes = new HashMap<String, MessageAttributeValue>();
//        _phoneNum = phoneNum;
//        _msg = "[ Current Location ]\n" +
//                _phoneNum+"\n\n From BUSAT" ;

        double lata = 43.000351;
        double latb = 42.999271;
        double latc = 42.999190;
        double latd = 42.998735;
        double late = 42.997244;

        double longa = -78.792700;
        double longb = -78.793451;
        double longc = -78.798987;
        double longd = -78.800489;
        double longe = -78.801648;
        double longf = -78.802270;
        double longg = -78.804137;



        if (_lat == 0 || _lon == 0) {
            _messageToPhone = "GPS not working";
        } else if (latb  < _lat && _lat < lata) {
            if (longb < _lon && _lon < longa) {
                _messageToPhone = "UB";
            } else if (longf < _lon && _lon < longb) {
                _messageToPhone = "Moving";
            } else {
                _messageToPhone = "Bus Out of Bound";
            }
        } else if (late < _lat && _lat < latb) {
            if (latc < _lat && longd < _lon && _lon < longc) {
                _messageToPhone = "Villas on Rensch East";
            } else if (longf < _lon && _lon < longe) {
                _messageToPhone = "Moving";
            } else if (_lat < latd && longg < _lon && _lon < longf) {
                _messageToPhone = "Villas on Rensch West";
            } else {
                _messageToPhone = "Bus Out of Bound";
            }
        } else {
            _messageToPhone = "Bus Out of Bound";
        }


//
        _msg = "[ Current Bus Location ]\n" +
                _messageToPhone + "\n" +
                "Longitude : " + _lon+ "\n" +
                "Latitude : "+ _lat+ "\n\n From BUSAT" ;
        Log.v("msg",_msg);
        sendSMSMessage(_snsClient,"asasd","17165633364", _attributes);
        //sendSMSMessage(_snsClient,_msg,"17165633364", _attributes);
    }

    private void subscribeTopic() {
        Log.v("디벅", "subscribed");
        SubscribeRequest subRequest = new SubscribeRequest(_arn, "email", "kangmink@buffalo.edu");
        _snsClient.subscribe(subRequest);
    }

    public void initTopic() {
        Log.v("디벅", "initTopic");
        //create a new SNS client and set endpoint
        _snsClient = new AmazonSNSClient(_myCred.getCredentials());
        _snsClient.setRegion(Region.getRegion(Regions.US_EAST_1));

        //create a new SNS topic
        CreateTopicRequest createTopicRequest = new CreateTopicRequest("BusAt");
        CreateTopicResult createTopicResult = _snsClient.createTopic(createTopicRequest);

        _arn = createTopicResult.getTopicArn();
    }

    public static void setDefaultSmsAttributes(AmazonSNSClient snsClient) {
        Log.v("디벅", "setDefaultSmsAttributes");
        SetSMSAttributesRequest setRequest = new SetSMSAttributesRequest()
                .addattributesEntry("DefaultSenderID", "BUSAT Co.")
                .addattributesEntry("MonthlySpendLimit", "50")
                .addattributesEntry("DeliveryStatusIAMRole",
                        "arn:aws:sns:us-west-2:292265077997:app/ADM/BUSAT")
                .addattributesEntry("DeliveryStatusSuccessSamplingRate", "10")
                .addattributesEntry("DefaultSMSType", "Promotional")
                .addattributesEntry("UsageReportS3Bucket", "sns-sms-daily-usage");
        snsClient.setSMSAttributes(setRequest);
        Map<String, String> myAttributes = snsClient.getSMSAttributes(new GetSMSAttributesRequest())
                .getAttributes();
        System.out.println("My SMS attributes:");
        for (String key : myAttributes.keySet()) {
            System.out.println(key + " = " + myAttributes.get(key));
        }
    }

    public static void sendSMSMessage(AmazonSNSClient snsClient, String message,
                                      String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        Log.v("디벅", "sendSMS");
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
    }
}
