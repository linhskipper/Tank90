package com.t3h.model;

import com.t3h.gui.MyFrame;

import java.awt.*;
import java.util.ArrayList;


public abstract class Tank {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    protected int x;
    protected int y;
    protected int orient;
    protected Image[] images;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(images[orient], x, y, null);
    }

    public void changeOrient(int newOrient) {
        orient = newOrient;
    }

    public void move(ArrayList<Map> arrMap) {
        int speed = 1;
        int xRaw = x;
        int yRaw = y;
        switch (orient) {
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
        }
        boolean check = checkMap(arrMap);
        if (check == false) {
            x = xRaw;
            y = yRaw;
        } else if (x < 0) {
            x = 0;
        } else if (y < 0) {
            y = 0;
        } else if (x > MyFrame.W_FRAME - images[orient].getWidth(null)) {
            x = MyFrame.W_FRAME - images[orient].getWidth(null);
        } else if (y > MyFrame.H_FRAME - images[orient].getHeight(null) - 30) {
            y = MyFrame.H_FRAME - images[orient].getHeight(null) - 30;
        }
    }

    public Bullet fire() {
        int x = this.x + images[orient].getWidth(null) / 2;
        int y = this.y + images[orient].getHeight(null) / 2;
        Bullet bullet = new Bullet(x, y, orient);
        return bullet;
    }

    private boolean checkMap(ArrayList<Map> arrMap) {
        for (Map map : arrMap) {
            Rectangle rectangle = getRect().intersection(map.getRect());
            if (!rectangle.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Rectangle getRect() {
        int x = this.x;
        int y = this.y;
        int w = images[orient].getWidth(null);
        int h = images[orient].getHeight(null);
        if (orient == RIGHT||orient == LEFT) {
            h = h - 2;
        } else if (orient == UP||orient == DOWN) {
            w = w - 2;
        }
        Rectangle rectangle = new Rectangle(x, y, w,h);
        return rectangle;
    }

    public boolean checkDie(ArrayList<Bullet> arrBullet){
        for (int i = 0; i < arrBullet.size(); i++) {
            Rectangle rectangle = getRect().intersection(arrBullet.get(i).getRect());
            if (rectangle.isEmpty() ==false){
                arrBullet.remove(i);
                return false;
            }
        }
        return true;
    }
}
