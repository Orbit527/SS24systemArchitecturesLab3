package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class ViewportTransformation implements IFilter<DataPair, DataPair>{

    private IFilter<DataPair, ?> successor;
    private Mat4 viewMatrix;

    public void setViewMatrix(Mat4 matrix) {
        this.viewMatrix = matrix;
    }

    @Override
    public void setSuccessor(IFilter<DataPair, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void write(DataPair dataPair) {

        Vec4 v1ViewPortNew = viewMatrix.multiply(dataPair.getFace().getV1().multiply( 1.0f / dataPair.getFace().getV1().getW()));
        Vec4 v2ViewPortNew = viewMatrix.multiply(dataPair.getFace().getV2().multiply( 1.0f / dataPair.getFace().getV2().getW()));
        Vec4 v3ViewPortNew = viewMatrix.multiply(dataPair.getFace().getV3().multiply( 1.0f / dataPair.getFace().getV3().getW()));

        Face newFace = new Face(v1ViewPortNew, v2ViewPortNew, v3ViewPortNew, dataPair.getFace().getN1(), dataPair.getFace().getN2(), dataPair.getFace().getN3());

        DataPair newDataPair = new DataPair(newFace, dataPair.getColor());

        this.successor.write(newDataPair);

    }
}
