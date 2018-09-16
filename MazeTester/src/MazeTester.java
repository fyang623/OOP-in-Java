import java.io.*;
import java.util.*;

public class MazeTester {
    public static void main(String[] args) {
        Maze labyrinth = new Maze("/Users/Fred/Desktop/labyrinth.txt");		
        System.out.println(labyrinth);
        MazeSolver solver = new MazeSolver(labyrinth);
        if (solver.traverse()) {
            System.out.println("Traversal done!");
            System.out.println("Maze successfully solved!\n");
            System.out.println(labyrinth);
        }
        else System.out.println("There is no possible path!");			
    }
}

class MazeSolver {
    Maze maze;
    LinkedList<Position> stack = new LinkedList<>();

    MazeSolver (Maze maze) {
        this.maze = maze;
    }

    boolean traverse () {
        stack.push(new Position());
        Position pos; 
        while (!stack.isEmpty()) {
            pos = stack.pop();
            maze.tryPosition(pos);
            if(pos.getX() == maze.getRow()-1 && pos.getY() == maze.getColumn()-1) {
                return true;
            }
            else {
                push_pos(new Position(pos.getX()-1, pos.getY()));
                push_pos(new Position(pos.getX()+1, pos.getY()));
                push_pos(new Position(pos.getX(), pos.getY()-1));				
                push_pos(new Position(pos.getX(), pos.getY()+1));
            }
        }
        return false;
    }

    void push_pos(Position pos) {
        if (maze.validPosition(pos)) stack.push(pos);
    }
}

class Maze {
    int[][] grid;
    int row, column;

    Maze (String fileName) {
        Scanner scan = null;
        try{
            File f = new File(fileName);
            scan = new Scanner(f);
            System.out.println("\n"+f.getName()+"\n");
            row = scan.nextInt();
            column = scan.nextInt();
            grid = new int[row][column];
            for(int i=0; i<row;i++) 
                for (int j=0; j<column; j++) 
                    grid[i][j] = scan.nextInt();

        }		
        catch (FileNotFoundException | InputMismatchException e) {
            e.printStackTrace();
        } finally {
            assert scan != null;
            scan.close();
        }
    }

    int getRow () {
        return row;
    }

    int getColumn () {
        return column;
    }

    boolean validPosition (Position pos) {
        if (pos.getX()>=0 && pos.getY()>=0 && pos.getX()<row && pos.getY()<column && grid[pos.getX()][pos.getY()] == 1) return true;
        else return false;
    }

    void tryPosition (Position pos) {
        grid[pos.getX()][pos.getY()] = 2;
    }

    public String toString() {
        String str = "";
        for(int i=0; i<row;i++) {
            for (int j=0; j<column; j++) {
                str += grid[i][j] + " ";
            }
            str += "\n";
        }
        return str;
    }
}

class Position {
    int x, y;

    Position () {
        x = 0; 
        y = 0;
    }

    Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX () {
        return this.x;
    }

    int getY() {
        return this.y;
    }
}
