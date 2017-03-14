import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class VaccumRobotProblem {
    
    private int rows;
    private int cols;
    private int crow;
    private int ccol;
    private int lag;
    
    public static void main(String args[]) {
        VaccumRobotProblem v = new VaccumRobotProblem();
        v.solve();
    }
    
    public void solve() {
        
        Boolean cont = true;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter N: ");
        rows = in.nextInt();
        System.out.print("Enter M: ");
        cols = in.nextInt();
        System.out.print("Enter start x: ");
        crow = in.nextInt();
        System.out.print("Enter start y: ");
        ccol = in.nextInt();
        System.out.print("Enter lag (d): ");
        lag = in.nextInt();
        in.nextLine();
        
        Position firstpos = new Position(crow,ccol);
        Move last = new Move();
        last.realposition = firstpos;
        last.move = "R";
        int step = 1;
        int filteredx = 0;
        int filteredy = 0;
        System.out.println("q to quit, 'Enter' to continue moving robot");
        int totalstates = 4*(rows*cols);
        int totalevidence = rows*cols;
        
        HMM2 markov = new HMM2(totalstates, totalevidence);
        
        
        //initialize the initial state to the actual initial state of the environment 
        double pi[] = new double[totalstates];
        int start = getsinglearrvalue(firstpos.row,firstpos.col);
        start+=3;
        for(int i = 0;i < totalstates; i++) {
            if(i == start)
                pi[i] = 1;
            else
                pi[i] = 0;
        }
        
        //initializes transition matrix
        double a[][] = new double[totalstates][totalstates];
        for(int i=0;i<totalstates;i++) {
            for(int j=0;j<totalstates;j++) {
                a[i][j] = getTransitionfromitoj(i,j);
            }
        }
        
        //initializes sensor matrix
        double b[][] = new double[totalstates][totalevidence];
        for(int i=0;i<totalstates;i++) {
            for(int j=0;j<totalevidence;j++) {
                b[i][j] = getSensorvalue(i,j);
            }
        }
        
        //use matrices in HMM2 class
        markov.setA(a);
        markov.setB(b);
        markov.setPi(pi);
        
        ArrayList<Integer> prevstates = new ArrayList<Integer>();
            
        while(cont) {
            //  Move the robot to a new location according to its motion policy 
            // given in the problem. This is the new state variable, but it is unobserved. 
            // It will be used in the next step to generate the evidence variable.
            last = moveRobot(last.realposition,last.move);
            
            // Generate the robot’s new location (noisy) based on its actual location 
            // in the above step and the error model given in the problem. This is the 
            // evidence variable.       
            Position robotsensedpos = getlocationfromsensor(last.realposition);
            if(robotsensedpos!=null) {
                int robotpos = getsinglearrvalue(robotsensedpos.row,robotsensedpos.col);
                if(prevstates.isEmpty())
                    prevstates.add(robotpos);
                else
                    prevstates.add(0,robotpos);
            }
            else {
                if(!prevstates.isEmpty())
                    prevstates.add(0,prevstates.get(0));
            }
            
            if(prevstates.size() > lag) {
                prevstates.remove(prevstates.size()-1);
            }
            
            int prev[] = new int[prevstates.size()];
            for(int i=0;i<prev.length;i++) {
                prev[i] = prevstates.get(i);
            }
            
            
            // Run the HMM based on the evidence variables to calculate the new state.
            double[][] t = null;
            if(prev.length != 0) 
                t = markov.backwardProc(prev);
            
            HashMap<Integer,Double> normalize = new HashMap<Integer,Double>();
            double totalsum = 0;
            filteredx = normalizevar(last.realposition.row,step);
            filteredy = normalizevar(last.realposition.col,step);
            if(t!=null) {
                for(int i=0;i<t.length;i++) {
                    double sum = 0;
                    for(int j=0;j<t[i].length;j++) {
                        if(t[i][j]!=0.0)
                            sum+=t[i][j];
                    }
                    normalize.put(i, sum);
                    totalsum+=sum;
                }       
            }
            
            if(prev.length != 0) 
                t = markov.forwardProc(prev);
            
            double normalized[] = new double[totalstates];
            for(int i=0;i<normalize.size();i++) {
                if(normalize.get(i) != 0)
                    normalized[i] = normalize.get(i)/totalsum;
            }
            
            int max = 0;
            for(int x=0;x<normalized.length;x++) {
                if (normalized[x] > max) 
                    max = x;
            }
            
            // Output the evidence state (location) and filtered state (location) in the 
            // format below, where (xe1, ye1), (xf1, yf1), etc. are coordinates calculated
            // by your program
            if(robotsensedpos!=null) {
                System.out.print("At step " + step + ": Ev: (xe" + robotsensedpos.row + ", ye" + robotsensedpos.col + ") Fil:  (xf" + filteredx + ", yf" + filteredy + ")");
            }
            else {
                System.out.print("At step " + step + ": Ev: NO READING!! Fil: (xf" + filteredx + ", yf" + filteredy + ")");
            }
            
            
            // Continue loop or not?
            String nl = in.nextLine();
            try {
                if (nl.charAt(0) == 'q')
                    cont = false;
            }
            catch(StringIndexOutOfBoundsException e) {
            }
            
            step++;
        }
        in.close();
        System.out.println("Finished!!");
    }
    
    private double getSensorvalue(int i, int j) {
        if(jis1fromi(i,j))
            return .05;
        if(jis2fromi(i,j))
            return .025;
        if(i == j)
            return .1;
        return 0;
    }

    private boolean jis1fromi(int i, int j) {
        if(j == i+1 || j == i-1 || j == i-cols || j == i+cols|| j==i+cols-1 || j==i+cols+1 || j==i-cols-1 || j==i-cols+1)
            return true;
        return false;
    }

    private boolean jis2fromi(int i, int j) {
        if(j == i+2 || j == i-2 || j == i-(2*cols) || j == i-(2*cols)+1 || j==i-(2*cols)+2 || j==i-(2*cols)-1 || j==i-(2*cols)-2 || j == i+(2*cols) || j == i+(2*cols)+1 || j==i+(2*cols)+2 || j==i+(2*cols)-1 || j==i+(2*cols)-2 || j== i+2+cols || j==i+2-cols || j==i-2+cols || j==i-2-cols)
            return true;
        return false;
    }

    private double getTransitionfromitoj(int i, int j) {
        int directionfrom = i % 4;
        int directionto = j % 4;
        int positionfrom = i / 4;
        int positionto = j / 4;
        double ret = 0;
        
        if(ineighborsj(positionfrom,positionto) && irightdirectionfromj(positionfrom,positionto,directionto)) {
            if(directionfrom == directionto) ret = .8;
            else ret = .2;
        }
        return ret;
    }

private boolean irightdirectionfromj(int i, int j, int directionto) {
        if(j==i-cols)
            return(directionto==0);
        if(j==i+cols)
            return(directionto==1);
        if(j==i-1)
            return(directionto==2);
        if(j==i+1)
            return(directionto==3);
        return false;
    }

private boolean ineighborsj(int i, int j) {
        if (j == i+1 || j == i-1 || j == i-cols || j == i+cols)
            return true;
        return false;
    }

/* p is the direction of the state; 0=u 1=d 2=l 3=r*/
    private int getsinglearrvalue(int row, int col) {
        return (row*rows + col % rows);
    }
    private int normalizevar(int row, int step) {
        double p = Math.random();
        if(step<2) {
            if(p<=.3)
                return row+1;
            if(p<=.6)
                return row-1;
        }
        else if(step<4) {
            if(p<=.1)
                return row+1;
            if(p<=.2)
                return row-1;
        }
        return row;
    }
    
    public Move moveRobot(Position currentpos, String previousdirection) {
        Move m = new Move();
        m.move = checkWalls(currentpos,previousdirection);
        m.realposition = applyMove(m.move,currentpos);
        return m;
    }
    
    public String checkWalls(Position currentpos, String pd) {
        Boolean loopagain = true;
        String dir = "";
        
        while (loopagain) {
            dir = getDirection(pd);
            switch(dir) {
            case "U": if (currentpos.row!=0) loopagain = false;
                      break;    
            case "D": if (currentpos.row!=rows-1) loopagain = false;
                      break;
            case "L": if (currentpos.col!=0) loopagain = false;
                      break;
            case "R": if (currentpos.col!=cols-1) loopagain = false;
                      break;
            }
        }
        return dir;
    }
    
    public Position applyMove(String direction, Position cpos) {
        Position newpos = new Position(cpos.row,cpos.col);
        
        if(direction.compareTo("U") == 0) 
            newpos.row--;
        else if(direction.compareTo("D") == 0)
            newpos.row++;
        else if(direction.compareTo("R") == 0)
            newpos.col++;
        else
            newpos.col--;
        return newpos;
    }
    
    
    public String getDirection(String previousmove) {
        double rand = Math.random();
        if(rand<=.8) return previousmove;
        else {
            String newmove = null;
            do {
                int dir = (int)(Math.random() * 4);
                switch(dir) {
                case 0: newmove = "U";
                break;
                case 1: newmove = "D";
                break;
                case 2: newmove = "L";
                break;
                case 3: newmove = "R";
                break;
                }
            } while (newmove.compareTo(previousmove) == 0);
            return newmove;
        }
    }
    
    public Position getlocationfromsensor(Position currentposition) {
        double rand = Math.random();
        int row = currentposition.row;
        int col = currentposition.col;
        Position position = new Position(currentposition.row,currentposition.col);
        do {
            if(rand <= 0.1) {
                
            }
            else if(rand>0.1 && rand <= 0.5) {
                int r;
                int j;
                do {
                    r = (int) Math.round(Math.random()*2)-1;
                    j = (int)Math.round(Math.random()*2)-1;
                    position.col = col+r;
                    position.row = row+j;
                } while (r==0 && j==0);
            }
            else if(rand>0.5 && rand <=0.9) {
                int r;
                int j;
                do {
                    r = (int)Math.round(Math.random()*4)-2;
                    j = (int)Math.round(Math.random()*4)-2;
                    position.col = col+r;
                    position.row = row+j;
                } while (Math.abs(j)!=2 && Math.abs(r)!=2);
            }
            else {
                return null;
            }
        } while(position.col > cols-1 || position.col < 0 || position.row > rows-1 || position.row < 0);
        
        return position;
    }
    
    public class Move {
        Position realposition;
        String move;
    }
    public class Position {
        public Position(int crow, int ccol) {
            row = crow;
            col = ccol;
        }
        int row;
        int col;
    }
}

