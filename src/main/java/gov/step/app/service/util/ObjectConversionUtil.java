package gov.step.app.service.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yzaman on 2/7/16.
 */
@Service
public class ObjectConversionUtil
{
    private final Logger log = LoggerFactory.getLogger(ObjectConversionUtil.class);

    /**
     * This method will retrieve value from get method
     * set the retrieved value to same field of destination object using set method.
     * @param sourceObject Source Object that
     * @param destObj Destination Object
     * @return
     */
    public Object convertClass (Object sourceObject, Object destObj)
    {
        Field[] fields = sourceObject.getClass().getDeclaredFields();

        for (Field field : fields)
        {
            try
            {
                Method getMethod = sourceObject.getClass().getMethod("get" + field.getName()
                    .replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));

                Method setMethod = destObj.getClass().getMethod("set" + field.getName()
                        .replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()),
                    field.getType());

                setMethod.invoke(destObj, getMethod.invoke(sourceObject));
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
            }
        }
        return destObj;
    }

    /**
     * This method will retrieve value from get method
     * set the retrieved value to same field of destination object using set method.
     * Restricted field will not copy value
     * @param sourceObject
     * @param destObj
     * @param restrictedFields
     * @return
     */
    public Object convertClass (Object sourceObject, Object destObj, String[] restrictedFields)
    {
        Field[] fields = sourceObject.getClass().getDeclaredFields();
        Set<String> accept = new HashSet<String>(Arrays.asList(restrictedFields));

        for (Field field : fields)
        {
            try
            {
                if (accept.contains(field.getName()) == false)
                {
                    Method getMethod = sourceObject.getClass().getMethod("get" + field.getName()
                        .replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));

                    Method setMethod = destObj.getClass().getMethod("set" + field.getName()
                            .replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()),
                        field.getType());

                    setMethod.invoke(destObj, getMethod.invoke(sourceObject));
                }
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
            }
        }
        return destObj;
    }

    /**
     * This method will retrieve value from get method
     * Generate Audit Data
     * Restricted field will not copy value
     * @param sourceObject
     * @param restrictedFields
     * @return
     */
    public List<AuditData> populateAuditData (Object sourceObject, String[] restrictedFields)
    {
        List<AuditData> auditList = new ArrayList<AuditData>();
        Field[] fields = sourceObject.getClass().getDeclaredFields();
        Set<String> accept = new HashSet<String>(Arrays.asList(restrictedFields));
        AuditData auditObj = null;
        for (Field field : fields)
        {
            try
            {
                if (accept.contains(field.getName()) == false)
                {
                    auditObj = new AuditData();
                    Method getMethod = sourceObject.getClass().getMethod("get" + field.getName()
                        .replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));

                    auditObj.setColumnName(field.getName());
                    auditObj.setColumnValue(getMethod.invoke(sourceObject).toString());
                }
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
            }
        }
        return auditList;
    }
}
