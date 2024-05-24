package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Matrices;

public class BackfaceCulling implements IFilter<Face, Face>{

    private IFilter<Face, ?> successor;
    @Override
    public void setSuccessor(IFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void write(Face f) {
        if (f.getV1().dot(f.getN1()) <= 0) {
            successor.write(f);
        }
    }
}
