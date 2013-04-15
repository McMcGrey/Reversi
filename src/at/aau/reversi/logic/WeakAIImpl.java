package at.aau.reversi.logic;

import at.aau.reversi.enums.Field;
import java.util.ArrayList;

import at.aau.reversi.bean.Move;

import javax.management.modelmbean.ModelMBean;

/**
 * The WeakAII looks at all possible moves and takes the move which has the most own stones as result<br/>
 * (Takes the move which "turns" the most stones)
 */
public class WeakAIImpl implements AI {

    GameLogicLocalImpl logic;

    public WeakAIImpl(){
        logic = new GameLogicLocalImpl();
    }

	@Override
	public Move calcNextStep(Field[][] gameField, Field color) {

        logic.setGameField(gameField);

        // Search for valid Moves
		ArrayList<Move> validMoves = new ArrayList<Move>();
		for(short i=0;i<8;i++){
			for(short z=0;z<8;z++){
				
                if(logic.validMove(i, z, color)){
                    validMoves.add(new Move(i, z));
                }
				
			}
		}

        // Search for best move
        Move best = null;
        int bestCount = 0;
        for(Move move:validMoves){
            if(best==null){
                best=move;
                bestCount = countFields(logic.calcNewGameField(best.getxCoord(), best.getyCoord(), color),color);
            }else{
                int tempCnt = countFields(logic.calcNewGameField(move.getxCoord(), move.getyCoord(), color),color);
                if(tempCnt>bestCount){
                    bestCount = tempCnt;
                    best=move;
                }
            }
        }

		return best;
	}

    private int countFields(Field[][] gameField, Field color){
        //todo: move countFields to better place
        int count=0;
        for(short i=0;i<8;i++){
           for(short z=0;z<8;z++){

               if(gameField[i][z].equals(color)){
                   count++;
               }

           }
        }
        return count;
    }

}
