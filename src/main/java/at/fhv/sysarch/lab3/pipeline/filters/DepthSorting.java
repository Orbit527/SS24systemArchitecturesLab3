package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;

public class DepthSorting implements IFilter<Face, Face> {

    private IFilter<Face, ?> successor;

    @Override
    public void setSuccessor(IFilter<Face, ?> r) {
        this.successor = r;
    }

    @Override
    public void write(Face face) {

        //TODO: implement further

        this.successor.write(face);
    }

}
