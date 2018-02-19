package sh.spinlock.higgins.agent.task;

import lombok.Getter;
import sh.spinlock.higgins.agent.job.RemoteJob;

public abstract class Task {
    @Getter
    private RemoteJob remoteJob;

    public Task(RemoteJob remoteJob) {
        this.remoteJob = remoteJob;
    }

    public abstract boolean isCompatible();
    public abstract TaskState run();
}
