package at.fhv.sysarch.lab3.pipeline.Pipes;

import at.fhv.sysarch.lab3.pipeline.filters.push.IFilter;

public class Pipe<I> {
    private IFilter<I, ?> successor;

    public void setSuccessor(IFilter<I, ?> r) {
        this.successor = r;
    }
    public void write(I input) {
        successor.write(input);
    }

}
