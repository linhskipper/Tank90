package com.t3h.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Map {
    private int x;
    private int y;
    private int bit;
    private Image[] images = new Image[]{
            new ImageIcon(getClass().getResource
                    ("/images/brick.png")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/images/rock.png")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/images/bird.png")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/images/tree.png")).getImage(),
            new ImageIcon(getClass().getResource
                    ("/images/water.png")).getImage()
    };

    public Map(int x, int y, int bit) {
        this.x = x;
        this.y = y;
        this.bit = bit;
    }

    public void draw(Graphics2D g2d) {
        if (bit > 0) {
            if (bit == 3) {
                g2d.drawImage(images[bit - 1], x, y, 38, 38, null);
            } else {
                g2d.drawImage(images[bit - 1], x, y, null);
            }
        }
    }

    public Rectangle getRect() {
        Rectangle rectangle;
        if (bit == 0 || bit == 4 || bit == 5) {
            rectangle = new Rectangle();
        } else if (bit == 3) {
            rectangle = new Rectangle(x, y, 38, 38);
        } else {
            rectangle = new Rectangle(x, y, images[bit - 1].getWidth(null), images[bit - 1].getHeight(null));
        }
        return rectangle;
    }

    public boolean checkBullet(ArrayList<Bullet> arrBulletBoss
            , ArrayList<Bullet> arrBulletPlayer) {
        for (int i = 0; i < arrBulletBoss.size(); i++) {
            Rectangle rectangle = getRect().intersection(arrBulletBoss.get(i).getRect());
            if (rectangle.isEmpty() == false){
                arrBulletBoss.remove(i);
                if (bit == 3){
                    return false;
                }
                if (bit != 2){
                    bit = 0;
                }
                return true;
            }
        }

        for (int i = 0; i < arrBulletPlayer.size(); i++) {
            Rectangle rectangle = getRect().intersection(arrBulletPlayer.get(i).getRect());
            if (rectangle.isEmpty() == false){
                arrBulletPlayer.remove(i);
                if (bit == 3){
                    return false;
                }
                if (bit != 2){
                    bit = 0;
                }
                return true;
            }
        }
        return true;
    }

}
