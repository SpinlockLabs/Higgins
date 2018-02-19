package sh.spinlock.higgins.agent.job;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Workspace {
    private Path rootPath;
    private Path tempDir;

    public Workspace(Path rootPath) {
        this.rootPath = rootPath;
        this.tempDir = rootPath.resolve("temp");
    }

    /**
     * Initializes the directories for the workspace.
     */
    public void initialize() throws IOException {
        Files.createDirectories(rootPath);
        Files.createDirectories(tempDir);
    }

    public Path createTempTextFile(final String extension, final String contents) throws IOException {
        String fileName = UUID.randomUUID().toString() + "." + extension;
        Path tempFile = tempDir.resolve(fileName);
        Files.write(tempFile, contents.getBytes());
        return tempFile;
    }
}
