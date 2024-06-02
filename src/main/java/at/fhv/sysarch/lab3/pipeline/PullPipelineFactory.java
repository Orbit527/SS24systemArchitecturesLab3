package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filters.PullRenderer;
import at.fhv.sysarch.lab3.pipeline.filters.PullSource;
import at.fhv.sysarch.lab3.pipeline.filters.PullModelViewTransformation;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: pull from the source (model)

        Model model = pd.getModel();

        PullSource source = new PullSource();
        PullModelViewTransformation trans = new PullModelViewTransformation();
        PullRenderer renderer = new PullRenderer(pd.getGraphicsContext(), pd.getRenderingMode());

        source.setModel(model);
        trans.setPredecessor(source);
        renderer.setPredecessor(trans);

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates

        // TODO 2. perform backface culling in VIEW SPACE

        // TODO 3. perform depth sorting in VIEW SPACE

        // TODO 4. add coloring (space unimportant)

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            // 5. TODO perform projection transformation
        }

        // TODO 6. perform perspective division to screen coordinates

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

                //  create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate
                Mat4 rotMat = Matrices.rotate(rotation, pd.getModelRotAxis());

                //  compute updated model-view tranformation
                Mat4 modelTransMat = pd.getModelTranslation().multiply(rotMat);

                // update model-view filter
                Mat4 viewTransMat = pd.getViewTransform().multiply(modelTransMat);

                // set additional matrices for filters
                trans.setTransMatrix(viewTransMat);
                //TODO: others

                // trigger rendering of the pipeline
                renderer.read();
            }
        };
    }
}