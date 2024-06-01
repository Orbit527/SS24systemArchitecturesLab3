package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class DepthSorting implements IFilter<Optional<Face>, Optional<Face>> {

    private Pipe<Optional<Face>> successor;
    private ArrayList<Face> faces = new ArrayList<>();

    @Override
    public void setSuccessor(Pipe<Optional<Face>> r) {
        this.successor = r;
    }

    @Override
    public void write(Optional<Face> face) {
        if (process(face) != null) {
            for (Face f : faces) {
                successor.write(Optional.of(f));
            }
            faces.clear();
        }

    }


    @Override
    public Optional<Face> process(Optional<Face> face) {
        if (face.isPresent()) {
            faces.add(face.get());
            return null;
        } else {
            faces.sort(Comparator.comparingDouble((Face f) -> f.getV1().getZ()));
            return face;
        }

    }
}
