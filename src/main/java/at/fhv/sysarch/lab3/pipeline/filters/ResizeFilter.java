package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;

import java.util.Optional;
import java.util.OptionalInt;

public class ResizeFilter implements IFilter<Optional<Face>, Optional<Face>> {

    private IFilter<Optional<Face>, ?> successor;

    private int size;

    public ResizeFilter(int size) {
        this.size = size;
    }

    @Override
    public void setSuccessor(IFilter<Optional<Face>, ?> r) {
        this.successor = r;
    }

    @Override
    public void write(Optional<Face> face) {
        successor.write(process(face));
    }

    @Override
    public Optional<Face> process(Optional<Face> face) {
        if (face.isPresent()) {
            Face newFace = new Face(face.get().getV1().multiply(size), face.get().getV2().multiply(size), face.get().getV3().multiply(size), face.get());
            return Optional.of(newFace);
        } else {
            return Optional.empty();
        }
    }
}
