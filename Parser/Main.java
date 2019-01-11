package Parser;

import java.io.File;
import Parser.UI.UI;

public class Main {

    // ini4j

    public static void main(String[] args) {
        File file = new File("src/Parser/Config.ini");
        Ini ini = null;
        try {
            ini = new Ini(file);
        } catch (Exception e) {
            System.err.println("Could not parse file: " + file.getName());
            e.printStackTrace();
        }
        UI ui = new UI(ini);

        ui.menu();
    }

}