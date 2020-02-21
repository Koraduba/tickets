package epam.pratsaunik.tickets.servlet;

import epam.pratsaunik.tickets.command.*;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.util.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    private final static Logger log = LogManager.getLogger();
    private static final String UPLOAD_PATH = "upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(ConfigurationManager2.HOME_PAGE_PATH.getProperty()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandResult commandResult=null;
        String path = req.getServletContext().getRealPath("/");
        String absPath = path + File.separator + UPLOAD_PATH;
        File uploadDir = new File(absPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            for (Part part : req.getParts()) {
                log.debug("Upload Servlet. Part:" +part);
                if (part.getSubmittedFileName()!=null) {
                    log.debug(part.getSubmittedFileName());
                    part.write(uploadDir + File.separator + part.getSubmittedFileName());
                    req.getSession().setAttribute("path", UPLOAD_PATH + File.separator + part.getSubmittedFileName());
                }
            }
        } catch (IOException e) {
            log.debug("Uploadservlet. IOException.");
            req.setAttribute("errorUploadMessage", MessageManager.INSTANCE.getProperty(MessageType.NO_CHOSEN_FILE));

            getServletContext().getRequestDispatcher(ConfigurationManager2.UPLOAD_PAGE_PATH.getProperty()).forward(req,resp);
        }
        String commandName = req.getParameter(ParameterName.COMMAND);
        AbstractCommand commandAction = CommandFactory.instance.getCommand(commandName);
        RequestContent requestContent = new RequestContent();
        requestContent.extractValues(req);
        if (commandAction != null) {
            try {
                commandResult = commandAction.execute(requestContent);
            } catch (CommandException e) {
                log.info(e);
            }
            try {
                requestContent.insertAttributes(req);
                switch (commandResult.getResponseType()) {
                    case FORWARD:
                        getServletContext().getRequestDispatcher(commandResult.getResponsePage()).forward(req, resp);
                        break;
                    case REDIRECT:
                        req.getSession().setAttribute("page", commandResult.getResponsePage());
                        resp.sendRedirect(req.getContextPath() + "/uploadservlet");
                        break;
                    default:
                        throw new ServletException("Command error!");
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            log.info("Command does not exist!");
            throw new ServletException();
    }
    }
}
