package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class ModelViewTransformation implements IFilter<Face, Face> {

    private IFilter<Face, ?> successor;
    private Mat4 transMatrix;

    public void setTransMatrix(Mat4 matrix) {
        this.transMatrix = matrix;
    }

    @Override
    public void setSuccessor(IFilter successor) {
        this.successor = successor;
    }

    @Override
    public void write(Face f) {
            Vec4 v1new = transMatrix.multiply(f.getV1());
            Vec4 v2new = transMatrix.multiply(f.getV2());
            Vec4 v3new = transMatrix.multiply(f.getV3());

            Vec4 v1NormalNew = transMatrix.multiply(f.getN1());
            Vec4 v2NormalNew = transMatrix.multiply(f.getN2());
            Vec4 v3NormalNew = transMatrix.multiply(f.getN3());

            Face transFace = new Face(v1new, v2new, v3new, v1NormalNew, v2NormalNew, v3NormalNew);



            successor.write(transFace);

    }
}
