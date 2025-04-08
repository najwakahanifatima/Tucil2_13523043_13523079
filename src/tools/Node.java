package tools;

public class Node {
    private int redValue; //mean intensity
    private int greenValue;
    private int blueValue;
    private final int width;
    private final int height;
    private final Point pos;
    private boolean isLeaf; //true if leaf node, false if not                                   
    private Node topLeft;
    private Node topRight;
    private Node botLeft;
    private Node botRight;

    public Node(){
        this.redValue = 0;
        this.greenValue = 0;
        this.blueValue = 0;
        this.width = 0;
        this.height = 0;
        this.pos = null;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.botLeft = null;
        this.botRight = null;
    }

    public Node(int _redValue, int _greenValue, int _blueValue, boolean _isLeaf, int _row, int _col, int _height, int _width){
        this.redValue = _redValue;
        this.greenValue = _greenValue;
        this.blueValue = _blueValue;
        this.isLeaf = _isLeaf;
        Point _pos = new Point(_row, _col);
        this.pos = _pos;
        this.width = _width;
        this.height = _height;
        this.topLeft = null;
        this.topRight = null;
        this.botLeft = null;
        this.botRight = null;
    }

    public Node (int _redValue, int _greenValue, int _blueValue, boolean _isLeaf, int _row, int _col, int _height, int _width, Node _topLeft, Node _topRight, Node _botLeft, Node _botRight){
        this.redValue = _redValue;
        this.greenValue = _greenValue;
        this.blueValue = _blueValue;
        this.isLeaf = _isLeaf;
        Point _pos = new Point(_row, _col);
        this.pos = _pos;
        this.width = _width;
        this.height = _height;
        this.topLeft = _topLeft;
        this.topRight = _topRight;
        this.botLeft = _botLeft;
        this.botRight = _botRight;
    }

    public void setRedValue(int newValue){
        this.redValue = newValue;
    }

    public void setGreenValue(int newValue){
        this.greenValue = newValue;
    }

    public void setBlueValue(int newValue){
        this.blueValue = newValue;
    }

    public void setIsLeaf(boolean newIsLeaf){
        this.isLeaf = newIsLeaf;
    }

    public void setTopLeft(Node newTopLeft){
        this.topLeft = newTopLeft;
    }

    public void setTopRight(Node newTopRight){
        this.topRight = newTopRight;
    }

    public void setBotLeft(Node newBotLeft){
        this.botLeft = newBotLeft;
    }

    public void setBotRight(Node newBotRight){
        this.botRight = newBotRight;
    }

    public int getRedValue(){
        return this.redValue;
    }

    public int getGreenValue(){
        return this.greenValue;
    }

    public int getBlueValue(){
        return this.blueValue;
    }
    
    public boolean getIsLeaf(){
        return this.isLeaf;
    }

    public Node getTopLeft(){
        return this.topLeft;
    }

    public Node getTopRight(){
        return this.topRight;
    }

    public Node getBotLeft(){
        return this.botLeft;
    }

    public Node getBotRight(){
        return this.botRight;
    }

    public Point getPos(){
        return this.pos;
    }

    public int getNumRows(){
        return this.height;
    }

    public int getNumCols(){
        return this.width;
    }

    public int getArea(){
        return this.height * this.width;
    }
}
