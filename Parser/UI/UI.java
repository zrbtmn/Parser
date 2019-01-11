package Parser.UI;

import Parser.*;

import java.util.List;
import java.util.Scanner;

public class UI {

    private Ini ini;

    public UI(Ini ini) {
        this.ini = ini;
    }

    public void menu() {
        while (true) {
            System.out.print("\nMENU\n");
            System.out.print("\n\t1) Print file");
            System.out.print("\n\t2) Get classified parameter value");
            System.out.print("\n\t*Type \"exit\" to quit");
            System.out.print("\n>> ");
            final String printFile = "1";
            final String classifiedValue = "2";
            final String exit = "exit";

            Scanner in = new Scanner(System.in);
            String answer;
            answer = in.nextLine();
            System.out.println();
            switch (answer) {
                case printFile:
                    printAllSections();
                    continue;
                case classifiedValue:
                    System.out.print("\nType section name: \n>> ");
                    answer = in.nextLine();
                    Section section = ini.findSection(answer);
                    if (section != null) {
                        System.out.print("\nWhat would you like to do?\n");
                        System.out.print("\n1) Print section parameters");
                        System.out.print("\n2) Get exact value");
                        System.out.print("\n*Type \"exit\" to quit \n>> ");
                        answer = in.nextLine();
                        final String sectionParameters = "1";
                        final String exactValue = "2";
                        switch (answer) {
                            case sectionParameters:
                                System.out.println("\n[" + section.getSectionName() + "]");
                                printSections(section);
                            case exactValue:
                                System.out.print("\nType parameter name: \n>> ");
                                answer = in.nextLine();
                                printRequiredValue(section, answer);
                                break;
                            case exit:
                                break;
                        }
                    } else System.out.println("\nSECTION NOT FOUND\n");
                    continue;
                case exit:
                    System.exit(0);
                default:
                    System.out.println("Try again");
            }
        }
    }

    private void printAllSections() {
        for (Section section : ini.getSectionList()) {
            System.out.println("[" + section.getSectionName() + "]");
            printSections(section);
        }
    }

    private void printSections(Section section) {
        List<ParameterPair> parameterList = section.getParameters();
        for (ParameterPair parameterPair : parameterList) {
            System.out.printf("\n%s = %s", parameterPair.getKey(), parameterPair.getValue());
        }
        System.out.println("\n");
    }

    private void printRequiredValue(Section section, String key) {
        List<ParameterPair> parameterList = section.getParameters();
        ParameterPair answer = null;
        for (ParameterPair parameterPair : parameterList) {
            if (parameterPair.getKey().equals(key)) {
                answer = parameterPair;
            }
        }
        if (answer == null) {
            System.out.println("There is no value with this parameter.\n");
        } else printOptions(answer);
    }

    private void printOptions(ParameterPair requiredPair) {
        while (true) {
            System.out.println("\nWhat would you like to get?");
            System.out.print("\n\t1) Integer");
            System.out.print("\n\t2) Double");
            System.out.print("\n\t3) Float");
            System.out.print("\n\t4) String\n");
            System.out.print("\n\t*Type \"exit\" to quit\n>> ");

            Scanner writer = new Scanner(System.in);

            String answer = writer.nextLine();
            final String getInteger = "1";
            final String getDouble = "2";
            final String getFloat = "3";
            final String getString = "4";
            final String exit = "exit";
            boolean stop = false;
            try {
                switch (answer) {
                    case getInteger:
                        System.out.printf("\n|    %s = %d    |\n\n", requiredPair.getKey(), requiredPair.getClassifiedValue(Integer.class));
                        break;
                    case getDouble:
                        System.out.printf("\n|    %s = %f    |\n\n", requiredPair.getKey(), requiredPair.getClassifiedValue(Double.class));
                        break;
                    case getFloat:
                        System.out.printf("\n|    %s = %f    |\n\n", requiredPair.getKey(), requiredPair.getClassifiedValue(Float.class));
                        break;
                    case getString:
                        System.out.printf("\n|    %s = %s    |\n\n", requiredPair.getKey(), requiredPair.getValue());
                        break;
                    case exit:
                        stop = true;
                        break;
                    default:
                        System.out.println("There is no such number. Try again.\n");
                        break;
                }
            } catch (InstantiationException ex) {
                System.out.println("Error occurred: " + ex.getMessage());
            }

            if (stop) break;
        }
    }
}