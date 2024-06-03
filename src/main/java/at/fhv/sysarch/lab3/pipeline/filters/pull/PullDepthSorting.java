package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class PullDepthSorting implements PullFilter<Optional<Face>, Optional<Face>>{

    private PullFilter<Optional<Face>, Optional<Face>> predecessor;

    private ArrayList<Face> faces = new ArrayList<>();
    private ArrayList<Face> depthSortedFaces = new ArrayList<>();
    private int i = 0;

    private Face testFace;

    @Override
    public void setPredecessor(PullFilter<?, Optional<Face>> predecessor) {
        this.predecessor = (PullFilter<Optional<Face>, Optional<Face>>) predecessor;
    }

    @Override
    public Optional<Face> read() {

        //If we have already collected the faces, return the sorted faces.
        if (!depthSortedFaces.isEmpty()) {
            return Optional.ofNullable(depthSortedFaces.removeFirst());
        }

        Optional<Face> face = predecessor.read();

        if (face.isPresent()) {
            if ( !(face.get() instanceof MarkedFace)) {
                if (face != null) {
                    faces.add(face.get());
                }
            } else {
                faces.add(face.get());
                pullSortFaces();
            }
        }
        return Optional.empty();

    }

    private void pullSortFaces() {
        if (!faces.isEmpty()) {
            // Sort the filtered list
            faces.sort(Comparator.comparing(f ->
                    (f != null ? f.getV1().getZ() : 0) +
                            (f != null ? f.getV2().getZ() : 0) +
                            (f != null ? f.getV3().getZ() : 0)));

            depthSortedFaces = faces;
            faces = new ArrayList<>();
        }
    }

}
