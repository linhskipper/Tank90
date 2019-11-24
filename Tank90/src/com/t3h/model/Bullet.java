package com.t3h.model;

import com.t3h.gui.MyFrame;

import javax.swing.*;
import java.awt.*;


public class Bullet {
    private int x;
    private int y;
    private Image img = new ImageIcon(getClass()
            .getResource("/images/bullet.png")).getImage();
    private int orient;

    public Bullet(int x, int y, int orient) {
        this.x = x - img.getWidth(null) / 2;
        this.y = y - img.getHeight(null) / 2;
        this.orient = orient;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);
    }

    public boolean move() {
        int speed = 2;
        switch (orient) {
            case Tank.LEFT:
                x -= speed;
                break;
            case Tank.RIGHT:
                x += speed;
                break;
            case Tank.UP:
                y -= speed;
                break;
            case Tank.DOWN:
                y += speed;
                break;
        }
        if (x <= 0
                || y <= 0
                || x >= MyFrame.W_FRAME - img.getWidth(null)
                || y >= MyFrame.H_FRAME - img.getHeight(null) - 30) {
            return true;
        }
        return false;
    }

    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
        return rectangle;
    }
}
