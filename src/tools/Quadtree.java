public class Quadtree {
    private final int method;
    private final double threshold;
    private final double minBlockSize;
    private int numNode;

    public Quadtree(){
        this.method = 0;
        this.threshold = 0;
        this.minBlockSize = 0;
        this.numNode = 0;
    }

    public Quadtree(int _method, double _threshold, double _minBlockSize){
        this.method = _method;
        this.threshold = _threshold;
        this.minBlockSize = _minBlockSize;
        this.numNode = 0;
    }

    public Node construct(int[][] red, int[][] green, int[][] blue){
        return checker(red, green, blue, 0, 0, red.length, red[0].length);
    }

    public Node checker(int[][] red, int[][] green, int[][] blue, int rowTL, int colTL, double w, double h){
        double meanPixel = meanPixel(red, green, blue);
        Node node = new Node(meanPixel, false, 0, 0, w, h);
        this.numNode ++;

        if (!isDivide(red, green, blue, w, h)){ //stop dividing, current block becomes leaf
            node.setIsLeaf(true);
            return node;
        }

        Node nodeTL = checker(red, green, blue, rowTL, colTL, w/2, h/2);
        Node nodeTR = checker(red, green, blue, rowTL, colTL, w/2, h/2);
        Node nodeBL = checker(red, green, blue, rowTL, colTL, w/2, h/2);
        Node nodeBR = checker(red, green, blue, rowTL, colTL, w/2, h/2);

        node.setTopLeft(nodeTL);
        node.setTopRight(nodeTR);
        node.setBotLeft(nodeBL);
        node.setBotRight(nodeBR);
        
        return node;
    }

    public boolean isDivide(int[][] red, int[][] green, int[][] blue, double w, double h){
        double blockArea = w * h;
        double subBlockArea = (w/2) * (h/2);

        // based on block size (bener ga < or <= --cek spek)?
        if (blockArea < minBlockSize || subBlockArea < minBlockSize){
            return false;
        }

        // based on error measurement method
        double value = 0;
        switch (method){
            case 1 -> {
                // variance
            }
            case 2 -> {
                // MAD
            }
            case 3 -> {
                // MPD
            }
            case 4 -> {
                // Entropy
            }
            case 5 -> {
                // SSIM (bonus)
            }
            default -> {
                // case dafault, didnt proceed
            }
        }

        return value >= threshold; //true if able to be divided
    }

    // might needed
    public double meanPixel(int[][] red, int[][] green, int[][] blue){
        double mean = 0;
        return mean;
    }

    public int getNumberOfNode(){
        return this.numNode;
    }

    public int getDepth(Node root){
        if (root == null){
            return -1;
        }

        int heightTL = getDepth(root.getTopLeft());
        int heightTR = getDepth(root.getTopRight());
        int heightBL = getDepth(root.getBotLeft());
        int heightBR = getDepth(root.getBotRight());

        return findMax(heightTL, heightTR, heightBL, heightBR) + 1;
    }

    public int findMax(int hTL, int hTR, int hBL, int hBR){
        int max = hTL;
        if (hTR > max) { max = hTR; }
        if (hBL > max) { max = hBL; }
        if (hBR > max) { max = hBR; }
        return max;
    }
}
