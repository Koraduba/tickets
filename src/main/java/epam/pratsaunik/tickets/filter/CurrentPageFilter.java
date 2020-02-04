package epam.pratsaunik.tickets.filter;

import epam.pratsaunik.tickets.util.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = {"/*"})
public class CurrentPageFilter implements Filter {
    private static final String REFERER = "referer";
    private static final String PATH_REGEX = "/myservlet.+";
    private final static Logger logger= LogManager.getLogger();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        logger.info("Current page filter works!");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(true);
        String url = request.getHeader(REFERER);
        logger.info(url);
        String path = substringPathWithRegex(url);
        session.setAttribute("current_page", path);
        chain.doFilter(req, resp);
    }

    private String substringPathWithRegex(String url) {

        Pattern pattern = Pattern.compile(PATH_REGEX);
        String path = null;
        if (url != null) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                path = matcher.group(0);
                System.out.println(path);
            } else {
                path =ConfigurationManager.USERS_PAGE_PATH;
            }
        }
        return path;
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
