package gov.step.app.web.rest.util;

/*import gov.step.app.domain.SmsGroupSetup;
import gov.step.app.repository.SmsGroupContactsRepository;
import gov.step.app.repository.SmsTemplateSetupRepository;
import gov.step.app.repository.search.SmsGroupContactsSearchRepository;
import gov.step.app.repository.search.SmsTemplateSetupSearchRepository;*/
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


/**
 * Created by RanaHasan on 5/25/16.
 */
public class SmsPushPull {
    private final Logger log = LoggerFactory.getLogger(SmsPushPull.class);

//    @Inject
//    private SmsGroupContactsRepository smsGroupContactsRepository;
//
//    @Inject
//    private SmsGroupContactsSearchRepository smsGroupContactsSearchRepository;
//
//    @Inject
//    private SmsTemplateSetupRepository smsTemplateSetupRepository;
//
//    @Inject
//    private SmsTemplateSetupSearchRepository smsTemplateSetupSearchRepository;

    public String sendGet(String msisdn, String txt) throws Exception {
        String smsApiResponse = "API not working";
        try {

            //String url = "http://bulksms.teletalk.com.bd/link_sms_send.php?op=SMS&user=BTEB&pass=BTEB1234&mobile=8801914959562&charset=UTF-8&sms=TestSMS";
            String txtProcessed = URLEncoder.encode(txt, "UTF-8");
            String url = "http://bulksms.teletalk.com.bd/link_sms_send.php?op=SMS&user=BTEB&pass=BTEB1234&mobile=" + msisdn.trim() + "&charset=UTF-8&sms=" + txtProcessed.trim();


            String urlencode = URLEncoder.encode(url, "UTF-8");
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);

            // add request header
            //request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = client.execute(request);

            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() > 0) {
                smsApiResponse = String.valueOf(response.getStatusLine().getStatusCode());
            }
            BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result.toString());

        } catch (Exception e){
            System.out.println("Error In SMS send : "+e);
        }
        return  smsApiResponse;

    }


//    public void sendGroupSMS(String groupType, Long groupId, String txt) throws Exception {
//
//        System.out.println("\n sms * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + groupType);
//        if(groupType.equalsIgnoreCase("Group")){
//            List<SmsGroupContacts> smsGroupContactsList = null;
//            try{
//                System.out.println("\n sms ** >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + groupId);
//                SmsGroupSetup smsGroupSetup = new SmsGroupSetup();
//                smsGroupSetup.setId(groupId);
//                smsGroupContactsList = smsGroupContactsRepository.findAllByGroupIds(smsGroupSetup);
//                //smsGroupContactsList = smsGroupContactsRepository.findAllByGroupIds(groupId);
//            }catch( Exception ex){
//                System.out.println("\n Error in send group sms group"+ex);
//            }
//
//            if (smsGroupContactsList != null){
//                for(SmsGroupContacts sms : smsGroupContactsList){
//                    try{
//                        System.out.println("\n sms 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                        sendGet(sms.getMemberPhone().trim(), txt.trim());
//                    }catch( Exception e){
//                        System.out.println("\n Error in send group sms"+e);
//                    }
//                }
//            }
//        }
//        /*switch(groupType)
//        {
//            case "Group" :
//                SmsGroupContacts smsGroupContacts = new SmsGroupContacts();
//                smsGroupContacts.setId(groupId);
//                List<SmsGroupContacts> smsGroupContactsList = smsGroupContactsRepository.findAllByGroupId(smsGroupContacts);
//                if (smsGroupContactsList != null){
//                    for(SmsGroupContacts sms : smsGroupContactsList){
//                        try{
//                            System.out.println("\n sms 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                            sendGet(sms.getMemberPhone().trim(), txt.trim());
//                        }catch( Exception e){
//                            System.out.println("Error in send group sms"+e);
//                        }
//                    }
//                }
//                break;
//            case "Teacher" :
//            case "Actor" :
//            case "CSV" :
//            default :
//                System.out.println("Invalid Type");
//        }*/
//
//    }
}
