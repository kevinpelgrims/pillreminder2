package com.kevinpelgrims.pillreminder2.helpers;

import java.util.Locale;

public class FormattingHelper {
    private FormattingHelper() {
    }

    public static String formatTime(int hours, int minutes) {
        return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);
    }
}
