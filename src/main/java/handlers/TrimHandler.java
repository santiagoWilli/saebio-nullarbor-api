package handlers;

import org.apache.commons.io.FileUtils;
import payloads.Sequence;
import utils.Answer;

import javax.servlet.http.Part;
import java.io.*;
import java.util.UUID;

public class TrimHandler extends AbstractHandler<Sequence> {
    private final String saebioApiUrl;

    public TrimHandler(String saebioApiUrl) {
        super(Sequence.class);
        this.saebioApiUrl = saebioApiUrl;
    }

    @Override
    protected Answer processRequest(Sequence sequence) {
        final String token = UUID.randomUUID().toString();
        final String folderPath = "/home/microb76/tests/santiago/api/temp/" + token;

        for (Part part : sequence.getParts()) {
            try (InputStream stream = part.getInputStream()) {
                File targetFile = new File(folderPath + "/" + part.getSubmittedFileName());
                FileUtils.copyInputStreamToFile(stream, targetFile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return Answer.error(500, "Exception occurred while processing the multipart provided");
            }
        }

        ProcessBuilder process = new ProcessBuilder(scriptsAbsolutePath() + "start-and-disconnect.sh",
                "./trim-and-post.sh", folderPath, token, saebioApiUrl + "/sequences/trimmed");
        try {
            process.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return Answer.error(500, "Trim could not be started");
        }

        return new Answer(202, "{\"token\":\""+token+"\"}");
    }

    private String scriptsAbsolutePath() {
        String scriptsAbsPath = new File(".").getAbsolutePath();
        return scriptsAbsPath.substring(0, scriptsAbsPath.length() - 1) + "scripts/";
    }
}
