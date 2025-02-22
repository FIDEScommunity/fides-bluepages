package community.fides.bluepages.backend.service.oidvcdisplay;


import community.fides.bluepages.backend.service.oidvcdisplay.dto.DisplayProperties;
import java.util.List;
import java.util.Optional;

public class LocaleDisplayFilter {

    public static DisplayProperties getByLocale(List<DisplayProperties> displays, String locale) {
        if (displays == null) {
            return new DisplayProperties();
        }
        if (locale == null) {
            return getDefaultDisplay(displays);
        }
        final Optional<DisplayProperties> displayByFullLocale = displays.stream()
                .filter(display -> locale.equalsIgnoreCase(display.getLocale()))
                .findFirst();
        if (displayByFullLocale.isPresent()) {
            return displayByFullLocale.get();
        }
        final Optional<DisplayProperties> displayByPartLocale = displays.stream()
                .filter(display -> firstTwoCharsEqual(locale, display.getLocale()))
                .findFirst();
        if (displayByPartLocale.isPresent()) {
            return displayByPartLocale.get();
        }
        return getDefaultDisplay(displays);
    }

    private static boolean firstTwoCharsEqual(final String locale1, final String locale2) {
        if (locale1 == null || locale2 == null) {
            return false;
        }
        return locale1.substring(0, 2).equalsIgnoreCase(locale2.substring(0, 2));
    }

    private static DisplayProperties getDefaultDisplay(final List<DisplayProperties> displays) {
        if (displays == null || displays.isEmpty()) {
            return new DisplayProperties();
        }
        return displays.stream()
                .filter(display -> display.getLocale() == null)
                .findFirst()
                .orElse(displays.getFirst());
    }
}
