package org.example.services.LogFormatter;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class HtmlFormatter extends Formatter {
    public String format(LogRecord record) {
        StringBuffer s = new StringBuffer(1000);
        Date d = new Date(record.getMillis());
        DateFormat df = DateFormat.getDateTimeInstance(
                DateFormat.LONG, DateFormat.MEDIUM, Locale.FRANCE);
        s.append("<tr><td>" + df.format(d) + "</td>");
        s.append("<td><span  style=\"font-family:"+
                " Courier New,Courier,monospace; color: rgb(204, 0, 0);\">"+
                "<b>"+formatMessage(record) +
                "</b></span></td></tr>\n");
        return s.toString();
    }
    // début du fichier de log
    public String getHead(Handler h) {
        return "<html>\n<body>\n<table>\n";
    }
    // fin du fichier de log
    public String getTail(Handler h) {
        return "</table>\n</body>\n</html>\n";
    }
}
