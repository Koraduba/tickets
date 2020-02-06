package epam.pratsaunik.tickets.tag;

import epam.pratsaunik.tickets.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Locale;

@SuppressWarnings("serial")
public class CustomTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        GregorianCalendar gc = new GregorianCalendar();
        String name = "Name: <b>"+((User)pageContext.getSession().getAttribute("user")).getName()+"</b>. ";
        String role = "Role: <b>"+((User)pageContext.getSession().getAttribute("user")).getRole().toString()+"</b>. ";
        String locale = "Locale : <b> " + pageContext.getSession().getAttribute("locale") + "</b>.";

        try {
            JspWriter out = pageContext.getOut();
            out.write(name+role+locale);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
