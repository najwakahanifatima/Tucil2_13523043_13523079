public class Node {
    private double value; //mean intensity
    private final double width;
    private final double height;
    private final Point pos;
    private boolean isLeaf;
    private Node topLeft;
    private Node topRight;
    private Node botLeft;
    private Node botRight;

    public Node(){
        this.value = 0;
        this.width = 0;
        this.height = 0;
        this.pos = null;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.botLeft = null;
        this.botRight = null;
    }

    public Node(double _value, boolean _isLeaf, int _row, int _col, double _width, double _height){
        this.value = _value;
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

    public Node (double _value, boolean _isLeaf, int _row, int _col, double _width, double _height, Node _topLeft, Node _topRight, Node _botLeft, Node _botRight){
        this.value = _value;
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

    public void setValue(double newValue){
        this.value = newValue;
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

    public double getValue(){
        return this.value;
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

    public double getArea(){
        return this.height * this.width;
    }
}
