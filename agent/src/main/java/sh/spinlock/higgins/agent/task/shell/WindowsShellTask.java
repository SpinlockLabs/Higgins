package sh.spinlock.higgins.agent.task.shell;

import sh.spinlock.higgins.agent.job.Job;
import sh.spinlock.higgins.agent.task.Task;
import sh.spinlock.higgins.agent.task.TaskState;
import sh.spinlock.higgins.util.OSUtil;

public class WindowsShellTask extends Task {
    public WindowsShellTask(Job job) {
        super(job);
    }

    @Override
    public boolean isCompatible() {
        return OSUtil.isWindows;
    }

    @Override
    public TaskState run() {
        return TaskState.FAILURE;
    }
}
