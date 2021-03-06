package es.unican.istr.rtgen.system.config.localization;

/**
 * Created by juanm on 17/08/2015.
 */
public enum LocalizationOptions {
    RANDOM(0), AVOID_CONSECUTIVE(1), AVOID_REPETITION(2);

    private final int value;

    LocalizationOptions(int i) {
        value = i;
    }

    public int getValue() { return value; }
}
