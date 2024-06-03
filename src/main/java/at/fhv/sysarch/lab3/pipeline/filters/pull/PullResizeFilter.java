package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import at.fhv.sysarch.lab3.utils.MarkedFace;

import java.util.Optional;

public class PullResizeFilter implements PullFilter<Optional<Face>, Optional<Face>>{

    private PullPipe< Optional<Face>> predecessor;

    private int size;

    public PullResizeFilter(int size) {
        this.size = size;
    }

    @Override
    public void setPredecessor(PullPipe<Optional<Face>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Optional<Face> read() {

        Optional<Face> face = predecessor.read();
        if (face.isPresent()) {
            if (face.get() instanceof MarkedFace) {
                return face;
            }

            Face newFace = new Face(face.get().getV1().multiply(size) , face.get().getV2().multiply(size), face.get().getV3().multiply(size), face.get());

            return Optional.of(newFace);

        }
        return Optional.empty();

    }
}
