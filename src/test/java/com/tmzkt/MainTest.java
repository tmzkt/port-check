package com.tmzkt;

import org.jmock.AbstractExpectations;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MainTest {
    private Mockery context;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUp() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void onePortIsOpenOnePortIsClosed() {
        final PortChecker portChecker = context.mock(PortChecker.class);
        Main main = new Main() {
            @Override
            protected PortChecker createPortChecker() {
                return portChecker;
            }
        };

        Expectations expectations = new Expectations();
        expectations.oneOf(portChecker).isPortOpen(123);
        expectations.will(AbstractExpectations.returnValue(true));
        expectations.oneOf(portChecker).isPortOpen(124);
        expectations.will(AbstractExpectations.returnValue(false));
        context.checking(expectations);

        main.run(new String[] { "123", "124" });

        assertEquals("Checking port 123...Open\r\n"
                + "Checking port 124...Closed\r\n", outContent.toString());
    }

    @Test
    public void noArguments() {
        Main.main(new String[] {});

        assertEquals("port-check must be called with at least one port number as argument\r\n", outContent.toString());
    }

    @Test
    public void firstInvalidArgumentReturnsError() {
        Main.main(new String[] { "234", "-9282", "invalid argument", "another invalid" });

        assertEquals("Argument 'invalid argument' is not a valid integer\r\n", outContent.toString());
    }
}