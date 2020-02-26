package epam.pratsaunik.tickets.filter;

import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.jvm.hotspot.debugger.win32.coff.COFFException;

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
    private static final Set<CommandType> HOST_AVAILABLE = new HashSet<>();
    private static final Set<CommandType> GUEST_AVAILABLE = new HashSet<>();


    private static final String LOGIN_PATH = "/index.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        GUEST_AVAILABLE.add(CommandType.CATALOG);
        GUEST_AVAILABLE.add(CommandType.EVENT);
        GUEST_AVAILABLE.add(CommandType.GUEST);
        GUEST_AVAILABLE.add(CommandType.HOME);
        GUEST_AVAILABLE.add(CommandType.CHANGE_LOCALE);

        USER_AVAILABLE.add(CommandType.CART);
        USER_AVAILABLE.add(CommandType.CATALOG);
        USER_AVAILABLE.add(CommandType.CHANGE_PASSWORD);
        USER_AVAILABLE.add(CommandType.EVENT);
        USER_AVAILABLE.add(CommandType.HOME);
        USER_AVAILABLE.add(CommandType.LOGOUT);
        USER_AVAILABLE.add(CommandType.NEW_PASSWORD);
        USER_AVAILABLE.add(CommandType.ORDER);
        USER_AVAILABLE.add(CommandType.ORDER_LINE);
        USER_AVAILABLE.add(CommandType.ORDERS);
        USER_AVAILABLE.add(CommandType.PROFILE);
        USER_AVAILABLE.add(CommandType.CHANGE_LOCALE);

        HOST_AVAILABLE.add(CommandType.NEW_EVENT);
        HOST_AVAILABLE.add(CommandType.NEW_EVENT_PAGE);
        HOST_AVAILABLE.add(CommandType.ADD_VENUE);
        HOST_AVAILABLE.add(CommandType.CATALOG);
        HOST_AVAILABLE.add(CommandType.CHANGE_PASSWORD);
        HOST_AVAILABLE.add(CommandType.EDIT_EVENT_PAGE);
        HOST_AVAILABLE.add(CommandType.EVENT);
        HOST_AVAILABLE.add(CommandType.HOME);
        HOST_AVAILABLE.add(CommandType.LOGIN);
        HOST_AVAILABLE.add(CommandType.MY_EVENTS);
        HOST_AVAILABLE.add(CommandType.NEW_VENUE);
        HOST_AVAILABLE.add(CommandType.NEW_PASSWORD);
        HOST_AVAILABLE.add(CommandType.LOGOUT);
        HOST_AVAILABLE.add(CommandType.PROFILE);
        HOST_AVAILABLE.add(CommandType.UPLOAD);
        HOST_AVAILABLE.add(CommandType.UPLOAD_LAYOUT);
        HOST_AVAILABLE.add(CommandType.CHANGE_LOCALE);

        ADMIN_AVAILABLE.add(CommandType.CATALOG);
        ADMIN_AVAILABLE.add(CommandType.EDIT_USER);
        ADMIN_AVAILABLE.add(CommandType.HOME);
        ADMIN_AVAILABLE.add(CommandType.LOGIN);
        ADMIN_AVAILABLE.add(CommandType.LOGOUT);
        ADMIN_AVAILABLE.add(CommandType.NEW_USER);
        ADMIN_AVAILABLE.add(CommandType.ORDERS_ABOVE_THRESHOLD);
        ADMIN_AVAILABLE.add(CommandType.ORDERS_BELOW_THRESHOLD);
        ADMIN_AVAILABLE.add(CommandType.PROFILE);
        ADMIN_AVAILABLE.add(CommandType.REGISTER);
        ADMIN_AVAILABLE.add(CommandType.STATISTIC);
        ADMIN_AVAILABLE.add(CommandType.USERS);
        ADMIN_AVAILABLE.add(CommandType.CHANGE_LOCALE);



    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Authorization filter works!");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String command = req.getParameter("command");
        log.debug("command: "+command);
        if (command==null||command.equalsIgnoreCase(CommandType.LOGIN.toString())||command.equalsIgnoreCase(CommandType.GUEST.toString())){
            log.info("login case");
            chain.doFilter(request,response);
            return;
        }

        if (session == null) {
            defaultRequest(request, response, chain, req, res, command);
        } else {
            log.info("Session is not null!");
            Role role = null;
            try {
                role =  Role.valueOf((String)session.getAttribute("role"));
            } catch (Exception e) {
                log.info(e);
            }
            log.info("role: "+role);
            log.debug("command:"+command);
            if (role == null) {
                defaultRequest(request, response, chain, req, res, command);
            } else {
                CommandType commandType = CommandType.valueOf(command.toUpperCase());
                switch (role) {
                    case USER:
                        if (USER_AVAILABLE.contains(commandType)) {
                            chain.doFilter(request, response);
                        } else {
                            log.info("Command is not available for USER!");
                            session.removeAttribute("user");
                            try {
                                log.debug("path"+req.getContextPath());
                                res.sendRedirect(req.getContextPath() + ConfigurationManager2.LOGIN_PAGE_PATH.getProperty());
                            } catch (Exception e) {
                                log.debug(e);
                            }
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
                    case HOST:
                        if (HOST_AVAILABLE.contains(commandType)) {
                            chain.doFilter(request, response);
                        } else {
                            log.info("Command is not available for HOST!");
                            session.removeAttribute("user");
                            res.sendRedirect(req.getContextPath() + LOGIN_PATH);
                        }
                        break;
                    case GUEST:
                        if (GUEST_AVAILABLE.contains(commandType)) {
                            chain.doFilter(request, response);
                        } else {
                            log.info("Command is not available for GUEST!");
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
                res.sendRedirect(req.getContextPath() + ConfigurationManager2.LOGIN_PAGE_PATH.getProperty());
            }
        } else {
            res.sendRedirect(req.getContextPath() + ConfigurationManager2.LOGIN_PAGE_PATH.getProperty());
        }
    }

    @Override
    public void destroy() {
    }
}
