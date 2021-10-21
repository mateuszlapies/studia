package pl.edu.pk.lab1.helpers;

import java.io.PrintWriter;

public class Html {
    public static void PrintHead(PrintWriter writer, String title) {
        writer.write(String.format("<html><head><title>%s</title><style>th, td {width: 15rem}</style></head><body>", title));
    }

    public static void PrintFoot(PrintWriter writer) {
        writer.write("</body><foot style='padding-top: 1rem; text-align: center'><form method='post' action='/LoginServlet'><input type='submit' value='Wyloguj'/></form></foot></html>");
        writer.flush();
    }
}
