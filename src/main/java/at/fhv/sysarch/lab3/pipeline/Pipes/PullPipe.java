package at.fhv.sysarch.lab3.pipeline.Pipes;

import at.fhv.sysarch.lab3.pipeline.filters.pull.PullFilter;

public class PullPipe<I> {

    private PullFilter<?, I> predecessor;

    public void setPredecessor(PullFilter<?, I> r) {
        this.predecessor = r;
    }
    public I read() {
        return predecessor.read();
    }

}
