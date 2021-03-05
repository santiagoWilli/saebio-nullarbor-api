package handlers;

import payloads.Sequence;
import utils.Answer;

public class TrimHandler extends AbstractHandler<Sequence> {
    public TrimHandler() {
        super(Sequence.class);
    }

    @Override
    protected Answer processRequest(Sequence payload) {
        return new Answer(501, "Not implemented yet");
    }
}
