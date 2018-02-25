package sh.spinlock.higgins.util;

import lombok.Getter;

public class MessageId {
    @Getter
    private long id;

    public long increment() {
        return id++;
    }
}
