package epam.pratsaunik.tickets.servlet;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandFactory;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private final static Logger log = LogManager.getLogger();

    @Override
    public void init() {
        log.info("MainServlet initiated!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet");
        String commandName = request.getParameter(ParameterName.COMMAND);
        log.debug("command: " + commandName);
        if (commandName != null) {
            doPost(request, response);
        } else {
            String page = (String) request.getSession().getAttribute(AttributeName.REDIRECT_PAGE);
            getServletContext().getRequestDispatcher(page).forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent content = new RequestContent();
        content.extractValues(request);
        String commandName = request.getParameter(ParameterName.COMMAND);
        log.debug("Command came to mainservlet: " + commandName);
        AbstractCommand commandAction = CommandFactory.instance.getCommand(commandName);
        if (commandAction != null) {
            CommandResult commandResult = null;
            try {
                commandResult = commandAction.execute(content);
            } catch (CommandException e) {
                log.error(e);
                throw new ServletException(e);
            }
            content.insertAttributes(request);
            log.debug("Main servlet. user: "+request.getSession().getAttribute("user"));
            switch (commandResult.getResponseType()) {
                case FORWARD:
                    getServletContext().getRequestDispatcher(commandResult.getResponsePage()).forward(request, response);
                    break;
                case REDIRECT:
                    request.getSession().setAttribute(AttributeName.REDIRECT_PAGE, commandResult.getResponsePage());
                    response.sendRedirect(request.getContextPath() + "/mainservlet");
                    break;
                default:
                    throw new ServletException("Command error!");
            }

        } else {
            log.warn("Command does not exist!");
            throw new ServletException("Command does not exist!");
        }
    }

}
