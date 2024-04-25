package ex2;

import java.util.HashMap;
import java.util.Map;

interface DocumentManagementSystem {
    void uploadDocument(String document, String user);
    String downloadDocument(String document, String user);
    void editDocument(String document, String user, String content);
    void deleteDocument(String document, String user);
}

class RealDocumentManagementSystem implements DocumentManagementSystem {
    private Map<String, String> documents = new HashMap<>();

    @Override
    public void uploadDocument(String document, String user) {
        documents.put(document, "Content of " + document);
        System.out.println(user + " uploaded document: " + document);
    }

    @Override
    public String downloadDocument(String document, String user) {
        System.out.println(user + " downloaded document: " + document);
        return documents.get(document);
    }

    @Override
    public void editDocument(String document, String user, String content) {
        documents.put(document, content);
        System.out.println(user + " edited document: " + document);
    }

    @Override
    public void deleteDocument(String document, String user) {
        documents.remove(document);
        System.out.println(user + " deleted document: " + document);
    }
}

class DocumentManagementSystemProxy implements DocumentManagementSystem {
    private RealDocumentManagementSystem realDocumentManagementSystem = new RealDocumentManagementSystem();
    private Map<String, String> userSessions = new HashMap<>();

    @Override
    public void uploadDocument(String document, String user) {
        if (validateUserSession(user)) {
            realDocumentManagementSystem.uploadDocument(document, user);
        } else {
            System.out.println("User " + user + " is not authenticated.");
        }
    }

    @Override
    public String downloadDocument(String document, String user) {
        if (validateUserSession(user)) {
            return realDocumentManagementSystem.downloadDocument(document, user);
        } else {
            System.out.println("User " + user + " is not authenticated.");
            return null;
        }
    }

    @Override
    public void editDocument(String document, String user, String content) {
        if (validateUserSession(user)) {
            realDocumentManagementSystem.editDocument(document, user, content);
        } else {
            System.out.println("User " + user + " is not authenticated.");
        }
    }

    @Override
    public void deleteDocument(String document, String user) {
        if (validateUserSession(user)) {
            realDocumentManagementSystem.deleteDocument(document, user);
        } else {
            System.out.println("User " + user + " is not authenticated.");
        }
    }

    private boolean validateUserSession(String user) {
        if (userSessions.containsKey(user)) {
            return true;
        } else {
            System.out.println("Session expired for user " + user);
            return false;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DocumentManagementSystem system = new DocumentManagementSystemProxy();

        system.uploadDocument("Document1", "User1");
        system.downloadDocument("Document1", "User1");
        system.editDocument("Document1", "User1", "Updated content of Document1");
        system.deleteDocument("Document1", "User1");
    }
}
