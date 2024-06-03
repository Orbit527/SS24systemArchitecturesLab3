package at.fhv.sysarch.lab3.pipeline.filters.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import at.fhv.sysarch.lab3.pipeline.filters.push.DataPair;
import javafx.scene.paint.Color;

import java.util.Optional;

public class PullColoring implements PullFilter<Optional<Face>, Optional<DataPair>>{

    private PullPipe<Optional<Face>> predecessor;
    private final Color color;

    public PullColoring(Color color) {
        this.color = color;
    }

    @Override
    public void setPredecessor(PullPipe<Optional<Face>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Optional<DataPair> read() {

        Optional<Face> face = predecessor.read();
        if (face.isPresent()) {

            DataPair dataPair = new DataPair(face.get(), color);

            return Optional.of(dataPair);

            // Apply transformation logic here
        }
        return Optional.empty();
    }
}
