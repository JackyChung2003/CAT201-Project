//package com.group_22.timetablemanagement;
//
//import javafx.scene.control.TableCell;
//import javafx.scene.paint.Color;
//
//import java.text.NumberFormat;
//
//public class PercantageFormatCell extends TableCell<Object, Double> {
//
//    public PercantageFormatCell() {
//    }
//
//    @Override
//    protected void updateItem(Double item, boolean empty) {
//        super.updateItem(item, empty);
//
//        // If the row is not empty but the Double-value is null,
//        // we will always display 0%
//        if (!empty && null == item) {
//            item = new Double(0.0d);
//        }
//
//        // Here we set the displayed text to anything we want without changing the
//        // real value behind it. We could also have used switch case or anything you
//        // like.
//        setText(item == null ? "" : NumberFormat.getPercentInstance().format(item));
//
//        // If the cell is selected, the text will always be white
//        // (so that it can be read against the blue background),
//        // if the value is 1 it will be green.
//        if (item != null) {
//            double value = item.doubleValue();
//            if (isFocused() || isSelected() || isPressed()) {
//                setTextFill(Color.WHITE);
//            } else if (value < 1) {
//                setTextFill(Color.BLACK);
//            } else {
//                setTextFill(Color.GREEN);
//            }
//        }
//    }
//}

package com.group_22.timetablemanagement;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

import java.text.NumberFormat;

public class PercantageFormatCell extends TableCell<SchoolDay, String> {

    public PercantageFormatCell() {
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        // If the row is not empty but the String-value is null,
        // we will always display an empty string
        if (!empty && item == null) {
            item = "";
        }

        // Here you can set the displayed text to anything you want based on the String value

        setText(item == null ? "" : formatString(item));

        // Customize the cell based on your requirements
        // For example, you can change text color based on certain conditions
        if (item != null) {
            // Your custom formatting logic here
            // Example: Change text color based on some condition
            if (isFocused() || isSelected() || isPressed()) {
                setTextFill(Color.WHITE);
            } else {
                setTextFill(Color.BLACK);
            }
        }
    }

    private String formatString(String input) {
        // Add your custom formatting logic for the String value
        // Example: format the String as a percentage
        return "Formatted: " + input;
    }
}
