package sh.spinlock.higgins.agent.task.shell;

import sh.spinlock.higgins.agent.task.Task;
import sh.spinlock.higgins.agent.task.TaskState;
import sh.spinlock.higgins.util.OSUtil;

public class UnixShellTask extends Task {
    @Override
    public boolean isCompatible() {
        return OSUtil.isUnix;
    }

    @Override
    public TaskState run() {
        return TaskState.FAILURE;
    }
}