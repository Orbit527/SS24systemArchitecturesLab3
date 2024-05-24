package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.PipelineData;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;

public class ResizeFilter implements IFilter<Face, Face> {

    private IFilter<Face, ?> successor;

    private int size;

    public ResizeFilter(int size) {
        this.size = size;
    }

    public void setSuccessor(IFilter<Face, ?> r) {
        this.successor = r;
    }

    public void write(Face face) {
        Face newFace = new Face(face.getV1().multiply(size), face.getV2().multiply(size), face.getV3().multiply(size), face);
        this.successor.write(newFace);
    }
}
