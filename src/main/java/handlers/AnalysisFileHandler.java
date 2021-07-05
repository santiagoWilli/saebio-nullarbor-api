package handlers;

import org.apache.commons.io.FileUtils;
import payloads.AnalysisFile;
import utils.Answer;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AnalysisFileHandler extends AbstractHandler<AnalysisFile> {
    public AnalysisFileHandler() {
        super(AnalysisFile.class);
    }

    @Override
    protected Answer processRequest(AnalysisFile file, Map<String, String> requestParams) {
        final String folderPath = Utils.tempFolderPath() + requestParams.get(":token") + "/";
        try {
            FileUtils.moveFile(file.get(), new File(folderPath + fileName(file.get().getName())));
        } catch (IOException e) {
            e.printStackTrace();
            return Answer.withMessage(500, "Error al mover uno de los ficheros");
        }
        return Answer.withMessage(200, "File uploaded");
    }

    private static String fileName(String fileName) {
        if (fileName.endsWith(".fa") || fileName.endsWith(".gbf")) return fileName;
        String withCorrectExtensions = extensionToFqGz(fileName);
        String[] fields = withCorrectExtensions.split("_");
        if (fields.length > 3) return fields[0] + "_" + fields[2] + "_" + fields[3];
        return withCorrectExtensions;
    }

    private static String extensionToFqGz(String fileName) {
        return fileName.replaceAll("(?<!^)[.].*", ".fq.gz");
    }
}
