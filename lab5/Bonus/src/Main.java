package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        Main app = new Main();
        // app.testRepo();
        app.commandHandler();


        //excel
        String excelFilePath = "E:\\Facultate\\an2\\sem2\\Java\\lab5\\Bonus5\\Repository\\Abilities.xlsx";

        try (FileInputStream inputStream = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            // read from Excel
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Set<String>> employeeAbilitiesMap = new HashMap<>();

            // skip the header row
            Iterator<Row> iterator = sheet.iterator();
            if (iterator.hasNext()) {
                iterator.next();
            }

            // process data
            while (iterator.hasNext()) {
                Row row = iterator.next();
                Cell nameCell = row.getCell(0);
                Cell abilityCell = row.getCell(1);

                if (nameCell != null && abilityCell != null) {
                    String name = nameCell.getStringCellValue();
                    String abilities = abilityCell.getStringCellValue();

                    // split abilities and add them to sets
                    String[] abilityArray = abilities.split(",");
                    for (String ability : abilityArray) {
                        String trimmedAbility = ability.trim();
                        if (!employeeAbilitiesMap.containsKey(name)) {
                            employeeAbilitiesMap.put(name, new HashSet<>());
                        }
                        employeeAbilitiesMap.get(name).add(trimmedAbility);
                    }
                }
            }

            //grouping
            Grouping grouping = new Grouping();
            List<List<String>> maximalGroups = grouping.findMaximalGroups(employeeAbilitiesMap);

            // maximal groups
            System.out.println("Maximal Groups:");
            for (List<String> group : maximalGroups) {
                // employees abilities
                Set<String> groupAbilities = new HashSet<>();
                for (String employee : group) {
                    groupAbilities.addAll(employeeAbilitiesMap.get(employee));
                }

                System.out.println("\nAbilities: " + groupAbilities + "\nEmployees list: " + group +"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
