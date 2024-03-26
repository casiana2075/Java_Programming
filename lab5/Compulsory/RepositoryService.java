package org.example;

import java.io.File;
import java.util.Arrays;

public class RepositoryService {

    public void print(Repository repo) {
        File dir = new File(repo.getDirectory());
        File[] files = dir.listFiles();
        int cnt = 0;
        System.out.println("In this repo you have: ");
        for (File file : files){
            cnt++;
            System.out.printf("%s). %s\n", cnt, file.getName());
        }
    }

}
