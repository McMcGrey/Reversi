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
        short xCoord;
        switch (firstChar){
            case 'a':
                xCoord = 0;
                break;
            case 'b':
                xCoord = 1;
                break;
            case 'c':
                xCoord = 2;
                break;
            case 'd':
                xCoord = 3;
                break;
            case 'e':
                xCoord = 4;
                break;
            case 'f':
                xCoord = 5;
                break;
            case 'g':
                xCoord = 6;
                break;
            case 'h':
                xCoord = 7;
                break;
            default:
                throw new InvalidInputException();
        }

        move.setxCoord(xCoord);

        char secondChar = input.charAt(1);
        if(!Character.isDigit(secondChar)){
            throw new InvalidInputException();
        }
        short secondCoord = (short) Character.getNumericValue(secondChar);
        if(secondCoord<1 || secondCoord >8){
            throw new InvalidInputException();
        }


        move.setyCoord(((short)(secondCoord-1)));

        return move;
    }
}
