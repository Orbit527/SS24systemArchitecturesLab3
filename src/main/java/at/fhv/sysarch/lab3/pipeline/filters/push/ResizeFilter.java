package at.fhv.sysarch.lab3.pipeline.filters.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;

import java.util.Optional;

public class ResizeFilter implements IFilter<Optional<Face>, Optional<Face>> {

    private Pipe<Optional<Face>> successor;

    private int size;

    public ResizeFilter(int size) {
        this.size = size;
    }

    @Override
    public void setSuccessor(Pipe<Optional<Face>> r) {
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
