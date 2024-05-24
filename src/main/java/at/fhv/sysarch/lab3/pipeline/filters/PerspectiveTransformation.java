package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class PerspectiveTransformation  implements IFilter<Face, Face> {

    private IFilter<Face, ?> successor;
    private Mat4 projMatrix;

    public void setProjMatrix(Mat4 matrix) {
        this.projMatrix = matrix;
    }

    @Override
    public void setSuccessor(IFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void write(Face f) {

        Vec4 v1ViewPort = projMatrix.multiply(f.getV1());
        Vec4 v2ViewPort = projMatrix.multiply(f.getV2());
        Vec4 v3ViewPort = projMatrix.multiply(f.getV3());

        Face newFace = new Face(v1ViewPort, v2ViewPort, v3ViewPort, f.getN1(), f.getN2(), f.getN3());
        this.successor.write(newFace);
    }
}
