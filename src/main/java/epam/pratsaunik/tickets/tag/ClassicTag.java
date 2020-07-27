package epam.pratsaunik.tickets.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ClassicTag extends BodyTagSupport {
    int count;

    @Override
    public int doStartTag() throws JspException {
        count=0;
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public void setBodyContent(BodyContent b) {
        super.setBodyContent(b);
    }

    @Override
    public void doInitBody() throws JspException {
        super.doInitBody();
    }

    @Override
    public int doAfterBody() throws JspException {
        if (count<5){
            count++;
            try {
//                pageContext.getOut().print(bodyContent.getString());
//                pageContext.getOut().print("<br>");
                bodyContent.append("*");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;


    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().print("End");
          pageContext.getOut().print(bodyContent.getString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
