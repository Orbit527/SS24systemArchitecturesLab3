package at.fhv.sysarch.lab3.pipeline.filters.pull;

public interface PullFilter<I, O> {
    public void setPredecessor(PullFilter<?, I> predecessor);
    public O read();
}
