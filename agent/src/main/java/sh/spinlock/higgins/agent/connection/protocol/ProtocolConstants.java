package sh.spinlock.higgins.agent.connection.protocol;

@SuppressWarnings("WeakerAccess")
public class ProtocolConstants {
    public static final int VERSION = 1;

    public class MessageIndex {
        public static final int ACK = 0;
        public static final int HELLO = 1;
        public static final int AUTH = 2;
        public static final int AGENT_INFO = 3;
    }
}
