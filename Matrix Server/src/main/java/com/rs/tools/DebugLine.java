package com.rs.tools;

/**
 * A debugging tool. Prints the line that ran. Put true in the argument to make it repeat, false to run once
 */
public class DebugLine {
    static int[] linesRecord = new int[0];
    static String[] filesRecord = new String[0];

    public DebugLine(boolean repeat) {
        boolean lineRan = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        //Check if this line as been taking, so as not to repeat with loops or recursion
        int line = stackTrace[2].getLineNumber();
        String fileName = stackTrace[2].getFileName();
        for(int i = 0; i < linesRecord.length; i++) {
            if(line == linesRecord[i] && fileName.equalsIgnoreCase(filesRecord[i])) {
                if(!repeat) {
                    lineRan = true; //Dont print
                }
            }
        }

        //Add the file to already taken lines, if it hasnt run then print
        if(!lineRan) {
            recordLine(line, fileName);
            System.out.println(fileName + ": line " + line + " ran!");
        }

    }

    private void recordLine(int line, String file) {
        int newSize = linesRecord.length+1;
        int[] previousLines = new int[newSize];
        String[] previousFiles = new String[newSize];

        for(int element = 0; element < newSize-1; element++) {
            previousLines[element] = linesRecord[element];
            previousFiles[element] = filesRecord[element];
        }
        previousLines[newSize-1] = line;
        previousFiles[newSize-1] = file;

        linesRecord = previousLines;
        filesRecord = previousFiles;
    }

    public static void print(String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int line = stackTrace[2].getLineNumber();
        String fileName = stackTrace[2].getFileName();
        System.out.println(fileName + ", line " + line + " - " + message);
    }
}
