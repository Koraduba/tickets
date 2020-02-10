package epam.pratsaunik.tickets.servlet;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandFactory;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.connection.ConnectionPoll;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ConnectionException;
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
        String commandName = request.getParameter(ParameterName.COMMAND);
        if(commandName!=null){
            doPost(request,response);
        }else{
            String page = (String) request.getSession().getAttribute("page");
            getServletContext().getRequestDispatcher(page).forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        RequestContent content = new RequestContent();
        String commandName = request.getParameter(ParameterName.COMMAND);
        content.extractValues(request);
        log.debug(commandName);
        AbstractCommand commandAction = CommandFactory.instance.getCommand(commandName);
        if (commandAction != null) {
            try {
                String page = commandAction.execute(content);
                log.info(page);
                content.insertAttributes(request);
                if (page != null) {
                    request.getSession().setAttribute("page", page);
                    log.debug(request.getContextPath());
                    response.sendRedirect(request.getContextPath() + "/mainservlet");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Command does not exist!");
        }
    }

}
