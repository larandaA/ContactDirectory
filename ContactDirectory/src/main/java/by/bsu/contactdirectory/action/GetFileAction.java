package by.bsu.contactdirectory.action;

import by.bsu.contactdirectory.util.file.FileNameGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Alexandra on 06.10.2016.
 */
public class GetFileAction implements Action {

    private static Logger logger = LogManager.getLogger(GetFileAction.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("GetFile action requested.");

        String name = request.getParameter(PHOTO_NAME_PARAMETER);
        if (name == null || name.trim().isEmpty()) {
            name = request.getParameter(FILE_NAME_PARAMETER);
            if (name == null || name.trim().isEmpty()) {
                return;
            }
            logger.info(String.format("Getting file with name: %s", name));
            writeFile(response, request, FileNameGenerator.filesPath, name);
        } else {
            logger.info(String.format("Getting photo with name: %s", name));
            writeFile(response, request, FileNameGenerator.photosPath, name);
        }
    }

    private void writeFile(HttpServletResponse response, HttpServletRequest request, String folder, String filename) throws IOException {
        File file = new File(folder, filename);
        if (!file.exists() || file.isDirectory()) {
            return;
        }
        response.setHeader("Content-Type", request.getSession().getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
