package com.sokoban.sokobanWorld;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class World {
    static private final int FREE = 0, CORNER = 1, INMATE = 2, BRICK = 3, RECEPTACLE = 4;
    public int moves;
    public Guy guy;
    public ArrayList<Box> groupBox;
    public ArrayList<Receptacle> groupReceptacle;
    public ArrayList<Brick> groupBrick;
    public ArrayList<Floor> groupFloor;
    public ArrayList<Brick> groupDeathFloor;
    public ArrayList<Reel> reel;
    int mark;
    int pushes;

    public World(String[] data) {
        groupBrick = new ArrayList<Brick>();
        groupReceptacle = new ArrayList<Receptacle>();
        groupBox = new ArrayList<Box>();
        groupFloor = new ArrayList<Floor>();
        groupDeathFloor = new ArrayList<Brick>();
        reel = new ArrayList<Reel>();
        setLevel(data);
    }

    void setLevel(String[] data) {
        groupBox.clear();
        groupReceptacle.clear();
        groupBrick.clear();
        groupFloor.clear();
        pushes = 0;
        reel.clear();

        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length(); j++)
                switch (data[i].charAt(j)) {
                    case 'B':
                    case '#':
                        groupBrick.add(new Brick(j, i));
                        break;
                    case 'X':
                    case '$':
                        groupBox.add(new Box(j, i));
                        break;
                    case 'f':
                        groupBox.add(new Box(j, i, true, true));
                        break;
                    case 'F':
                        groupBox.add(new Box(j, i, true, true));
                        groupReceptacle.add(new Receptacle(j, i));
                        break;
                    case 'R':
                    case '.':
                        groupReceptacle.add(new Receptacle(j, i));
                        break;
                    case 'G':
                    case '@':
                        guy = new Guy(j, i);
                        break;
                    case 'g':
                    case '+':
                        guy = new Guy(j, i);
                        groupReceptacle.add(new Receptacle(j, i));
                        break;
                    case 'E':
                    case '*':
                        groupBox.add(new Box(j, i));
                        groupReceptacle.add(new Receptacle(j, i));
                        break;
                }
        //System.out.println(guy.x + " " + guy.y);


        //groupFloor=allFloors();
        setFloors();
        restartOrBuild();
        setBoxStatus();
        setEmbonated();
    }

    public void restart() {
        //verificar referencias
        if (mark > -1) {
            //System.out.println("restart");
            //groupBrick.clear();
            //groupBox.clear();
            //groupReceptacle.clear();
            final int Size = 0;

            // for (int i = 0; i < groupBrick.size(); i++)
            //     groupBrick.get(i).setPosition(reel.get(Size).groupBrick.get(i).x, reel.get(Size).groupBrick.get(i).y);


            for (int i = 0; i < groupBox.size(); i++) {
                groupBox.get(i).setPosition(reel.get(Size).groupBox.get(i).x, reel.get(Size).groupBox.get(i).y);
                groupBox.get(i).isEmbonated = reel.get(Size).groupBox.get(i).isEmbonated;
                groupBox.get(i).isSlippy = reel.get(Size).groupBox.get(i).isSlippy;
            }

            for (int i = 0; i < groupReceptacle.size(); i++) {
                groupReceptacle.get(i).setPosition(reel.get(Size).groupReceptacle.get(i).x, reel.get(Size).groupReceptacle.get(i).y);
                groupReceptacle.get(i).status = reel.get(Size).groupReceptacle.get(i).status;
            }

            //groupBox.add(new Box(reel.get(Size).groupBox.get(i).x, reel.get(Size).groupBox.get(i).y, reel.get(Size).groupBox.get(i).isSlippy));

            guy.setPosition(reel.get(Size).guy.x, reel.get(Size).guy.y);
            guy.orientation = reel.get(Size).guy.orientation;
            guy.push = reel.get(Size).guy.push;

            pushes = 0;

            reel.clear();
            restartOrBuild();
            setEmbonated();
            setBoxStatus();
        }
    }

    private void restartOrBuild() {
        //emboned();
        reel.add(new Reel(guy, groupBox, groupReceptacle, groupBrick));
        mark = -1;
        moves = 0;
    }

    public boolean move(int direction) {
        Guy guyTemp = new Guy(guy.x, guy.y);
        guyTemp.move(direction);
        //borrar esto para quitar perinola
        guy.orientation = direction;
        for (int i = 0; i < groupBrick.size(); i++)
            if (groupBrick.get(i).x == guyTemp.x && groupBrick.get(i).y == guyTemp.y) {
                //System.out.println("CASO PARED");
                guy.push = false;
                return false;
            }
        for (int i = 0; i < groupBox.size(); i++)
            if (groupBox.get(i).x == guyTemp.x && groupBox.get(i).y == guyTemp.y) {
                Box tempBox = new Box(groupBox.get(i).x, groupBox.get(i).y);
                tempBox.move(direction);
                for (int j = 0; j < groupBox.size(); j++)
                    if (groupBox.get(j).x == tempBox.x && groupBox.get(j).y == tempBox.y) {
                        //System.out.println("CASO CAJA-CAJA");
                        guy.push = false;
                        return false;
                    }
                for (int j = 0; j < groupBrick.size(); j++)
                    if (groupBrick.get(j).x == tempBox.x && groupBrick.get(j).y == tempBox.y) {
                        //System.out.println("CAJA-PARED");
                        guy.push = false;
                        return false;
                    }
            }

        for (int i = 0; i < groupBox.size(); i++)
            if (groupBox.get(i).x == guyTemp.x && groupBox.get(i).y == guyTemp.y) {
                for (int j = reel.size() - 1; j > mark + 1; j--)
                    reel.remove(j);


                if (!groupBox.get(i).isSlippy) {
                    groupBox.get(i).move(direction);
                    guy.move(direction);
                } else {
                    Box tempBox = new Box(groupBox.get(i).x, groupBox.get(i).y);
                    int j = 0;
                    tempBox.move(direction);
                    while (!contains(tempBox)) {
                        tempBox.move(direction);
                        j++;
                    }
                    //revisar
                    for (int k = 0; k < j; k++)
                        groupBox.get(i).move(direction);

                }
                reel.add(new Reel(guy, groupBox, groupReceptacle, groupBrick));
                mark++;
                //emboned();  //actualiza el status del recepataculo a cada movimiento
                for (int j = 0; j < groupReceptacle.size(); j++)
                    if (groupReceptacle.get(j).status) {
                        //System.out.print("EMBONA-CAJA\n");

                    }
                //System.out.print("MUEVE-CAJA\n");
                guy.push = true;
                setEmbonated();
                moves++;
                return true;
            }
        for (int j = reel.size() - 1; j > mark + 1; j--)
            reel.remove(j);
        guy.move(direction);
        reel.add(new Reel(guy, groupBox, groupReceptacle, groupBrick));
        mark++;
        //System.out.print("MUEVE-GUY\n");
        guy.push = false;
        moves++;
        return true;
    }

    // public  boolean isEmbonated(Box box){
    //	 for(int i=0; i < groupReceptacle.size();i++)
    //		 if(box.x == groupReceptacle.get(i).x && box.y == groupReceptacle.get(i).y)
    //			 return true;
    //	 return false;
    // }
//
    // void setEmbonated(){
    //	for(Box box:groupBox) {
    //		box.isEmbonated = isEmbonated(box);
    //		System.out.println(box.isEmbonated);
    //	}
    // }

    private void setEmbonated() {
        for (Box box : groupBox)
            box.isEmbonated = false;
        for (Receptacle receptacle : groupReceptacle) {
            // System.out.println("setEmbonated");
            receptacle.status = false;
            for (Box box : groupBox) {
                if (receptacle.x == box.x && receptacle.y == box.y) {
                    receptacle.status = true;
                    box.isEmbonated = true;
                    // System.out.println("setEmbonated>>ok");
                }
            }
        }


        //for (Box box : groupBox) {
//      //      System.out.println("setEmbonated");
        //    box.isEmbonated = false;
        //    for (Receptacle receptacle : groupReceptacle) {
        //        receptacle.status = false;
        //        if (receptacle.x == box.x && receptacle.y == box.y) {
        //            receptacle.status = true;
        //            box.isEmbonated = true;
        //            System.out.println("setEmbonated>>ok");
        //        }
        //    }
        //}

    }

    public boolean win() {
        for (int i = 0; i < groupReceptacle.size(); i++)
            if (!(groupReceptacle.get(i).status))
                return false;
        //System.out.print("win\n");
        return true;
    }

    public void undo() {
        //verificar referencias
        if (mark > -1) {
            //System.out.println("undo");

            final int Size = mark;
            for (int i = 0; i < groupBox.size(); i++) {
                groupBox.get(i).setPosition(reel.get(Size).groupBox.get(i).x, reel.get(Size).groupBox.get(i).y);
                groupBox.get(i).isEmbonated = reel.get(Size).groupBox.get(i).isEmbonated;
                groupBox.get(i).isSlippy = reel.get(Size).groupBox.get(i).isSlippy;
            }

            for (int i = 0; i < groupReceptacle.size(); i++) {
                groupReceptacle.get(i).setPosition(reel.get(Size).groupReceptacle.get(i).x, reel.get(Size).groupReceptacle.get(i).y);
                groupReceptacle.get(i).status = reel.get(Size).groupReceptacle.get(i).status;
            }

            //groupBox.add(new Box(reel.get(Size).groupBox.get(i).x, reel.get(Size).groupBox.get(i).y, reel.get(Size).groupBox.get(i).isSlippy));

            guy.setPosition(reel.get(Size).guy.x, reel.get(Size).guy.y);
            guy.orientation = reel.get(Size).guy.orientation;
            guy.push = reel.get(Size).guy.push;

            mark--;
            moves--;
            pushes = reel.get(Size).pushes;
            setEmbonated();
            setBoxStatus();
        }
    }

    public void redo() {
        if (mark < reel.size() - 2) {
            final int Size = mark + 2;
            for (int i = 0; i < groupBox.size(); i++) {
                groupBox.get(i).setPosition(reel.get(Size).groupBox.get(i).x, reel.get(Size).groupBox.get(i).y);
                groupBox.get(i).isEmbonated = reel.get(Size).groupBox.get(i).isEmbonated;
                groupBox.get(i).isSlippy = reel.get(Size).groupBox.get(i).isSlippy;
            }

            for (int i = 0; i < groupReceptacle.size(); i++) {
                groupReceptacle.get(i).setPosition(reel.get(Size).groupReceptacle.get(i).x, reel.get(Size).groupReceptacle.get(i).y);
                groupReceptacle.get(i).status = reel.get(Size).groupReceptacle.get(i).status;
            }

            //groupBox.add(new Box(reel.get(Size).groupBox.get(i).x, reel.get(Size).groupBox.get(i).y, reel.get(Size).groupBox.get(i).isSlippy));

            guy.setPosition(reel.get(Size).guy.x, reel.get(Size).guy.y);
            guy.orientation = reel.get(Size).guy.orientation;
            guy.push = reel.get(Size).guy.push;

            mark++;
            moves++;
            pushes = reel.get(Size).pushes;
            setEmbonated();
            setBoxStatus();
        }

    }

    public void setFloors() {
        ArrayList<Brick> allFloors = allFloors();
        groupDeathFloor = allDeathSpaces();

        for (Brick brick : allFloors)
            groupFloor.add(new Floor(brick.x, brick.y, true));

        for (Floor floor : groupFloor)
            Gdx.app.log("floor.isAlive", "" + floor.isAlive);

        for (Floor floor : groupFloor) {
            if (contain(groupDeathFloor, new Brick(floor.x, floor.y))) {
                floor.isAlive = false;
            }
            //floor.isAlive=true;
        }

    }


    public ArrayList<Brick> allFloors() {
        ArrayList<Brick> adjacents = new ArrayList<Brick>();
        ArrayList<Brick> openList = new ArrayList<Brick>();
        ArrayList<Brick> closeList = new ArrayList<Brick>();
        Brick current = new Brick();
        openList.add(guy);
        do {
            closeList.add(openList.get(0));
            current = new Brick(openList.get(0));
            openList.remove(0);
            adjacents = allAdjacents(closeList, current);
            for (int i = 0; i < adjacents.size(); i++)
                if (!contain(openList, adjacents.get(i)))
                    openList.add(adjacents.get(i));
        } while (!openList.isEmpty());
        return closeList;
    }

    ArrayList<Brick> allAdjacents(ArrayList<Brick> list, Brick brick) {
        ArrayList<Brick> temp = new ArrayList<Brick>();
        if (noCrash(groupBrick, brick.projec(0, 1)) && !contain(list, brick.projec(0, 1)))
            temp.add(brick.projec(0, 1));
        if (noCrash(groupBrick, brick.projec(1, 0)) && !contain(list, brick.projec(1, 0)))
            temp.add(brick.projec(1, 0));
        if (noCrash(groupBrick, brick.projec(0, -1)) && !contain(list, brick.projec(0, -1)))
            temp.add(brick.projec(0, -1));
        if (noCrash(groupBrick, brick.projec(-1, 0)) && !contain(list, brick.projec(-1, 0)))
            temp.add(brick.projec(-1, 0));
        return temp;
    }

    private boolean contain(ArrayList<Brick> list, Brick brick) {
        for (int i = 0; i < list.size(); i++)
            if (brick.compare(list.get(i)))
                return true;
        return false;
    }

    private boolean noCrash(ArrayList<Brick> list, Brick brick) {
        for (int i = 0; i < list.size(); i++)
            if (brick.compare(list.get(i)))
                return false;
        return true;
    }

    //private void buildFloor(){
    //	ArrayList<Brick> deathFloor = new ArrayList<Brick>();
    //	ArrayList<Brick> allFloors = new ArrayList<Brick>();
    //	ArrayList<Brick> temp = new ArrayList<Brick>();
    //	allFloors = allFloors();
    //	for(int i=0; i<allFloors.size(); i++){
    //		if(is(allFloors.get(i))==CORNER){
    //			deathFloor.add(allFloors.get(i));
    //			temp.clear();
    //			for (int j=1; true ; j++){
    //				if(is(allFloors.get(i).projec(0, j))==BRICK)
    //					break;
    //				if(is(allFloors.get(i).projec(0, j))==RECEPTACLE)
    //					break;
    //				if(is(allFloors.get(i).projec(0, j))==FREE)
    //					break;
    //				if(is(allFloors.get(i).projec(0, j))==INMATE)
    //					temp.add(allFloors.get(i).projec(0, j));
    //				if (is(allFloors.get(i).projec(0, j))==CORNER){
    //					for (int k= 0; k< temp.size(); k++)
    //						if (!contain(deathFloor, temp.get(k)))
    //							deathFloor.add(temp.get(k));
    //					//System.out.println("Aquí les llego");
    //					break;
    //				}
    //			}
    //			temp.clear();
    //			for (int j=-1; true ; j--){
    //				if(is(allFloors.get(i).projec(0, j))==BRICK)
    //					break;
    //				if(is(allFloors.get(i).projec(0, j))==RECEPTACLE)
    //					break;
    //				if(is(allFloors.get(i).projec(0, j))==FREE)
    //					break;
    //				if(is(allFloors.get(i).projec(0, j))==INMATE)
    //					temp.add(allFloors.get(i).projec(0, j));
    //				if (is(allFloors.get(i).projec(0, j))==CORNER){
    //					for (int k= 0; k< temp.size(); k++)
    //						if (!contain(deathFloor, temp.get(k)))
    //							deathFloor.add(temp.get(k));
    //					break;
    //				}
    //			}
    //			temp.clear();
    //			for (int j=1; true ; j++){
    //				if(is(allFloors.get(i).projec(j, 0))==BRICK)
    //					break;
    //				if(is(allFloors.get(i).projec(j, 0))==RECEPTACLE)
    //					break;
    //				if(is(allFloors.get(i).projec(j, 0))==FREE)
    //					break;
    //				if(is(allFloors.get(i).projec(j, 0))==INMATE)
    //					temp.add(allFloors.get(i).projec(j, 0));
    //				if (is(allFloors.get(i).projec(j, 0))==CORNER){
    //					for (int k= 0; k< temp.size(); k++)
    //						if (!contain(deathFloor, temp.get(k)))
    //							deathFloor.add(temp.get(k));
    //					break;
    //				}
    //			}
    //			temp.clear();
    //			for (int j=-1; true ; j--){
    //				if(is(allFloors.get(i).projec(j, 0))==BRICK)
    //					break;
    //				if(is(allFloors.get(i).projec(j, 0))==RECEPTACLE)
    //					break;
    //				if(is(allFloors.get(i).projec(j, 0))==FREE)
    //					break;
    //				if(is(allFloors.get(i).projec(j, 0))==INMATE)
    //					temp.add(allFloors.get(i).projec(j, 0));
    //				if (is(allFloors.get(i).projec(j, 0))==CORNER){
    //					for (int k= 0; k< temp.size(); k++)
    //						if (!contain(deathFloor, temp.get(k)))
    //							deathFloor.add(temp.get(k));
    //					break;
    //				}
    //			}
    //		}
    //	}
    //	return deathFloor;
    //}


    public ArrayList<Brick> allDeathSpaces() {
        ArrayList<Brick> deathFloor = new ArrayList<Brick>();
        ArrayList<Brick> allFloors = new ArrayList<Brick>();
        ArrayList<Brick> temp = new ArrayList<Brick>();
        allFloors = allFloors();
        for (int i = 0; i < allFloors.size(); i++) {
            if (is(allFloors.get(i)) == CORNER) {
                deathFloor.add(allFloors.get(i));
                temp.clear();
                for (int j = 1; true; j++) {
                    if (is(allFloors.get(i).projec(0, j)) == BRICK)
                        break;
                    if (is(allFloors.get(i).projec(0, j)) == RECEPTACLE)
                        break;
                    if (is(allFloors.get(i).projec(0, j)) == FREE)
                        break;
                    if (is(allFloors.get(i).projec(0, j)) == INMATE)
                        temp.add(allFloors.get(i).projec(0, j));
                    if (is(allFloors.get(i).projec(0, j)) == CORNER) {
                        for (int k = 0; k < temp.size(); k++)
                            if (!contain(deathFloor, temp.get(k)))
                                deathFloor.add(temp.get(k));
                        //System.out.println("Aquí les llego");
                        break;
                    }
                }
                temp.clear();
                for (int j = -1; true; j--) {
                    if (is(allFloors.get(i).projec(0, j)) == BRICK)
                        break;
                    if (is(allFloors.get(i).projec(0, j)) == RECEPTACLE)
                        break;
                    if (is(allFloors.get(i).projec(0, j)) == FREE)
                        break;
                    if (is(allFloors.get(i).projec(0, j)) == INMATE)
                        temp.add(allFloors.get(i).projec(0, j));
                    if (is(allFloors.get(i).projec(0, j)) == CORNER) {
                        for (int k = 0; k < temp.size(); k++)
                            if (!contain(deathFloor, temp.get(k)))
                                deathFloor.add(temp.get(k));
                        break;
                    }
                }
                temp.clear();
                for (int j = 1; true; j++) {
                    if (is(allFloors.get(i).projec(j, 0)) == BRICK)
                        break;
                    if (is(allFloors.get(i).projec(j, 0)) == RECEPTACLE)
                        break;
                    if (is(allFloors.get(i).projec(j, 0)) == FREE)
                        break;
                    if (is(allFloors.get(i).projec(j, 0)) == INMATE)
                        temp.add(allFloors.get(i).projec(j, 0));
                    if (is(allFloors.get(i).projec(j, 0)) == CORNER) {
                        for (int k = 0; k < temp.size(); k++)
                            if (!contain(deathFloor, temp.get(k)))
                                deathFloor.add(temp.get(k));
                        break;
                    }
                }
                temp.clear();
                for (int j = -1; true; j--) {
                    if (is(allFloors.get(i).projec(j, 0)) == BRICK)
                        break;
                    if (is(allFloors.get(i).projec(j, 0)) == RECEPTACLE)
                        break;
                    if (is(allFloors.get(i).projec(j, 0)) == FREE)
                        break;
                    if (is(allFloors.get(i).projec(j, 0)) == INMATE)
                        temp.add(allFloors.get(i).projec(j, 0));
                    if (is(allFloors.get(i).projec(j, 0)) == CORNER) {
                        for (int k = 0; k < temp.size(); k++)
                            if (!contain(deathFloor, temp.get(k)))
                                deathFloor.add(temp.get(k));
                        break;
                    }
                }
            }
        }
        return deathFloor;
    }


    private int getIndex(Brick brick) {
        int i = 0;
        for (Floor floor1 : groupFloor) {
            if (brick.x == floor1.x && brick.y == floor1.y)
                return i;
            i++;
        }
        return 0;
    }

    private int is(Brick brick) {
        if (containsBrick(brick)) {
            return BRICK;
        }
        for (int i = 0; i < groupReceptacle.size(); i++)
            if (brick.compare(groupReceptacle.get(i))) {
                // brick.print();
                //System.out.println(": IS RECEPTACLE");
                return RECEPTACLE;
            }
        if ((containsBrick(brick.projec(-1, 0)) && containsBrick(brick.projec(0, -1))) || (containsBrick(brick.projec(1, 0)) && containsBrick(brick.projec(0, -1))) || (containsBrick(brick.projec(-1, 0)) && containsBrick(brick.projec(0, 1))) || (containsBrick(brick.projec(1, 0)) && containsBrick(brick.projec(0, 1)))) {
            // brick.print();
            // System.out.println(": IS CORNRE");
            return CORNER;
        }
        if (containsBrick(brick.projec(-1, 0)) || containsBrick(brick.projec(1, 0)) || containsBrick(brick.projec(0, -1)) || containsBrick(brick.projec(0, 1))) {
            //  brick.print();
            //  System.out.println(": IS INMATE");
            return INMATE;
        }
        // brick.print();
        // System.out.println(": IS FREE");
        return FREE;
    }

    boolean containsBrick(Brick brick) { //bien
        for (int i = 0; i < groupBrick.size(); i++)
            if (brick.compare(groupBrick.get(i)))
                return true;
        return false;
    }

    boolean isAlive() {
        for (int i = 0; i < groupBox.size(); i++)
            if (!groupBox.get(i).isAlive)
                return false;
        for (int i = 0; i < groupBox.size(); i++)
            if (contain(groupDeathFloor, groupBox.get(i)))
                return false;
        return true;
    }

    boolean isAlive(Box box) {
        if (box.isEmbonated)
            return true;
        if (contains(box.translate(-1, 0)) && contains(box.translate(0, -1)) && contains(box.translate(-1, -1)))
            return false;
        if (contains(box.translate(1, 0)) && contains(box.translate(0, -1)) && contains(box.translate(1, -1)))
            return false;
        if (contains(box.translate(-1, 0)) && contains(box.translate(0, 1)) && contains(box.translate(-1, 1)))
            return false;
        if (contains(box.translate(1, 0)) && contains(box.translate(0, 1)) && contains(box.translate(1, 1)))
            return false;
        //TODO quitar allDeathSpaces
        if (contain(groupDeathFloor, box))
            return false;
        return true;
    }

    public void setBoxStatus() {
        for (Box box : groupBox)
            box.isAlive = isAlive(box);
    }


    boolean contains(Brick brick) { //bien
        for (int i = 0; i < groupBrick.size(); i++)
            if (brick.compare(groupBrick.get(i)))
                return true;
        for (int i = 0; i < groupBox.size(); i++)
            if (brick.compare((Brick) groupBox.get(i)))
                return true;
        return false;
    }

    public int enhancedMove(int direction) {
        Guy guyTemp = new Guy(guy.x, guy.y);
        guyTemp.move(direction);
        //borrar esto para quitar perinola
        guy.orientation = direction;

        for (Brick brick : groupBrick)
            if (brick.x == guyTemp.x && brick.y == guyTemp.y) {
                guy.push = false;
                //System.out.println("CASO PARED");
                return -2;
            }

        for (Box boxI : groupBox)
            if (boxI.x == guyTemp.x && boxI.y == guyTemp.y) {
                Box tempBox = new Box(boxI.x, boxI.y);
                tempBox.move(direction);
                for (Box boxJ : groupBox)
                    if (boxJ.x == tempBox.x && boxJ.y == tempBox.y) {
                        guy.push = false;
                        //System.out.println("CASO CAJA-CAJA");
                        return -2;
                    }
                for (Brick brick : groupBrick)
                    if (brick.x == tempBox.x && brick.y == tempBox.y) {
                        guy.push = false;
                        // System.out.println("CAJA-PARED");
                        return -2;
                    }
            }

        //estoy seguro que va a haber movimiento de cajas

        for (int i = 0; i < groupBox.size(); i++)
            if (groupBox.get(i).x == guyTemp.x && groupBox.get(i).y == guyTemp.y) {
                for (int j = reel.size() - 1; j > mark + 1; j--)
                    reel.remove(j);
                groupBox.get(i).move(direction);
                guy.move(direction);
                //if (!groupBox.get(i).isSlippy) {
                //    groupBox.get(i).move(direction);
                //    guy.move(direction);
                //} else {
                //    Box tempBox = new Box(groupBox.get(i).x, groupBox.get(i).y);
                //    int j = 0;
                //    tempBox.move(direction);
                //    while (!contains(tempBox)) {
                //        tempBox.move(direction);
                //        j++;
                //    }
                //    //revisar
                //    for (int k = 0; k < j; k++)
                //        groupBox.get(i).move(direction);
//
                //}

                reel.add(new Reel(guy, groupBox, groupReceptacle, groupBrick));
                mark++;
                //emboned();  //actualiza el status del recepataculo a cada movimiento


                setEmbonated();
                //for (Box box : groupBox) {
                //    if (box.isEmbonated)
                //        //System.out.println("isEmbonated");
                //    //System.out.println(" ");
                //}

                //for (int j = 0; j < groupReceptacle.size(); j++)
                //    if (groupReceptacle.get(j).status) {
                //        System.out.print("EMBONA-CAJA\n");
                //    }
                //System.out.print("MUEVE-CAJA\n");
                setBoxStatus();

                guy.push = true;
                System.out.println(pushes);
                reel.get(reel.size() - 1).pushes = ++pushes;

                moves++;
                return i;
                //return 1;
            }
        guy.move(direction);
        guy.push = false;
        //System.out.print("MUEVE-GUY\n");
        mark++;
        //pushes++;

        moves++;
        for (int j = reel.size() - 1; j > mark + 1; j--)
            reel.remove(j);
        reel.add(new Reel(guy, groupBox, groupReceptacle, groupBrick));
        reel.get(reel.size() - 1).pushes = pushes;
        return -1;
    }

    public int getMoves() {
        return moves;
    }

    public int getPushes() {
        return pushes;
    }

}
