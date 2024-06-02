package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec4;

public class MarkedFace extends Face {

    public MarkedFace(Vec4 v1, Vec4 v2, Vec4 v3, Vec4 n1, Vec4 n2, Vec4 n3) {
        super(v1, v2, v3, n1, n2, n3);
    }

    public MarkedFace(Vec4 v1, Vec4 v2, Vec4 v3, Face otherNormals) {
        super(v1, v2, v3, otherNormals);
    }

    public MarkedFace(Face other) {
        super(other);
    }
}
