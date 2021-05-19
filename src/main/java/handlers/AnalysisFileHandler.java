package handlers;

import org.apache.commons.io.FileUtils;
import payloads.AnalysisFile;
import utils.Answer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AnalysisFileHandler extends AbstractHandler<AnalysisFile> {
    public AnalysisFileHandler() {
        super(AnalysisFile.class);
    }

    @Override
    protected Answer processRequest(AnalysisFile file, Map<String, String> requestParams) {
        final String folderPath = "/home/microb76/tests/santiago/api/temp/" + requestParams.get(":token") + "/";
        try {
            FileUtils.moveFile(file.get(), new File(folderPath + file.get().getName()));
        } catch (IOException e) {
            e.printStackTrace();
            return Answer.withMessage(500, "Error al mover uno de los ficheros");
        }
        return Answer.withMessage(200, "File uploaded");
    }
}
