package by.epam.hr.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * The Class EncodingFilter.
 */
@WebFilter(urlPatterns = { "/*" }, initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8") })
public class EncodingFilter implements Filter {

    /** The encoding. */
    private String code = "UTF-8";

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        String encodingParam = fConfig.getInitParameter("encoding");
        if(encodingParam != null){
            code = encodingParam;
        }
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(code);
        response.setCharacterEncoding(code);
        chain.doFilter(request, response);
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }
}
