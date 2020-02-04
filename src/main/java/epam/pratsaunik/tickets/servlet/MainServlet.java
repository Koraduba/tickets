package epam.pratsaunik.tickets.servlet;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandFactory;
import epam.pratsaunik.tickets.command.RequestContent;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.info("--------------------------- i n f o ----------------");
        log.info(request.getParameter(ParameterName.COMMAND));
        RequestContent content = new RequestContent();
        content.extractValues(request);
        String commandName = content.getRequestParameter(ParameterName.COMMAND);
        AbstractCommand commandAction = CommandFactory.instance.getCommand(commandName);
        if (commandAction != null) {
            try {
                String page = commandAction.execute(content);
                log.info(page);
                content.insertAttributes(request);
                if (page != null) {
                    getServletContext().getRequestDispatcher(page).forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Command does not exist!");
        }
    }

}
