package at.fhv.sysarch.lab3.pipeline.Pipes;

import at.fhv.sysarch.lab3.pipeline.filters.push.PushFilter;

public class Pipe<I> {
    private PushFilter<I, ?> successor;

    public void setSuccessor(PushFilter<I, ?> r) {
        this.successor = r;
    }
    public void write(I input) {
        successor.write(input);
    }

}
