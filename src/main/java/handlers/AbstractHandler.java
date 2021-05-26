package handlers;

import org.apache.commons.io.FileUtils;
import payloads.EmptyPayload;
import payloads.Multipart;
import payloads.Validable;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.Answer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractHandler<V extends Validable> implements RequestHandler<V>, Route {
    private final Class<V> payloadClass;
    private final String uuid;

    protected AbstractHandler(Class<V> payloadClass) {
        this.payloadClass = payloadClass;
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public final Answer process(V payload, Map<String, String> requestParams) {
        if (payload != null && !payload.isValid()) {
            return Answer.badRequest("Cuerpo de la petición no válido");
        } else {
            return processRequest(payload, requestParams);
        }
    }

    protected abstract Answer processRequest(V payload, Map<String, String> requestParams);

    @Override
    public Object handle(Request request, Response response) throws IOException {
        try {
            V payload = null;
            if (payloadClass.getSuperclass().equals(Multipart.class)) {
                request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
                Collection<File> files = partsToFiles(request.raw().getParts());
                payload = payloadClass.getConstructor(Collection.class).newInstance(files);
            }

            Answer answer = process(payload, request.params());
            FileUtils.deleteDirectory(new File("temp/" + uuid));

            response.status(answer.getCode());
            response.type("application/json");
            return answer.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            FileUtils.deleteDirectory(new File("temp/" + uuid));
            response.status(500);
            response.type("application/json");
            return e.getMessage();
        }
    }

    private Collection<File> partsToFiles(Collection<Part> parts) throws IOException {
        Collection<File> files = new ArrayList<>();
        for (Part part : parts) {
            File file = new File("temp/" + uuid + "/" + part.getSubmittedFileName());
            FileUtils.copyInputStreamToFile(part.getInputStream(), file);
            files.add(file);
        }
        return files;
    }
}
