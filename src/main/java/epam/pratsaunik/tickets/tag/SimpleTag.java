package epam.pratsaunik.tickets.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class SimpleTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        PrintWriter writer = new PrintWriter(new File("supertest.txt"));
        getJspBody().invoke(writer);
        writer.flush();

    }
}
