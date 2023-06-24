package wuziqi;


public class QIzi {
    private int x;
    private int y;
    private int mark;          //棋子标记  0---空，1----电脑，2----人
    private int priority;             //优先级
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMark() {
        return mark;
    }

    public int getPriority() {
        return priority;
    }

    public QIzi(int x, int y, int mark, int priority) {
        this.x = x;
        this.y = y;
        this.mark = mark;
        this.priority = priority;
    }
}
