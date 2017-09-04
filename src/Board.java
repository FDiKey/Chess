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
    int ClickCcount = 0; //кол-во кликов
    int coordX; // координата Х после клика
    int coordY; // координата Y после клика
    int[] Rast = {1,2,3,4,5,4,3,2,1};
    int countFigure = 16;
    Figure[] wight_figure = new Figure[countFigure];
    Figure[] black_figure = new Figure[countFigure];
    Board() {

        initBoard();
        initFigure();

        this.setOnMouseClicked(MouseEvent -> {
            coordX = (int) MouseEvent.getX() / 50;
            coordY = (int) MouseEvent.getY() / 50;
            if(!cells[coordY][coordX].isEmpty()) ClickCcount = 0;
                if (ClickCcount == 0) {
                    ClickCcount = 1;
                    System.out.println(coordX + " " + coordY);

                    int eating = 0;
                    for(int i = 0; i < countFigure; i++ ) {
                        if (wight_figure[i].isSelected()) {
                            for (int aa = 0; aa < countFigure; aa++) {
                                if (black_figure[aa].iCoordX == coordX * 50 && black_figure[aa].iCoordY == coordY * 50) {
                                    wight_figure[i].eat(coordX, coordY, this);
                                    ClickCcount = 0;
                                }
                            }
                        } else {
                            for (int d = 0; d < countFigure; d++)
                                if ((int) (wight_figure[d].iCoordY / 50) == coordY && (int) (wight_figure[d].iCoordX / 50) == coordX)
                                    wight_figure[d].Select(coordX, coordY, this);
                        }
                    }
                    } else {
                    for(int iSel = 0; iSel < countFigure; iSel++ )
                        if(wight_figure[iSel].isSelected())
                        {
                            if(cells[coordY][coordX].isMove()) {
                                wight_figure[iSel].move(coordX, coordY, this);
                                ClickCcount = 0;
                            }
                        } else
                            ClickCcount = 0;
                    }
});


    }

    void initBoard(){ // создание доски
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
        for (int i = 1; i <= QOUNT_BLOCKS; i++)
            if (i < 3 || i > 6) {
                for (int j = 1; j <= QOUNT_BLOCKS; j++) {
                    if(i == 1 || i == 7) {
                        switch (j) {
                            case 1: {

                            }
                        }
                    }
                    try {
                        wight_figure[j - 1] = new Pawn(j * BLOCK_SIZE, i * BLOCK_SIZE, true);
                        this.getChildren().add(wight_figure[j - 1]);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    cells[i][j].setEmpty(false);
                    cells[i][j].setFigure("pawn");
                    if (i == 7 && j > 0) // черные пешки
                    {
                        try {
                            black_figure[j - 1] = new Pawn(j * BLOCK_SIZE + 5, i * BLOCK_SIZE + 5, false);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        this.getChildren().add(black_figure[j - 1]);

                        cells[i][j].setEmpty(false);
                        cells[i][j].setFigure("pawn");
                    }
                }
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
        if(side){
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\pawn.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
        try {
            this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\Blackpawn.png"));
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
                    if(this.iCoordX == board.wight_figure[i].iCoordX && this.iCoordY == board.wight_figure[i].iCoordY)
                    {
                        board.getChildren().removeAll(board.wight_figure[i]);
                        board.wight_figure[i] = new Rook((int) this.getTranslateX(), (int) this.getTranslateY(), this.isbSide());
                        board.getChildren().add(board.wight_figure[i]);
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
            board.wight_figure[a].setSelected(false);


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
                if(board.black_figure[i].iCoordX == (X + 1) * 50  && board.black_figure[i].iCoordY == (Y + 1) * 50)
                {
                    board.cells[Y + 1][X + 1].setMove(true);
                    board.cells[Y + 1][X + 1].setStrokeWidth(4);
                    board.cells[Y + 1][X + 1].setStroke(Color.RED);
                }
            }
            }
            for(int i = 0 ; i < board.countFigure ; i++)
            {
                if(board.black_figure[i].iCoordX == (X - 1) * 50  && board.black_figure[i].iCoordY == (Y + 1) * 50)
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
                    if ((int) (board.black_figure[i].iCoordY / 50) == coordY && (int) (board.black_figure[i].iCoordX / 50) == coordX)
                    {
                        board.cells[coordY][coordX].setEmpty(true);
                        board.black_figure[i].iCoordX = 0;
                        board.black_figure[i].iCoordY = 0;
                        board.getChildren().removeAll(board.black_figure[i]);
                        this.move(coordX, coordY, board);
                    }

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
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\Rook.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\BlackRook.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
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
} //ладья
class Bishop extends Figure{

    Bishop(int createX, int createY, boolean side)
    {
        if(side){
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\Bishop.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\BlackBishop.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX);
        this.setTranslateY(createY);
        this.iCoordX = createX - 5;
        this.iCoordY = createY - 5;
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
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\Knight.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\BlackKnight.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX);
        this.setTranslateY(createY);
        this.iCoordX = createX - 5;
        this.iCoordY = createY - 5;
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
} // Конь
class King extends Figure{

    King(int createX, int createY, boolean side)
    {
        if(side){
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\King.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\BlackKing.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX);
        this.setTranslateY(createY);
        this.iCoordX = createX - 5;
        this.iCoordY = createY - 5;
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
} // король
class Queen extends Figure{

    Queen(int createX, int createY, boolean side)
    {
        if(side){
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\Queen.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();}
        }else {
            try {
                this.image = new Image(new FileInputStream("C:\\Users\\malyshev.ko\\IntelliJIDEAProjects\\tests\\res\\BlackQueen.png"));
                this.setImage(image);
                this.setbSide(side);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }}
        this.setTranslateX(createX);
        this.setTranslateY(createY);
        this.iCoordX = createX - 5;
        this.iCoordY = createY - 5;
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