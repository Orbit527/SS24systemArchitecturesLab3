package at.fhv.sysarch.lab3.pipeline.filters;

import at.fhv.sysarch.lab3.obj.Face;
import javafx.scene.paint.Color;

import java.util.Optional;

public class Coloring implements IFilter<Optional<Face>, DataPair>{


    private IFilter<DataPair, ?> successor;
    private final Color color;

    public Coloring(Color color) {
        this.color = color;
    }

    @Override
    public void setSuccessor(IFilter<DataPair, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void write(Optional<Face> face) {
        if (face.isPresent()) {
            DataPair dataPair = new DataPair(face.get(), color);
            this.successor.write(dataPair);
        }
    }
}
