package at.fhv.sysarch.lab3.pipeline.Pipes;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filters.IFilter;

import java.util.Optional;

public class Pipe<I> {
    private IFilter<I, ?> successor;

    public void setSuccessor(IFilter<I, ?> r) {
        this.successor = r;
    }
    public void write(I input) {
        successor.write(input);
    }

}
