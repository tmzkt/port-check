import org.jmock.AbstractExpectations;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PortCheckerTest {
    private Mockery context;

    @Before
    public void setUp() {
        context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
    }

    @Test
    public void portIsOpen() throws IOException {
        final ServerSocket serverSocket = context.mock(ServerSocket.class);
        final DatagramSocket datagramSocket = context.mock(DatagramSocket.class);
        PortChecker instance = new PortChecker() {
            @Override
            protected ServerSocket createServerSocket(int portNumber) throws IOException {
                return serverSocket;
            }

            @Override
            protected DatagramSocket createDatagramSocket(int portNumber) throws IOException {
                return datagramSocket;
            }
        };

        Expectations expectations = new Expectations();
        expectations.oneOf(serverSocket).close();
        expectations.oneOf(datagramSocket).close();
        context.checking(expectations);

        assertTrue(instance.isPortOpen(123));
    }

    @Test
    public void portIsOpenAndExceptionThrownWhileClosingSocket() throws IOException {
        final ServerSocket serverSocket = context.mock(ServerSocket.class);
        final DatagramSocket datagramSocket = context.mock(DatagramSocket.class);
        PortChecker instance = new PortChecker() {
            @Override
            protected ServerSocket createServerSocket(int portNumber) throws IOException {
                return serverSocket;
            }

            @Override
            protected DatagramSocket createDatagramSocket(int portNumber) throws IOException {
                return datagramSocket;
            }
        };

        Expectations expectations = new Expectations();
        expectations.oneOf(serverSocket).close();
        expectations.will(AbstractExpectations.throwException(new IOException()));
        expectations.oneOf(datagramSocket).close();
        context.checking(expectations);

        assertTrue(instance.isPortOpen(123));
    }

    @Test
    public void portIsClosedIfServerSocketFails() throws IOException {
        final DatagramSocket datagramSocket = context.mock(DatagramSocket.class);
        PortChecker instance = new PortChecker() {
            @Override
            protected ServerSocket createServerSocket(int portNumber) throws IOException {
                throw new IOException();
            }

            @Override
            protected DatagramSocket createDatagramSocket(int portNumber) throws IOException {
                return datagramSocket;
            }
        };

        Expectations expectations = new Expectations();
        expectations.never(datagramSocket).close();
        context.checking(expectations);

        assertFalse(instance.isPortOpen(123));
    }

    @Test
    public void portIsClosedIfDatagramSocketFails() throws IOException {
        final ServerSocket serverSocket = context.mock(ServerSocket.class);
        PortChecker instance = new PortChecker() {
            @Override
            protected ServerSocket createServerSocket(int portNumber) throws IOException {
                return serverSocket;
            }

            @Override
            protected DatagramSocket createDatagramSocket(int portNumber) throws IOException {
                throw new IOException();
            }
        };

        Expectations expectations = new Expectations();
        expectations.oneOf(serverSocket).close();
        context.checking(expectations);

        assertFalse(instance.isPortOpen(123));
    }
}