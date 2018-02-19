package sh.spinlock.higgins.agent.file;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RootDirectory {

    @Getter
    private Path rootDir;

    @Getter
    private Path workspacesDir;

    public RootDirectory() {
        this(".");
    }

    public RootDirectory(final String path) {
        rootDir = Paths.get(path);
        workspacesDir = rootDir.resolve("workspaces");
    }

    public void initialize() throws IOException {
        Files.createDirectories(rootDir);
        Files.createDirectories(workspacesDir);
    }

    public Path createWorkspace(final String name) throws IOException {
        Path workspaceDir = workspacesDir.resolve(name);
        Files.createDirectories(workspaceDir);
        return workspaceDir;
    }

}
