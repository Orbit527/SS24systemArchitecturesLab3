package at.fhv.sysarch.lab3.pipeline.filters.push;

import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import at.fhv.sysarch.lab3.utils.DataPair;
import javafx.scene.canvas.GraphicsContext;

public class PushRenderer implements PushFilter<DataPair, DataPair> {

    private final GraphicsContext gpc;
    private final RenderingMode renderingMode;

    public PushRenderer(GraphicsContext gpc, RenderingMode renderingMode) {
        this.gpc = gpc;
        this.renderingMode = renderingMode;
    }

    @Override
    public void setSuccessor(Pipe<DataPair> successor) {
        // NOT IMPLEMENTED
    }

    public void write(DataPair dp) {
         process(dp);
    }

    @Override
    public DataPair process(DataPair dp) {
        gpc.setFill(dp.getColor());
        gpc.setStroke(dp.getColor());

        if(this.renderingMode == RenderingMode.POINT) {
            gpc.setLineWidth(1);
            gpc.fillOval(dp.getFace().getV1().getX(),dp.getFace().getV1().getY(),1,1);
            gpc.fillOval(dp.getFace().getV2().getX(),dp.getFace().getV2().getY(),1,1);
            gpc.fillOval(dp.getFace().getV3().getX(),dp.getFace().getV3().getY(),1,1);
        }
        else if (this.renderingMode == RenderingMode.WIREFRAME) {
            gpc.strokeLine(dp.getFace().getV1().getX(), dp.getFace().getV1().getY(), dp.getFace().getV2().getX(), dp.getFace().getV2().getY());
            gpc.strokeLine(dp.getFace().getV2().getX(), dp.getFace().getV2().getY(), dp.getFace().getV3().getX(), dp.getFace().getV3().getY());
            gpc.strokeLine(dp.getFace().getV1().getX(), dp.getFace().getV1().getY(), dp.getFace().getV3().getX(), dp.getFace().getV3().getY());
        } else if (this.renderingMode == RenderingMode.FILLED) {
            double[] xPoints = { dp.getFace().getV1().getX(), dp.getFace().getV2().getX(), dp.getFace().getV3().getX() };
            double[] yPoints = { dp.getFace().getV1().getY(), dp.getFace().getV2().getY(), dp.getFace().getV3().getY() };
            gpc.fillPolygon(xPoints, yPoints, 3);
            // Colours in the wireframe
            gpc.strokePolygon(xPoints, yPoints, 3);
        }
        return dp;
    }
}
