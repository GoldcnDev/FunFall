package funfall.managers;

import net.md_5.bungee.api.ChatColor;

public final class ColourUtils {
    private ColourUtils() {
        throw new UnsupportedOperationException();
    }

    public static String colour(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
