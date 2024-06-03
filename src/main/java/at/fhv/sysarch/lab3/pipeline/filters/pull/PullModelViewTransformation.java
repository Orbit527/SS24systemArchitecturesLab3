package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

import java.util.Optional;

public class PullModelViewTransformation implements PullFilter<Optional<Face>, Optional<Face>> {
    private PullPipe<Optional<Face>> predecessor;

    private Mat4 transMatrix;

    public void setTransMatrix(Mat4 matrix) {
        this.transMatrix = matrix;
    }

    @Override
    public void setPredecessor(PullPipe<Optional<Face>> predecessor) {
        this.predecessor =  predecessor;
    }

    @Override
    public Optional<Face> read() {
        Optional<Face> face = predecessor.read();
        if (face.isPresent()) {

            if (face.get() instanceof MarkedFace) {
                return face;
            }

            Vec4 v1new = transMatrix.multiply(face.get().getV1());
            Vec4 v2new = transMatrix.multiply(face.get().getV2());
            Vec4 v3new = transMatrix.multiply(face.get().getV3());

            Vec4 v1NormalNew = transMatrix.multiply(face.get().getN1());
            Vec4 v2NormalNew = transMatrix.multiply(face.get().getN2());
            Vec4 v3NormalNew = transMatrix.multiply(face.get().getN3());

            Face transFace = new Face(v1new, v2new, v3new, v1NormalNew, v2NormalNew, v3NormalNew);

            return Optional.of(transFace);

        }
        return Optional.empty();
    }
}
