package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filters.push.DataPair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Optional;

public class PullRenderer implements PullFilter<Optional<DataPair>, Void> {
    private final GraphicsContext gpc;
    private final RenderingMode renderingMode;
    private PullFilter<Optional<DataPair>, Optional<DataPair>> predecessor;

    public PullRenderer(GraphicsContext gpc, RenderingMode renderingMode) {
        this.gpc = gpc;
        this.renderingMode = renderingMode;
    }

    @Override
    public void setPredecessor(PullFilter<?, Optional<DataPair>> predecessor) {
        this.predecessor = (PullFilter<Optional<DataPair>, Optional<DataPair>>) predecessor;
    }

    @Override
    public Void read() {

        Optional<DataPair> dp;

        while (true) {
            dp = predecessor.read();

            if (dp.isPresent()) {
                if (dp.get().getFace() instanceof MarkedFace) {
                    break;
                }


                Face f = dp.get().getFace();

                if (this.renderingMode == RenderingMode.POINT) {
                    gpc.setLineWidth(1);
                    gpc.setStroke(Color.RED);
                    //TODO: look for optimised render
                    gpc.fillOval(f.getV1().getX(), f.getV1().getY(), 1, 1);
                    gpc.fillOval(f.getV2().getX(), f.getV2().getY(), 1, 1);
                    gpc.fillOval(f.getV3().getX(), f.getV3().getY(), 1, 1);
                } else if (this.renderingMode == RenderingMode.WIREFRAME) {
                    gpc.setStroke(dp.get().getColor());
                    gpc.strokeLine(f.getV1().getX(), f.getV1().getY(), f.getV2().getX(), f.getV2().getY());
                    gpc.strokeLine(f.getV2().getX(), f.getV2().getY(), f.getV3().getX(), f.getV3().getY());
                    gpc.strokeLine(f.getV1().getX(), f.getV1().getY(), f.getV3().getX(), f.getV3().getY());
                } else if (this.renderingMode == RenderingMode.FILLED) {
                    gpc.setFill(dp.get().getColor());
                    double[] xPoints = {f.getV1().getX(), f.getV2().getX(), f.getV3().getX()};
                    double[] yPoints = {f.getV1().getY(), f.getV2().getY(), f.getV3().getY()};
                    gpc.fillPolygon(xPoints, yPoints, 3);
                    gpc.strokePolygon(xPoints, yPoints, 3);
                }
            }
        }

        return null;
    }
}
