package epam.pratsaunik.tickets.servlet;

import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.util.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    private static final String UPLOAD_PATH = "upload";
    private static final String NEW_UPLOAD = "new";
    private static final String EDIT_UPLOAD = "edit";
    private final static Logger log = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        switch ((String) req.getSession().getAttribute(AttributeName.MODE)) {
            case NEW_UPLOAD:
                getServletContext().getRequestDispatcher(ConfigurationManager2.NEW_EVENT_PAGE_PATH.getProperty()).forward(req, resp);
                break;
            case EDIT_UPLOAD:
                getServletContext().getRequestDispatcher(ConfigurationManager2.EDIT_EVENT_PAGE_PATH.getProperty()).forward(req, resp);
                break;
            default:
                throw new ServletException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletContext().getRealPath("/");
        String absPath = path + File.separator + UPLOAD_PATH;
        File uploadDir = new File(absPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            for (Part part : req.getParts()) {
                if (part.getSubmittedFileName() != null) {
                    log.debug(part.getSubmittedFileName());
                    part.write(uploadDir + File.separator + part.getSubmittedFileName());
                    req.getSession().setAttribute(AttributeName.EVENT_IMAGE, UPLOAD_PATH + File.separator + part.getSubmittedFileName());
                }
            }
        } catch (IOException e) {
            req.setAttribute(AttributeName.ERROR_UPLOAD_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.NO_CHOSEN_FILE));
            getServletContext().getRequestDispatcher(ConfigurationManager2.UPLOAD_PAGE_PATH.getProperty()).forward(req, resp);
            return;
        }
        req.removeAttribute(AttributeName.ERROR_UPLOAD_MESSAGE);
        resp.sendRedirect(req.getContextPath() + "/uploadservlet");
    }
}
