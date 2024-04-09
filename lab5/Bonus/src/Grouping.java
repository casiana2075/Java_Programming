package org.example;

import java.util.*;

    public class Grouping {
        public List<List<String>> findMaximalGroups(Map<String, Set<String>> personAbilitiesMap) {
            List<List<String>> maximalGroups = new ArrayList<>();

            // Track processed persons
            Set<String> processedPersons = new HashSet<>();

            // Iterate through each person and their abilities
            for (Map.Entry<String, Set<String>> entry : personAbilitiesMap.entrySet()) {
                String person = entry.getKey();
                Set<String> abilities = entry.getValue();

                // If the person is already processed, continue to the next person
                if (processedPersons.contains(person)) {
                    continue;
                }

                // Create a new group with the current person
                List<String> group = new ArrayList<>();
                group.add(person);

                // Add the current person to the list of processed persons
                processedPersons.add(person);

                // Iterate through remaining persons to find common abilities
                for (Map.Entry<String, Set<String>> otherEntry : personAbilitiesMap.entrySet()) {
                    String otherPerson = otherEntry.getKey();
                    Set<String> otherAbilities = otherEntry.getValue();

                    // Skip if the person is the same as the current person
                    if (person.equals(otherPerson)) {
                        continue;
                    }

                    // Check if there are common abilities between the current person and other persons
                    boolean hasCommonAbilities = abilities.stream().anyMatch(otherAbilities::contains);

                    // If there are common abilities, add the person to the group and mark as processed
                    if (hasCommonAbilities) {
                        group.add(otherPerson);
                        processedPersons.add(otherPerson);
                    }
                }

                // Add the group to maximalGroups
                maximalGroups.add(group);
            }

            return maximalGroups;
        }
    }
