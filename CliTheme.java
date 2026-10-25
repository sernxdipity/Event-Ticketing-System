public final class CliTheme {

    // Baby blue and white colours
    private static final String BLUE = "\u001B[38;2;169;217;255m";
    private static final String WHITE = "\u001B[38;2;241;250;255m";
    private static final String MINT = "\u001B[38;2;171;235;212m";
    private static final String PINK = "\u001B[38;2;255;178;195m";
    private static final String RESET = "\u001B[0m";

    private CliTheme() {}

    // Apply terminal colour
    private static String color(String code, String text) {
        if (System.getenv("NO_COLOR") != null) {
            return text;
        }
        return code + text + RESET;
    }

    // Main menu banner
    public static void banner() {

        System.out.println(color(BLUE,
            "\n       .----.    ♪  ♫     .---------."));
        System.out.println(color(BLUE,
            "       |::::|             |  TALK   |"));
        System.out.println(color(BLUE,
            "       |::::|             |  o      |"));
        System.out.println(color(BLUE,
            "       '----'             '---------'"));
        System.out.println(color(BLUE,
            "         ||                   ||"));
        System.out.println(color(BLUE,
            "       __||__               __||__"));
        System.out.println(color(BLUE,
            "      /______\\             /______\\"));

        System.out.println(color(BLUE,
            "\n  ╭──────────────────────────────────╮"));
        System.out.println(color(WHITE,
            "     ♫  EVENT TICKETING SYSTEM  ♫"));
        System.out.println(color(BLUE,
            "  ╰──────────────────────────────────╯"));
    }

    // Section headings
    public static void section(String title) {
        System.out.println(color(BLUE,
            "\n  ✧  " + title.toUpperCase() + "  ✧"));
        System.out.println(color(BLUE,
            "  ──────────────────────────────────"));
    }

    // Menu options
    public static void option(int number, String label) {
        System.out.println(color(WHITE,
            "     [" + number + "]  " + label));
    }

    // Input prompt
    public static void prompt(String message) {
        System.out.print(color(BLUE, "  › " + message));
    }

    // Success message
    public static void success(String message) {
        System.out.println(color(MINT, "  ♡ " + message));
    }

    // Error message
    public static void error(String message) {
        System.out.println(color(PINK, "  ✗ " + message));
    }

    // Concert microphone drawing
    public static void concertArt() {
        System.out.println(color(BLUE,
            "\n       .----."));
        System.out.println(color(BLUE,
            "       |::::|   ♪"));
        System.out.println(color(BLUE,
            "       |::::|"));
        System.out.println(color(BLUE,
            "       '----'"));
        System.out.println(color(BLUE,
            "         ||"));
        System.out.println(color(BLUE,
            "       __||__"));
        System.out.println(color(BLUE,
            "      /______\\"));
    }

    // Conference presentation drawing
    public static void conferenceArt() {
        System.out.println(color(BLUE,
            "\n     .----------."));
        System.out.println(color(BLUE,
            "     |  SPEAKER |"));
        System.out.println(color(BLUE,
            "     |    o     |"));
        System.out.println(color(BLUE,
            "     |   /|\\    |"));
        System.out.println(color(BLUE,
            "     '----------'"));
        System.out.println(color(BLUE,
            "         ||"));
        System.out.println(color(BLUE,
            "       __||__"));
    }
}