package at.aau.reversi.logic;

import at.aau.reversi.bean.Move;
import at.aau.reversi.exceptions.InvalidInputException;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 16.04.13
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameLogicAbstract implements GameLogic {

    @Override
    public Move getMoveFromInputstring(String input) throws InvalidInputException{

        if(input.length() != 2){
            throw new InvalidInputException();
        }

        Move move = new Move();

        input = input.toLowerCase();
        char firstChar = input.charAt(0);
        short yCoord;
        switch (firstChar){
            case 'a':
                yCoord = 0;
                break;
            case 'b':
                yCoord = 1;
                break;
            case 'c':
                yCoord = 2;
                break;
            case 'd':
                yCoord = 3;
                break;
            case 'e':
                yCoord = 4;
                break;
            case 'f':
                yCoord = 5;
                break;
            case 'g':
                yCoord = 6;
                break;
            case 'h':
                yCoord = 7;
                break;
            default:
                throw new InvalidInputException();
        }

        move.setyCoord(yCoord);

        char secondChar = input.charAt(1);
        if(!Character.isDigit(secondChar)){
            throw new InvalidInputException();
        }
        short secondCoord = (short) Character.getNumericValue(secondChar);
        if(secondCoord<1 || secondCoord >8){
            throw new InvalidInputException();
        }


        move.setxCoord(((short) (secondCoord-1)));

        return move;
    }
}
