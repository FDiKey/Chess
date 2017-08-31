import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;




public class Board extends Pane {
    public Cell[][] cells = new Cell[9][9]; // массив клеток
    public  final int BLOCK_SIZE = 50; // размер клетки
    int ClickCcount = 0; //кол-во кликов
    int coordX; // координата Х после клика
    int coordY; // координата Y после клика
    Figure[] figure = new Figure[8];
    Board() {
        boolean flag = true;

// создание клаток
        for (int i = 0; i < 9; i++) {
            for(int j = 0; j <9; j++)
            {

                int finalI = i;
                int finalJ = j;

                if((i == 0 ^ j == 0) || (i == 0 && i == j) )
                {

                    cells[i][j] = new Cell();
                    cells[i][j].setTranslateX(j * BLOCK_SIZE);
                    cells[i][j].setTranslateY(i * BLOCK_SIZE);
                    cells[i][j].setFill(Color.GRAY);
                    cells[i][j].setStroke(Color.BLACK);
                    this.getChildren().add(cells[i][j]);
                    flag = !flag;
                    if (i == 0)
                    {
                        String str = " ABCDEFGH";
                        char[] ch = str.toCharArray();
                        Label lbl = new Label("" + ch[j]);
                        lbl.setTranslateX(j*BLOCK_SIZE);
                        lbl.setTranslateY(i * BLOCK_SIZE);
                        this.getChildren().add(lbl);

                    } else
                        {
                        Label lbl = new Label("" + i);
                        lbl.setTranslateX(j * BLOCK_SIZE);
                        lbl.setTranslateY(i * BLOCK_SIZE);
                        this.getChildren().add(lbl);
                    }
                } else
                    {
                    if(flag) {
                        cells[i][j] = new Cell();
                        cells[i][j].setTranslateX(j * BLOCK_SIZE);
                        cells[i][j].setTranslateY(i * BLOCK_SIZE);
                        cells[i][j].setFill(Color.WHITE);
                        flag = !flag;
                        this.getChildren().add(cells[i][j]);
                    }  else
                        {
                        cells[i][j] = new Cell();
                        cells[i][j].setTranslateX(j * BLOCK_SIZE);
                        cells[i][j].setTranslateY(i * BLOCK_SIZE);
                        cells[i][j].setFill(Color.BLACK);
                        flag = !flag;
                        this.getChildren().add(cells[i][j]);
                    }
                }
                if(i == 2 && j > 0)
                {

                    try {
                        figure[j-1] = new Pawn(j * BLOCK_SIZE, i * BLOCK_SIZE, true);
                        this.getChildren().add(figure[j-1]);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    cells[i][j].setEmpty(false);
                    cells[i][j].setFigure("pawn");

                }



            }

        }
        this.setOnMouseClicked(MouseEvent -> {
            coordX = (int) MouseEvent.getX() / 50;
            coordY = (int) MouseEvent.getY() / 50;
            if(!cells[coordY][coordX].isEmpty()) ClickCcount = 0;
                if (ClickCcount == 0) {
                    ClickCcount = 1;
                    System.out.println(coordX + " " + coordY);
                    for (int i = 0; i < 8; i++)
                        if ((int) (figure[i].iCoordY / 50) == coordY && (int) (figure[i].iCoordX / 50) == coordX)
                            figure[i].Select(coordX, coordY, this);


                } else {
                    for(int iSel = 0; iSel < 8; iSel++ )
                        if(figure[iSel].isSelected())
                        {
                            if(cells[coordY][coordX].isMove()) {
                                figure[iSel].move(coordX, coordY, this);
                                System.out.println(figure[iSel].getId());
                                ClickCcount = 0;
                            }
                        }

                }


});
    }

    void initBoard(){

    }

}

class Cell extends Rectangle {
    public final int BLOCK_SIZE = 50;
    private boolean move = false;
    private boolean empty = true;
    private boolean moved = false;
    private String Figure = "";

    public boolean isMove() {
        return move;
    }
    public void setMove(boolean move) {
        this.move = move;
    }
    public boolean isEmpty() {
        return empty;
    }
    public String getFigure() {
        return Figure;
    }
    public void setFigure(String figure) {
        Figure = figure;
    }
    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    Cell()
    {
        this.empty = true;
        this.setHeight(BLOCK_SIZE);
        this.setWidth(BLOCK_SIZE);
    }
}

abstract class Figure extends ImageView{


    private boolean bSide;
    private boolean selected;
    int iCoordX;
    int iCoordY;
    Image image;


    public boolean isbSide() {
        return bSide;
    }
    public void setbSide(boolean bSide) {
        this.bSide = bSide;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    abstract void move(int moveX, int moveY, Board board);
    abstract void Select (int X, int Y, Board board);
    public abstract boolean isFisrStep();
}

class Pawn extends Figure{
    String name = "Pawn";
    private boolean FisrStep = true;
    public boolean isFisrStep() {
        return FisrStep;
    }
    public void setFisrStep(boolean fisrStep) {
        FisrStep = fisrStep;
    }

    Pawn(int createX, int createY, boolean side) throws FileNotFoundException {
        try {
            this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\pawn.png"));
            this.setImage(image);
            this.setbSide(side);
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        this.setTranslateX(createX+5);
        this.setTranslateY(createY+5);
        this.iCoordX = createX;
        this.iCoordY = createY;
        this.setId("Pawn:" + createX / 50);
        System.out.println(this.getId());

    }

    @Override
    void move(int moveX, int moveY, Board board) {
        int X = (int)this.getTranslateX() / 50;
        int Y =(int)this.getTranslateY() / 50;
        board.cells[Y][X].setEmpty(true);
        this.setTranslateX(moveX * 50 + 5);
        this.setTranslateY(moveY * 50 + 5);
        this.iCoordX = moveX * 50;
        this.iCoordY = moveY * 50;
        board.cells[moveY][moveX].setEmpty(true);
        this.toFront();
        this.setFisrStep(false);
        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());

            }
        }

    }
    @Override
    void Select (int X, int Y, Board board)
    {
        for(int a = 0; a < 8; a++)
            board.figure[a].setSelected(false);
        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        this.setSelected(true);

        if(this.isFisrStep()) {
            for (int i = Y; i < Y + 3; i++) {
                board.cells[i][X].setMove(true);
                board.cells[i][X].setStrokeWidth(4);
                board.cells[i][X].setStroke(Color.RED);
            }

        } else {
            for (int i = Y; i < Y + 2; i++) {
                board.cells[i][X].setMove(true);
                board.cells[i][X].setStrokeWidth(4);
                board.cells[i][X].setStroke(Color.RED);
        }
    }}

}
