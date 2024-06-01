package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;
import javafx.scene.paint.Color;

import java.util.Optional;

public class Coloring implements IFilter<Optional<Face>, DataPair>{


    private Pipe<DataPair> successor;
    private final Color color;

    public Coloring(Color color) {
        this.color = color;
    }

    @Override
    public void setSuccessor(Pipe<DataPair> successor) {
        this.successor = successor;
    }

    @Override
    public void write(Optional<Face> face) {
        if (face.isPresent()) {
            successor.write(process(face));
        }
    }

    @Override
    public DataPair process(Optional<Face> face) {
            DataPair dataPair = new DataPair(face.get(), color);
            return dataPair;
    }
}
