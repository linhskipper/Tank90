package com.t3h.manager;

import com.t3h.gui.MyFrame;
import com.t3h.model.BossTank;
import com.t3h.model.Bullet;
import com.t3h.model.Map;
import com.t3h.model.MyTank;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;


public class GameManager {
    private MyTank myTank;
    private ArrayList<BossTank> arrBoss;
    private ArrayList<Bullet> arrBulletBoss;
    private ArrayList<Bullet> arrBulletPlayer;
    private ArrayList<Map> arrMap;


    public void initGame() {
        arrBulletBoss = new ArrayList<Bullet>();
        arrBulletPlayer = new ArrayList<Bullet>();
        myTank = new MyTank(170, 460);
        arrBoss = new ArrayList<BossTank>();
        readMap("map.txt");
        initBoss();
    }

    private void initBoss() {
        if (arrBoss.size() <= 2) {
            BossTank bossTank = new BossTank(0, 0);
            arrBoss.add(bossTank);
            bossTank = new BossTank(MyFrame.W_FRAME / 2 - 16, 0);
            arrBoss.add(bossTank);
            bossTank = new BossTank(MyFrame.W_FRAME - 32, 0);
            arrBoss.add(bossTank);
        }
    }

    private void readMap(String name) {
        arrMap = new ArrayList<Map>();
        File file = new File("src/map/" + name);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            int row = 0;
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    int bit = Integer.parseInt(line.charAt(i) + "");
                    int x = i * 19;
                    int y = row * 19;
                    Map map = new Map(x, y, bit);
                    arrMap.add(map);
                }
                line = bufferedReader.readLine();
                row++;
            }
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void draw(Graphics2D g2d) {
        try {
            for (Bullet bullet : arrBulletBoss) {
                bullet.draw(g2d);
            }
            for (Bullet bullet : arrBulletPlayer) {
                bullet.draw(g2d);
            }
            myTank.draw(g2d);
            for (BossTank bossTank : arrBoss) {
                bossTank.draw(g2d);
            }
            for (Map m : arrMap) {
                m.draw(g2d);
            }
        } catch (ConcurrentModificationException ex) {

        }
    }

    public void playerFire() {
        Bullet bullet = myTank.fire();
        arrBulletPlayer.add(bullet);
    }

    public void myTankMove(int newOrient) {
        myTank.changeOrient(newOrient);
        myTank.move(arrMap);
    }

    public boolean AI() {
        for (int i = arrBoss.size()-1; i >= 0; i--) {
            arrBoss.get(i).createOrient();
            arrBoss.get(i).move(arrMap);
            Bullet bullet = arrBoss.get(i).fire();
            if (bullet != null) {
                arrBulletBoss.add(bullet);
            }
            boolean check = arrBoss.get(i).checkDie(arrBulletPlayer);
            if (check == false){
                arrBoss.remove(i);
                initBoss();
            }
        }
        for (int i = arrBulletBoss.size() - 1; i >= 0; i--) {
            boolean check = arrBulletBoss.get(i).move();
            if (check) {
                arrBulletBoss.remove(i);
            }
        }
        for (int i = arrBulletPlayer.size() - 1; i >= 0; i--) {
            boolean check = arrBulletPlayer.get(i).move();
            if (check) {
                arrBulletPlayer.remove(i);
            }
        }
        for (Map map:arrMap){
            boolean check = map.checkBullet(arrBulletBoss,arrBulletPlayer);
            if (check == false){
                return false;
            }
        }
        return myTank.checkDie(arrBulletBoss);
    }

}
