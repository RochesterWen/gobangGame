package wuziqi;

import java.awt.*;
import java.util.ArrayList;


public class Game {
    public Game(GameComputer gameComputer){
        this.gameComputer=gameComputer;
    }

    private GameComputer gameComputer=null;
    /**棋子绘制**/
    public void drGame(Graphics g,ArrayList<QIzi> element){
        ArrayList<QIzi> node=element;
        for(QIzi qIzi:node){
            if(qIzi.getMark()==1){
                Color c=g.getColor();
                g.setColor(Color.white);
                g.fillOval(qIzi.getX()*40-20,qIzi.getY()*40-20,40,40);
                g.setColor(c);
            }
            if(qIzi.getMark()==2){
                Color c=g.getColor();
                g.setColor(Color.black);
                g.fillOval(qIzi.getX()*40-20,qIzi.getY()*40-20,40,40);
                g.setColor(c);
            }
        }
    }
    //  输赢判定
    public int  win(ArrayList<int[][]> element){
        int wincount=0;
        int c=0;
        int h=0;
        ArrayList<int[][]> node=element;
        for(int i=0;i<node.size();i++){
            for(int j=0;j<node.get(i).length;j++) {
                if(node.get(i)[j][0]==1){
                    c++;
                }
                if(node.get(i)[j][0]==2){
                    h++;
                }
            }
            if(c==5){
                wincount=1;
                break;
            }
            c=0;
            if(h==5){
                wincount=2;
                break;
            }
            h=0;
        }
        return wincount;
    }
}
