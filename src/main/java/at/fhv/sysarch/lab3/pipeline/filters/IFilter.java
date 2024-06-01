package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;

public interface IFilter<I, O> {

    public void setSuccessor(Pipe<O> successor);

    public void write(I input);

    public O process(I input);
}
