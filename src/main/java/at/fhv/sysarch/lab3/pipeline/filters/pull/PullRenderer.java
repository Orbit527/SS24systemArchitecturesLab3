package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import at.fhv.sysarch.lab3.utils.DataPair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import at.fhv.sysarch.lab3.utils.MarkedFace;
import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public class PullRenderer implements PullFilter<Optional<DataPair>, Void> {
    private final GraphicsContext gpc;
    private final RenderingMode renderingMode;
    private PullPipe<Optional<DataPair>> predecessor;

    public PullRenderer(GraphicsContext gpc, RenderingMode renderingMode) {
        this.gpc = gpc;
        this.renderingMode = renderingMode;
    }

    @Override
    public void setPredecessor(PullPipe<Optional<DataPair>> predecessor) {
        this.predecessor = predecessor;
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

                gpc.setFill(dp.get().getColor());
                gpc.setStroke(dp.get().getColor());

                if (this.renderingMode == RenderingMode.POINT) {
                    gpc.setLineWidth(1);
                    gpc.fillOval(f.getV1().getX(), f.getV1().getY(), 1, 1);
                    gpc.fillOval(f.getV2().getX(), f.getV2().getY(), 1, 1);
                    gpc.fillOval(f.getV3().getX(), f.getV3().getY(), 1, 1);
                } else if (this.renderingMode == RenderingMode.WIREFRAME) {
                    gpc.strokeLine(f.getV1().getX(), f.getV1().getY(), f.getV2().getX(), f.getV2().getY());
                    gpc.strokeLine(f.getV2().getX(), f.getV2().getY(), f.getV3().getX(), f.getV3().getY());
                    gpc.strokeLine(f.getV1().getX(), f.getV1().getY(), f.getV3().getX(), f.getV3().getY());
                } else if (this.renderingMode == RenderingMode.FILLED) {
                    double[] xPoints = {f.getV1().getX(), f.getV2().getX(), f.getV3().getX()};
                    double[] yPoints = {f.getV1().getY(), f.getV2().getY(), f.getV3().getY()};
                    gpc.fillPolygon(xPoints, yPoints, 3);
                    // Colours in the wireframe
                    gpc.strokePolygon(xPoints, yPoints, 3);
                }
            }
        }
        return null;
    }
}
