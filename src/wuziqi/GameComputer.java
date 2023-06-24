package wuziqi;
import java.util.ArrayList;

public class GameComputer {

    /**
     * 存储所有棋盘节点
     */
    private ArrayList<QIzi> node =new ArrayList();

    public ArrayList<QIzi> getNode() {
        return node;
    }

    public void setNode(ArrayList<QIzi> node) {
        this.node = node;
    }

    public GameComputer(){
        setNode();
    }

    /**
     * 初始化节点
     */
    private void setNode(){
        for(int i=1;i<=15;i++) {
            for (int j=1;j<=15;j++) {
                node.add(new QIzi(i,j,0,0));
            }
        }
    }

    /**
     * 获取棋盘所有五元组合
     * @return
     */
    public ArrayList quintet(){
        ArrayList<int[][]> quintetList=new ArrayList();
        //棋盘节点下标
        int count=0;
        //y轴下标
        int countY=0;
        //x轴下标
        int countX=0;
        //横竖方向
        int direction=0;
        while(true){
            //     五元组，棋子判别+棋盘索引
            int[][] element=new int[5][2];
            //右上
            if(direction==5){
                if(count-64>=0){
                    for(int i=0; i<5;i++){
                        element[i][0]=node.get(count).getMark();
                        element[i][1]=count;
                        count-=16;
                    }
                    count+=64;
                }else{
                    countX-=15;
                    count=countX;
                }
                if(count==14){
                    break;
                }
            }
            //左上
            if (direction==4){
                if(count-56>=0){
                    for(int i=0;i<5;i++){
                        element[i][0]=node.get(count).getMark();
                        element[i][1]=count;
                        count-=14;
                    }
                    count+=56;
                }else{
                    countY-=15;
                    count=countY;
                }
                if(count==0){
                    direction=5;
                    count=209;
                    countX=209;
                    countY=0;
                }
            }
            //右下
            if (direction==3){
                if((count+56)<225){
                    for(int i=0;i<5;i++){
                        element[i][0]=node.get(count).getMark();
                        element[i][1]=count;
                        count+=14;
                    }
                    count-=56;
                }else {
                    countX+=15;
                    count=countX;
                }
                if(count==224){
                    direction=4;
                    count=195;
                    countX=0;
                    countY=195;
                }
            }
            //左下
            if (direction==2) {
                if ((count + 64) < 225) {
                    for (int i = 0; i < 5; i++) {
                        element[i][0] = node.get(count).getMark();
                        element[i][1] = count;
                        count += 16;
                    }
                    count -= 64;
                } else {
                    countY += 15;
                    count = countY;
                }
                if (count == 210) {
                    direction=3;
                    count = 14;
                    countX = 14;
                    countY=0;
                }
            }
            //纵向
            if(direction==1){
                for(int i = 0; i < 5; i++){
                    element[i][0]=node.get(count).getMark();
                    element[i][1]=count;
                    count+=15;
                }
                count-=60;
                if(count==179){
                    direction=2;
                    count=0;
                    countX=0;
                    countY=0;
                }
                if(count==165+countX){
                    countX++;
                    count=countX;
                }
            }
            //横向
            if(direction==0) {
                for (int i = 0; i < 5; i++) {
                    element[i][0] = node.get(count).getMark();
                    element[i][1]=count;
                    count++;
                }
                count -= 4;
                // 方向转换，下标归零
                if(count==221){
                    direction=1;
                    count=0;
                    countY=0;
                }
                //末尾判断
                if(count==11+15*countY){
                    countY++;
                    count=15*countY;
                }
            }
            quintetList.add(element);
        }
        return quintetList;
    }
    //  获得棋子坐标
    private void getPiece(){
        int count=0;
        int index=0;
        for(int i=0;i<node.size();i++){
            if((node.get(i).getMark()==0)&&(node.get(i).getPriority() > count)) {
                count = node.get(i).getPriority();
                index = i;
            }
        }
        node.get(index).setMark(1);
        this.clear();
    }
    //   优先级计算算法
    public void priority(){
        ArrayList<int[][]> quintetList=this.quintet();
        int[][] element;
        for(int j=0;j<quintetList.size();j++) {
            element=quintetList.get(j);
            int a = 0;  //  空格
            int b = 0;  //  机
            int c = 0;  //  人
            for (int i = 0; i < element.length; i++) {
                if (element[i][0] == 0) {
                    a++;
                }
                if (element[i][0] == 1) {
                    b++;
                }
                if (element[i][0] == 2) {
                    c++;
                }
            }
            //10111，11101，11011，11110，01111=======100000
            if (a == 1 && b == 4) {
                this.addIndex(element, 100000);
            }
            //20222，22202，22022，22220，02222========10000
            if (a == 1 && c == 4) {
                this.addIndex(element, 10000);
            }
            //01110===============5000
            if (element[0][0] == 0 && element[4][0] == 0 && b == 3) {
                this.addPriority(element[0][1], 5000);
                this.addPriority(element[4][1], 5000);
            }
            //20220,02202===============800
            if((element[1][0] == 0 && element[4][0] == 0 && c == 3) || (element[0][0] == 0 && element[3][0] == 0 && c == 3)){
                this.addPriority(element[1][1],800);
                this.addPriority(element[3][1],800);
            }
            //00111,11100 =========200
            if ((element[0][0] == 0 && element[1][0] == 0 && b == 3) || (element[3][0] == 0 && element[4][0] == 0 && b == 3)) {
                this.addIndex(element, 200);
            }
            //20111，11102============   100
            if ((element[0][0] == 2 && element[1][0] == 0 && a == 3) || (element[4][0] == 2 && element[3][0] == 0 && a == 3)) {
                this.addIndex(element, 100);
            }
            //02220 ====================2000
            if (element[0][0] == 0 && element[4][0] == 0 && c == 3) {
                this.addPriority(element[0][1], 2000);
                this.addPriority(element[4][1], 2000);
            }
            //12220,02221================90
            if ((element[0][0] ==1 && element[4][0] == 0) && c == 3 || (element[0][0] == 0 && element[4][0] == 1 && c == 3)) {
                this.addIndex(element, 90);
            }
            //00110，01100===================500
            if ((element[2][0] == 1 && element[3][0] == 1 && a == 3) || (element[1][0] == 1 && element[2][0] == 1 && a == 3)) {
                this.addIndex(element, 500);
            }
            //00220，02200================================400
            if ((element[2][0] == 2 && element[3][0] == 2 && a == 3) || (element[1][0] == 2 && element[2][0] == 2 && a == 3)) {
                this.addIndex(element, 400);
            }
            //11001，10011====================70
            if ((element[2][0] == 0 && element[3][0] == 0 && b == 3) || (element[1][0] == 0 && element[2][0] == 0 && b == 3)) {
                this.addIndex(element, 70);
            }
            //22002，20022====================60
            if ((element[2][0] == 0 && element[3][0] == 0 && c == 3) || (element[1][0] == 0 && element[2][0] == 0 && c == 3)) {
                this.addIndex(element, 60);
            }
            //10001=======================50
            if (element[0][0] == 1 && element[4][0] == 1 && a == 3) {
                this.addIndex(element, 50);
            }
            //20002=====================40
            if (element[0][0] == 2 && element[4][0] == 2 && a == 3) {
                this.addIndex(element, 40);
            }
            //00100===========================30
            if (element[2][0] == 1 && a == 4) {
                this.addIndex(element, 30);
            }
            //00200========================20
            if (element[2][0] == 2 && a == 4) {
                this.addIndex(element, 20);
            }
        }
        this.getPiece();
    }
    //     清空优先级
    private void clear(){
        for(int i=0;i<node.size();i++){
            node.get(i).setPriority(0);
        }
    }
    //   棋子优先级赋予
    private void addPriority(int index,int prior){
        int countPriority=node.get(index).getPriority();
        node.get(index).setPriority(countPriority+prior);
    }
    //      五元组棋子遍历
    private void addIndex(int[][] element,int prior){
        for(int i=0;i<element.length;i++){
            if(element[i][0]==0){
                this.addPriority(element[i][1],prior);
            }
        }
    }
}
