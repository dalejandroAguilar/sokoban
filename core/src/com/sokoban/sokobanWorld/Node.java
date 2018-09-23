package com.sokoban.sokobanWorld;

public class Node {
    Node parent;
    int f;
    int g;
    int h;
    int x;
    int y;

    public Node(Node unref) {
        parent = unref.parent;
        f = unref.f;
        g = unref.g;
        h = unref.h;
        x = unref.x;
        y = unref.y;
    }

    public Node() {
    }

    public Node(Point point) {
        x = point.x;
        y = point.y;
    }

    public Node(Brick point) {
        x = point.x;
        y = point.y;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
