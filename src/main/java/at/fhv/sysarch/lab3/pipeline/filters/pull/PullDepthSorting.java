package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.ArrayList;
import java.util.Optional;

public class PullDepthSorting implements PullFilter<Optional<Face>, Optional<Face>>{

    private PullFilter<Optional<Face>, Optional<Face>> predecessor;

    private ArrayList<Face> faces = new ArrayList<>();

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

            //TODO: implement

            return face;

        }
        return Optional.empty();
    }
}
