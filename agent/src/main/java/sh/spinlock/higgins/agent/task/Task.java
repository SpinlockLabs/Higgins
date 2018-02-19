package sh.spinlock.higgins.agent.task;

import lombok.Getter;
import sh.spinlock.higgins.agent.job.Job;

public abstract class Task {
    @Getter
    private Job job;

    public Task(Job job) {
        this.job = job;
    }

    public abstract boolean isCompatible();
    public abstract TaskState run();
}
