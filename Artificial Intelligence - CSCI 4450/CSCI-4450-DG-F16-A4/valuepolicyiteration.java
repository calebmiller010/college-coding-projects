import java.util.Scanner;

public class valuepolicyiteration {
    
// Problem 2a - value iteration
    public void valueiteration(MDP grid, double err) {
        double maxutilitychange = 0;
        double u[] = new double[9];
        double uprime[] = new double[9];
        
        // terminal state is utility of 10
        uprime[2] = 10;
        
        do { //repeat
            u = uprime.clone(); // U <- U'
            maxutilitychange = 0; // m.u.c. <- 0
            
            for(int i=0;i<9;i++) { // for each state s in S do
                if(i==2) // the terminal state doesn't change
                    continue;
                
                //calculates the max Sumforeachaction( P(s'|s,a)*U[s'] )
                double max = 0;
                for(int j=1;j<=4;j++) { // for each action (U,D,L,R)
                    double sum = getsum(grid,i,j,u);
                    if(sum>max)
                        max = sum;  
                }
                 
                // U'[s] <- R(s) + discount * max  
                uprime[i] = grid.states[i].reward + grid.discount * max;
                
                // if| U'[s] - U[s] | > delta
                if (Math.abs(uprime[i] - u[i]) > maxutilitychange)
                {
                    // then delta <- | U'[s] - U[s] |
                    maxutilitychange =Math.abs(uprime[i] - u[i]);
                }
            }
        //until delta < error(1-discount)/discount
        } while(maxutilitychange>=(err*(1-grid.discount))/grid.discount);
        
        
        //Then take our utility function and find our policy
        for(int i=0;i<9;i++) {
            System.out.printf("(%d,%d) : %s\n", i/3,i%3,calculatepolicy(i,u) );
        }
        
        /*//Uncomment to print out policy values
        System.out.println()
        for(int i=0;i<9;i++) {
            System.out.printf("(%d,%d) : %.4f\n", i/3,i%3,u[i] );           
        }
        */
        
        System.out.println();
        
        //return u;
    }
    
    // Problem 2b - policy iteration
    public void policyiteration(MDP grid) {
        boolean changed = false;
        double[] utilities = new double[9];
        int[] policies = randompolicy();
        
        do {
            // U <- policyevaulation(pi,U,mdp)
            // unchanged? <- true
            utilities = policyevalutation(policies,utilities,grid);
            changed = false;
            
            // for each state s in S do
            for(int i=0;i<9;i++) {
                
                // calculate utility-maximizing action
                double max = -10000;
                int direction = 0;
                for(int j=1;j<=4;j++) { // for each action (U,D,L,R)
                    double sum = getsum(grid,i,j,utilities);
                    if(sum > max) {
                        max = sum;
                        direction = j;
                    }
                }
            
                // if max( SUM[ P(s'|s,a)*U[s'] ] ) > SUM[ P(s'|s,pi[s])*U[s'] ] then do
                if(max > getsum(grid,i,policies[i], utilities)) {
                    // assign new direction to policies
                    policies[i] = direction; 
                    changed = true;
                }
            }
        // until unchanged?
        } while (changed);
        
        for(int i=0;i<9;i++) {
            if(i==2)
                System.out.printf("(%d,%d) : GOAL!!\n", i/3,i%3);
            else
                System.out.printf("(%d,%d) : %s\n", i/3,i%3,getdirection(policies[i]));
        }
        System.out.println();
        
        //return policies;
    }
        
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        int select = 0;
        valuepolicyiteration v = new valuepolicyiteration();
        double err =.0001;
        
        while(select!=3) {
            System.out.print("Enter r: ");
            int r = in.nextInt();
            System.out.print("Enter 1 for Value Iteration, 2 for Policy Iteration, 3 to Exit: ");
            select = in.nextInt();
            
            MDP grid = v.new MDP(r);
            
            if(select==1)
                v.valueiteration(grid,err);
            if(select==2)
                v.policyiteration(grid);
        }
        in.close();
    }

    private double getsum(MDP env, int index, int direction, double[] u) {
        double prob = 0.0;
        State s = env.states[index];
        
        if(s.col!=0)
            prob += env.transition(s, env.states[index-1], direction) * u[index-1];
        if(s.col!=2)
            prob += env.transition(s, env.states[index+1], direction) * u[index+1];
        if(s.row!=0)
            prob += env.transition(s, env.states[index-3], direction) * u[index-3];
        if(s.row!=2)
            prob += env.transition(s, env.states[index+3], direction) * u[index+3];
        return prob;
    }
    

    private double[] policyevalutation(int[] policies, double[] utilities, MDP grid) {
        double newu[] = new double[9];
        for(int i=0;i<9;i++) {
            double sum = 0.0;
            sum = getsum(grid, i, policies[i], utilities);
            newu[i] = grid.states[i].reward + grid.discount * sum;
        }
        return newu;
    }

    public int[] randompolicy() {
        int[] ret = new int[9];
        for(int i=0;i<ret.length;i++) {
            ret[i] = (int)(Math.random()*4)+1;
        }
        return ret;
    }

    
    private double getutility(int i, double[] u, int d) {
        double ret = 0.0;
        if(i/3!=2)
            ret+=u[i+3] * getprob(2,d);
        if(i/3!=0)
            ret+=u[i-3] * getprob(1,d);
        if(i%3!=0)
            ret+=u[i-1] * getprob(3,d);
        if(i%3!=2)
            ret+=u[i+1] * getprob(4,d);
        return ret;
    }


    // based on the utility function, it calculates what direction to go
    private String calculatepolicy(int i, double[] u) {
        int direction = 0;
        double max = 0;
        if(i == 2)
            return "GOAL!!";
        
        for(int d=1;d<=4;d++) {
            double utility = getutility(i,u,d);
            if(utility>max) {
                direction = d;
                max = utility;
            }
        }
        return getdirection(direction);
    }
    
    private String getdirection(int i) {
        if(i==1)
            return "U";
        if(i==2)
            return "D";
        if(i==3)
            return "L";
        if(i==4)
            return "R";
        return "";
    }
    
    public class MDP {
        State[] states = new State[9];
        //int[] actions = {+1,-1,-1,+1};//U,D,L,R
        double discount = .99;
        
        public MDP(int r) {
            for(int i=0;i<states.length;i++) {
                int rew = -1;
                if(i==0)
                    rew = r;
                    //rew = 10;
                if(i==2)
                    rew = 10;
                    //rew = r;
                states[i] = new State(rew,i/3,i%3);
            }
        }
        /* calculates P(s'|s,a);
         * 
         */
        public double transition(State oldstate, State newstate, int action) {
            if(oldstate.row==newstate.row) {//moving L or R 
                if(oldstate.col==newstate.col+1)
                    return getprob(3,action); //get probability of actually moving left when the action is ??
                if(oldstate.col==newstate.col-1)
                    return getprob(4,action);
            }
            else if(oldstate.col == newstate.col) {//moving U or D
                if(oldstate.row==newstate.row+1)
                    return getprob(1,action);
                if(oldstate.row==newstate.row-1)
                    return getprob(2,action);
            }
            return 0.0;
        }
    }
    
    /* gets the probability of moving 'actualaction' given desiredaction
     * (U=1,D=2,L=3,R=4)
     */
        public double getprob(int actualaction, int desiredaction) {
            if(actualaction == desiredaction)
                return 0.8;
            if((actualaction == 1 && desiredaction == 2) || (actualaction == 2 && desiredaction == 1) || (actualaction == 4 && desiredaction == 3) || (actualaction == 3 && desiredaction == 4))
                return 0.0;
            return 0.1;
            
        }
            
    public class State {
        int reward;
        int row;
        int col;
        int singleindex;
        
        public State(int r, int ro, int c) {
            reward = r;
            row = ro;
            col = c;
            singleindex = row*3 + col;
        }
    }
}

