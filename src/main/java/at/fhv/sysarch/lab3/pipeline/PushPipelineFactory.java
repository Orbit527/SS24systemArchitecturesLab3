package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filters.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import javafx.animation.AnimationTimer;

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
        Lighting lighting = new Lighting(pd.getLightPos(), pd.isPerformLighting());
        PerspectiveTransformation persTrans = new PerspectiveTransformation();
        ViewportTransformation viewTrans = new ViewportTransformation();
        Renderer renderer = new Renderer(pd.getGraphicsContext(), pd.getRenderingMode());

        viewTrans.setSuccessor(renderer);
        persTrans.setSuccessor(viewTrans);
        lighting.setSuccessor(persTrans);
        coloring.setSuccessor(lighting);
        depthSorting.setSuccessor(coloring);
        backface.setSuccessor(depthSorting);
        trans.setSuccessor(backface);
        resizeFilter.setSuccessor(trans);
        source.setSuccessor(resizeFilter);

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