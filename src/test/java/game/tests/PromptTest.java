package game.tests;

import Classes.ConsoleIO;
import Classes.Dependencies;
import Classes.Prompt;
import navis.injection.Resettable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by mikehollibaugh on 11/11/16.
 */
public class PromptTest {

    protected ConsoleIO mockedConsoleIO = null;
    public final String theRegex = "[abc]";
    public final String promptString = "input a letter a, b or c:";
    public final String defaultInput = "x";
    public final String theInputString = "a";

    public Resettable withMockConsoleIO() {
        mockedConsoleIO = mock(ConsoleIO.class);
        return Dependencies.console.override(() -> mockedConsoleIO);
    }

    @Before
    public void setup() {
        withMockConsoleIO();
    }

    @After
    public void tearDown() throws Exception {
        Dependencies.console.close();
    }

    @Test
    public void input_with_white_space_is_trimmed() throws IOException {
        Prompt thePrompt = Dependencies.prompt.make();
        when(mockedConsoleIO.readFromConsole()).thenReturn(theInputString + "    ");

        String input = thePrompt.prompt(promptString, theRegex, defaultInput);

        assertEquals(input, theInputString);
    }

    @Test
    public void input_with_all_white_space_returns_default_response() throws IOException {
        Prompt prompt = Dependencies.prompt.make();
        when(mockedConsoleIO.readFromConsole()).thenReturn("    ");

        String theInput = prompt.prompt(promptString, theRegex, defaultInput);

        assertEquals(theInput, defaultInput);
    }

    @Test
    public void string_is_written_to_console() throws IOException {
        Prompt prompt = Dependencies.prompt.make();
        when(mockedConsoleIO.readFromConsole()).thenReturn(theInputString);

        prompt.prompt(promptString, theRegex, defaultInput);

        verify(mockedConsoleIO).writeToConsole(promptString);
    }

    @Test
    public void returns_a_character_if_it_is_in_the_allowed_characters() throws IOException {
        when(mockedConsoleIO.readFromConsole()).thenReturn("a");
        Prompt prompt = Dependencies.prompt.make();

        String inputString = prompt.prompt(promptString, theRegex, defaultInput);

        assertTrue(inputString.equals(theInputString));
    }

    @Test
    public void returns_default_character_if_nothing_is_entered() throws IOException {
        when(mockedConsoleIO.readFromConsole()).thenReturn("");
        Prompt prompt = Dependencies.prompt.make();

        String inputString = prompt.prompt(promptString, theRegex, defaultInput);

        assertTrue(inputString.equals(defaultInput));
    }

    @Test
    public void repeats_prompt_until_valid_charcter_is_entered() throws IOException {
        when(mockedConsoleIO.readFromConsole()).thenReturn("z", "x", "a");
        Prompt prompt = Dependencies.prompt.make();

        prompt.prompt(promptString, theRegex, defaultInput);

        verify(mockedConsoleIO, times(3)).readFromConsole();
    }

    @Test
    public void returns_default_character_on_io_exception() {
        try {
            when(mockedConsoleIO.readFromConsole()).thenThrow(new IOException());
        } catch (IOException e) {
        }
        Prompt prompt = Dependencies.prompt.make();

        String inputString = prompt.prompt(promptString, theRegex, defaultInput);

        assertTrue(inputString.equals(defaultInput));
    }

}
