package epam.pratsaunik.tickets.command;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.servlet.AttributeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/** used to prevent use of request {@code HttpServletRequest} instance outside servlet
 */
public class RequestContent {
    private final static Logger log = LogManager.getLogger();
    private Map<String, String[]> requestParameters = new HashMap<>();
    private Map<String, Object> requestAttributes = new HashMap<>();
    private Map<String, Object> sessionAttributes = new HashMap<>();

    /**
     * method to swap request parameters to {@code RequestContent} instance
     * @param request request received by servlet
     */
    public void extractValues(HttpServletRequest request) {
        request.getParameterMap();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {

            List<String> value = new ArrayList<>();
            for (String string: entry.getValue() ) {
                String stringNew=string.replaceAll("<","&lt").replaceAll(">","&gt");
                value.add(string);
            }
            requestParameters.put(entry.getKey(), value.toArray(new String[0]));
        }
        Enumeration<String> enumeration = request.getSession().getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            sessionAttributes.put(key, request.getSession().getAttribute(key));
        }
    }

    /**
     * method to include data from commands in session and request attributes
     * @param request request received by servlet
     */
    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        HttpSession session = request.getSession(true);
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            if (entry.getValue() != null) {
                session.setAttribute(entry.getKey(), entry.getValue());
            } else {
                if (entry.getKey() != null) {
                    session.removeAttribute(entry.getKey());
                }
            }
        }
    }

    /**
     * to save attribute to further adding to session
     * @param name name of attribute
     * @param value value of attribute
     */
    public void setSessionAttribute(String name, Object value) {
        log.info(name);
        sessionAttributes.put(name, value);
    }

    /**
     * to get session attribute by name
     * @param name name of attribute
     * @return attribute value
     */
    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    /**
     * to save attribute to further adding to request
     * @param name name of attribute
     * @param value value of attribute
     */
    public void setRequestAttribute(String name, Object value) {
        log.info(name);
        requestAttributes.put(name, value);
    }

    /**
     * to get request attribute by name
     * @param name of attribute
     * @return attribute value
     */
    public String getRequestParameter(String name) {
        log.info("getRequestParameter" + name);
        String[] results = requestParameters.get(name);
        if (results != null) {
            return results[0];
        } else {
            return null;
        }
    }

}
