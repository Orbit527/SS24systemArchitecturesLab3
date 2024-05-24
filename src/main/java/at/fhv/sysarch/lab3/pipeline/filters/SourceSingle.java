package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

public class SourceSingle implements IFilter<Model, Face> {

    private IFilter<Face, ?> successor;



    public void setSuccessor(IFilter<Face, ?> r) {
        this.successor = r;
    }



    public void write(Model model) {
        model.getFaces().forEach(face -> successor.write(face));
    }
}
