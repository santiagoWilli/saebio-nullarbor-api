package handlers;

import payloads.EmptyPayload;
import utils.Answer;
import utils.Utils;

import java.io.IOException;
import java.util.Map;

public class AnalysisStartHandler extends AbstractHandler<EmptyPayload> {
    private final String saebioApiUrl;

    public AnalysisStartHandler(String saebioApiUrl) {
        super(EmptyPayload.class);
        this.saebioApiUrl = saebioApiUrl;
    }

    @Override
    protected Answer processRequest(EmptyPayload payload, Map<String, String> requestParams) {
        final String folderPath = Utils.tempFolderPath() + requestParams.get(":token") + "/";
        ProcessBuilder process = new ProcessBuilder(Utils.scriptsAbsolutePath() + "start-and-disconnect.sh",
                "./run-and-post.sh", folderPath, requestParams.get(":token"), saebioApiUrl + "/reports/result");
        try {
            process.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            return Answer.withMessage(500, "Analysis could not be started");
        }
        return Answer.withMessage(202, "Analysis started");
    }
}
