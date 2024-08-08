package top.antennababy.demo.web.webtest.demos.common.cow;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileSystemLayer readOnlyLayer = new FileSystemLayer(true);
        FileSystemLayer readWriteLayer = new FileSystemLayer(false);

        // 初始化只读层文件
        readOnlyLayer.addFile(new File("example.txt", "This is a read-only file."));
        readOnlyLayer.addFile(new File("readonly.txt", "You cannot modify this file directly."));

        CopyOnWriteFileManager fileManager = new CopyOnWriteFileManager(readOnlyLayer, readWriteLayer);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a command (read, write, append, exit):");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.println("Enter the file name:");
            String fileName = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "read":
                    String content = fileManager.readFile(fileName);
                    if (content != null) {
                        System.out.println("File content:\n" + content);
                    } else {
                        System.out.println("File not found.");
                    }
                    break;

                case "write":
                    System.out.println("Enter the content to write:");
                    String newContent = scanner.nextLine();
                    fileManager.writeFile(fileName, newContent);
                    System.out.println("File written successfully.");
                    break;

                case "append":
                    System.out.println("Enter the content to append:");
                    String appendContent = scanner.nextLine();
                    fileManager.appendToFile(fileName, appendContent);
                    System.out.println("Content appended successfully.");
                    break;

                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }

        scanner.close();
    }
}
