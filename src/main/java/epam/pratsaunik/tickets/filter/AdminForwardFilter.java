package epam.pratsaunik.tickets.filter;

import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AdminForwardFilter implements Filter {
    private final static Logger log = LogManager.getLogger();

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException  {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = (User) httpRequest.getSession().getAttribute("user");
        if(user == null || !user.getRole().equals(Role.ADMINISTRATOR))  {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
        chain.doFilter(request, response);
    }

    public void destroy()  {
    }
}
