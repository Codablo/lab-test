package Interfaces;

import java.io.IOException;

/**
 * Created by mikehollibaugh on 11/8/16.
 */
public interface ConsoleI {
    void writeToConsole(String output);

    String readFromConsole() throws IOException;
}
