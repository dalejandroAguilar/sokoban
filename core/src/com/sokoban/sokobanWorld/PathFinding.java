package com.sokoban.sokobanWorld;

import com.badlogic.gdx.Gdx;
import com.sokoban.Constants;

import java.util.ArrayList;
import java.util.Stack;
import static com.sokoban.Constants.*;

public class PathFinding {
    final int distance = 1;
    Stack<Node> stack = new Stack<Node>();
    ArrayList<Point> groupObstacles = new ArrayList<Point>();
    Node start = new Node();
    Node target = new Node();
    ArrayList<Node> openList = new ArrayList<Node>();
    ArrayList<Node> closeList = new ArrayList<Node>();
    char[][] grafo;

    public PathFinding(String[] grafo, Node target) {
        this.grafo = new char[grafo.length][grafo[0].length()];
        for (int i = 0; i < grafo.length; i++)
            System.arraycopy(grafo[i].toCharArray(), 0, this.grafo[i], 0, grafo[0].length());
        for (int i = 0; i < grafo.length; i++)
            for (int j = 0; j < grafo[0].length(); j++)
                switch (grafo[i].charAt(j)) {
                    case 'E':
                    case 'X':
                    case 'B':
                        groupObstacles.add(new Point(j, i));
                        break;
                    case 'G':
                        start.x = j;
                        start.y = i;
                        break;
                }
        this.target.x = target.x;
        this.target.y = target.y;
    }

    public PathFinding(World mapa, Node target) {

        for (int i = 0; i < mapa.groupBrick.size(); i++)
            groupObstacles.add(new Point(mapa.groupBrick.get(i)));
        for (int i = 0; i < mapa.groupBox.size(); i++)
            groupObstacles.add(new Point(mapa.groupBox.get(i)));
        start = new Node(mapa.guy);
        this.target.x = target.x;
        this.target.y = target.y;

        // System.out.println("start: " + start.x + ", " + start.y);

        // System.out.println("target:" + target.x + ", " + target.y);
    }

    //Elegir el nodo con menor coste entre todos
    private Node AStar() {
        ArrayList<Node> adjacents = new ArrayList<Node>();
        Node current = new Node();
        openList.add(start);
        do {
            current = new Node(discriminate(openList));
            closeList.add(current);
            remove(openList, current);
            if (compare(current, target))
                return current;
            else {
                adjacents = allAdjacents(closeList, current);
                for (int i = 0; i < adjacents.size(); i++)
                    if (!contain(openList, adjacents.get(i))) {
                        set(current, adjacents.get(i));
                        adjacents.get(i).parent = current;
                        openList.add(adjacents.get(i));

                    } else if (adjacents.get(i).g < current.g) {
                        set(current, adjacents.get(i));
                        adjacents.get(i).parent = current;
                    }
            }
        } while (!openList.isEmpty());
        return new Node();
    }

    private void recursive(final Node target, final Node start, Stack<Node> stack) {
        //System.out.println("recursive");
        if (compare(start, target)) {
            stack.push(target);
        } else {
            stack.push(target);
            recursive(target.parent, start, stack);
        }
    }

    //elije el de menor coste de la lista.
    private Node discriminate(ArrayList<Node> list) {
        Node result = new Node(list.get(0));
        if (list.size() > 1)
            for (int i = 1; i < list.size(); i++)
                if (list.get(i).f < result.f) {
                    result = new Node(list.get(i));
                }
        return result;
    }

    private void set(Node parent, Node child) {
        child.g = parent.g + distance;
        child.h = manhattan(child, target);
        child.f = child.g + child.h;
    }

    private int manhattan(Node node1, Node node2) {
        return delta(node1.x, node2.x) + delta(node1.y, node2.y);
    }

    private int delta(int val1, int val2) {
        return Math.abs(val1 - val2);
    }

    private ArrayList<Node> allAdjacents(ArrayList<Node> list, Node node) {
        ArrayList<Node> adjacent = new ArrayList<Node>();
        if (noCrash(groupObstacles, translate(node, 0, 1)) && noN(list, translate(node, 0, 1)))
            adjacent.add(translate(node, 0, 1));
        if (noCrash(groupObstacles, translate(node, 1, 0)) && noN(list, translate(node, 1, 0)))
            adjacent.add(translate(node, 1, 0));
        if (noCrash(groupObstacles, translate(node, 0, -1)) && noN(list, translate(node, 0, -1)))
            adjacent.add(translate(node, 0, -1));
        if (noCrash(groupObstacles, translate(node, -1, 0)) && noN(list, translate(node, -1, 0)))
            adjacent.add(translate(node, -1, 0));
        return adjacent;
    }

    private boolean noN(ArrayList<Node> list, Node node) {
        for (int i = 0; i < list.size(); i++)
            if (compare(node, list.get(i)))
                return false;
        return true;
    }

    private boolean noCrash(ArrayList<Point> list, Node node) {
        for (int i = 0; i < list.size(); i++)
            if (compare(node, list.get(i)))
                return false;
        return true;
    }

    private boolean contain(ArrayList<Node> list, Node node) {
        for (int i = 0; i < list.size(); i++)
            if (compare(list.get(i), node))
                return true;
        return false;
    }

    private boolean compare(Node node1, Node node2) {
        if (node1.x == node2.x && node1.y == node2.y)
            return true;
        return false;
    }

    private boolean compare(Node node, Point point) {
        if (node.x == point.x && node.y == point.y)
            return true;
        return false;
    }

    private boolean realCompare(Node node1, Node node2) {
        if (compare(node1, node2) && node1.f == node2.f && node1.g == node2.g && node1.parent == node2.parent)
            return true;
        return false;
    }

    private boolean remove(ArrayList<Node> list, Node node) {
        for (int i = 0; i < list.size(); i++)
            if (realCompare(list.get(i), node)) {
                list.remove(i);
                return true;
            }
        return false;
    }

    private Node translate(Node node, int x, int y) {
        Node result = new Node(node.x + x, node.y + y);
        return result;
    }

    void execute() {
        recursive(AStar(), start, stack);
        stack = transpose(stack);
    }

    private void print(ArrayList<Node> list) {
        //System.out.println("PRINT");
        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i).x + ", " + list.get(i).y);
    }

    public void print() {
            execute();
            //System.out.println("ESTE ES EL PATH A SEGUIR: ");
            for (int i = 0; i < stack.size(); i++)
                System.out.println(stack.get(i).x + " ," + stack.get(i).y);

    }

    private Stack<Node> transpose(Stack<Node> stack) {
        Stack<Node> result = new Stack<Node>();
        for (int i = stack.size() - 1; i >= 0; i--)
            result.push(stack.get(i));
        return result;
    }

    public int[] getPath() {
        int direction[]={NULL};
        try {
            execute();
            int size = stack.size();
            Gdx.app.log("AS",""+size);
            if (size>1) {
                direction = new int[size - 1];
                int diffX, diffY;
//
                for (int i = 1; i < size; i++) {
                    diffX = stack.get(i).x - stack.get(i - 1).x;
                    diffY = stack.get(i).y - stack.get(i - 1).y;
                    if (diffX == 1 && diffY == 0)
                        direction[i - 1] = RIGHT;
                    else if (diffX == -1 && diffY == 0)
                        direction[i - 1] = LEFT;
                    else if (diffX == 0 && diffY == 1)
                        direction[i - 1] = DOWN;
                    else if (diffX == 0 && diffY == -1)
                        direction[i - 1] = UP;
                }
            }
        }
        catch (Exception ex){
            direction = new int[1];
            direction[0]=NULL;
        }
        return direction;
    }
}

class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point(Brick brick) {
        x = brick.x;
        y = brick.y;
    }

    Point(Box box) {
        x = box.x;
        y = box.y;
    }
}