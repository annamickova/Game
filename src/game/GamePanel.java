package game;

import javax.swing.*;
import java.awt.*;



public class GamePanel extends JPanel implements Runnable  {

    //screen settings
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreeenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreeenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    // set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

     public GamePanel(){
         this.setPreferredSize(new Dimension(screenWidth, screenHeight));
         this.setBackground(Color.BLACK);
         this.setDoubleBuffered(true);
         this.addKeyListener(keyH);
         this.setFocusable(true);
     }

     public void startGameThread(){
         gameThread = new Thread(this);
         gameThread.start();
     }

   /* @Override
    public void run() {
         double drawInterval = 1000000000/FPS; // 0.01666 seconds
         double nextDrawTime = System.nanoTime() + drawInterval;

         while (gameThread != null){

             // 1. UPDATE info such as character positions
             update();
             // 2. DRAW the screen with updated info
             repaint();

             try {
                 double remainingTime = nextDrawTime - System.nanoTime();
                 remainingTime = remainingTime/1000000;

                 if (remainingTime < 0){
                     remainingTime = 0;
                 }
                 Thread.sleep((long) remainingTime);
                 nextDrawTime += drawInterval;
             }catch (InterruptedException e){
                 e.printStackTrace();
             }
         }
    }*/    //sleep method

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime )/drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta --;
                drawCount++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D) g;
         player.draw(g2);
         g2.dispose();
    }
}
