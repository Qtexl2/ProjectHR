package by.hr.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FirstTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        String name = "<h1>LALKA LOLO</h1>";
        JspWriter out = pageContext.getOut();
        try {
            out.write(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
