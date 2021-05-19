package handlers;

import payloads.EmptyPayload;
import utils.Answer;

import java.util.Map;
import java.util.UUID;

public class CreateAnalysisHandler extends AbstractHandler<EmptyPayload> {
    public CreateAnalysisHandler() {
        super(EmptyPayload.class);
    }

    @Override
    protected Answer processRequest(EmptyPayload payload, Map<String, String> requestParams) {
        return Answer.withToken(200, UUID.randomUUID().toString());

    }
}
