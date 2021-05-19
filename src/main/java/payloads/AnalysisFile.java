package payloads;

import java.io.File;
import java.util.Collection;

public class AnalysisFile extends Multipart implements Validable {
    public AnalysisFile(Collection<File> files) {
        super(files);
    }

    public File get() {
        return files.iterator().next();
    }

    @Override
    public boolean isValid() {
        return files.size() == 1;
    }
}

