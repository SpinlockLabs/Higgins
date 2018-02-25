package sh.spinlock.higgins.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OSUtilTest {
    private String osName;

    @BeforeEach
    void setUp() {
        osName = System.getProperty("os.name");
    }

    @Test
    void testIsWindows() {
        assertEquals(osName.startsWith("Windows"), OSUtil.isWindows);
    }

    @Test
    void testIsMacOS() {
        assertEquals(osName.startsWith("Mac OS X"), OSUtil.isMacOS);
    }

    @Test
    void testIsLinux() {
        assertEquals(osName.startsWith("Linux"), OSUtil.isLinux);
    }

    @Test
    void testIsFreeBSD() {
        assertEquals(osName.startsWith("FreeBSD"), OSUtil.isFreeBSD);
    }

    @Test
    void testIsSunOS() {
        assertEquals(osName.startsWith("SunOS"), OSUtil.isSunOS);
    }

    @Test
    void testIsUnix() {
        boolean isMacOS = osName.startsWith("Mac OS X");
        boolean isLinux = osName.startsWith("Linux");
        boolean isFreeBSD = osName.startsWith("FreeBSD");
        boolean isSunOS = osName.startsWith("SunOS");
        assertEquals(isMacOS || isLinux || isFreeBSD || isSunOS, OSUtil.isUnix);
    }
}