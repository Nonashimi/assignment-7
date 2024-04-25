package ex1;

interface SupportHandler {
    void handleRequest(SupportRequest request);
}

class HardwareSupportHandler implements SupportHandler {
    private SupportHandler nextHandler;

    @Override
    public void handleRequest(SupportRequest request) {
        if (request.getType() == SupportRequest.Type.HARDWARE) {
            System.out.println("Hardware support team is handling request: " + request.getDescription());
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}

class SoftwareSupportHandler implements SupportHandler {
    private SupportHandler nextHandler;

    @Override
    public void handleRequest(SupportRequest request) {
        if (request.getType() == SupportRequest.Type.SOFTWARE) {
            System.out.println("Software support team is handling request: " + request.getDescription());
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}

class NetworkSupportHandler implements SupportHandler {
    private SupportHandler nextHandler;

    @Override
    public void handleRequest(SupportRequest request) {
        if (request.getType() == SupportRequest.Type.NETWORK) {
            System.out.println("Network support team is handling request: " + request.getDescription());
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}

public class Main {
    public static void main(String[] args) {
        SupportHandler hardwareHandler = new HardwareSupportHandler();
        SupportHandler softwareHandler = new SoftwareSupportHandler();
        SupportHandler networkHandler = new NetworkSupportHandler();

        ((HardwareSupportHandler) hardwareHandler).setNextHandler(softwareHandler);
        ((SoftwareSupportHandler) softwareHandler).setNextHandler(networkHandler);

        SupportRequest request1 = new SupportRequest(1, "Hardware issue", SupportRequest.Type.HARDWARE);
        SupportRequest request2 = new SupportRequest(2, "Software issue", SupportRequest.Type.SOFTWARE);
        SupportRequest request3 = new SupportRequest(3, "Network issue", SupportRequest.Type.NETWORK);

        hardwareHandler.handleRequest(request1);
        hardwareHandler.handleRequest(request2);
        hardwareHandler.handleRequest(request3);
    }
}

class SupportRequest {
    private int id;
    private String description;
    private Type type;

    public SupportRequest(int id, String description, Type type) {
        this.id = id;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    enum Type {
        HARDWARE, SOFTWARE, NETWORK
    }
}
