package myapplication.busat;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

public class Credential implements AWSCredentialsProvider{

    public Credential(String mSecretKey, String mAccessKey) {
        super();
        this.secretKey = mSecretKey;
        this.accessKey = mAccessKey;
    }

    private String secretKey;
    private String accessKey;

    @Override
    public AWSCredentials getCredentials() {
        AWSCredentials awsCredentials = new AWSCredentials() {

            @Override
            public String getAWSSecretKey() {
                // TODO Auto-generated method stub
                return secretKey;
            }

            @Override
            public String getAWSAccessKeyId() {
                // TODO Auto-generated method stub
                return accessKey;
            };
        };
        return awsCredentials;
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }
}