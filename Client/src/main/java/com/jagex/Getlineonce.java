package com.jagex;

public class Getlineonce {
    static int[] linesRun = new int[0];
    static String[] filesRun = new String[0];

    public Getlineonce(boolean repeat) {
        boolean lineRan = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        //Check if this line as been taking, so as not to repeat with loops or recursion
        int line = stackTrace[2].getLineNumber();
        String fileName = stackTrace[2].getFileName();
        for(int i = 0; i < linesRun.length; i++) {
            if(line == linesRun[i] && fileName.equalsIgnoreCase(filesRun[i])) {
                if(!repeat) {
                    lineRan = true; //Dont print
                }
            }
        }

        //Add the file to already taken lines, if it hasnt run then print
        if(!lineRan) {
            add(line, fileName);
            System.out.println(fileName + ": line " + line + " ran!");
        }

    }

    public void add(int line, String file) {
        int newSize = linesRun.length+1;
        int[] linesRunBuff = new int[newSize];
        String[] filesRunBuff = new String[newSize];

        for(int i = 0; i < newSize-1; i++) {
            linesRunBuff[i] = linesRun[i];
            filesRunBuff[i] = filesRun[i];
        }
        linesRunBuff[newSize-1] = line;
        filesRunBuff[newSize-1] = file;

        linesRun = linesRunBuff;
        filesRun = filesRunBuff;
    }

}
