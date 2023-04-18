package Custom_Panels;

import Custom_Elements.CButton;
import Custom_Elements.CLabel;
import Custom_Elements.CTextField;
import Custom_Objects.Pipe;
import Main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CPanel extends JPanel {
    public static BufferedImage background, bird, birdRotated;
    public CButton playButton, scoreButton;
    public JPanel menuPanel;
    public CPanel() {
        try {
            bird = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/Bird.png")));
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/Background.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuPanel = new JPanel(new GridLayout(11,0));
        menuPanel.setBackground(new Color(0,0,0,0));

        playButton = new CButton("PLAY", 32, Color.ORANGE, Color.WHITE);
        scoreButton = new CButton("Scoreboard", 21, Color.ORANGE, Color.WHITE);

        menuPanel.add(new CLabel("Andrzej Byrd", 48, Color.WHITE));
        menuPanel.add(new CLabel("", 0));

        menuPanel.add(new CLabel("Type Nickname", 32, Color.WHITE));
        menuPanel.add(new CTextField("Nick", 21, Color.BLACK));
        menuPanel.add(new CButton("Scoreboard", 21, Color.ORANGE, Color.WHITE));

        menuPanel.add(new CLabel("", 0));
        menuPanel.add(playButton);
        menuPanel.add(new CLabel("", 0));
        menuPanel.add(scoreButton);
        menuPanel.add(new CLabel("", 0));

        add(menuPanel);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background, 0, 0,Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, null);



        if (Main.birdAlive) {
            birdRotated = rotate(bird, Main.birdRotation);

            for (Pipe pipe : Main.pipeList)
                pipe.render(g2D);

            g2D.drawImage(birdRotated, (int) Main.birdCordX, (int) Main.birdCordY, (int) Main.birdSize, (int) Main.birdSize, null);

            int newX = (Main.WINDOW_WIDTH / 2);

            g2D.setColor(Color.BLACK);
            Font TitleFont = new Font("Comic Sans MS", Font.BOLD, 128);
            FontMetrics titleFontMetrics = g2D.getFontMetrics(TitleFont);
            int titleCordX = newX - (titleFontMetrics.stringWidth("" + Main.score) / 2);
            g2D.setFont(TitleFont);
            g2D.drawString("" + Main.score, titleCordX, 128);
        }
    }

    public BufferedImage rotate(BufferedImage image, Double degrees) {
        double radians = Math.toRadians(degrees);
        int newWidth = image.getWidth() + 50;
        int newHeight = image.getHeight() + 50;

        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();

        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;

        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);

        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }

}
