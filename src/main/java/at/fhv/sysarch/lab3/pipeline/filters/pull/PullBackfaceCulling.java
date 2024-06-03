package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import at.fhv.sysarch.lab3.utils.MarkedFace;

import java.util.Optional;

public class PullBackfaceCulling implements PullFilter<Optional<Face>, Optional<Face>>{

    private PullPipe<Optional<Face>> predecessor;

    @Override
    public void setPredecessor(PullPipe< Optional<Face>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Optional<Face> read() {
        Optional<Face> face = predecessor.read();
        if (face.isPresent()) {

            if (face.get() instanceof MarkedFace) {
                return face;
            }

            if (face.get().getV1().dot(face.get().getN1()) <= 0) {
                return face;
            } else {
                return Optional.empty();
            }

        }
        return Optional.empty();
    }
}
