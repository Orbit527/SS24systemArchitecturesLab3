package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Pipes.Pipe;
import at.fhv.sysarch.lab3.pipeline.Pipes.PullPipe;
import at.fhv.sysarch.lab3.pipeline.filters.pull.*;
import at.fhv.sysarch.lab3.pipeline.filters.push.DataPair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;

import java.util.Optional;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // pull from the source (model)

        Model model = pd.getModel();
        PullSource source = new PullSource();
        PullResizeFilter resizeFilter = new PullResizeFilter(1);
        PullModelViewTransformation trans = new PullModelViewTransformation();
        PullBackfaceCulling backface = new PullBackfaceCulling();
        PullDepthSorting depthSorting = new PullDepthSorting();
        PullColoring coloring = new PullColoring(pd.getModelColor());
        PullLighting lighting = new PullLighting(pd.getLightPos());
        PullPerspectiveTransformation persTrans = new PullPerspectiveTransformation();
        PullViewportTransformation viewTrans = new PullViewportTransformation();
        PullRenderer renderer = new PullRenderer(pd.getGraphicsContext(), pd.getRenderingMode());

        // Pipes
        PullPipe<Optional<Face>> sourceResizeFilterPipe = new PullPipe<>();
        PullPipe<Optional<Face>> resizeFilterTransPipe = new PullPipe<>();
        PullPipe<Optional<Face>> transBackfacePipe = new PullPipe<>();
        PullPipe<Optional<Face>> backfaceDepthSortingPipe = new PullPipe<>();
        PullPipe<Optional<Face>> depthSortingColoringPipe = new PullPipe<>();
        PullPipe<Optional<DataPair>> coloringLightingPipe = new PullPipe<>();
        PullPipe<Optional<DataPair>> lightingPersTransPipe = new PullPipe<>();
        PullPipe<Optional<DataPair>> persTransViewTransPipe = new PullPipe<>();
        PullPipe<Optional<DataPair>> viewTransRendererPipe = new PullPipe<>();


        // perform model-view transformation from model to VIEW SPACE coordinates
        source.setModel(model);
        sourceResizeFilterPipe.setPredecessor(source);
        resizeFilter.setPredecessor(sourceResizeFilterPipe);
        resizeFilterTransPipe.setPredecessor(resizeFilter);
        trans.setPredecessor(resizeFilterTransPipe);
        transBackfacePipe.setPredecessor(trans);

        // perform backface culling in VIEW SPACE
        backface.setPredecessor(transBackfacePipe);
        backfaceDepthSortingPipe.setPredecessor(backface);

        // perform depth sorting in VIEW SPACE
        depthSorting.setPredecessor(backfaceDepthSortingPipe);
        depthSortingColoringPipe.setPredecessor(depthSorting);

        // add coloring (space unimportant)
        coloring.setPredecessor(depthSortingColoringPipe);
        coloringLightingPipe.setPredecessor(coloring);

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. perform lighting in VIEW SPACE
            lighting.setPerformLighting(true);

            // 5. perform projection transformation on VIEW SPACE coordinates
            persTrans.setPredecessor(lightingPersTransPipe);
            persTransViewTransPipe.setPredecessor(persTrans);
        } else {
            // 5. perform projection transformation
            persTrans.setPredecessor(lightingPersTransPipe);
            persTransViewTransPipe.setPredecessor(persTrans);
        }

        lighting.setPredecessor(coloringLightingPipe);
        lightingPersTransPipe.setPredecessor(lighting);

        // perform perspective division to screen coordinates
        viewTrans.setPredecessor(persTransViewTransPipe);
        viewTransRendererPipe.setPredecessor(viewTrans);

        // feed into the sink (renderer)
        renderer.setPredecessor(viewTransRendererPipe);

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

                //  create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate
                Mat4 rotMat = Matrices.rotate(rotation, pd.getModelRotAxis());

                //  compute updated model-view tranformation
                Mat4 modelTransMat = pd.getModelTranslation().multiply(rotMat);

                // update model-view filter
                Mat4 viewTransMat = pd.getViewTransform().multiply(modelTransMat);

                // set additional matrices for filters
                trans.setTransMatrix(viewTransMat);
                persTrans.setProjMatrix(pd.getProjTransform());
                viewTrans.setViewMatrix(pd.getViewportTransform());

                // trigger rendering of the pipeline
                renderer.read();
            }
        };
    }
}