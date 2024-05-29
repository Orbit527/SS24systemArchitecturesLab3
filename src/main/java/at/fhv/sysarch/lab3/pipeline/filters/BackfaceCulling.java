package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Matrices;

import java.util.Optional;

public class BackfaceCulling implements IFilter<Optional<Face>, Optional<Face>>{

    private IFilter<Optional<Face>, ?> successor;
    @Override
    public void setSuccessor(IFilter<Optional<Face>, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void write(Optional<Face> f) {
        if (f.isPresent()) {
            if (f.get().getV1().dot(f.get().getN1()) <= 0) {
                successor.write(f);
            }
        } else {
            successor.write(Optional.empty());
        }
    }
}
