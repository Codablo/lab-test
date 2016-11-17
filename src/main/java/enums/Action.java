package enums;

/**
 * Created by mikehollibaugh on 11/16/16.
 */
public enum Action {
    Stay ("s"), Hit("h"), Busted("");

    public final String text;

    private Action(final String text) {
        this.text = text;
    }

}

