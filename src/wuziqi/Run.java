package wuziqi;

public class Run {
    public static void main(String[] args) {
        GameComputer game =new GameComputer();
        MyGameJFrame myGameJframe=new MyGameJFrame(game);
        myGameJframe.runGame();
    }
}
