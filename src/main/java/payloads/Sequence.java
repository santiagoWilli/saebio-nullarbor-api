package payloads;

import java.io.File;
import java.util.Collection;

public class Sequence extends Multipart implements Validable {
    public Sequence(Collection<File> files) {
        super(files);
    }

    @Override
    public boolean isValid() {
        return files.size() == 2;
    }
}

