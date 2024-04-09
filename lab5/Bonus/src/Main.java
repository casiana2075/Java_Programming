package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        Main app = new Main();
        // app.testRepo();
        app.commandHandler();


        try {
            // Create a random Excel
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Employees");

            //list of amilities
            List<String> abilities = Arrays.asList("secretary", "manager", "tester", "graphic designer", "programming");

            //header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Abilities");

            //fill the sheet with data
            Random rand = new Random();
            for (int i = 1; i <= 100; i++) { // 100 rows
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue("employee" + i);

                // random number  of abilityes
                int numAbilities = rand.nextInt(abilities.size()) + 1;
                List<String> employeeAbilities = new ArrayList<>();
                for (int j = 0; j < numAbilities; j++) {
                    // add a random ability
                    String ability;
                    do {
                        ability = abilities.get(rand.nextInt(abilities.size()));
                    } while (employeeAbilities.contains(ability));
                    employeeAbilities.add(ability);
                }

                // convert to string and set it as the cell value
                row.createCell(1).setCellValue(String.join(", ", employeeAbilities));
            }

            // write the output to a file
            String excelFilePath = "random_employees.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }

            workbook.close();

            // read from excel
            try (FileInputStream inputStream = new FileInputStream(excelFilePath);
                 Workbook workbookRead = new XSSFWorkbook(inputStream)) {

                Sheet sheetRead = workbookRead.getSheetAt(0);
                Map<String, Set<String>> employeeAbilitiesMap = new HashMap<>();

                // skip the header row
                Iterator<Row> iterator = sheetRead.iterator();
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
                        String ability = abilityCell.getStringCellValue();

                        // add abilities to sets
                        if (!employeeAbilitiesMap.containsKey(name)) {
                            employeeAbilitiesMap.put(name, new HashSet<>());
                        }
                        employeeAbilitiesMap.get(name).add(ability);
                    }
                }

                // grouping
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
