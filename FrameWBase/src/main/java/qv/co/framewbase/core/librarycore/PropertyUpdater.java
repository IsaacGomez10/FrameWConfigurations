package qv.co.framewbase.core.librarycore;

import qv.co.framewbase.core.librarycore.evidence.Reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PropertyUpdater {
    public static void addProperty(String key, String value) {
        File serenityPropertiesFile = new File("serenity.properties");
        try {
            if (!serenityPropertiesFile.exists())
                serenityPropertiesFile.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(serenityPropertiesFile, true))) {
                writer.write("\n" + key + "=" + value);
                writer.newLine();
            }
        } catch (IOException e) {
            Reports.reportEvent(Reports.FAILED, "No se logro actualizar el serenity.properties, porfavor verifique.");
            throw new IllegalArgumentException("No se logro actualizar el serenity.properties, porfavor verifique.");
        }

    }
}