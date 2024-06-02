package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filters.push.DataPair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

import java.util.Optional;

public class PullViewportTransformation implements PullFilter<Optional<DataPair>, Optional<DataPair>>{

    private PullFilter<Optional<DataPair>, Optional<DataPair>> predecessor;
    private Mat4 viewMatrix;

    public void setViewMatrix(Mat4 matrix) {
        this.viewMatrix = matrix;
    }

    @Override
    public void setPredecessor(PullFilter<?, Optional<DataPair>> predecessor) {
        this.predecessor = (PullFilter<Optional<DataPair>, Optional<DataPair>>) predecessor;
    }

    @Override
    public Optional<DataPair> read() {
        Optional<DataPair> dp = predecessor.read();
        if (dp.isPresent()) {

            if (dp.get().getFace() instanceof MarkedFace) {
                return dp;
            }

            Vec4 v1ViewPortNew = viewMatrix.multiply(dp.get().getFace().getV1().multiply( 1.0f / dp.get().getFace().getV1().getW()));
            Vec4 v2ViewPortNew = viewMatrix.multiply(dp.get().getFace().getV2().multiply( 1.0f / dp.get().getFace().getV2().getW()));
            Vec4 v3ViewPortNew = viewMatrix.multiply(dp.get().getFace().getV3().multiply( 1.0f / dp.get().getFace().getV3().getW()));

            Face newFace = new Face(v1ViewPortNew, v2ViewPortNew, v3ViewPortNew, dp.get().getFace().getN1(), dp.get().getFace().getN2(), dp.get().getFace().getN3());

            DataPair dataPair = new DataPair(newFace, dp.get().getColor());

            return Optional.of(dataPair);

        }
        return Optional.empty();
    }
}
