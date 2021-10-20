package pl.edu.pk.lab1.helpers;

import java.io.PrintWriter;

public class Html {
    public static void PrintHead(PrintWriter writer, String title) {
        writer.write(String.format("<html><head><title>%s</title></head><body>", title));
    }

    public static void PrintFoot(PrintWriter writer) {
        writer.write("</body></html>");
        writer.flush();
    }
}
