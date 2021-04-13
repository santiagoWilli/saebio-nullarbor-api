package payloads;

import java.io.File;
import java.util.Collection;

public abstract class Multipart {
    protected final Collection<File> files;

    protected Multipart(Collection<File> files) {
        this.files = files;
    }

    public Collection<File> getFiles() {
        return files;
    }
}