package top.antennababy.demo.web.webtest.demos.common.cow;

class CopyOnWriteFileManager {
    private FileSystemLayer readOnlyLayer;
    private FileSystemLayer readWriteLayer;

    public CopyOnWriteFileManager(FileSystemLayer readOnlyLayer, FileSystemLayer readWriteLayer) {
        this.readOnlyLayer = readOnlyLayer;
        this.readWriteLayer = readWriteLayer;
    }

    public String readFile(String fileName) {
        File file = readWriteLayer.getFile(fileName);
        if (file == null) {
            file = readOnlyLayer.getFile(fileName);
        }
        return file != null ? file.getContent() : null;
    }

    public void writeFile(String fileName, String content) {
        File file = readWriteLayer.getFile(fileName);
        if (file == null) {
            file = readOnlyLayer.getFile(fileName);
            if (file != null) {
                file = new File(file.getName(), file.getContent());
            } else {
                file = new File(fileName, "");
            }
            readWriteLayer.addFile(file);
        }
        file.setContent(content);
    }

    public void appendToFile(String fileName, String content) {
        File file = readWriteLayer.getFile(fileName);
        if (file == null) {
            file = readOnlyLayer.getFile(fileName);
            if (file != null) {
                file = new File(file.getName(), file.getContent());
            } else {
                file = new File(fileName, "");
            }
            readWriteLayer.addFile(file);
        }
        file.appendContent(content);
    }
}
