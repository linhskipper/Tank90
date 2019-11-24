package com.t3h.model;

import javax.swing.*;
import java.awt.*;


public class MyTank extends Tank {

    public MyTank(int x, int y) {
        super(x, y);
        orient = UP;
        images = new Image[]{
                new ImageIcon(getClass().getResource
                        ("/images/player_green_left.png")).getImage(),
                new ImageIcon(getClass().getResource
                        ("/images/player_green_right.png")).getImage(),
                new ImageIcon(getClass().getResource
                        ("/images/player_green_up.png")).getImage(),
                new ImageIcon(getClass().getResource
                        ("/images/player_green_down.png")).getImage()
        };
    }
}
