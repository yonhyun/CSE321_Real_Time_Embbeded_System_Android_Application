package myapplication.busat;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBAsyncClient;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import java.io.IOException;
import java.util.Properties;
import java.util.*;
/**
 * Created by KKM on 10/27/2016.
 */

public class LocationHandler {

    private String name;
    private double location;
    private String description;

    private static String domain;
    private static Properties properties;

    private static AmazonSimpleDB sdb;

    LocationHandler(){

    }


    public static AmazonSimpleDB getSimpleDB() {
        if(sdb == null ) {
            System.out.print("here get simple db");
            BasicAWSCredentials credentials = new BasicAWSCredentials( getProperties().getProperty("accessKey"), LocationHandler.getProperties().getProperty("secretKey"));
            sdb = new AmazonSimpleDBAsyncClient(credentials);
        }
        return sdb;
    }



    // properties
    private static String getDomain(){
        if(domain == null){
            domain = getProperties().getProperty("domain");
        }
        return domain;
    }


    public static void save(double location) {
        LocationHandler.getSimpleDB().createDomain(new CreateDomainRequest(LocationHandler.getDomain()));
        List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>(1);
        attributes.add(new ReplaceableAttribute().withName("location").withValue(Double.toString(location)));

        sdb.putAttributes(new PutAttributesRequest(LocationHandler.getDomain(), Double.toString(location), attributes));
    }

    public static String[] getResult(){
        String[] results = new String[0];
        return results;
    }

    private static Properties getProperties(){
        if(properties == null){
            properties = new Properties();
            try {
                properties.load(LocationHandler.class.getResourceAsStream("AwsCredentials.properties"));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return properties;
    }
}
