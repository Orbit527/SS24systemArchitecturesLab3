package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import at.fhv.sysarch.lab3.utils.MarkedFace;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class PullSource implements PullFilter<Model, Optional<Face>> {
    private Iterator<Face> iterator;
    private List<Face> faces;

    private Model model;

    public void setModel(Model model) {
        this.model = model;
        this.faces = model.getFaces();
        setFaceIterator();

    }

    private void setFaceIterator() {
        this.iterator = faces.iterator();
    }

    @Override
    public void setPredecessor(PullPipe<Model> predecessor) {
        // Source has no predecessor
    }

    @Override
    public Optional<Face> read() {

        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        } else {
            setFaceIterator();
            return Optional.of(new MarkedFace(iterator.next()));
        }

    }
}
