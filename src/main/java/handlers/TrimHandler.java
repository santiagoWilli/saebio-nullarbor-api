package handlers;

import org.apache.commons.io.FileUtils;
import payloads.Sequence;
import utils.Answer;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class TrimHandler extends AbstractHandler<Sequence> {
    private final String saebioApiUrl;

    public TrimHandler(String saebioApiUrl) {
        super(Sequence.class);
        this.saebioApiUrl = saebioApiUrl;
    }

    @Override
    protected Answer processRequest(Sequence sequence, Map<String, String> requestParams) {
        final String token = UUID.randomUUID().toString();
        final String folderPath = "/home/microb76/tests/santiago/api/temp/" + token;

        for (File file : sequence.getFiles()) {
            try {
                FileUtils.moveFile(file, new File(folderPath + "/" + fileName(file.getName())));
            } catch (IOException e) {
                e.printStackTrace();
                return new Answer(500, "{\"message\":\"Error al mover uno de los ficheros\"}");
            }
        }

        ProcessBuilder process = new ProcessBuilder(scriptsAbsolutePath() + "start-and-disconnect.sh",
                "./trim-and-post.sh", folderPath, token, saebioApiUrl + "/sequences/trimmed");
        try {
            process.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return Answer.withMessage(500, "Trim could not be started");
        }

        return Answer.withToken(202, token);
    }

    private static String scriptsAbsolutePath() {
        String scriptsAbsPath = new File(".").getAbsolutePath();
        return scriptsAbsPath.substring(0, scriptsAbsPath.length() - 1) + "scripts/";
    }

    private static String fileName(String fileName) {
        String withCorrectExtensions = extensionToFqGz(fileName);
        String[] fields = withCorrectExtensions.split("_");
        return fields[0] + "_" + fields[2];
    }

    private static String extensionToFqGz(String fileName) {
        return fileName.replaceAll("(?<!^)[.].*", ".fq.gz");
    }
}
