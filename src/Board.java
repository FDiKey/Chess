import javafx.scene.control.Button;
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
    int countFigure = 8;
    Figure[] figure = new Figure[countFigure];
    Figure[] figure1 = new Figure[countFigure];
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
                if(i == 2 && j > 0) //белые пешки
                {
                        try {
                            figure[j - 1] = new Pawn(j * BLOCK_SIZE, i * BLOCK_SIZE , true);
                            this.getChildren().add(figure[j - 1]);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        cells[i][j].setEmpty(false);
                        cells[i][j].setFigure("pawn");
                }
                if(i == 7 && j > 0) // черные пешки
                {
                    figure1[j - 1] = new Rook(j * BLOCK_SIZE + 5, i * BLOCK_SIZE + 5, false);
                    this.getChildren().add(figure1[j - 1]);

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

                    int eating = 0;
                    for(int i = 0; i < countFigure; i++ ) {
                        if (figure[i].isSelected()) {
                            for (int aa = 0; aa < countFigure; aa++) {
                                if (figure1[aa].iCoordX == coordX * 50 && figure1[aa].iCoordY == coordY * 50) {
                                    figure[i].eat(coordX, coordY, this);
                                    ClickCcount = 0;
                                }
                            }
                        } else {
                            for (int d = 0; d < countFigure; d++)
                                if ((int) (figure[d].iCoordY / 50) == coordY && (int) (figure[d].iCoordX / 50) == coordX)
                                    figure[d].Select(coordX, coordY, this);
                        }
                    }
                    } else {
                    for(int iSel = 0; iSel < countFigure; iSel++ )
                        if(figure[iSel].isSelected())
                        {
                            if(cells[coordY][coordX].isMove()) {
                                figure[iSel].move(coordX, coordY, this);
                                ClickCcount = 0;
                            }
                        } else
                            ClickCcount = 0;
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

    abstract void eat(int coordX, int coordY, Board board);

}
class Pawn extends Figure{
    String name = "Pawn";
    int indexFigure;
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
        board.cells[moveY][moveX].setEmpty(false);
        this.toFront();
        this.setFisrStep(false);
        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        if(moveY == 8) {
            Pane pane = new Pane();
            pane.setMinSize(300,300);
            Button btn1 = new Button("ЛОДЬЯ");
            pane.getChildren().add(btn1);
            btn1.setOnMouseClicked(mouseEvent -> {
                for(int i = 0; i < board.countFigure; i++)
                {
                    if(this.iCoordX == board.figure[i].iCoordX && this.iCoordY == board.figure[i].iCoordY)
                    {
                        board.getChildren().removeAll(board.figure[i]);
                        board.figure[i] = new Rook((int) this.getTranslateX(), (int) this.getTranslateY(), this.isbSide());
                        board.getChildren().add(board.figure[i]);
                    }
                }
                this.toFront();
                btn1.setVisible(false);
                board.getChildren().remove(btn1);



            });
            pane.toFront();
            pane.setTranslateX((moveX - 1) * 50);
            pane.setTranslateY(moveY * 50);
            board.getChildren().add(pane)

            ;}


        }
    @Override
    void Select (int X, int Y, Board board) {

        for(int a = 0; a < board.countFigure; a++)
            board.figure[a].setSelected(false);


        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        this.setSelected(true);
        if(Y + 1 < 9) {
        if(X + 1 < 9) {
            for(int i = 0 ; i < board.countFigure ; i++)
            {
                if(board.figure1[i].iCoordX == (X + 1) * 50  && board.figure1[i].iCoordY == (Y + 1) * 50)
                {
                    board.cells[Y + 1][X + 1].setMove(true);
                    board.cells[Y + 1][X + 1].setStrokeWidth(4);
                    board.cells[Y + 1][X + 1].setStroke(Color.RED);
                }
            }
            }
            for(int i = 0 ; i < board.countFigure ; i++)
            {
                if(board.figure1[i].iCoordX == (X - 1) * 50  && board.figure1[i].iCoordY == (Y + 1) * 50)
                {
                    board.cells[Y + 1][X - 1].setMove(true);
                    board.cells[Y + 1][X - 1].setStrokeWidth(4);
                    board.cells[Y + 1][X - 1].setStroke(Color.RED);
                }
            }
        }
        if(Y < 8){
        if(this.isFisrStep()) {
            for (int i = Y + 1; i < Y + 3; i++) {
                if(board.cells[i][X].isEmpty()) {
                    board.cells[i][X].setMove(true);
                    board.cells[i][X].setStrokeWidth(4);
                    board.cells[i][X].setStroke(Color.FORESTGREEN);
                } else break;
            }

        } else {

                if(board.cells[Y+1][X].isEmpty()) {
                    board.cells[Y+1][X].setMove(true);
                    board.cells[Y+1][X].setStrokeWidth(4);
                    board.cells[Y+1][X].setStroke(Color.FORESTGREEN);
                }

        }
        }
    }
    @Override
    void eat(int coordX, int coordY, Board board) {
        System.out.println("Eat :" + coordX +" " + coordY);
        for(int i = 0; i < board.countFigure; i++)
                    if ((int) (board.figure1[i].iCoordY / 50) == coordY && (int) (board.figure1[i].iCoordX / 50) == coordX)
                    {
                        board.cells[coordY][coordX].setEmpty(true);
                        board.figure1[i].iCoordX = 0;
                        board.figure1[i].iCoordY = 0;
                        board.getChildren().removeAll(board.figure1[i]);
                        this.move(coordX, coordY, board);
                    }

    }
    void retSide(int X, int Y, Board board) {
        for(int i = 0 ; i < board.countFigure ; i++)
        {
            if(board.figure1[i].iCoordX == X * 50 && board.figure1[i].iCoordY == Y * 50) indexFigure = i;
        }
    }

}

class Rook extends Figure{

    Rook(int createX, int createY, boolean side)
    {
        try {
            this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\Rook.png"));
            this.setImage(image);
            this.setbSide(side);
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        this.setTranslateX(createX);
        this.setTranslateY(createY);
        this.iCoordX = createX - 5;
        this.iCoordY = createY - 5;
        this.setId("Rook:" + createX / 50);
        System.out.println(this.getId());
    }

    @Override
    void move(int moveX, int moveY, Board board) {

    }

    @Override
    void Select(int X, int Y, Board board) {

    }



    @Override
    public boolean isFisrStep() {
        return false;
    }

    @Override
    void eat(int coordX, int coordY, Board board) {

    }
}
