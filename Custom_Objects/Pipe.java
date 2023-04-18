package Custom_Objects;

import java.awt.*;
import java.util.Random;

public class Pipe {
    private static final Random pipeHeightGenerator = new Random();
    public static int pipeGap = 150, pipeWidth = 75;
    public Rectangle upperPipe, middlePipe, lowerPipe;
    boolean pipeMoving = true;
    private int offsetY = 0, movingSpeedY = 1;

    public Pipe() {
        makeNewPipe();
    }

    public void makeNewPipe() {
        int upperPipeHeight = pipeHeightGenerator.nextInt(500);

        int pipeCordX = Main.Main.WINDOW_WIDTH + 100;
        upperPipe = new Rectangle(pipeCordX, -1000 + upperPipeHeight, pipeWidth, 1000);
        middlePipe = new Rectangle(pipeCordX, upperPipeHeight, pipeWidth, pipeGap);
        lowerPipe = new Rectangle(pipeCordX, upperPipeHeight + pipeGap, pipeWidth, 1000);
    }

    public void movePipe(int deltaX) {
        upperPipe.x += deltaX;
        lowerPipe.x += deltaX;
        middlePipe.x += deltaX;

        if (pipeMoving) {

            if (offsetY > 100)
                movingSpeedY *= -1;
            else if (offsetY < -100)
                movingSpeedY *= -1;

            offsetY += movingSpeedY;

            upperPipe.y += movingSpeedY;
            lowerPipe.y += movingSpeedY;
            middlePipe.y += movingSpeedY;
        }

    }

    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.GREEN);
        graphics2D.fillRect(upperPipe.x, upperPipe.y, upperPipe.width, upperPipe.height);
        graphics2D.fillRect(lowerPipe.x, lowerPipe.y, lowerPipe.width, lowerPipe.height);
        graphics2D.setColor(Color.ORANGE);
        graphics2D.fillRect(middlePipe.x, middlePipe.y, middlePipe.width, middlePipe.height);
    }

}
