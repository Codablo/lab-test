package Classes;

import Interfaces.ConsoleI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by mikehollibaugh on 11/8/16.
 */


public class ConsoleIO implements ConsoleI {
    public String readString;

    @Override
    public void writeToConsole(String theOutput) {
        System.out.println(theOutput);
    }

    @Override
    public String readFromConsole() throws IOException {
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        line = br.readLine();
        return (line);
    }
}

