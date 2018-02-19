package sh.spinlock.higgins.agent.job;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import sh.spinlock.higgins.agent.Agent;
import sh.spinlock.higgins.agent.task.Task;
import sh.spinlock.higgins.agent.task.TaskState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Job {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private String name;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private Workspace workspace;

    private List<Task> tasks;

    public Job() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void run() {
        System.out.println("Starting Job " + getName());
        for (Task task : tasks) {
            TaskState state = task.run();
            if (state == TaskState.SUCCESS) {
                System.out.println("Task " + task.getClass().getSimpleName() + ": SUCCESS");
            }
        }

        System.out.println("Job " + getName() + ": SUCCESS");
    }

    public static Job createJob(String name) throws IOException {
        Job job = new Job();
        job.setName(name);

        Workspace workspace = new Workspace(Agent.getInstance().getRootDirectory().getWorkspacesDir().resolve(name));
        workspace.initialize();
        job.setWorkspace(workspace);

        return job;
    }
}
