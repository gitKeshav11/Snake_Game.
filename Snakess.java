import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Snakess extends JPanel implements ActionListener {

    private Image apple;
    private Image dot;
    private Image head;

    private int apple_x;
    private int apple_y;

    private final int All_Dot = 3600;
    private final int Dot_Size = 10;
    private final int Random_Position = 29;

    private final int[] x = new int[All_Dot];
    private final int[] y = new int[All_Dot];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;

    private int dots;
    private Timer timer;

    public Snakess() {
        addKeyListener(new TAdapter());

        setBackground(Color.black);
        setPreferredSize(new Dimension(600,600));
        setFocusable(true);

        setPreferredSize(new Dimension(600, 600));

        loadImages();
        initGame();
    }

    public void loadImages() {
        apple = new ImageIcon(getClass().getResource("/icons/apple.png")).getImage();
        dot = new ImageIcon(getClass().getResource("/icons/dot.png")).getImage();
        head = new ImageIcon(getClass().getResource("/icons/head.png")).getImage();
    }

    public void initGame() {
        dots = 5;

        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * Dot_Size;
        }

        locateApple();

        timer = new Timer(190, this);  // Fix: Use the class-level timer
        timer.start();
    }

    public void locateApple() {
        int r = (int) (Math.random() * Random_Position);
        apple_x = r * Dot_Size;
        r = (int) (Math.random() * Random_Position);
        apple_y = r * Dot_Size;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (inGame){
            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }

    }

    public void gameOver(Graphics g){
        String msg = "@_Everything_is_Over ! \uD83D\uDE22";
        Font font=new Font("arial",Font.BOLD,10 );
        FontMetrics metrices = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.drawString(msg,(600 - metrices.stringWidth(msg))/2, 600/2);
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (leftDirection) {
            x[0] = x[0] - Dot_Size;
        }

        if (rightDirection) {
            x[0] = x[0] + Dot_Size;
        }

        if (upDirection) {
            y[0] = y[0] - Dot_Size;
        }

        if (downDirection) {
            y[0] = y[0] + Dot_Size;
        }
    }
    public void checkApple(){
        if ((x[0] == apple_x) && (y[0] == apple_y)){
            dots ++ ;
            locateApple();
        }
    }

    public void checkCollosion(){
        for (int i= dots; i>0; i--){
           if (( i > 4 ) && (x[0] == x[i]) && (y[0] == y[i]) ){
               inGame = false;
           }
        }

        if (y[0] >= 600){
            inGame = false;
        }

        if (x[0] >= 600){
            inGame = false;
        }

        if (y[0] < 0){
            inGame = false;
        }

        if (x[0] < 0){
            inGame = false;
        }

        if (!inGame){
           timer.stop();

        }
    }
    @Override
    public void actionPerformed(ActionEvent ac) {
        if (inGame){
            checkApple();
            checkCollosion();
            move();
            repaint();
        }

    }

    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
