package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Optional;

public class PullRenderer implements PullFilter<Optional<Face>, Void> {
    private final GraphicsContext gpc;
    private final RenderingMode renderingMode;
    private PullFilter<Optional<Face>, Optional<Face>> predecessor;

    public PullRenderer(GraphicsContext gpc, RenderingMode renderingMode) {
        this.gpc = gpc;
        this.renderingMode = renderingMode;
    }

    @Override
    public void setPredecessor(PullFilter<?, Optional<Face>> predecessor) {
        this.predecessor = (PullFilter<Optional<Face>, Optional<Face>>) predecessor;
    }

    @Override
    public Void read() {

        Optional<Face> face;

        while ((face = predecessor.read()).isPresent()) {
            Face f = face.get();

            double[] xPoints = { f.getV1().getX(), f.getV2().getX(), f.getV3().getX() };
            double[] yPoints = { f.getV1().getY(), f.getV2().getY(), f.getV3().getY() };



            if (this.renderingMode == RenderingMode.POINT) {

                gpc.setLineWidth(1);

                gpc.setStroke(Color.WHITE);
                //gpc.strokeLine(50, 50, 100, 100);

                //System.out.println(face);


                gpc.fillOval(f.getV1().getX() * 40, f.getV1().getY() * 40, 1, 1);
                gpc.fillOval(f.getV2().getX() * 40, f.getV2().getY() * 40, 1, 1);
                gpc.fillOval(f.getV3().getX() * 40, f.getV3().getY() * 40, 1, 1);
            } else if (this.renderingMode == RenderingMode.WIREFRAME) {
                gpc.setFill(Color.WHITE);
                gpc.strokeLine(f.getV1().getX() * 40, f.getV1().getY() * 40, f.getV2().getX() * 40, f.getV2().getY() * 40);
                gpc.strokeLine(f.getV2().getX() * 40, f.getV2().getY() * 40, f.getV3().getX() * 40, f.getV3().getY() * 40);
                gpc.strokeLine(f.getV1().getX() * 40, f.getV1().getY() * 40, f.getV3().getX() * 40, f.getV3().getY() * 40);
            } else if (this.renderingMode == RenderingMode.FILLED) {
                gpc.setFill(Color.WHITE);
                gpc.fillPolygon(xPoints, yPoints, 3);
                gpc.strokePolygon(xPoints, yPoints, 3);
            }
        }
        return null;
    }
}
