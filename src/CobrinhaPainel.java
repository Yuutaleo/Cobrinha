import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class CobrinhaPainel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    CobrinhaPainel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        comecarjogo();

    }

    public void comecarjogo(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        desenho(g);
    }

    public void desenho(Graphics g){
        if(running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Cabeça e o corpo da cobra
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.green);
            g.setFont(new Font("Ink Free",Font.BOLD,75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontos: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Pontos: "+applesEaten))/2,g.getFont().getSize());
        }
        else {
            fimdejogo(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)( SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)( SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;

    }

    public void mover(){
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        switch (direction){
            case 'U':
                y[0] = y[0] -UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checarmaca(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checarcolisao(){
        //Checa que a cabeça bateu em alguma parte do corpo
        for(int i = bodyParts;i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //Checa se a cabeça bateu na borda da esquerda
        if (x[0] < 0){
            running = false;
        }

        //Checa se a cabeça bateu na borda da direita
        if (x[0] > SCREEN_WIDTH){
            running = false;
        }
        //Checa se a cabeça bateu na borda de cima
        if (y[0] < 0) {
            running = false;
        }
        //Checa se a cabeça bateu na borda de baixo
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running){
            timer.stop();
        }
    }

    public void fimdejogo(Graphics g){
        //Texto de fim de jogo
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Fim de Jogo", (SCREEN_WIDTH - metrics.stringWidth("Fim de Jogo"))/2,SCREEN_HEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            mover();
            checarmaca();;
            checarcolisao();;

        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
