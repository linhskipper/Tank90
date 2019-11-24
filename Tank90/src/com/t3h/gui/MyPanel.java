package com.t3h.gui;

import com.t3h.manager.GameManager;
import com.t3h.model.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;


public class MyPanel extends JPanel implements KeyListener, Runnable {
    private GameManager gameManager = new GameManager();
    private boolean isRunning = true;
    private BitSet bitSet = new BitSet(256);
    private int fps = 0;

    public MyPanel() {
        setBackground(Color.BLACK);
        gameManager.initGame();
        setFocusable(true);
        this.addKeyListener(this);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
        gameManager.draw(g2d);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        bitSet.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bitSet.clear(e.getKeyCode());
    }

    @Override
    public void run() {
        int t = 0;
        int frames = 0;
        long lastTimeChecked = System.nanoTime();
        while (isRunning) {
            t++;
            if (t > 100000) {
                t = 0;
            }
            if (bitSet.get(KeyEvent.VK_LEFT) == true) {
                gameManager.myTankMove(Tank.LEFT);
            } else if (bitSet.get(KeyEvent.VK_RIGHT) == true) {
                gameManager.myTankMove(Tank.RIGHT);
            } else if (bitSet.get(KeyEvent.VK_UP) == true) {
                gameManager.myTankMove(Tank.UP);
            } else if (bitSet.get(KeyEvent.VK_DOWN) == true) {
                gameManager.myTankMove(Tank.DOWN);
            }
            if (bitSet.get(KeyEvent.VK_SPACE) == true) {
                if (t >= 50) {
                    gameManager.playerFire();
                    t = 0;
                }
            }
            isRunning = gameManager.AI();
            if (isRunning == false){
                int confirm = JOptionPane.showConfirmDialog
                        (null,"Do you want to replay?","Game over",JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.NO_OPTION){
                    System.exit(0);
                }else{
                    isRunning = true;
                    gameManager.initGame();
                    bitSet.clear();
                }
            }
            synchronized (MyPanel.this) {
                repaint();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frames++;
            if (System.nanoTime() - lastTimeChecked >= 1000000000L){
                fps = frames;
                System.out.println(fps);
                frames = 0;
                lastTimeChecked = System.nanoTime();
            }
        }
    }
}
