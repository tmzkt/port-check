package com.tmzkt;

public class Main {
    Main() {
    }

    public static void main(String[] args) {
        new Main().run(args);
    }

    void run(String[] args) {
        int[] portNumbers = validateAndInterpretArguments(args);
        PortChecker portChecker = createPortChecker();

        for (int portNumber : portNumbers) {
            System.out.print("Checking port " + portNumber + "...");
            if (portChecker.isPortOpen(portNumber)) {
                System.out.println("Open");
            } else {
                System.out.println("Closed");
            }
        }
    }

    private int[] validateAndInterpretArguments(String[] args) {
        int[] portNumbers = new int[args.length];

        if (args.length == 0) {
            System.out.println("port-check must be called with at least one port number as argument");
            return new int[0];
        }

        for (int i = 0; i < args.length; i++) {
            try {
                portNumbers[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException ex) {
                System.out.println("Argument '" + args[i] + "' is not a valid integer");
                return new int[0];
            }
        }

        return portNumbers;
    }

    protected PortChecker createPortChecker() {
        return new PortChecker();
    }
}
