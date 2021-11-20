import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

class Color {
    int r;
    int g;
    int b;
}

class Point {
    int x;
    int y;
}

class Rectangle {
    Point p; // lowerleft
    int width;
    int height;
    Color c;
}

class Circle {
    Point p; // center
    int r; // radius
    Color c;
}

class Triangle {
    TriPoint p1;
    TriPoint p2;
    TriPoint p3;
    Color c;
}
class TriPoint {
    Rational x;
    Rational y;
}
class Rational {
    int n;
    int d;
}

class shapes {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("What .shapes file do you want to draw from?");
        String filename = s.nextLine();
        String[] shapesFile = readFile(filename);
        int i;
        for (i = 0; !shapesFile[0].substring(i,i+1).equals(" "); i++) {

        }
        int width = Integer.parseInt(shapesFile[0].substring(0,i));
        int height = Integer.parseInt(shapesFile[0].substring(i+1));
        System.out.println("width & height: " + width + " " + height);
        System.out.println("Name of file?");
        String nextFile = s.nextLine();
        Color[] colArr = new Color[width * height];
        for (int m = 0; colArr.length > m; m++) {
            colArr[m] = new Color();
            colArr[m].r = 0;
            colArr[m].g = 0;
            colArr[m].b = 0;
        }
        int[] ia;
        for (int m = 1; m < shapesFile.length; m++) {
            if (shapesFile[m].substring(0,1).equals("r")) {
                System.out.println("Rectangle.");
                ia = numArray(shapesFile[m], 10);
                Rectangle r = new Rectangle();
                //Point r.p = new Point();
                //Color r.c = new Color();
                r.p = new Point();
                r.c = new Color();
                r.p.x = ia[0];
                r.p.y = ia[1];
                r.width = ia[2];
                r.height = ia[3];
                r.c.r = ia[4];
                r.c.g = ia[5];
                r.c.b = ia[6];
                Point pama = pixelToCoord(0, 5, 9);
                System.out.println("pama.x: " + pama.x);
                System.out.println("pama.y: " + pama.y);
                colArr = addRect(colArr, r, width, height);
            }
            if (shapesFile[m].substring(0,1).equals("c")) {
                System.out.println("Circle.");
                ia = numArray(shapesFile[m], 7);
                Circle c = new Circle();
                c.p = new Point();
                c.c = new Color();
                c.p.x = ia[0];
                c.p.y = ia[1];
                c.r = ia[2];
                c.c.r = ia[3];
                c.c.g = ia[4];
                c.c.b = ia[5];
                colArr = addCir(colArr, c, width, height);
            }
            if (shapesFile[m].substring(0,1).equals("t")) {
                System.out.println("Triangle.");
                ia = numArray(shapesFile[m], 9);
                Triangle t = new Triangle();
                t.p1 = new TriPoint();
                t.p2 = new TriPoint();
                t.p3 = new TriPoint();
                t.p1.x = new Rational();
                t.p2.x = new Rational();
                t.p3.x = new Rational();
                t.p1.y = new Rational();
                t.p2.y = new Rational();
                t.p3.y = new Rational();
                t.c = new Color();
                t.p1.x.n = ia[0];
                t.p1.y.n = ia[1];
                t.p2.x.n = ia[2];
                t.p2.y.n = ia[3];
                t.p3.x.n = ia[4];
                t.p3.y.n = ia[5];
                t.p1.x.d = 1;
                t.p2.x.d = 1;
                t.p3.x.d = 1;
                t.p1.y.d = 1;
                t.p2.y.d = 1;
                t.p3.y.d = 1;
                t.c.r = ia[6];
                t.c.g = ia[7];
                t.c.b = ia[8];
                colArr = addTri(colArr, t, width, height);
            }
            System.out.println("Spaces: " + spaces(shapesFile[m]));
        }
        String[] endThing = new String[height + 3];
        endThing[0] = "P3";
        endThing[1] = width + " " + height;
        endThing[2] = "255";
        int n = 0;
        int limiter;
        boolean firstRotation;
        for (int m = 3; m < height + 3; m++) {
            firstRotation = true;
            limiter = n + width;
            for (n = n; n < limiter; n++) {
                if (firstRotation) {
                    endThing[m] = colArr[n].r + " " + colArr[n].g + " " + colArr[n].b + " ";
                    firstRotation = false;
                } else {
                    endThing[m] = endThing[m] + colArr[n].r + " " + colArr[n].g + " " + colArr[n].b + " ";
                }
            }
        }
        writeFile(nextFile, endThing);
    }
     public static String[] readFile(String filename) {
        ArrayList<String> lines1 = new ArrayList<String>();
        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);
            while(s.hasNextLine()) {
                String data = s.nextLine();
                lines1.add(data);
            }
            s.close();
        } catch(FileNotFoundException e) {
            System.out.println("File  " + filename + " not found.");
        }

        String[] lines2 = new String[lines1.size()];
        lines2 = lines1.toArray(lines2);

        return lines2;
    }
    public static void writeFile(String filename, String[] contents) {
        try {
            FileWriter fw = new FileWriter(filename);
            for(int i = 0; i < contents.length; i++) {
                fw.write(contents[i] + "\n");
            }
        fw.close();
        } catch(IOException e) {
            System.out.println("Could not write to " + filename);
        }
    }
    public static int readNum(String s, int i) {
        String newNum = "";
        while (i < s.length() && !s.substring(i,i+1).equals(" ")) {
            newNum = newNum + s.substring(i,i+1);
            i++;
            // System.out.println("String s: " + s.substring(i,i+1));
        }
        int t = -1;
        if (!newNum.equals("")) {
            t = Integer.parseInt(newNum);
        }
        return t;
    }
    public static int spaces(String s) {
        int r = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.substring(i,i+1).equals(" ")) {
                r++;
            }
        }
        return r;
        //7 7 7 7 7 7 
    }
    public static int[] numArray(String s, int i) { // i = starting point; has to be less than the string length
        int[] ia;
        if (spaces(s) == 9) {
            ia = new int[9];
        } else if (spaces(s) == 6) {
            ia = new int[6];
        } else {
            ia = new int[7];
        }
        int beginning = i;
        for (i = 0; i < ia.length; i++) {
            ia[i] = readNum(s, beginning);
            beginning = returnI(s, beginning);
        }
        return ia;
    }
    public static int returnI(String s, int i) {
        while (i < s.length() && !s.substring(i,i+1).equals(" ")) {
            i++;
        }
        i++;
        return i;
    }
    public static void printArray(int[] ia) {
        for (int i = 0; i < ia.length; i++) {
            System.out.println("[ " + i + " ] " + ia[i]);
        }
    }
    public static Point pixelToCoord(int i, int width, int height) { // Color[] array is the inputted array while "i" is the integer in the array.
        Point p = new Point();
        p.y = i / width;
        p.x = i % width;
        p.y = height - p.y - 1;
        return p;
    }
    public static Color[] addRect(Color[] ca, Rectangle r, int width, int height) {
        Point limiter = new Point();
        limiter.x = r.p.x + r.width;
        limiter.y = r.p.y + r.height;
        Point p;
        System.out.println("lower and higher:");
        for (int i = 0; i < ca.length; i++) {
            p = pixelToCoord(i, width, height);
            if (inBetween(p, r.p, limiter)) {
                ca[i].r = r.c.r;
                ca[i].g = r.c.g;
                ca[i].b = r.c.b;
            }
        }
        return ca;
    }
    public static boolean inBetween(Point checked, Point smaller, Point larger) {
        if (checked.x >= smaller.x && checked.x <= larger.x && checked.y >= smaller.y && checked.y <= larger.y) return true;
        return false;
    }
    public static void printColor(Color c) {
        System.out.println("Color: " + c.r + ", " + c.g + ", " + c.b);
    }
    public static void printPoint(Point p) {
        System.out.println("x: " + p.x);
        System.out.println("y: " + p.y);
    }
    public static Color[] addCir(Color[] ca, Circle c, int width, int height) {
        Point p;
        int o;
        for (int i = 0; i < ca.length; i++) {
            p = pixelToCoord(i, width, height);
            o = (p.x - c.p.x) * (p.x - c.p.x) + (p.y - c.p.y) * (p.y - c.p.y);
            if (o < Math.pow(c.r, 2)) {
                ca[i].r = c.c.r;
                ca[i].g = c.c.g;
                ca[i].b = c.c.b;
            }
        }
        return ca;
    }
    public static Color[] addTri(Color[] ca, Triangle t, int width, int height) {
        Point p;
        TriPoint triPoint;
        for (int i = 0; i < ca.length; i++) {
            p = pixelToCoord(i, width, height);
            triPoint = pointToTri(p);
            
            if (pointInside(t, triPoint)) {
                ca[i].r = t.c.r;
                ca[i].g = t.c.g;
                ca[i].b = t.c.b;
            }
        }
        return ca;
    }
    public static TriPoint pointToTri(Point p) {
        TriPoint t = new TriPoint();
        t.x = new Rational();
        t.y = new Rational();
        t.x.n = p.x;
        t.y.n = p.y;
        t.x.d = 1;
        t.y.d = 1;
        return t;
    }
    public static boolean pointInside(Triangle m, TriPoint n) {
        Rational slope1 = new Rational();
        Rational slope2 = new Rational();
        Rational slope3 = new Rational();
        slope1 = findSlope(m.p1, m.p2);
        slope2 = findSlope(m.p1, m.p3);
        slope3 = findSlope(m.p3, m.p2);
        // slope1: 1, 2
        // slope2: 1, 3
        // slope3: 2, 3
        Rational b1 = findIntercept(m.p1, m.p2);
        Rational b2 = findIntercept(m.p1, m.p3);
        Rational b3 = findIntercept(m.p3, m.p2);

        Rational equation1 = add(mult(slope1, m.p3.x), b1);
        Rational equation2 = add(mult(slope2, m.p2.x), b2);
        Rational equation3 = add(mult(slope3, m.p1.x), b3);
        Rational equation4 = add(mult(slope1, n.x), b1);
        Rational equation5 = add(mult(slope2, n.x), b2);
        Rational equation6 = add(mult(slope3, n.x), b3);
        
        if (slope1.d == 0) {
            if (lessThan(m.p3.x, b1)) {
                if (!lessThan(n.x, b1)) {
                    return false;
                }
            } else {
                if (!greaterThan(n.x, b1)) {
                    return false;
                }
            }
        }
        if (slope2.d == 0) {
            if (lessThan(m.p2.x, b2)) {
                if (!lessThan(n.x, b2)) {
                    return false;
                }
            } else {
                if (!greaterThan(n.x, b2)) {
                    return false;
                }
            }
        }
        if (slope3.d == 0) {
            if (lessThan(m.p1.x, b3)) {
                if (!lessThan(n.x, b3)) {
                    return false;
                }
            } else {
                if (!greaterThan(n.x, b3)) {
                    return false;
                }
            }
        }
        if (lessThan(m.p3.y, equation1)) {
            if (!lessThan(n.y, equation4)) {
                return false;
            }
        } else {
            if (!greaterThan(n.y, equation4)) {
                return false;
            }
        }
        if (lessThan(m.p2.y, equation2)) {
            if (!lessThan(n.y, equation5)) {
                return false;
            }
        } else {
            if (!greaterThan(n.y, equation5)) {
                return false;
            }
        }
        if (lessThan(m.p1.y, equation3)) {
            if (!lessThan(n.y, equation6)) {
                return false;
            }
        } else {
            if (!greaterThan(n.y, equation6)) {
                return false;
            }
        }
        return true;
    }
    public static boolean greaterThan(Rational a, Rational b) {
        boolean aNegative = ((a.n < 0) != (a.d < 0));
        boolean bNegative = ((b.n < 0) != (b.d < 0));
        if (ifEqual(a,b)) {
            //return false;
        }
        if (a.n < 0 && a.d < 0) {
            a.n = a.n * -1;
            a.d = a.d * -1;
        }
        if (b.n < 0 && b.d < 0) {
            b.n = b.n * -1;
            b.d = b.d * -1;
        }
        if (a.n * b.d > a.d * b.n) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean lessThan(Rational a, Rational b) {
        boolean aNegative = ((a.n < 0) != (a.d < 0));
        boolean bNegative = ((b.n < 0) != (b.d < 0));
        if (ifEqual(a,b)) {
            //return false;
        }
        if (a.n < 0 && a.d < 0) {
            a.n = a.n * -1;
            a.d = a.d * -1;
        }
        if (b.n < 0 && b.d < 0) {
            b.n = b.n * -1;
            b.d = b.d * -1;
        }
        if (a.n * b.d < a.d * b.n) {
            return true;
        } else {
            return false;
        }
    }
    public static Rational add(Rational a, Rational b) {
        Rational c = new Rational();
        c.d = a.d * b.d;
        int stuff1, stuff2;
        stuff1 = a.n * b.d;
        stuff2 = b.n * a.d;
        c.n = stuff1 + stuff2;
        //simplify(c);
        return c;
    }
    public static Rational mult(Rational a, Rational b) {
        Rational c = new Rational();
        c.n = a.n * b.n;
        c.d = a.d * b.d;
        //simplify(c);
        return c;
    }
    public static Rational findSlope(TriPoint a, TriPoint b) {
        /*
        slope.n = a.y.n - b.y.n;
        slope.d = a.x.d - b.x.d;
        */
        // a.y - b.y = numerator
        // a.x - b.x = denominator
        // Create a new rational that is the negative version of b
        Rational bNegativey = new Rational();
        Rational bNegativex = new Rational();
        bNegativey.n = b.y.n * -1;
        bNegativex.n = b.x.n * -1;
        bNegativey.d = b.y.d;
        bNegativex.d = b.x.d;
        /*
        slope.n = add(a.y, b.y);
        slope.d = add(a.x, b.x);
        */
        Rational dy = add(a.y, bNegativey);
        Rational dx = add(a.x, bNegativex);
        //divide(add(a.y,b.y));
        //divide(add(a.x,b.x));
        dy = divide(dy,dx);
        //simplify(dy);
        return dy;
    }
    public static Rational findIntercept(TriPoint a, TriPoint b) {
        Rational m = new Rational();
        m = findSlope(a, b);
        
        // y = mx + b;
        // b = y - mx;
        // a.y.n = m(a.x.n) + b
        Rational total = new Rational();
        if (ifEqual(a.x,b.x)) {
            return a.x;
        }
        if (a.x.n * b.x.d == a.x.d * b.x.n) {
            total.n = 0;
            total.d = 1;
            return total;
        }
        /*
        if (m.d == 0) {
        Rational temp = new Rational();
        temp.n = a.x.n;
        temp.d = a.x.d;
        if (a.x.d == b.x.d) {
            // c.d and d.d are the slope you should use.
            temp = add(mult(findSlope(), a.x.c), findIntercept(a.x.c, a.x.d));
        } else {
            // a.d and b.d
        }
        // a.x is the x value for the intersection when bottom is 0.
        }*/
        total = mult(m, a.x);
        total.n = total.n * -1;
        total = add(total, a.y);
        //simplify(total);
        return total;
    }
    public static boolean ifEqual(Rational a, Rational b) {
        if (a.n * b.d == a.d * b.n) {
            return true;
        } else {
            return false;
        }
    }
    public static void simplify(Rational num) {
        // If the signs are different the entire rational is negative
        boolean negative = ((num.n < 0) != (num.d < 0));
        // Remove signs
        if(num.n < 0) {
            num.n = num.n * -1;
        }
        if(num.d < 0) {
            num.d = num.d * -1;
        }
        // Reduce both the numerator and denominator by largest possible number
        for(int i = min(num.n, num.d); i > 0; i--) {
            if((num.n % i == 0) && (num.d % i == 0)) {
                num.n = num.n / i;
                num.d = num.d / i;
            }
        }
        // Finally, apply the sign
        if(negative) {
            num.n = num.n * -1;
        }
        // Special case: zero
        if(num.n == 0) {
            num.d = 1;
        }
    }
    public static Rational divide(Rational a, Rational b) {
        Rational c = new Rational();
        c.n = b.d;
        c.d = b.n;
        c = mult(a,c);
        //simplify(c);
        return c;
    }
    public static int min(int a, int b) {
        return a < b ? a : b;
    }
    public static void printRational(Rational a) {
        System.out.println("printedRational: " + a.n + "/" + a.d);
    }
}