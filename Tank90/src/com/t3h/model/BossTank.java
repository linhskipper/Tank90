package com.t3h.model;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class BossTank extends Tank {
    private int timeFire = 50;
    private Random random = new Random();
    public BossTank(int x, int y) {
        super(x, y);
        orient = DOWN;
        images = new Image[]{
                new ImageIcon(getClass().getResource
                        ("/images/bossyellow_left.png")).getImage(),
                new ImageIcon(getClass().getResource
                        ("/images/bossyellow_right.png")).getImage(),
                new ImageIcon(getClass().getResource
                        ("/images/bossyellow_up.png")).getImage(),
                new ImageIcon(getClass().getResource
                        ("/images/bossyellow_down.png")).getImage()
        };
    }

    public void createOrient(){
        int percent = random.nextInt(101);
        if (percent>95) {
            int newOrient = random.nextInt(4);
            changeOrient(newOrient);
        }
    }

    @Override
    public Bullet fire() {
        timeFire --;
        if (timeFire>0){
            return null;
        }else {
            timeFire = 150;
        }
        return super.fire();
    }
}
