package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;

public interface PullFilter<I, O> {
    public void setPredecessor(PullPipe<I> predecessor);
    public O read();
}
