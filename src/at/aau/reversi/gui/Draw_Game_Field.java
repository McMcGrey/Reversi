package at.aau.reversi.gui;

import at.aau.reversi.bean.GameBean;
import at.aau.reversi.enums.Field;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Draw_Game_Field extends JPanel {

    GameBean gameBean;

    public Draw_Game_Field() {

    }


    protected void paintComponent(Graphics g) // Zeichnet das Feld
    {
        super.paintComponent(g);


        g.drawRect(0, 0, 399, 399);

        int corx = 0;
        int cory = 0;
        int cory1 = 0;
        int corx1 = 0;


        for (int i = 0; i <= 7; i++) {

            cory = cory + 50;
            cory1 = cory1 + 50;

            g.drawLine(0, cory, 399, cory1);

        }
        for (int i = 0; i <= 7; i++) {

            corx = corx + 50;
            corx1 = corx1 + 50;

            g.drawLine(corx, 0, corx1, 399);


        }

        Gamefield_update(g);

    }

    private void Gamefield_update(Graphics g) {                                   // Updatet das Feld

        Image white = null;
        Image black = null;
        Image maybe = null;
        try {
            white = ImageIO.read(new File("src/at/aau/reversi/gui/images/white.png"));
            black = ImageIO.read(new File("src/at/aau/reversi/gui/images/black.png"));
            maybe = ImageIO.read(new File("src/at/aau/reversi/gui/images/possibleTurn.png"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        for (int posX = 0; posX < 8; posX++) {

            for (int posY = 0; posY < 8; posY++) {

                Field[][] gamefield = gameBean.getGameField();

                if (gamefield[posX][posY].equals(Field.WHITE)) {
                    g.drawImage(white, 50 * posX, 50 * posY, this);
                } else if (gamefield[posX][posY].equals(Field.BLACK)) {
                    g.drawImage(black, 50 * posX, 50 * posY, this);
                } else if (gamefield[posX][posY].equals(Field.MAYBE)) {
                    g.drawImage(maybe, 50 * posX, 50 * posY, this);
                } else {

                }

                //repaint();
            }
        }
    }

    public void updateGameField(GameBean gameBean) {
        this.gameBean = gameBean;
        repaint();
    }

}
