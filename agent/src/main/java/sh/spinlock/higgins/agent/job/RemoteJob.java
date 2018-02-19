package sh.spinlock.higgins.agent.job;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.agent.HigginsAgent;
import sh.spinlock.higgins.agent.task.Task;
import sh.spinlock.higgins.agent.task.TaskState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoteJob {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private String name;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private Workspace workspace;

    private final List<Task> tasks;

    public RemoteJob() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public TaskState run() {
        for (Task task : tasks) {
            TaskState state = task.run();
            if (state == TaskState.FAILURE) {
                return TaskState.FAILURE;
            }
        }

        return TaskState.SUCCESS;
    }

    public static RemoteJob createJob(String name) throws IOException {
        RemoteJob remoteJob = new RemoteJob();
        remoteJob.setName(name);

        Workspace workspace = new Workspace(HigginsAgent.getInstance().getRootDirectory().getWorkspacesDir().resolve(name));
        workspace.initialize();
        remoteJob.setWorkspace(workspace);

        return remoteJob;
    }
}
