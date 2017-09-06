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
    static final int BLOCK_SIZE = 50; // размер клетки
    static final int QOUNT_BLOCKS = 8;
    boolean turn = false;
    boolean cheh = false;
    boolean mat = false;
    int ClickCcount = 0; //кол-во кликов
    int coordX; // координата Х после клика
    int coordY; // координата Y после клика
    int countFigure = 16; // Количество фигур с одной стороны
    Figure[] wight_figure = new Figure[countFigure]; // белые фигуры
    Figure[] black_figure = new Figure[countFigure]; // черные фигуры
    Board()  {

        initBoard(); // инициализация поля
        initFigure(); // инициализация фигур
        this.setOnMouseClicked(MouseEvent -> {
            if(turn) TurnSide(wight_figure, black_figure, MouseEvent);
            else TurnSide(black_figure, wight_figure, MouseEvent);
        });





    }

    void initBoard() { // создание доски
    boolean flag =true;
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

        }
        else {
            if(flag) {
                cells[i][j] = new Cell();
                cells[i][j].setTranslateX(j * BLOCK_SIZE);
                cells[i][j].setTranslateY(i * BLOCK_SIZE);
                cells[i][j].setFill(Color.WHITE);
                flag = !flag;
                this.getChildren().add(cells[i][j]);
            }
            else {
                cells[i][j] = new Cell();
                cells[i][j].setTranslateX(j * BLOCK_SIZE);
                cells[i][j].setTranslateY(i * BLOCK_SIZE);
                cells[i][j].setFill(Color.BLACK);
                flag = !flag;
                this.getChildren().add(cells[i][j]);

            }
        }
    }

    }
    }
    void initFigure() {
        for (int i = 1; i <= QOUNT_BLOCKS; i++) {
            if (i < 3 || i > 6) {
                for (int j = 1; j <= QOUNT_BLOCKS; j++) {
                    if (i == 1 || i == 8) {
                        boolean flag;
                        if (i == 1) flag = true;
                        else flag = false;
                        switch (j) {
                            case 1: {
                                if(flag) {
                                    wight_figure[j - 1] = new Rook(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(wight_figure[j - 1]);
                                }else {
                                    black_figure[j - 1] = new Rook(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(black_figure[j - 1]);
                                }
                                this.cells[i][j].setEmpty(false);
                                this.cells[i][j].setFigure("Rook");
                                break;
                            }
                            case 2: {
                                if(flag) {
                                    wight_figure[j - 1] = new Knight(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(wight_figure[j - 1]);
                                }else {
                                    black_figure[j - 1] = new Knight(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(black_figure[j - 1]);
                                }
                                this.cells[i][j].setEmpty(false);
                                this.cells[i][j].setFigure("Knight");
                                break;
                            }
                            case 3: {
                                if(flag) {
                                    wight_figure[j - 1] = new Bishop(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(wight_figure[j - 1]);
                                }else {
                                    black_figure[j - 1] = new Bishop(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(black_figure[j - 1]);
                                }
                                this.cells[i][j].setEmpty(false);
                                this.cells[i][j].setFigure("Bishop");
                                break;
                            }
                            case 4: {
                                if(flag) {
                                    wight_figure[j - 1] = new King(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(wight_figure[j - 1]);
                                }else {
                                    black_figure[j - 1] = new King(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(black_figure[j - 1]);
                                }

                                this.cells[i][j].setEmpty(false);
                                this.cells[i][j].setFigure("King");
                                break;
                            }
                            case 5: {
                                if(flag) {
                                    wight_figure[j - 1] = new Queen(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(wight_figure[j - 1]);
                                }else {
                                    black_figure[j - 1] = new Queen(j * BLOCK_SIZE, i * BLOCK_SIZE, flag);
                                    this.getChildren().add(black_figure[j - 1]);
                                }
                                this.cells[i][j].setEmpty(false);
                                this.cells[i][j].setFigure("Queen");
                                break;
                            }
                            case 6: {
                                if(flag) {
                                    wight_figure[j - 1] = new Bishop(j * BLOCK_SIZE, i * BLOCK_SIZE, true);
                                    this.getChildren().add(wight_figure[j - 1]);
                                }else {
                                    black_figure[j - 1] = new Bishop(j * BLOCK_SIZE, i * BLOCK_SIZE, false);
                                    this.getChildren().add(black_figure[j - 1]);
                                }
                                this.cells[i][j].setEmpty(false);
                                this.cells[i][j].setFigure("Bishop");
                                break;
                            }
                            case 7: {
                                if(flag) {
                                    wight_figure[j - 1] = new Knight(j * BLOCK_SIZE, i * BLOCK_SIZE, true);
                                    this.getChildren().add(wight_figure[j - 1]);
                                    wight_figure[j - 1].toFront();
                                }else {
                                    black_figure[j - 1] = new Knight(j * BLOCK_SIZE, i * BLOCK_SIZE, false);
                                    this.getChildren().add(black_figure[j - 1]);
                                }
                                this.cells[i][j].setEmpty(false);
                                this.cells[i][j].setFigure("Knight");
                                break;
                            }
                            case 8: {
                                if(flag) {
                                    wight_figure[j - 1] = new Rook(j * BLOCK_SIZE, i * BLOCK_SIZE, true);
                                    this.getChildren().add(wight_figure[j - 1]);
                                }else {
                                    black_figure[j - 1] = new Rook(j * BLOCK_SIZE, i * BLOCK_SIZE, false);
                                    this.getChildren().add(black_figure[j - 1]);
                                }
                                this.cells[i][j].setEmpty(false);
                                this.cells[i][j].setFigure("Rook");
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int iPawn = 8; iPawn < wight_figure.length; iPawn++) {
            try {
                wight_figure[iPawn] = new Pawn((iPawn - 7) * BLOCK_SIZE, 2 * BLOCK_SIZE, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.getChildren().add(wight_figure[iPawn]);
            this.cells[2][(iPawn + 1)%8].setEmpty(false);
            this.cells[2][(iPawn + 1)%8].setFigure("Pawn");

            try {
                black_figure[iPawn] = new Pawn((iPawn - 7) * BLOCK_SIZE, 7 * BLOCK_SIZE, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.getChildren().add(black_figure[iPawn]);
            this.cells[7][(iPawn + 1)%8].setEmpty(false);
            this.cells[7][(iPawn + 1)%8].setFigure("BlackPawn");
        }
    }
    void TurnSide(Figure f1[], Figure f2[], MouseEvent ME) {
            //int iSel = 8;
            coordX = (int) ME.getX() / 50;
            coordY = (int) ME.getY() / 50;
            if(!cells[coordY][coordX].isEmpty()) ClickCcount = 0;
            if (ClickCcount == 0) {
                ClickCcount = 1;
                System.out.println(coordX + " " + coordY);
                int eating = 0;
                for(int i = 0; i < countFigure; i++ ) {
                    if (f1[i].isSelected()) {
                        for (int aa = 0; aa < countFigure; aa++) {
                            if (f2[aa].iCoordX == coordX * BLOCK_SIZE && f2[aa].iCoordY == coordY * BLOCK_SIZE && this.cells[coordY][coordX].isMove()) {
                                f1[i].eat(coordX, coordY, this);
                                this.turn = !this.turn;
                                ClickCcount = 0;
                            }
                        }
                    } else {
                        for (int d = 0; d < countFigure; d++)
                            if ((int) (f1[d].iCoordY / BLOCK_SIZE) == coordY && (int) (f1[d].iCoordX / BLOCK_SIZE) == coordX)
                                f1[d].Select(coordX, coordY, this);

                    }
                }
            } else {
                for(int iSel = 0; iSel < countFigure; iSel++ )
                    if(f1[iSel].isSelected())
                    {
                        if(cells[coordY][coordX].isMove()) {
                            f1[iSel].move(coordX, coordY, this);
                            this.turn = !this.turn;
                            ClickCcount = 0;
                        }
                    } else
                        ClickCcount = 0;
            }

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

abstract class Figure extends ImageView {

    private boolean bSide;
    static final String url= "C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\";
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
        if(side){
            try {
                this.image = new Image(new FileInputStream(this.url+"Pawn.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
        try {
            this.image = new Image(new FileInputStream(this.url+"Blackpawn.png"));
            this.setImage(image);
            this.setbSide(side);
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }}
        this.setTranslateX(createX+5);
        this.setTranslateY(createY+5);
        this.iCoordX = createX;
        this.iCoordY = createY;
        this.setId("Pawn:");

    }

    @Override
    void move(int moveX, int moveY, Board board) {
        int X = (int)this.getTranslateX() / 50;
        int Y = (int)this.getTranslateY() / 50;
        board.cells[Y][X].setEmpty(true);
        this.setTranslateX(moveX * 50);
        this.setTranslateY(moveY * 50);
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

        if(moveY == 8 || moveY == 1 ) {
            Pane pane = new Pane();
            pane.setMinSize(300,300);
            Button btn1 = new Button("ЛОДЬЯ");
            pane.getChildren().add(btn1);
            btn1.setOnMouseClicked(mouseEvent -> {

                for(int i = 0; i < board.countFigure; i++)
                {
                    if (this.isbSide()) {
                    if(this.iCoordX == board.wight_figure[i].iCoordX && this.iCoordY == board.wight_figure[i].iCoordY)
                    {
                        board.getChildren().removeAll(board.wight_figure[i]);
                        board.wight_figure[i] = new Rook((int) this.getTranslateX() - 5, (int) this.getTranslateY() - 5, this.isbSide());
                        board.getChildren().add(board.wight_figure[i]);
                    }
                    } else {
                        if(this.iCoordX == board.black_figure[i].iCoordX && this.iCoordY == board.black_figure[i].iCoordY)
                        {
                            board.getChildren().removeAll(board.black_figure[i]);
                            board.black_figure[i] = new Rook((int) this.getTranslateX() - 5, (int) this.getTranslateY() - 5, this.isbSide());
                            board.getChildren().add(board.black_figure[i]);
                        }
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

        if(this.isbSide()) {
            for (int a = 0; a < board.countFigure; a++)
                board.wight_figure[a].setSelected(false);

            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    board.cells[i][j].setMove(false);
                    board.cells[i][j].setStroke(board.cells[i][j].getFill());
                }
            }
            this.setSelected(true);
            System.out.println("Selected: "+this.getId());
            if (Y + 1 < 9) {
                if (X + 1 < 9) {
                    for (int i = 0; i < board.countFigure; i++) {
                        if (board.black_figure[i].iCoordX == (X + 1) * 50 && board.black_figure[i].iCoordY == (Y + 1) * 50) {
                            board.cells[Y + 1][X + 1].setMove(true);
                            board.cells[Y + 1][X + 1].setStrokeWidth(4);
                            board.cells[Y + 1][X + 1].setStroke(Color.RED);
                        }
                    }
                }
                for (int i = 0; i < board.countFigure; i++) {
                    if (board.black_figure[i].iCoordX == (X - 1) * 50 && board.black_figure[i].iCoordY == (Y + 1) * 50) {
                        board.cells[Y + 1][X - 1].setMove(true);
                        board.cells[Y + 1][X - 1].setStrokeWidth(4);
                        board.cells[Y + 1][X - 1].setStroke(Color.RED);
                    }
                }
            }
            if (Y < 8) {
                if (this.isFisrStep()) {
                    for (int i = Y + 1; i < Y + 3; i++) {
                        if (board.cells[i][X].isEmpty()) {
                            board.cells[i][X].setMove(true);
                            board.cells[i][X].setStrokeWidth(4);
                            board.cells[i][X].setStroke(Color.FORESTGREEN);
                        } else break;
                    }

                } else {

                    if (board.cells[Y + 1][X].isEmpty()) {
                        board.cells[Y + 1][X].setMove(true);
                        board.cells[Y + 1][X].setStrokeWidth(4);
                        board.cells[Y + 1][X].setStroke(Color.FORESTGREEN);
                    }

                }
            }
        }else{
        for (int a = 0; a < board.countFigure; a++)
            board.black_figure[a].setSelected(false);

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        this.setSelected(true);
        System.out.println("Selected: " + this.getId());

        for (int i = 0; i < board.countFigure; i++) {
            if (board.wight_figure[i].iCoordX == (X + 1) * 50 && board.wight_figure[i].iCoordY == (Y - 1) * 50) {
                board.cells[Y - 1][X + 1].setMove(true);
                board.cells[Y - 1][X + 1].setStrokeWidth(4);
                board.cells[Y - 1][X + 1].setStroke(Color.RED);
            }
        }

        for (int i = 0; i < board.countFigure; i++) {
            if (board.wight_figure[i].iCoordX == (X - 1) * 50 && board.wight_figure[i].iCoordY == (Y - 1) * 50) {
                board.cells[Y - 1][X - 1].setMove(true);
                board.cells[Y - 1][X - 1].setStrokeWidth(4);
                board.cells[Y - 1][X - 1].setStroke(Color.RED);
            }
        }
        if (Y > 1) {
            int selY = Y;
            if (this.isFisrStep()) {
                if (board.cells[selY - 1][X].isEmpty()) {
                    board.cells[selY - 1][X].setMove(true);
                    board.cells[selY - 1][X].setStrokeWidth(4);
                    board.cells[selY - 1][X].setStroke(Color.FORESTGREEN);
                }
                if (board.cells[selY - 2][X].isEmpty()) {
                    board.cells[selY - 2][X].setMove(true);
                    board.cells[selY - 2][X].setStrokeWidth(4);
                    board.cells[selY - 2][X].setStroke(Color.FORESTGREEN);
                }

            } else {
                if (board.cells[selY - 1][X].isEmpty()) {
                    board.cells[selY - 1][X].setMove(true);
                    board.cells[selY - 1][X].setStrokeWidth(4);
                    board.cells[selY - 1][X].setStroke(Color.FORESTGREEN);
                }
            }
        }
    }



    }
    @Override
    void eat(int coordX, int coordY, Board board) {
        System.out.println("Eat :" + coordX +" " + coordY);
        if(this.isbSide()) {
            for (int i = 0; i < board.countFigure; i++)
                if ((int) (board.black_figure[i].iCoordY / 50) == coordY && (int) (board.black_figure[i].iCoordX / 50) == coordX) {
                    board.cells[coordY][coordX].setEmpty(true);
                    board.black_figure[i].iCoordX = 0;
                    board.black_figure[i].iCoordY = 0;
                    board.getChildren().removeAll(board.black_figure[i]);
                    this.move(coordX, coordY, board);
                }
        } else {
            for (int i = 0; i < board.countFigure; i++)
                if ((int) (board.wight_figure[i].iCoordY / 50) == coordY && (int) (board.wight_figure[i].iCoordX / 50) == coordX) {
                    board.cells[coordY][coordX].setEmpty(true);
                    board.wight_figure[i].iCoordX = 0;
                    board.wight_figure[i].iCoordY = 0;
                    board.getChildren().removeAll(board.wight_figure[i]);
                    this.move(coordX, coordY, board);
                }
        }
        //board.turn = !board.turn;
    }

    void retSide(int X, int Y, Board board) {
        for(int i = 0 ; i < board.countFigure ; i++)
        {
            if(board.black_figure[i].iCoordX == X * 50 && board.black_figure[i].iCoordY == Y * 50) indexFigure = i;
        }
    }

} // пешка
class Rook extends Figure{

    Rook(int createX, int createY, boolean side)
    {
        if(side){
            try {
                this.image = new Image(new FileInputStream(this.url + "Rook.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream(this.url + "BlackRook.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX + 5);
        this.setTranslateY(createY + 5);
        this.iCoordX = createX;
        this.iCoordY = createY;
        this.setId("Rook:" + createX / 50);
        System.out.println(this.getId());
    }

    @Override
    void move(int moveX, int moveY, Board board) {

    }

    @Override
    void Select(int X, int Y, Board board) {

        for(int a = 0; a < board.countFigure; a++)
            board.wight_figure[a].setSelected(false);


        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        this.setSelected(true);


    }

    @Override
    public boolean isFisrStep() {
        return false;
    }
    @Override
    void eat(int coordX, int coordY, Board board) {

    }
} //ладья
class Bishop extends Figure{

    Bishop(int createX, int createY, boolean side)
    {
        if(side){
            try {
                this.image = new Image(new FileInputStream(this.url + "Bishop.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream(this.url + "BlackBishop.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX + 5);
        this.setTranslateY(createY + 5);
        this.iCoordX = createX;
        this.iCoordY = createY;
        this.setId("Bishop:" + createX / 50);
        System.out.println(this.getId());
    }

    @Override
    void move(int moveX, int moveY, Board board) {

    }

    @Override
    void Select(int X, int Y, Board board) {
        for(int a = 0; a < board.countFigure; a++)
            board.wight_figure[a].setSelected(false);


        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        this.setSelected(true);
        System.out.println("Rook: " + this.iCoordY + " " + this.iCoordX);

    }



    @Override
    public boolean isFisrStep() {
        return false;
    }

    @Override
    void eat(int coordX, int coordY, Board board) {

    }
} // Слон
class Knight extends Figure{

    Knight(int createX, int createY, boolean side)
    {
        if(side){
            try {
                this.image = new Image(new FileInputStream(this.url + "Knight.png"));
                this.setImage(image);
                this.setbSide(side);

            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream(this.url + "BlackKnight.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX + 5);
        this.setTranslateY(createY + 5);
        this.iCoordX = createX;
        this.iCoordY = createY;
        this.setId("Knight:" + createX / 50);
        this.toFront();
        System.out.println(this.getId());
    }

    @Override
    void move(int moveX, int moveY, Board board) {

    }

    @Override
    void Select(int X, int Y, Board board) {
        for(int a = 0; a < board.countFigure; a++)
            board.wight_figure[a].setSelected(false);


        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        this.setSelected(true);
        System.out.println("Knight: " + this.iCoordY + " " + this.iCoordX);

    }



    @Override
    public boolean isFisrStep() {
        return false;
    }

    @Override
    void eat(int coordX, int coordY, Board board) {

    }
} // Конь
class King extends Figure{

    King(int createX, int createY, boolean side)
    {
        if(side){
            try {
                this.image = new Image(new FileInputStream(this.url + "King.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream(this.url + "BlackKing.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX + 5);
        this.setTranslateY(createY + 5);
        this.iCoordX = createX;
        this.iCoordY = createY;
        this.setId("King:" + createX / 50);
        System.out.println(this.getId());
    }

    @Override
    void move(int moveX, int moveY, Board board) {

    }

    @Override
    void Select(int X, int Y, Board board) {
        for(int a = 0; a < board.countFigure; a++)
            board.wight_figure[a].setSelected(false);


        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        this.setSelected(true);
        System.out.println("King: " + this.iCoordY + " " + this.iCoordX);

    }



    @Override
    public boolean isFisrStep() {
        return false;
    }

    @Override
    void eat(int coordX, int coordY, Board board) {

    }
} // король
class Queen extends Figure{

    Queen(int createX, int createY, boolean side)
    {
        if(side){
            try {
                this.image = new Image(new FileInputStream(this.url + "Queen.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream(this.url + "BlackQueen.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX + 5);
        this.setTranslateY(createY + 5);
        this.iCoordX = createX;
        this.iCoordY = createY;
        this.setId("Knight:" + createX / 50);
        System.out.println(this.getId());
    }

    @Override
    void move(int moveX, int moveY, Board board) {

    }

    @Override
    void Select(int X, int Y, Board board) {
        for(int a = 0; a < board.countFigure; a++)
            board.wight_figure[a].setSelected(false);


        for(int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                board.cells[i][j].setMove(false);
                board.cells[i][j].setStroke(board.cells[i][j].getFill());
            }
        }
        this.setSelected(true);
        System.out.println("Knight: " + this.iCoordY + " " + this.iCoordX);

    }



    @Override
    public boolean isFisrStep() {
        return false;
    }

    @Override
    void eat(int coordX, int coordY, Board board) {

    }
} // Ферзь