package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SourceSingle implements IFilter<Model, Optional<Face>> {

    private Pipe<Optional<Face>> successor;



    public void setSuccessor(Pipe<Optional<Face>> r) {
        this.successor = r;
    }


    public void write(Model model) {
        List<Face> faces = model.getFaces();

        for (Face f : faces) {
            successor.write(Optional.ofNullable(f));
        }

        successor.write(Optional.empty());
    }

    @Override
    public Optional<Face> process(Model input) {
        // NOT IMPLEMENTED
        return Optional.empty();
    }
}
