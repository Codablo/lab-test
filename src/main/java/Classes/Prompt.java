package Classes;

import Interfaces.PromptI;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by mikehollibaugh on 11/11/16.
 */
public class Prompt implements PromptI {
    @Override
    public String prompt(String question, String legalResponsePattern, String defaultReturnValue) {
        ConsoleIO io = Dependencies.console.make();
        String resp;
        Boolean patternMatch = false;
        do{
            try {
                io.writeToConsole(question);
                resp = io.readFromConsole().trim();
                if (resp.length() == 0) return(defaultReturnValue);
            } catch (IOException e) {
                e.printStackTrace();
                return (defaultReturnValue);
            }
        } while (!Pattern.matches(legalResponsePattern,resp));
        return (resp);
    }
}
