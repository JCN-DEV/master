package gov.step.app.domain;

/**
 * Created by Anik on 20/12/16.
 */
public class MailTemplate {

    static String headerHtml="<html><body>" +
        "<center><table border='0'cellpadding='' cellspacing='0' style='margin:0;padding:0;max-width:700px;width:100%;height:100px;'><tbody>" +
        "<tr style='background-color:green;'><td style='text-align:right'><img src='http://s11.postimg.org/dtyrs54eb/bteb.png' height='80' width='110' alt='DTE' class='CToWUd'></td>"+
        "<td style='background-color:green;font-size:1.7em;color:white;text-align:left'>Directorate of Technical Education</td></tr>" +
        "<tr style='background-color:white; height:350px; vertical-align:top;'><td colspan='2'><p>";
    static String my="Dear ";
    static String bodyHtml="";
    static String endHtml="<br><b>Regards, <br><br>DTE Team</b></p></td></tr></table></center</body></html>";

    public String createMailTemplate(String dear, String body){
        String template=headerHtml+my+dear+"<br>"+body+endHtml;
        return template.toString();
    }

}
