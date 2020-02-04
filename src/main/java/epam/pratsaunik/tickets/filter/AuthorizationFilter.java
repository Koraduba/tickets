package epam.pratsaunik.tickets.filter;

import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthorizationFilter implements Filter {

    private final static Logger log = LogManager.getLogger();
    private static final Set<CommandType> USER_AVAILABLE = new HashSet<>();
    private static final Set<CommandType> ADMIN_AVAILABLE = new HashSet<>();
    private static final String LOGIN_PATH = "/index.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Authorization initialized");
        USER_AVAILABLE.add(CommandType.LOGIN);
        USER_AVAILABLE.add(CommandType.REGISTER);
        USER_AVAILABLE.add(CommandType.NEW_USER);
        ADMIN_AVAILABLE.add(CommandType.LOGIN);
        ADMIN_AVAILABLE.add(CommandType.REGISTER);
        ADMIN_AVAILABLE.add(CommandType.NEW_USER);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Authorization started!");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String command = req.getParameter("command");
        log.info("Command: " + command);
        log.info("Session: " + session);
        if (session == null) {
            defaultRequest(request, response, chain, req, res, command);
        } else {

            log.info("Session is not null!");
            User user = (User) session.getAttribute("user");
            if (user == null) {
                defaultRequest(request, response, chain, req, res, command);
            } else {
                log.info("User is not null!");
                CommandType commandType = CommandType.valueOf(command.toUpperCase());
                log.info(commandType);
                Role role = user.getRole();
                switch (role) {
                    case USER:
                        if (USER_AVAILABLE.contains(commandType)) {
                            chain.doFilter(request, response);
                        } else {
                            log.info("Command is not available for USER!");
                            session.removeAttribute("user");
                            res.sendRedirect(req.getContextPath() + LOGIN_PATH);
                        }
                        break;
                    case ADMINISTRATOR:
                        if (ADMIN_AVAILABLE.contains(commandType)) {
                            chain.doFilter(request, response);
                        } else {
                            log.info("Command is not available for ADMIN!");
                            session.removeAttribute("user");
                            res.sendRedirect(req.getContextPath() + LOGIN_PATH);
                        }
                        break;
                    default:
                        log.info("Role is undefined");
                        session.removeAttribute("user");
                        res.sendRedirect(req.getContextPath() + LOGIN_PATH);
                        break;
                }
            }
        }
    }

    private void defaultRequest(ServletRequest request, ServletResponse response, FilterChain chain, HttpServletRequest req, HttpServletResponse res, String command) throws IOException, ServletException {
        log.info("defaultRequest");
        if (req.getMethod().equals("POST")) {
            if (CommandType.valueOf(command.toUpperCase()) == CommandType.LOGIN) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN_PATH);
            }
        } else {
            res.sendRedirect(req.getContextPath() + LOGIN_PATH);
        }
    }

    @Override
    public void destroy() {
    }
}
