package org.example.model.conversation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History {

    private static final DateFormat DFormat = new SimpleDateFormat("MM-yyyy");
    private Date date = new Date();
    private List<Message> messages = new ArrayList<>();

    public History() {

    }
}
