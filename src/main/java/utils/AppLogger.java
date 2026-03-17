package utils;

public class AppLogger {

    private FileLogger fileLogger;

    public AppLogger(String fileName) {
        this.fileLogger = new FileLogger(fileName);
    }

    public void info(String message) {
        String line = "[INFO] " + message;
        System.out.println(line);
        fileLogger.log(line);
    }

    public void error(String message) {
        String line = "[ERROR] " + message;
        System.out.println(line);
        fileLogger.log(line);
    }
}

