package utils;

import java.util.Objects;

public class Answer {
    private final int code;
    private final String body;

    public Answer(int code, String body){
        this.code = code;
        this.body = body;
    }

    public static Answer withMessage(int code, String message) {
        return new Answer(code, errorJson(message));
    }

    public static Answer badRequest(String message) {
        return Answer.withMessage(400, message);
    }

    public static Answer withToken(int code, String token) {
        return new Answer(code, "{\"token\":\""+ token +"\"}");
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

    private static String errorJson(String message) {
        return "{\"message\":\"" + message + "\"}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Answer answer = (Answer) object;
        if (code != answer.code) return false;
        return Objects.equals(body, answer.body);
    }

    @Override
    public String toString() {
        return "Answer (code=" + code + ", body=" + body + ")";
    }
}
