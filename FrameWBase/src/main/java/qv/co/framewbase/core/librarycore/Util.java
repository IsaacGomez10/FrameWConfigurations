package qv.co.framewbase.core.librarycore;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {
    private static LocalDate today = LocalDate.now();

    public Util (int seconds){
        waitSeconds(seconds);
    }
    public static void waitSeconds(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }


    public static void waitMilliseconds(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public static int dayOfMonth() {
        return LocalDate.now().getDayOfMonth();
    }

    public static String dayOfWeek() {
        return String.valueOf(LocalDate.now().getDayOfWeek());
    }

    public static String currentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter);
    }
    public static String nowDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
        return today.format(formatter);
    }

    public static String currentTime(){
        Date current_date = new Date();
        DateFormat local_date_format = new SimpleDateFormat("HH_mm_ss");
        return local_date_format.format(current_date);
    }

    public static int dayOfMonthPlusDays(int days){
        return today.plusDays(days).getDayOfMonth();
    }

    public static String cleanTextFromFile(String text){
        return text.replace("\\","").replaceAll("[?|<>*:/\"]","");
    }

    public static boolean directoryExist(String path_directory){
        File directory = new File(path_directory);
        boolean exist = false;

        if (directory.exists())
            exist = directory.isDirectory();

        return exist;
    }
}
