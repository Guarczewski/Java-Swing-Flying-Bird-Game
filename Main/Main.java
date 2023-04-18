package Main;

import Custom_Frames.CScoreboardFrame;
import Custom_Objects.Pipe;
import Custom_Panels.CPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame implements MouseListener, ActionListener {
    public static CPanel cPanel;
    public static Main main;
    public static Rectangle birdBox, floorBox;
    public static List<Pipe> pipeList = new ArrayList<>();
    public static boolean birdAlive;
    public static final int WINDOW_WIDTH = 720, WINDOW_HEIGHT = 1280;
    public static double birdCordX, birdCordY, birdVelY, birdRotation, birdSize = 70;
    public static int score;
    Main(){
        super("Shit!");
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cPanel = new CPanel();

        cPanel.playButton.addActionListener(this);
        cPanel.scoreButton.addActionListener(this);

        addMouseListener(this);
        setContentPane(cPanel);
        setVisible(true);
    }
    public static void main(String[] args) {
        main = new Main();
        pipeList.add(new Pipe());
        floorBox = new Rectangle(0,Main.WINDOW_HEIGHT, Main.WINDOW_WIDTH, 50);
        StartUpdating();
    }

    public static void initGame(){
        birdCordX = 20;
        birdCordY = 0;
        birdBox = new Rectangle((int) birdCordX, 0, (int) (birdSize * 0.8), (int) (birdSize * 0.8));
        birdAlive = true;
        score = 0;

        for (Pipe pipe : Main.pipeList)
            pipe.makeNewPipe();

    }

    private static void StartUpdating(){
        new Thread(() -> {
            // FPS Stuff
            long lastTime = System.nanoTime();
            double nsRender = 1000000000.0 / 120;
            double nsUpdate = 1000000000.0 / 60;
            double deltaRender = 0;
            double deltaUpdate = 0;
            int updates = 0;
            int frames = 0;
            long timer = System.currentTimeMillis();

            while(true) {
                long now = System.nanoTime();

                deltaRender += (now - lastTime) / nsRender;
                deltaUpdate += (now - lastTime) / nsUpdate;
                lastTime = now;

                if(deltaRender >= 1) {
                    deltaRender--;
                    cPanel.repaint();
                    frames++;
                }

                if (deltaUpdate >= 1) {
                    deltaUpdate--;
                    if (birdAlive)
                        Update();
                    updates++;
                }

                if(System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    main.setTitle(" Ticks: " + updates + ", FPS: " + frames);
                    updates = 0;
                    frames = 0;
                }
            }

            //StartUpdating();

        }).start();



    }

    public static void Update(){
        if (birdVelY < 5)
            birdVelY += 0.2;

        if (birdVelY > 0) {
            if (Main.birdRotation < 15) {
                Main.birdRotation++;
            }
        }
        else if (birdVelY < 0) {
            if (Main.birdRotation > -15) {
                Main.birdRotation--;
            }
        }

        birdCordY += birdVelY;
        birdBox.y = (int) birdCordY;



        for(Pipe pipe : Main.pipeList) {
            pipe.movePipe(-3);
            if (pipe.lowerPipe.x < -Pipe.pipeWidth)
                pipe.makeNewPipe();

        }
        Collision();

    }

    public static void Collision(){
        for (Pipe pipe : pipeList) {
            if (birdBox.intersects(pipe.lowerPipe) || birdBox.intersects(pipe.upperPipe)) {
                birdAlive = false;
            }
            else if (birdBox.intersects(pipe.middlePipe)) {
                pipe.middlePipe.x = -1000;
                score++;
            }
        }

        if (birdBox.intersects(floorBox))
            birdAlive = false;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        birdVelY = -3;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cPanel.playButton) {
            if (!birdAlive) {
                initGame();
                cPanel.remove(cPanel.menuPanel);
            }
        }
        else if (e.getSource() == cPanel.scoreButton) {
            new CScoreboardFrame();
        }
    }
}