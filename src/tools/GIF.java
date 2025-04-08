package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import org.w3c.dom.NodeList;


public class GIF {
    public static void makeGIF(Quadtree tree, String outputName){
        File outputFile = new File(outputName); // output gif name
        // create directory for frames
        File d = new File("test/frames");
        if (!d.exists()) {
            d.mkdirs();
        }
        getFrames(tree);

        File frameDir = new File("test/frames");
        File[] frameFiles = frameDir.listFiles((dir, name) -> name.endsWith(".png"));

        if (frameFiles == null || frameFiles.length == 0) {
            System.out.println("Frame tidak ditemukan.");
            return;
        }

        Arrays.sort(frameFiles); // sort alphabetically by filename

        try (ImageOutputStream output = new FileImageOutputStream(outputFile)) {

            BufferedImage first = ImageIO.read(frameFiles[0]);
            GifSequence writer = new GifSequence(output, first.getType(), true);
            writer.writeToSequence(first, 700);

            for (File frame : frameFiles) {
                BufferedImage img = ImageIO.read(frame);
                writer.writeToSequence(img, 700);
            }
            
            writer.close();
            System.out.println("GIF telah dibuat! : " + outputName);

            // delete frames
            for (File frame : frameFiles) {
                frame.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getFrames(Quadtree tree) {
        int frameIndex = 0;
        int height = tree.getImage().getHeight(); 
        int width = tree.getImage().getWidth();
        int[][] matrix = new int[height][width];
        int maxDepth = Quadtree.getDepth(tree.getRoot());

        List<List<Node>> depthQueues = new ArrayList<>();

        for (int i = 0; i <= maxDepth; i++) {
            depthQueues.add(new ArrayList<>());
        }

        getNodesBasedOnDepth(tree.getRoot(), depthQueues, 0);

        for (int depth = 0; depth <= maxDepth; depth++) {
            // System.out.println("depth: " + depth + ", " + depthQueues.get(depth).size() + " nodes");
            
            // process all nodes at current depth
            for (Node node : depthQueues.get(depth)) {
                updateMatrix(node, matrix);
            }
            
            // save frame to image
            saveFrame(matrix, frameIndex++, height, width);
        }
    }

    private static void getNodesBasedOnDepth(Node node, List<List<Node>> depthQueues, int depth) {
        if (node == null) {
            return;
        }
        
        depthQueues.get(depth).add(node);
        
        // process child if not leaf
        if (!node.getIsLeaf()) {
            getNodesBasedOnDepth(node.getTopLeft(), depthQueues, depth + 1);
            getNodesBasedOnDepth(node.getTopRight(), depthQueues, depth + 1);
            getNodesBasedOnDepth(node.getBotLeft(), depthQueues, depth + 1);
            getNodesBasedOnDepth(node.getBotRight(), depthQueues, depth + 1);
        }
    }

    public static void updateMatrix(Node node, int[][] matrix){
        int h = node.getNumRows();
        int w = node.getNumCols();
        for (int i = node.getPos().row; i < node.getPos().row + h; i++) {
            for (int j = node.getPos().col; j < node.getPos().col + w; j++) {
                int red = node.getRedValue() << 16;
                int green = node.getGreenValue() << 8;
                int blue = node.getBlueValue();
                int rgb = red | green | blue;
                matrix[i][j] = rgb;
            }
        }
    }

    public static void saveFrame(int[][] matrix, int frameIndex, int h, int w) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                img.setRGB(j, i, matrix[i][j]);
            }
        }

        String filename = String.format("test/frames/frame_%03d.png", frameIndex++);
        try {
            ImageIO.write(img, "png", new File(filename));
        } catch (IOException e) {
            System.out.println("Error membuat GIF.");
        }
    }
}

class GifSequence {
    private final ImageOutputStream output;
    private final ImageWriter writer;
    private final ImageTypeSpecifier typeSpecifier;
    private final boolean isLoop;
    private boolean isFirstFrame = true;

    public GifSequence(ImageOutputStream output, int imageType, boolean isLoop) throws IOException {
        this.output = output;
        this.isLoop = isLoop;

        writer = ImageIO.getImageWritersBySuffix("gif").next();
        writer.setOutput(output);
        writer.prepareWriteSequence(null);

        typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
    }

    private IIOMetadata getFrameMetadata(int delayMs) throws IIOInvalidTreeException {
        // convert ms to cs (1/100)
        int delayCs = Math.max(2, delayMs / 10);
        // System.out.println("setting delay" + delayCs + " cs");
        
        IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, null);
        String metaFormat = metadata.getNativeMetadataFormatName();
        
        // get the root node
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormat);
    
        IIOMetadataNode gce = null;
        NodeList nodeList = root.getElementsByTagName("GraphicControlExtension");
        if (nodeList.getLength() > 0) {
            gce = (IIOMetadataNode) nodeList.item(0);
        } else {
            gce = new IIOMetadataNode("GraphicControlExtension");
            root.appendChild(gce);
        }
        
        // set the delay time
        gce.setAttribute("disposalMethod", "none");
        gce.setAttribute("userInputFlag", "FALSE");
        gce.setAttribute("transparentColorFlag", "FALSE");
        gce.setAttribute("delayTime", Integer.toString(delayCs));
        gce.setAttribute("transparentColorIndex", "0");
        
        // loop extension
        if (isFirstFrame && isLoop) {
            // Find or create ApplicationExtensions node
            IIOMetadataNode appExtensions = null;
            nodeList = root.getElementsByTagName("ApplicationExtensions");
            if (nodeList.getLength() > 0) {
                appExtensions = (IIOMetadataNode) nodeList.item(0);
            } else {
                appExtensions = new IIOMetadataNode("ApplicationExtensions");
                root.appendChild(appExtensions);
            }
            
            // create the loop extension
            IIOMetadataNode appNode = new IIOMetadataNode("ApplicationExtension");
            appNode.setAttribute("applicationID", "NETSCAPE");
            appNode.setAttribute("authenticationCode", "2.0");
            byte[] loopBytes = {1, 0, 0}; // loop forever
            appNode.setUserObject(loopBytes);
            appExtensions.appendChild(appNode);
        }
        
        metadata.setFromTree(metaFormat, root);
        return metadata;
    }

    public void writeToSequence(BufferedImage image, int delayMs) throws IOException {
        try {
            // System.out.println("delay: " + delayMs);
            IIOMetadata metadata = getFrameMetadata(delayMs);
            writer.writeToSequence(new IIOImage(image, null, metadata), null);
        } catch (IIOInvalidTreeException e) {
            throw new IOException("Gagal set metadata frame :(", e);
        }
        isFirstFrame = false;
    }

    public void close() throws IOException {
        writer.endWriteSequence();
    }
}
