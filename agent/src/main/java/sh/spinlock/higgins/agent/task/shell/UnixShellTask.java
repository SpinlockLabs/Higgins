package sh.spinlock.higgins.agent.task.shell;

import sh.spinlock.higgins.agent.job.RemoteJob;
import sh.spinlock.higgins.agent.task.Task;
import sh.spinlock.higgins.agent.task.TaskState;
import sh.spinlock.higgins.util.OSUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UnixShellTask extends Task {
    private String contents = "echo Test!";

    public UnixShellTask(RemoteJob remoteJob) {
        super(remoteJob);
    }

    @Override
    public boolean isCompatible() {
        return OSUtil.isUnix;
    }

    @Override
    public TaskState run() {
        Path tempFile;
        try {
            tempFile = this.getRemoteJob().getWorkspace().createTempTextFile("sh", contents);
        } catch (IOException e) {
            e.printStackTrace();
            return TaskState.FAILURE;
        }

        if (tempFile == null) {
            return TaskState.FAILURE;
        }

        ProcessBuilder pb = new ProcessBuilder("/bin/sh", tempFile.toAbsolutePath().toString());
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process p;
        try {
            p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return TaskState.FAILURE;
        }

        try {
            Files.delete(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return TaskState.FAILURE;
        }

        return TaskState.SUCCESS;
    }
}
