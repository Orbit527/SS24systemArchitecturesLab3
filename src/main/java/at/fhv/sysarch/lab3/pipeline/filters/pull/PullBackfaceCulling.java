package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.Optional;

public class PullBackfaceCulling implements PullFilter<Optional<Face>, Optional<Face>>{

    private PullFilter<Optional<Face>, Optional<Face>> predecessor;

    @Override
    public void setPredecessor(PullFilter<?, Optional<Face>> predecessor) {
        this.predecessor = (PullFilter<Optional<Face>, Optional<Face>>) predecessor;
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
