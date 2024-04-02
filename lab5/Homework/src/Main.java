package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        Main app = new Main();
        // app.testRepo();
        app.commandHandler();            
    }

    // private void testRepo() {

    //     String masterDirectory = new String("C:\\Users\\alinm\\Desktop\\java_course_fii_2024\\lab5\\homework");

    //     var repo = new Repository(masterDirectory);
    //     var service = new RepositoryService();
    //     System.out.println("Contents of '" + masterDirectory + "':");
    //     service.print(repo);

    //     try {
    //         service.export(repo, "C:\\Users\\alinm\\Desktop\\java_course_fii_2024\\lab5\\homework\\repository.json");
    //     } catch (Exception ex) {
    //         System.err.println("Error at exporting the repository" + ex);
    //     }

    //     var doc = repo.findDocument(4);
    //     // service.view(doc.get());
    //     // service.generateTemplate(repo);
    // }

    private void commandHandler() {
        String masterDirectory = "E:\\Facultate\\an2\\sem2\\Java\\lab5\\Homework5\\Repository";
        var repo = new Repository(masterDirectory);
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            System.out.print("\nEnter your command: ");
            String commandLine = scanner.nextLine().trim();
    
            // Split the command line into command and arguments
            String[] parts = commandLine.split(" ");
            String command = parts[0];
    
            try {
                switch (command) {
                    case "view":
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Usage: view <documentId>");
                        }
                        int documentId = Integer.parseInt(parts[1]);
                        ShellCommand viewCommand = new ViewCommand(repo,documentId);
                        viewCommand.runCommand();
                        break;
    
                    case "report":
                        ShellCommand reportCommand = new ReportCommand(repo);
                        reportCommand.runCommand();
                        break;
    
                    case "export":
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Usage: export <outputFilePath.json>");
                        }
                        String outputFilePath = parts[1];
                        ShellCommand exportCommand = new ExportCommand(repo, outputFilePath);
                        exportCommand.runCommand();
                        break;
    
                    case "exit":
                        System.out.println("Exiting application...");
                        scanner.close();
                        return;
    
                    default:
                        System.out.println("Invalid command. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
}
