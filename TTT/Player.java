import java.util.*;


public class Player {
    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    public GameState play(final GameState gameState, final Deadline deadline) {
        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        /**
         * Here you should write your algorithms to get the best next move, i.e.
         * the best next state. This skeleton returns a random move instead.
         */
        int player = gameState.getNextPlayer();
        int bestStateIndex = 0;
        int bestScore = -1;

  	    int count = 0;
 	    for(GameState gs : nextStates){
          int score = minimax(gs, player);
 	       // System.out.println("score of CELL_X:"+score);
       	if(score>bestScore){
       		bestScore = score;
       		bestStateIndex = count;
       	}
       	count++;
       	
      }
        return nextStates.get(bestStateIndex);
        
        
    }
    public int minimax(final GameState state, int player){
    	 Vector<GameState> nextStates = new Vector<GameState>();
         state.findPossibleMoves(nextStates);
         int bestPossible = 0;
         
//        if((nextStates.size()==5) || (nextStates.size()==0)){
//        		
//        	    return heuristic(state,player);
//        }
         if(nextStates.size()==5){
        	 return heuristic(state,player);
        	 //return 0;
         }
         if(nextStates.size()==0){
        	 if(state.isOWin()) return -1;
             else if(state.isXWin()) return 1;
             else return 0;
         }
    	else{
    		int v= 0;    		
    		if(player == Constants.CELL_X){
    			bestPossible = -1000000;
        		for(int i=0;i<nextStates.size();i++){
        			v = minimax(nextStates.get(i), Constants.CELL_O);
        		    bestPossible = Math.max(bestPossible, v);
        		}
        		return bestPossible;
        	}else{
        		bestPossible = 1000000;
        		for(int i=0;i<nextStates.size();i++){
    			    v = minimax(nextStates.get(i), Constants.CELL_X);        		        			
    		        bestPossible = Math.min(bestPossible, v);
        		}
        		return bestPossible;
        	}
    	}
    	
    }
    public int heuristic(final GameState state, int player){
    	int scores=0,sum=0;
    	//score by row
    	for(int i=0;i<4;i++){
    		for(int j=0;j<4;j++){
    			if(state.at(i, j)==player){   				
    				scores++;
    			}else if(state.at(i, j)==Constants.CELL_EMPTY){
    				scores++;
    			}
    			else{
    				scores = 0;
    				break;
    			}
    			
    		}
    		sum+=scores;
    		scores=0;
    		
    	}
    	scores =0;
    	//score by column
    	for(int i=0;i<4;i++){

    		for(int j=0;j<4;j++){
    			if(state.at(j, i)==player){   				
    				scores++;
    			}else if(state.at(j, i)==Constants.CELL_EMPTY){
    				scores++;
    			}else{
    				scores = 0;
    				break;
    			}
    			
    		}
            sum+=scores;
            scores=0;
    		
    	}
    	//diagonal
    	scores = 0;
    	for(int i =0;i<4;i++){
    		if(state.at(i, i)==player) scores++;
    		else if(state.at(i, i)==Constants.CELL_EMPTY) scores++;
    		else {
    			scores = 0;
    			break;
    		}
    	}
    	sum+=scores;
    	scores=0;
    	for(int i=0;i<4;i++){
    		if(state.at(i, 3-i)==player) scores++;
    		else if(state.at(i, 3-i)==player) scores++;
    		else {
    			scores=0;
    			break;
    		}
    		
    	}
    	sum+=scores;
		scores=0;
    	return sum;
    }
}