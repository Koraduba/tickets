package epam.pratsaunik.tickets.tag;

import epam.pratsaunik.tickets.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;

public class SimpleCustomTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print("simpleTag");


        GregorianCalendar gc = new GregorianCalendar();
        String name = "Name: <b>"+((User) getJspContext().getAttribute("user", 1)).getName()+"</b>. ";
        String role = "Role: <b>"+((User)getJspContext().getAttribute("user")).getRole().toString()+"</b>. ";
        String locale = "Locale : <b> " + getJspContext().getAttribute("locale") + "</b>.";

        try {
            JspWriter out = getJspContext().getOut();
            out.write(name+role+locale);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return;
    }
}
