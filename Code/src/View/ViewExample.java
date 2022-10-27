package View;

import javax.swing.*;
import javax.swing.text.View;

public class ViewExample extends JFrame {

    JTextArea jt = new JTextArea();

    public ViewExample() {
        jt.setText("Model not found");
        this.add(jt);
        this.pack();
    }

    public ViewExample(String s) {
        jt.setText(s);
        this.pack();
    }

    public void setTextToView(String s) {
        jt.setText(s);
        this.pack();
    }

    public void printView() {
        this.setVisible(true);
    }

}
