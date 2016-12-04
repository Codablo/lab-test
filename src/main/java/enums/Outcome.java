package enums;

/**
 * Created by mikehollibaugh on 11/17/16.
 */
public enum Outcome {
    Push ("Push"), Human("Human"), Bot("Dealer"), None("None");

    public final String text;

    private Outcome(final String text) {
        this.text = text;
    }

}
