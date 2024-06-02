package at.fhv.sysarch.lab3.pipeline.filters.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;

import java.util.Optional;

public class BackfaceCulling implements IFilter<Optional<Face>, Optional<Face>>{

    private Pipe<Optional<Face>> successor;
    @Override
    public void setSuccessor(Pipe<Optional<Face>> successor) {
        this.successor = successor;
    }

    @Override
    public void write(Optional<Face> f) {
        Optional.ofNullable(process(f)).ifPresent(successor::write);
    }

    @Override
    public Optional<Face> process(Optional<Face> f) {
        if (f.isPresent()) {
            if (f.get().getV1().dot(f.get().getN1()) <= 0) {
                return f;
            }
        } else {
            return Optional.empty();
        }
        return null;
    }
}
