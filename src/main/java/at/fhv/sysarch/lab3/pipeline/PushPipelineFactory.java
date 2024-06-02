package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;
import at.fhv.sysarch.lab3.pipeline.filters.push.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;

import java.util.Optional;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: push from the source (model)

        SourceSingle source = new SourceSingle();
        //TODO: size not setting correctly
        ResizeFilter resizeFilter = new ResizeFilter(1);
        ModelViewTransformation trans = new ModelViewTransformation();
        BackfaceCulling backface = new BackfaceCulling();
        DepthSorting depthSorting = new DepthSorting();
        Coloring coloring = new Coloring(pd.getModelColor());
        Lighting lighting = new Lighting(pd.getLightPos());
        PerspectiveTransformation persTrans = new PerspectiveTransformation();
        ViewportTransformation viewTrans = new ViewportTransformation();
        Renderer renderer = new Renderer(pd.getGraphicsContext(), pd.getRenderingMode());

        // Pipes
        Pipe<Optional<Face>> sourceResizeFilterPipe = new Pipe<>();
        Pipe<Optional<Face>> resizeFilterTransPipe = new Pipe<>();
        Pipe<Optional<Face>> transBackfacePipe = new Pipe<>();
        Pipe<Optional<Face>> backfaceDepthSortingPipe = new Pipe<>();
        Pipe<Optional<Face>> depthSortingColoringPipe = new Pipe<>();
        Pipe<DataPair> coloringLightingPipe = new Pipe<>();
        Pipe<DataPair> lightingPersTransPipe = new Pipe<>();
        Pipe<DataPair> persTransViewTransPipe = new Pipe<>();
        Pipe<DataPair> viewTransRendererPipe = new Pipe<>();



        // perform model-view transformation from model to VIEW SPACE coordinates
        source.setSuccessor(sourceResizeFilterPipe);
        sourceResizeFilterPipe.setSuccessor(resizeFilter);
        resizeFilter.setSuccessor(resizeFilterTransPipe);
        resizeFilterTransPipe.setSuccessor(trans);
        trans.setSuccessor(transBackfacePipe);
        transBackfacePipe.setSuccessor(backface);

        // perform backface culling in VIEW SPACE
        backface.setSuccessor(backfaceDepthSortingPipe);
        backfaceDepthSortingPipe.setSuccessor(depthSorting);

        // perform depth sorting in VIEW SPACE
        depthSorting.setSuccessor(depthSortingColoringPipe);
        depthSortingColoringPipe.setSuccessor(coloring);

        // add coloring (space unimportant)
        coloring.setSuccessor(coloringLightingPipe);
        coloringLightingPipe.setSuccessor(lighting);

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {

            // 4a. perform lighting in VIEW SPACE
            lighting.setPerformLighting(true);

            // 5. TODO perform projection transformation on VIEW SPACE coordinates
            persTrans.setSuccessor(persTransViewTransPipe);
            persTransViewTransPipe.setSuccessor(viewTrans);

        } else {
            // 5. TODO perform projection transformation
            persTrans.setSuccessor(persTransViewTransPipe);
            persTransViewTransPipe.setSuccessor(viewTrans);

        }

        lighting.setSuccessor(lightingPersTransPipe);
        lightingPersTransPipe.setSuccessor(persTrans);

        // perform perspective division to screen coordinates
        viewTrans.setSuccessor(viewTransRendererPipe);
        viewTransRendererPipe.setSuccessor(renderer);

        // TODO 7. feed into the sink (renderer)

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {

            private float rotation = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {

                // compute rotation in radians
                rotation += fraction * 1;

                // TODO: Diese drei vielleicht auslagern

                // create new model rotation matrix using pd.modelRotAxis
                Mat4 rotMat = Matrices.rotate(rotation, pd.getModelRotAxis());

                // compute updated model-view tranformation
                Mat4 modelTransMat = pd.getModelTranslation().multiply(rotMat);

                // update model-view filter
                Mat4 viewTransMat = pd.getViewTransform().multiply(modelTransMat);

                // set additional matrices for filters
                trans.setTransMatrix(viewTransMat);
                persTrans.setProjMatrix(pd.getProjTransform());
                viewTrans.setViewMatrix(pd.getViewportTransform());

                // trigger rendering of the pipeline
                source.write(model);
            }
        };
    }
}