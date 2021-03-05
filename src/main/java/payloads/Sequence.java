package payloads;

import javax.servlet.http.Part;
import java.util.Collection;

public class Sequence extends Multipart implements Validable {
    public Sequence(Collection<Part> parts) {
        super(parts);
    }

    @Override
    public boolean isValid() {
        return parts.size() == 2;
    }
}

