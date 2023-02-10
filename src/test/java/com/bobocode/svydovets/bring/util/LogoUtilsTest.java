package com.bobocode.svydovets.bring.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoUtilsTest {

    @Test
    public void testGetBringLogo() {
        String expected = "\n\r,-----.        ,--.                         ,---.                      ,--.                        ,--.          \n" +
                "|  |) /_,--.--.`--',--,--,  ,---.  ,-----. '   .-',--.  ,--.,--. ,--.,-|  | ,---.,--.  ,--.,---. ,-'  '-. ,---.  \n" +
                "|  .-.  \\  .--',--.|      \\| .-. | '-----' `.  `-. \\  `'  /  \\  '  /' .-. || .-. |\\  `'  /| .-. :'-.  .-'(  .-'  \n" +
                "|  '--' /  |   |  ||  ||  |' '-' '         .-'    | \\    /    \\   ' \\ `-' |' '-' ' \\    / \\   --.  |  |  .-'  `)  \n" +
                "`------'`--'   `--'`--''--'.`-  /          `-----'   `--'   .-'  /   `---'  `---'   `--'   `----'  `--'  `----'  \n" +
                "                           `---'                            `---'                                                \n";
        assertEquals(expected, LogoUtils.getBringLogo());
    }
}