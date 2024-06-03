package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import at.fhv.sysarch.lab3.utils.MarkedFace;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;

public class PullDepthSorting implements PullFilter<Optional<Face>, Optional<Face>>{

    private PullPipe<Optional<Face>> predecessor;

    private LinkedList<Face> faces = new LinkedList<>();
    private LinkedList<Face> facesSorted = new LinkedList<>();

    @Override
    public void setPredecessor(PullPipe<Optional<Face>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Optional<Face> read() {

        if (!facesSorted.isEmpty()) {
            return Optional.ofNullable(facesSorted.removeFirst());
        }

        Optional<Face> face = predecessor.read();

        if (face.isPresent()) {
            faces.add(face.get());

            if ( face.get() instanceof MarkedFace) {
                sortFaces();
            } else {

            }
        }
        return Optional.empty();

    }

    private void sortFaces() {
        if (!faces.isEmpty()) {
            faces.sort(Comparator.comparing(f ->
                            (f != null ? f.getV1().getZ() : 0) +
                            (f != null ? f.getV2().getZ() : 0) +
                            (f != null ? f.getV3().getZ() : 0)));
            facesSorted = faces;
            faces = new LinkedList<>();
        }
    }

}
