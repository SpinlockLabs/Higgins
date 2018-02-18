package sh.spinlock.higgins.agent.task;

public abstract class Task {
    public abstract boolean isCompatible();
    public abstract TaskState run();
}
