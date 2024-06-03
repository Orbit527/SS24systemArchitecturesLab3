package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import at.fhv.sysarch.lab3.pipeline.filters.push.DataPair;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

import java.util.Optional;

public class PullLighting implements PullFilter<Optional<DataPair>, Optional<DataPair>>{

    private PullPipe<Optional<DataPair>> predecessor;

    private Vec3 lightPos;
    private Boolean isPerformLighting = false;

    public PullLighting(Vec3 lightPos) {
        this.lightPos = lightPos;
    }

    public void setPerformLighting(Boolean isPerformLighting) {
        this.isPerformLighting = isPerformLighting;
    }


    @Override
    public void setPredecessor(PullPipe<Optional<DataPair>> predecessor) {
        this.predecessor =  predecessor;
    }

    @Override
    public Optional<DataPair> read() {
        Optional<DataPair> dp = predecessor.read();
        if (dp.isPresent()) {

            if (dp.get().getFace() instanceof MarkedFace) {
                return dp;
            }

            if (this.isPerformLighting == true) {

                double factor = lightPos.getUnitVector().dot(dp.get().getFace().getN1().getUnitVector().toVec3());

                if (factor < 0) {
                    factor = 0;
                }

                double red = Math.min(dp.get().getColor().getRed() * factor, 1.0);
                double green = Math.min(dp.get().getColor().getGreen() * factor, 1.0);
                double blue = Math.min(dp.get().getColor().getBlue() * factor, 1.0);

                Color newColor = new Color(red, green, blue, dp.get().getColor().getOpacity());

                return Optional.of(new DataPair(dp.get().getFace(), newColor));
            }
            return dp;

        }
        return Optional.empty();
    }
}
