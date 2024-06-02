package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec4;

import java.util.Optional;

public class PullResizeFilter implements PullFilter<Optional<Face>, Optional<Face>>{

    private PullFilter<Optional<Face>, Optional<Face>> predecessor;

    private int size;

    public PullResizeFilter(int size) {
        this.size = size;
    }

    @Override
    public void setPredecessor(PullFilter<?, Optional<Face>> predecessor) {
        this.predecessor = (PullFilter<Optional<Face>, Optional<Face>>) predecessor;
    }

    @Override
    public Optional<Face> read() {

        Optional<Face> face = predecessor.read();
        if (face.isPresent()) {


            Face newFace = new Face(face.get().getV1().multiply(size) , face.get().getV2().multiply(size), face.get().getV3().multiply(size), face.get());

            return Optional.of(newFace);

            // Apply transformation logic here
        }
        return Optional.empty();

    }
}