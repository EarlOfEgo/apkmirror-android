package com.pascalwelsch.apkmirror.detail;

import com.squareup.picasso.Transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.lang.ref.WeakReference;

/**
 * https://gist.github.com/ryanbateman/6667995
 */
public class BlurTransform implements Transformation {

    private final int mRadius;

    private final WeakReference<Context> mContextReference;


    public BlurTransform(final Context context, final int radius) {
        super();
        mRadius = radius;
        mContextReference = new WeakReference<Context>(context);
    }

    @Override
    public String key() {
        return "blur";
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {

        RenderScript renderScript = RenderScript.create(mContextReference.get());
        // Create another bitmap that will hold the results of the filter.
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap);

        // Allocate memory for Renderscript to work with
        Allocation input = Allocation
                .createFromBitmap(renderScript, bitmap, Allocation.MipmapControl.MIPMAP_FULL,
                        Allocation.USAGE_SHARED);
        Allocation output = Allocation.createTyped(renderScript, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(
                renderScript, Element.U8_4(renderScript));
        script.setInput(input);

        // Set the blur radius
        script.setRadius(mRadius);

        // Start the ScriptIntrinisicBlur
        script.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        return blurredBitmap;
    }

}