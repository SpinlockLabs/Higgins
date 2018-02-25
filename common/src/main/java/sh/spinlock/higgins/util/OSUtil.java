package sh.spinlock.higgins.util;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OSUtil {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    /**
     * Name: Windows
     * Class: Windows
     */
    public static boolean isWindows = matchesName("Windows");

    /**
     * Name: macOS
     * Class: Unix
     */
    public static boolean isMacOS = matchesName("Mac OS X");

    /**
     * Name: Linux
     * Class: Unix
     */
    public static boolean isLinux = matchesName("Linux");

    /**
     * Name: FreeBSD
     * Class: Unix
     */
    public static boolean isFreeBSD = matchesName("FreeBSD");

    /**
     * Name: SunOS
     * Class: Unix
     */
    public static boolean isSunOS = matchesName("SunOS");

    /**
     * True if any of the above Unix systems equate to true.
     */
    public static boolean isUnix = isMacOS || isLinux || isFreeBSD || isSunOS;

    private static boolean matchesName(String prefix) {
        return OS_NAME.startsWith(prefix.toLowerCase());
    }
}
