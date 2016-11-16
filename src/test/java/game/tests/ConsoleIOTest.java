package game.tests;

import Classes.ConsoleIO;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assume.assumeTrue;

/**
 * Created by mikehollibaugh on 11/11/16.
 */
public class ConsoleIOTest {

    /* Run this manually to test */

    @Test
    public void can_read_and_write_to_console_run_manually() {
        assumeTrue(false);
    }

    public static void main(String[] args) throws IOException {
        String catchPhrase = "I see this!";
        write_to_console("If you can see this type: " + catchPhrase);
        String lineRead = read_from_console();
        if (lineRead.equals(catchPhrase)) {
            write_to_console("Strings are Equal");
        } else {
            write_to_console("Strings are not Equal!");
        }
    }

    public static void write_to_console(String output) {
        ConsoleIO theConsoleIO = new ConsoleIO();
        theConsoleIO.writeToConsole(output);
    }

    public static String read_from_console() throws IOException {
        ConsoleIO theConsoleIO = new ConsoleIO();
        String inputString = theConsoleIO.readFromConsole();
        return (inputString);
    }


}
