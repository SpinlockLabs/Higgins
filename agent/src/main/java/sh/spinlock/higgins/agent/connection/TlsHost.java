package sh.spinlock.higgins.agent.connection;

public class TlsHost extends BaseHost {
    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public void receiveString(String str) {

    }

    @Override
    public void sendString(String str) {

    }
}
