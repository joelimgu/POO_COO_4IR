package Controller;

import Model.ModelExample;
import View.ViewExample;

import javax.swing.text.View;

public class ControllerExample {

    ModelExample me;
    ViewExample ve;

    public ControllerExample() {
        me = new ModelExample();
        ve = new ViewExample();
    }

    public ControllerExample(ModelExample m, ViewExample v) {
        me = m;
        ve = v;
        ve.setTextToView(me.getValue());
    }

    public void setTextView(String s) {
        me.setValue(s);
        ve.setTextToView(me.getValue());
    }

    public void printView() {
        ve.printView();
    }

    public static void main(String[] args) {
        ControllerExample ce = new ControllerExample();
        //ce.setTextView("Teest");
        ce.printView();
    }


}
