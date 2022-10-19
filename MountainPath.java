// Maaz Karim - 24503

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
// Maaz Karim - 24503

public class MountainPath {

    public static int[][] grid;

    public static void init(String filename, int rows, int cols) {
        // read data from given file into a 2 D array
        MountainPath.grid
                = new int[rows][cols];
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException f) {
            return;
        }
        int i = 0;
        int j = 0;
        while (sc.hasNextInt()) {
            int x = sc.nextInt();
            MountainPath.grid[i][j] = x;
            j++;
            if (j == grid[0].length) {
                j = 0;
                i++;
            }
        }
    }

    public static int findMin() {
        int minimum = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (MountainPath.grid[i][j] < minimum) minimum = MountainPath.grid[i][j];
            }
        }
        return minimum;
    }


    public static int findMax() {
        int maximum = Integer.MIN_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (MountainPath.grid[i][j] > maximum) maximum = MountainPath.grid[i][j];
            }
        }
        return maximum;
    }


    public static void drawMap() {
        int x = findMin();
        int y = findMax();
        double div = ((y - x) / 256.0);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int c = (int) ((MountainPath.grid[i][j] - x) / div);
                if (c >= 256) c = 255;
                StdDraw.setPenColor(new Color(c, c, c));
                StdDraw.filledSquare(j, grid.length - i - 1, 0.5);
            }
        }
    }


    public static int indexOfMinRow(int col) {
        int min = Integer.MAX_VALUE;
        int a = 0;
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][col] < min) {
                min = grid[i][col];
                a = i;
            }
        }
        return a;
    }


    public static int drawLowestElevPath(int row) {
        int total = 0;
        int temp = row;
        StdDraw.filledSquare(temp, 0, 0.5);
        for (int i = 0; i < grid[0].length - 1; i++) {
            int f = temp + 1;
            int u = temp - 1;
            if (temp == 0) u = temp;
            if (temp == grid.length - 1) f = temp;
            int a = Math.abs(MountainPath.grid[temp][i] - MountainPath.grid[temp][i + 1]);
            int b = Math.abs(MountainPath.grid[temp][i] - MountainPath.grid[f][i + 1]);
            int c = Math.abs(MountainPath.grid[temp][i] - MountainPath.grid[u][i + 1]);
            if (a < b && a < c) {
                total = total + a;
            } else if (b < a && b < c) {
                temp = f;
                total = total + b;
            } else if (c < a && c < b) {
                temp = u;
                total = total + c;
            } else if (a < c && a == b) total = total + a;
            else if (a < b && a == c) total = total + a;
            else if (b < a && c == b) {
                double toss = Math.random();
                if (toss < 0.5) {
                    temp = f;
                } else temp = u;
                total = total + c;
            }
            StdDraw.filledSquare(i, grid.length - temp - 1, 0.5);
        }
        return total;
    }

    public static int indexOfLowestElevPath() {
        int x = Integer.MAX_VALUE;
        int a = 0;
        for (int i = 0; i < grid.length; i++) {
            int n = drawLowestElevPath(i);
            if (n < x) {
                x = n;
                a = i;
            }
        }
        return a;
    }

    public static void main(String[] args) {
        String f = "Colorado_844cols_480rows.dat";
        int rows = 480;
        int cols = 844;
        int n = 1;  //insert the number that u would like enlarge the image by
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(n * (cols), n * (rows));
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        init(f, rows, cols);
        drawMap();
        System.out.println("max--> " + findMax());
        System.out.println("min--> " + findMin());
        StdDraw.setPenColor(StdDraw.RED);
        int t = indexOfLowestElevPath();
        StdDraw.setPenColor(StdDraw.GREEN);
        drawLowestElevPath(t);
        StdDraw.show();
        System.out.println("the shortest elevation change is at index: " + t);
        System.out.println("the shortest elevation change is: " + drawLowestElevPath(t));
        int Rowind = 50;
        System.out.println("The lowest point in column " + Rowind + " is: " + indexOfMinRow(50));
    }
}

