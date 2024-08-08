package top.antennababy.demo.web.webtest.demos.common.cow;

import java.util.HashMap;
import java.util.Map;

class FileSystemLayer {
    private Map<String, File> files = new HashMap<>();
    private boolean readOnly;

    public FileSystemLayer(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public File getFile(String name) {
        return files.get(name);
    }

    public void addFile(File file) {
        files.put(file.getName(), file);
    }

    public void removeFile(String name) {
        files.remove(name);
    }

    public boolean containsFile(String name) {
        return files.containsKey(name);
    }
}
