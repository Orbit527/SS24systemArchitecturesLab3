package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Renderer implements IFilter<DataPair, DataPair>{

    private final GraphicsContext gpc;
    private final RenderingMode renderingMode;

    public Renderer(GraphicsContext gpc, RenderingMode renderingMode) {
        this.gpc = gpc;
        this.renderingMode = renderingMode;
    }

    @Override
    public void setSuccessor(IFilter<DataPair, ?> successor) {
        // NOT IMPLEMENTED
    }

    public void write(DataPair dp) {
        gpc.setStroke(dp.getColor());
        if(this.renderingMode == RenderingMode.POINT) {
            gpc.setLineWidth(1);
            gpc.setFill(dp.getColor());
            //TODO: look for optimised render
            gpc.fillOval(dp.getFace().getV1().getX(),dp.getFace().getV1().getY(),1,1);
            gpc.fillOval(dp.getFace().getV2().getX(),dp.getFace().getV2().getY(),1,1);
            gpc.fillOval(dp.getFace().getV3().getX(),dp.getFace().getV3().getY(),1,1);
        }
        else if (this.renderingMode == RenderingMode.WIREFRAME) {
            gpc.setFill(dp.getColor());
            gpc.strokeLine(dp.getFace().getV1().getX(), dp.getFace().getV1().getY(), dp.getFace().getV2().getX(), dp.getFace().getV2().getY());
            gpc.strokeLine(dp.getFace().getV2().getX(), dp.getFace().getV2().getY(), dp.getFace().getV3().getX(), dp.getFace().getV3().getY());
            gpc.strokeLine(dp.getFace().getV1().getX(), dp.getFace().getV1().getY(), dp.getFace().getV3().getX(), dp.getFace().getV3().getY());
        } else if (this.renderingMode == RenderingMode.FILLED) {
            gpc.setFill(dp.getColor());
            double[] xPoints = { dp.getFace().getV1().getX(), dp.getFace().getV2().getX(), dp.getFace().getV3().getX() };
            double[] yPoints = { dp.getFace().getV1().getY(), dp.getFace().getV2().getY(), dp.getFace().getV3().getY() };
            gpc.fillPolygon(xPoints, yPoints, 3);

            // TODO: Make this more efficient, if possible
            // Colours in the wireframe
            gpc.setStroke(dp.getColor());
            gpc.setLineWidth(1);
            gpc.strokePolygon(xPoints, yPoints, 3);
        }
    }
}
