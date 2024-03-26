package org.example;

import javax.swing.text.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    private String directory;
    private Map<Person, List<Document>> documents = new HashMap<>();

    public Repository(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

   /* public Object findDocument(String s) {
    }*/
}
