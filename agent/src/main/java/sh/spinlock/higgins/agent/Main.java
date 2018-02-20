package sh.spinlock.higgins.agent;

import sh.spinlock.higgins.agent.connection.SocketHostConnection;

public class Main {
    public static void main(String[] args) {
        HigginsAgent agent = new HigginsAgent();
        HigginsAgent.initializeInstance(agent);
        HigginsAgent.getInstance().setConnection(new SocketHostConnection());
        agent.start();
    }
}
