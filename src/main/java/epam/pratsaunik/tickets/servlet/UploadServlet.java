package epam.pratsaunik.tickets.servlet;

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
import java.io.IOException;
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private final static Logger log = LogManager.getLogger();
    private static final String UPLOAD_PATH = "upload";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletContext().getRealPath("/");
        log.debug(path);
        String absPath = path + File.separator + UPLOAD_PATH;
        File uploadDir = new File(absPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        for (Part part : req.getParts()) {
            log.debug(absPath);
            if(part.getSubmittedFileName()!=null){
                part.write(uploadDir + File.separator + part.getSubmittedFileName());
//                resp.getWriter().print(part.getSubmittedFileName());//fixme

            }
        }
    }
}
