package com.example.java_threads_2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class HelloController {

    public TextField max_value;
    public TextArea output;

    public void startCounting(MouseEvent mouseEvent)
    {
        output.setText(""); // Will clear the textArea each time the button "Start counting" is clicked.
        Counter counter = new Counter(Integer.parseInt(max_value.getText().replace(".", "")));
        counter.setOutput(new Output() {    // Interface
            @Override
            public void append(String message) {
                Platform.runLater(new Runnable() {  // Call from a thread
                    @Override
                    public void run() {
                        output.setText(output.getText() + message);
                    }
                });
            }
        });
        counter.startIncrementing();
    }
}