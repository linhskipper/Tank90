package com.t3h.gui;

import javax.swing.*;
import java.awt.*;


public class MyFrame extends JFrame{
    public static final int W_FRAME = 500;
    public static final int H_FRAME = 523;
    private MyPanel myPanel;
    public MyFrame(){
        setTitle("Buoi 10");
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myPanel = new MyPanel();
        add(myPanel);
        setVisible(true);
    }
}
