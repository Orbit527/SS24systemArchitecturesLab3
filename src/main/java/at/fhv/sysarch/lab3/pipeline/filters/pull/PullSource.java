package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.Iterator;
import java.util.Optional;

public class PullSource implements PullFilter<Model, Optional<Face>> {
    private Iterator<Face> faceIterator;

    private Model model;

    public void setModel(Model model) {
        this.model = model;
        setFaceIterator();

    }

    private void setFaceIterator() {
        this.faceIterator = this.model.getFaces().iterator();
    }

    @Override
    public void setPredecessor(PullFilter<?, Model> predecessor) {
        // Source has no predecessor
    }

    @Override
    public Optional<Face> read() {


        if (faceIterator != null && faceIterator.hasNext()) {

            //System.out.println("READ!!");
            return Optional.of(faceIterator.next());
        }
        setFaceIterator();
        return Optional.empty();
    }
}
