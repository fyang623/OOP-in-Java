package hanoitowers;

public class HanoiTowers {
    static private int disks = 3;
    private static int start = 1, end = 3, transit = 2;
    
    public static void main(String[] args) {
         moveDisks(disks, start, transit, end);
    }
    
    private static void moveDisks(int disks, int start, int transit, int end){
        if (disks==1) {
            System.out.println(start + " to " + end);     
        }
        else {
            moveDisks(disks-1, start, end, transit);
            System.out.println(start + " to " + end);
            moveDisks(disks-1, transit, start, end);
        }
    }
}
