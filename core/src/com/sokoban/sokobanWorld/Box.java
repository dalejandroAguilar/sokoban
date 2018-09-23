package com.sokoban.sokobanWorld;

public class Box extends Brick {
    public boolean isEmbonated;
    protected boolean isSlippy;
    public boolean isAlive;

    Box(int x, int y, boolean isAlive,boolean isSlippy) {
        super(x, y);
        isEmbonated = false;
        this.isSlippy = isSlippy;
        this.isAlive = isAlive;
    }

    Box(int x, int y) {
        this(x, y, true, false);
    }

    Box() {
        super();
        isEmbonated = false;
    }

    public Box(Box unref) {
        super(unref);
        this.isEmbonated = unref.isEmbonated;
        this.isSlippy = unref.isSlippy;
    }

    void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void move(int direction) {
        switch (direction) {
            case Sokoban.UP:
                y--;
                break;
            case Sokoban.DOWN:
                y++;
                break;
            case Sokoban.LEFT:
                x--;
                break;
            case Sokoban.RIGHT:
                x++;
                break;
        }
    }

    Box translate(int x, int y) {
        return new Box(this.x + x, this.y + y);
    }
}
