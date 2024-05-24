package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class ViewportTransformation implements IFilter<Face, Face>{

    private IFilter<Face, ?> successor;
    private Mat4 viewMatrix;

    public void setViewMatrix(Mat4 matrix) {
        this.viewMatrix = matrix;
    }

    @Override
    public void setSuccessor(IFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void write(Face f) {

        Vec4 v1ViewPortNew = viewMatrix.multiply(f.getV1().multiply( 1.0f / f.getV1().getW()));
        Vec4 v2ViewPortNew = viewMatrix.multiply(f.getV2().multiply( 1.0f / f.getV2().getW()));
        Vec4 v3ViewPortNew = viewMatrix.multiply(f.getV3().multiply( 1.0f / f.getV3().getW()));

        Face newFace = new Face(v1ViewPortNew, v2ViewPortNew, v3ViewPortNew, f.getN1(), f.getN2(), f.getN3());
        this.successor.write(newFace);

    }
}
