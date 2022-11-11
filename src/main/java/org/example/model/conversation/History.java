package org.example.model.conversation;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class History implements Comparable<History> {

    private static final DateFormat DFormat = new SimpleDateFormat("MM-yyyy");
    private Date date = new Date();
    private List<Message> messages = new ArrayList<>();

    public History() {

    }

    public History(@NotNull Date d, @NotNull ArrayList<Message> mes) {
        this.date = d;
        this.messages = mes;
    }

    public History(@NotNull String date, @NotNull ArrayList<Message> mes) throws ParseException {
        this.date = DFormat.parse(date);
        this.messages = mes;
    }

    public Date getDate() {
        return date;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getFormattedDate() {
        return DFormat.format(this.date);
    }

    @Override
    public String toString() {
        return "History{" +
                "date=" + date +
                ", messages=" + messages +
                '}';
    }


    @Override
    public int compareTo(@NotNull History history) {
        return this.date.compareTo(history.date);
    }
}
