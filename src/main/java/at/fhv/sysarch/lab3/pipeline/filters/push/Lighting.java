package at.fhv.sysarch.lab3.pipeline.filters.push;

import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;

public class Lighting implements IFilter <DataPair, DataPair>{

    private Pipe<DataPair> successor;

    private Vec3 lightPos;
    private Boolean isPerformLighting = false;

    public Lighting(Vec3 lightPos) {
        this.lightPos = lightPos;
    }

    public void setPerformLighting(Boolean isPerformLighting) {
        this.isPerformLighting = isPerformLighting;
    }

    @Override
    public void setSuccessor(Pipe<DataPair> successor) {
        this.successor = successor;
    }

    @Override
    public void write(DataPair dp) {
        successor.write(process(dp));
    }

    @Override
    public DataPair process(DataPair dp) {
        if (this.isPerformLighting == true) {

            double factor = lightPos.getUnitVector().dot(dp.getFace().getN1().getUnitVector().toVec3());

            if (factor < 0) {
                factor = 0;
            }

            double red = Math.min(dp.getColor().getRed() * factor, 1.0);
            double green = Math.min(dp.getColor().getGreen() * factor, 1.0);
            double blue = Math.min(dp.getColor().getBlue() * factor, 1.0);

            Color newColor = new Color(red, green, blue, dp.getColor().getOpacity());

            DataPair newDataPair = new DataPair(dp.getFace(), newColor);

            return newDataPair;
        }
            return dp;
    }
}
