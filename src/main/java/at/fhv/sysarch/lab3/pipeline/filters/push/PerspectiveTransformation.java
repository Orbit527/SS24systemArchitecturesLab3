package at.fhv.sysarch.lab3.pipeline.filters.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class PerspectiveTransformation  implements IFilter<DataPair, DataPair> {

    private Pipe<DataPair> successor;
    private Mat4 projMatrix;

    public void setProjMatrix(Mat4 matrix) {
        this.projMatrix = matrix;
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
        Vec4 v1ViewPort = projMatrix.multiply(dp.getFace().getV1());
        Vec4 v2ViewPort = projMatrix.multiply(dp.getFace().getV2());
        Vec4 v3ViewPort = projMatrix.multiply(dp.getFace().getV3());

        Face newFace = new Face(v1ViewPort, v2ViewPort, v3ViewPort, dp.getFace().getN1(), dp.getFace().getN2(), dp.getFace().getN3());

        DataPair newDataPair = new DataPair(newFace, dp.getColor());

        return newDataPair;
    }
}
