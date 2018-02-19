package sh.spinlock.higgins.agent.connection;

public class TlsHost extends BaseHost {
    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public void receive(byte[] bytes) {

    }

    @Override
    public void send(byte[] bytes) {

    }
}
