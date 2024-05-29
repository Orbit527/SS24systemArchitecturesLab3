package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SourceSingle implements IFilter<Model, Optional<Face>> {

    private IFilter<Optional<Face>, ?> successor;



    public void setSuccessor(IFilter<Optional<Face>, ?> r) {
        this.successor = r;
    }


    public void write(Model model) {
        List<Face> faces = model.getFaces();
        Iterator<Face> iterator = faces.iterator();

        while (iterator.hasNext()) {
            Face face = iterator.next();
            successor.write(Optional.ofNullable(face));

            if (!iterator.hasNext()) {
                successor.write(Optional.empty());
            }

        }
    }
}
