package com.bobocode.svydovets.annotation.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogoUtilsTest {

    @Test
    void testGetBringLogo() {
        String expected = "\n\r,-----.        ,--.                         ,---.                      ,--.                        ,--.          \n" +
                "|  |) /_,--.--.`--',--,--,  ,---.  ,-----. '   .-',--.  ,--.,--. ,--.,-|  | ,---.,--.  ,--.,---. ,-'  '-. ,---.  \n" +
                "|  .-.  \\  .--',--.|      \\| .-. | '-----' `.  `-. \\  `'  /  \\  '  /' .-. || .-. |\\  `'  /| .-. :'-.  .-'(  .-'  \n" +
                "|  '--' /  |   |  ||  ||  |' '-' '         .-'    | \\    /    \\   ' \\ `-' |' '-' ' \\    / \\   --.  |  |  .-'  `)  \n" +
                "`------'`--'   `--'`--''--'.`-  /          `-----'   `--'   .-'  /   `---'  `---'   `--'   `----'  `--'  `----'  \n" +
                "                           `---'                            `---'                                                \n";
        assertEquals(expected, LogoUtils.getBringLogo());
    }
}