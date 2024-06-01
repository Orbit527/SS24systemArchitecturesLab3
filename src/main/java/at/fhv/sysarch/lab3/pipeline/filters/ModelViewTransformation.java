package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

import java.util.Optional;

public class ModelViewTransformation implements IFilter<Optional<Face>, Optional<Face>> {

    private Pipe<Optional<Face>> successor;
    private Mat4 transMatrix;

    public void setTransMatrix(Mat4 matrix) {
        this.transMatrix = matrix;
    }

    @Override
    public void setSuccessor(Pipe successor) {
        this.successor = successor;
    }

    @Override
    public void write(Optional<Face> f) {
        successor.write(process(f));
    }

    @Override
    public Optional<Face> process(Optional<Face> f) {
        if (f.isPresent()) {
            Vec4 v1new = transMatrix.multiply(f.get().getV1());
            Vec4 v2new = transMatrix.multiply(f.get().getV2());
            Vec4 v3new = transMatrix.multiply(f.get().getV3());

            Vec4 v1NormalNew = transMatrix.multiply(f.get().getN1());
            Vec4 v2NormalNew = transMatrix.multiply(f.get().getN2());
            Vec4 v3NormalNew = transMatrix.multiply(f.get().getN3());

            Face transFace = new Face(v1new, v2new, v3new, v1NormalNew, v2NormalNew, v3NormalNew);

            return Optional.of(transFace);

        } else {
            return Optional.empty();
        }
    }
}
