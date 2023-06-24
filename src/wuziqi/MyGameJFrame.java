package wuziqi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyGameJFrame extends JFrame {
    public MyGameJFrame(GameComputer gameComputer){
        this.gameComputer=gameComputer;
    }
    GameComputer gameComputer=null;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;
    private static final int CELL = 40;
    private ImageIcon imageIcon = new ImageIcon("1.jpg");
    private int x1;
    private int y1;
    //人机判定
    private boolean countOpponent=true;
    //界面绘制
    private boolean countThread=false;
    private int gameOver=0;
    Game game=new Game(gameComputer);
    //    界面组件
    public void runGame() {
        new Thread(new gameThread()).start();
        this.setBounds(600, 200, WIDTH, HEIGHT);
        this.setVisible(true);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //   鼠标下棋节点
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(countOpponent) {
                    if (e.getX() % 40 >= 20) {
                        x1 = e.getX() + 40 - e.getX() % 40;
                    } else {
                        x1 = e.getX() - e.getX() % 40;
                    }
                    if (e.getY() % 40 >= 20) {
                        y1 = e.getY() + 40 - e.getY() % 40;
                    } else {
                        y1 = e.getY() - e.getY() % 40;
                    }
                    for (QIzi qIzi : gameComputer.getNode()) {
                        if (qIzi.getX() == x1 / 40 && qIzi.getY() == y1 / 40) {
                            if(qIzi.getMark()==0) {
                                qIzi.setMark(2);
                                countOpponent = false;
                                countThread = true;
                            }
                        }
                    }
                }
            }

        });
    }
    //      界面绘制
    @Override
    public void paint(Graphics g) {
        g.drawImage(imageIcon.getImage(), 0, 0, null);
        this.backgroundLine(g);
        if(!countOpponent){
            gameComputer.priority();
            countOpponent=true;
            countThread=true;
        }
        game.drGame(g,gameComputer.getNode());
        gameOver=game.win(gameComputer.quintet());
        if(gameOver==1||gameOver==2){
            new Win().myFrame(gameOver,gameComputer);
        }
    }
    //       棋盘线段
    private void backgroundLine(Graphics g) {
        g.fillRect(0, 0, 8, 640);
        g.fillRect(0, 632, 640, 8);
        g.fillRect(632, 0, 8, 640);
        for (int i = 0; i < WIDTH / CELL; i++) {
            g.drawLine(0, CELL * i, WIDTH, CELL * i);
            g.drawLine(CELL * i, 0, CELL * i, HEIGHT);
        }
    }
    //输赢界面
    public class Win extends JFrame{
        private JPanel main=new JPanel();
        private JButton reopen=new JButton("再来一局");
        private JButton exit=new JButton("关闭游戏");
        public void myFrame(int count,GameComputer gameComputer){
            String text="恭喜你，你赢了";
            if(count==1){
                text="很遗憾，你输了";
            }
            JLabel textbean=new JLabel(text);
            this.setBounds(600, 200, 300, 130);
            this.setVisible(true);
            this.setResizable(false);
            main.setLayout(null);
            reopen.setBounds(30,50,100,40);
            exit.setBounds(170,50,100,40);
            textbean.setBounds(30,10,200,20);
            main.add(reopen);
            main.add(exit);
            main.add(textbean);
            this.add(main);
            exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.exit(0);
                }
            });
            reopen.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    gameOver=0;
                    for (int i = 0; i < gameComputer.getNode().size(); i++) {
                        gameComputer.getNode().get(i).setMark(0);
                    }
                }
            });

        }
    }
    //       游戏线程
    public class gameThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(gameOver==0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (countThread) {
                        repaint();
                        countThread = false;
                    }
                }
            }
        }
    }
}
