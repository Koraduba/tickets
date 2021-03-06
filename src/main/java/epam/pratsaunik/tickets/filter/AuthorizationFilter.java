package epam.pratsaunik.tickets.filter;

import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.util.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * filter for authorization of users based on commands available for them
 */
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
        GUEST_AVAILABLE.add(CommandType.LOGIN_PAGE);
        GUEST_AVAILABLE.add(CommandType.LOGOUT);

        USER_AVAILABLE.add(CommandType.CART);
        USER_AVAILABLE.add(CommandType.CATALOG);
        USER_AVAILABLE.add(CommandType.NEW_PASSWORD_PAGE);
        USER_AVAILABLE.add(CommandType.EVENT);
        USER_AVAILABLE.add(CommandType.HOME);
        USER_AVAILABLE.add(CommandType.LOGOUT);
        USER_AVAILABLE.add(CommandType.NEW_PASSWORD);
        USER_AVAILABLE.add(CommandType.ORDER);
        USER_AVAILABLE.add(CommandType.ORDER_LINE);
        USER_AVAILABLE.add(CommandType.ORDERS);
        USER_AVAILABLE.add(CommandType.PROFILE);
        USER_AVAILABLE.add(CommandType.CHANGE_LOCALE);
        USER_AVAILABLE.add(CommandType.LOGIN_PAGE);
        USER_AVAILABLE.add(CommandType.CLEAR_CART);
        USER_AVAILABLE.add(CommandType.ORDER_INFO);

        HOST_AVAILABLE.add(CommandType.NEW_EVENT);
        HOST_AVAILABLE.add(CommandType.NEW_EVENT_PAGE);
        HOST_AVAILABLE.add(CommandType.ADD_VENUE);
        HOST_AVAILABLE.add(CommandType.CATALOG);
        HOST_AVAILABLE.add(CommandType.NEW_PASSWORD_PAGE);
        HOST_AVAILABLE.add(CommandType.EDIT_EVENT_PAGE);
        HOST_AVAILABLE.add(CommandType.EDIT_EVENT);
        HOST_AVAILABLE.add(CommandType.EVENT);
        HOST_AVAILABLE.add(CommandType.HOME);
        HOST_AVAILABLE.add(CommandType.LOGIN);
        HOST_AVAILABLE.add(CommandType.MY_EVENTS);
        HOST_AVAILABLE.add(CommandType.NEW_VENUE_PAGE);
        HOST_AVAILABLE.add(CommandType.NEW_PASSWORD);
        HOST_AVAILABLE.add(CommandType.LOGOUT);
        HOST_AVAILABLE.add(CommandType.PROFILE);
        HOST_AVAILABLE.add(CommandType.UPLOAD_PAGE);
        HOST_AVAILABLE.add(CommandType.CHANGE_LOCALE);
        HOST_AVAILABLE.add(CommandType.LOGIN_PAGE);

        ADMIN_AVAILABLE.add(CommandType.CATALOG);
        ADMIN_AVAILABLE.add(CommandType.HOME);
        ADMIN_AVAILABLE.add(CommandType.LOGIN);
        ADMIN_AVAILABLE.add(CommandType.LOGOUT);
        ADMIN_AVAILABLE.add(CommandType.NEW_USER_PAGE);
        ADMIN_AVAILABLE.add(CommandType.ORDERS_ABOVE_THRESHOLD);
        ADMIN_AVAILABLE.add(CommandType.ORDERS_BELOW_THRESHOLD);
        ADMIN_AVAILABLE.add(CommandType.PROFILE);
        ADMIN_AVAILABLE.add(CommandType.REGISTER);
        ADMIN_AVAILABLE.add(CommandType.STATISTIC);
        ADMIN_AVAILABLE.add(CommandType.USERS);
        ADMIN_AVAILABLE.add(CommandType.CHANGE_LOCALE);
        ADMIN_AVAILABLE.add(CommandType.LOGIN_PAGE);
        ADMIN_AVAILABLE.add(CommandType.DELETE_USER);
        ADMIN_AVAILABLE.add(CommandType.ORDER_INFO);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Authorization filter works!");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String command = req.getParameter(ParameterName.COMMAND);
        log.debug("command: "+command);
        if (command==null
                ||command.equalsIgnoreCase(CommandType.LOGIN.toString())
                ||command.equalsIgnoreCase(CommandType.LOGIN_PAGE.toString())
                ||command.equalsIgnoreCase(CommandType.GUEST.toString())
                ||command.equalsIgnoreCase(CommandType.NEW_USER_PAGE.toString())
                ||command.equalsIgnoreCase(CommandType.REGISTER.toString())){
            log.info("login case");
            chain.doFilter(request,response);
            return;
        }

        if (session == null) {
            log.debug("session is null");
            defaultRequest(request, response, chain, req, res, command);
        } else {
            Role role = null;
            try {
                role =  Role.valueOf((String)session.getAttribute(AttributeName.USER_ROLE));
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
                            log.info("Command is not available for User!");
                            try {
                                res.sendRedirect(req.getContextPath() + ConfigurationManager.LOGIN_PAGE_PATH.getProperty());
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
                            req.getSession().setAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.COMMAND_IS_NOT_AVAILABLE));
                            req.getRequestDispatcher(ConfigurationManager.HOME_PAGE_PATH.getProperty()).forward(request,response);
                        }
                        break;
                    case HOST:
                        if (HOST_AVAILABLE.contains(commandType)) {
                            chain.doFilter(request, response);
                        } else {
                            log.info("Command is not available for HOST!");
                            req.getSession().setAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.COMMAND_IS_NOT_AVAILABLE));
                            req.getRequestDispatcher(ConfigurationManager.HOME_PAGE_PATH.getProperty()).forward(request,response);
                        }
                        break;
                    case GUEST:
                        if (GUEST_AVAILABLE.contains(commandType)) {
                            chain.doFilter(request, response);
                        } else {
                            log.info("Command is not available for GUEST!");
                            req.getSession().setAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.COMMAND_IS_NOT_AVAILABLE));
                            req.getRequestDispatcher(ConfigurationManager.HOME_PAGE_PATH.getProperty()).forward(request,response);
                        }
                        break;
                    default:
                        log.info("Role is undefined");
                        req.getSession().setAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.COMMAND_IS_NOT_AVAILABLE));
                        req.getRequestDispatcher(ConfigurationManager.HOME_PAGE_PATH.getProperty()).forward(request,response);
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
                res.sendRedirect(req.getContextPath() + ConfigurationManager.LOGIN_PAGE_PATH.getProperty());
            }
        } else {
            res.sendRedirect(req.getContextPath() + ConfigurationManager.LOGIN_PAGE_PATH.getProperty());
        }
    }

    @Override
    public void destroy() {
    }
}
