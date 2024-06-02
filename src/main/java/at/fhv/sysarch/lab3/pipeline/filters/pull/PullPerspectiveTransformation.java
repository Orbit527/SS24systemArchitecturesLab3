package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filters.push.DataPair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

import java.util.Optional;

public class PullPerspectiveTransformation implements PullFilter<Optional<DataPair>, Optional<DataPair>>{

    private PullFilter<Optional<DataPair>, Optional<DataPair>> predecessor;

    private Mat4 projMatrix;

    public void setProjMatrix(Mat4 matrix) {
        this.projMatrix = matrix;
    }

    @Override
    public void setPredecessor(PullFilter<?, Optional<DataPair>> predecessor) {
        this.predecessor = (PullFilter<Optional<DataPair>, Optional<DataPair>>) predecessor;
    }

    @Override
    public Optional<DataPair> read() {
        Optional<DataPair> dp = predecessor.read();
        if (dp.isPresent()) {



            Vec4 v1ViewPort = projMatrix.multiply(dp.get().getFace().getV1());
            Vec4 v2ViewPort = projMatrix.multiply(dp.get().getFace().getV2());
            Vec4 v3ViewPort = projMatrix.multiply(dp.get().getFace().getV3());

            Face newFace = new Face(v1ViewPort, v2ViewPort, v3ViewPort, dp.get().getFace().getN1(), dp.get().getFace().getN2(), dp.get().getFace().getN3());

            DataPair dataPair = new DataPair(newFace, dp.get().getColor());

            return Optional.of(dataPair);

        }
        return Optional.empty();
    }
}
