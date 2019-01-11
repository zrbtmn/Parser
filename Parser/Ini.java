package Parser;

import Parser.UI.ParameterPair;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ini {
    private final static Pattern p = Pattern.compile("\\[[a-zA-Z0-9_]+]\\s*;?.*");
    private static List<Section> sectionList;

    Ini(File file){
        try {
            parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: private field should not be taken out
    public List<Section> getSectionList() {
        return Collections.unmodifiableList(sectionList);
    }

    public Section findSection(String sectionName) {
        for (Section section : sectionList) {
            if (section.getSectionName().equals(sectionName))
                return section;
        }
        return null;
    }

    private static List<Section> parse(File file) throws Exception {
        BufferedReader br;
        // TODO: check out try-with-resources
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
            throw ex;
        }
        boolean sectionExist = false;
        String sectionName;
        Section section = null;
        List<ParameterPair> sectionParameters;
        int lineNumber = 0;
        while (true) {
            lineNumber++;
            String line;

            try {
                line = br.readLine();
            } catch (IOException ex) {
                System.err.println("Reader error");
                throw ex;
            }
            if (line == null) {
                break;
            }

            try {
                if (line.startsWith("[") && line.endsWith("]")) {
                    sectionName = readSectionName(line);
                    sectionParameters = new ArrayList<>();
                    section = new Section(sectionName, sectionParameters);
                    sectionList.add(section);
                    sectionExist = true;
                } else if (sectionExist) {
                    readSectionParameters(line, section);
                }
            } catch (Exception ex) {
                String error = String.format("Line %d: ", lineNumber);
                System.out.println(error + ex.getMessage() + "\nSection dumped");
                sectionExist = false;
            }
        }
        return sectionList;
    }

    private static String readSectionName(String line) throws Exception {
        StringBuilder sectionName = new StringBuilder();
        if (line.contains(";")) {
            String[] split = line.split(";");
            return readSectionName(split[0].trim());
        } else {
            //TODO
            Matcher m = p.matcher(line);
            if (!m.matches()) {
                throw new Exception("\nSection name syntax error: " + line);
            }
            if (!line.contains("]"))
                throw new Exception("\nSection name syntax error: " + line);
            String[] args = line.split("");
            for (String arg : args) {
                if (!arg.equals("[") && !arg.equals("]"))
                    sectionName.append(arg);
            }
        }
        if (sectionName.toString().contains(" ")) {
            throw new Exception("\nSection name syntax error: " + line);
        } else return sectionName.toString();
    }

    private static void readSectionParameters(String line, Section section) throws Exception {
        if (line.contains(";")) {
            if (line.charAt(0) == ':') {
                String error = "Section parameters syntax error in section \"" + section.getSectionName() + "\"";
                throw new Exception(error);
            }
            // TODO: consider using second argument (int=2)
            String[] split = line.split(";");
            if (split[0] != null) {
                readSectionParameters(split[0], section);
            }
        } else if (line.length() != 0) {
            String[] args = line.split("\\s*");
            ParameterPair parametersPair;
            if (args[1].equals("=")) {
                parametersPair = new ParameterPair(args[0], args[2]);
                section.getParameters().add(parametersPair);
            } else {
                String error = "Section parameters syntax error in section \"" + section.getSectionName() + "\"";
                throw new Exception(error);
            }
        }
    }
}