package gov.step.app.web.rest.jdbc.dao;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by leads on 1/30/17.
 */
@Component
public class CommonFolderCreator {

    public String folderCreation(String x){

        String m="/backup";
        File file = new File(m);
        String[] names = file.list();

        for(String name : names){
            if (new File(m+"/" + name).isDirectory())
            {
                System.out.println(name);
                String a=name;
                String b=x;
                if(a.equals(b))
                {
                    break;
                }
                else{
                    new File(m+"/"+x).mkdir();
                }
            }

        }
        return m+"/"+x;
    }
}
