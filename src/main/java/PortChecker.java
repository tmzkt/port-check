import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class PortChecker {
    public boolean isPortOpen(int portNumber) {
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = createServerSocket(portNumber);
            ds = createDatagramSocket(portNumber);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                }
            }
        }

        return false;
    }

    protected ServerSocket createServerSocket(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        serverSocket.setReuseAddress(true);
        return serverSocket;
    }

    protected DatagramSocket createDatagramSocket(int portNumber) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(portNumber);
        datagramSocket.setReuseAddress(true);
        return datagramSocket;
    }
}
