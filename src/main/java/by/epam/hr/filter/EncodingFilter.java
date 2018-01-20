package by.epam.hr.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = { "/*" }, initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8") })
public class EncodingFilter implements Filter {
    private String code = "UTF-8";

    public void init(FilterConfig fConfig) throws ServletException {
        String encodingParam = fConfig.getInitParameter("encoding");
        if(encodingParam != null){
            code = encodingParam;
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(code);
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
