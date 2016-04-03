package com.example.android.drroster.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.drroster.R;


/**
 * Created by Nir on 4/2/2016.
 */
public class StepBarView extends View {


    private Paint dot;
    private int boldDotColor, normalDotColor, dotXPosition, dotYPosition, dotXStepLength;
    private int numberOfDots = 7;//default
    private int currentBoldDot = 0;//default
    private static final int RELATIVELY_HEIGHT_SIZE = 33;
    private static final int RELATIVELY_MARGIN_SIZE = 10;


    public StepBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //paint object for drawing in onDraw
        dot = new Paint();

        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StepBarView, 0, 0);

        try {
            //Retrieve dot color attributes from xml
            normalDotColor = a.getInteger(R.styleable.StepBarView_normalDotColor, 0);
            boldDotColor = a.getInteger(R.styleable.StepBarView_boldDotColor, 0);
            numberOfDots = a.getInteger(R.styleable.StepBarView_numberOfDots, 0);
        } finally {
            a.recycle();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Sets the radius of one dot based on device height
        int mRadius = (this.getMeasuredHeight() / RELATIVELY_HEIGHT_SIZE) - RELATIVELY_MARGIN_SIZE;

        //Initiate bold dot style
        dot.setStyle(Paint.Style.FILL);
        dot.setAntiAlias(true);

        //Sets first position and step length between dots
        dotXStepLength = (int) ((this.getMeasuredWidth() / numberOfDots) * 0.9);
        dotXPosition = dotXStepLength;
        dotYPosition = this.getMeasuredHeight() - (this.getMeasuredHeight() / RELATIVELY_HEIGHT_SIZE);

        //Draw number of dots
        for (int i = 0; i < numberOfDots; i++) {

            if (i != currentBoldDot) {
                //Sets normal dot color
                dot.setColor(normalDotColor);
                //Draw normal dot
                canvas.drawCircle(
                        //Sets the dot x position based on number of dots divided by device width
                        dotXPosition,
                        // sets height base on height - relative height in screen
                        dotYPosition,
                        //sets smaller radius for normal dots
                        ((float) (mRadius * 0.7)),
                        dot);
            } else {
                //Sets bold dot color
                dot.setColor(boldDotColor);
                //Draw Bold dot
                canvas.drawCircle(
                        //Sets the dot x position based on number of dots divided by device width
                        dotXPosition,
                        // sets height base on height - relative height in screen
                        dotYPosition,
                        mRadius,
                        dot);

            }
            //Change the x position
            dotXPosition = dotXPosition + dotXStepLength;
        }
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        //Change behavior for warp content
//        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
//    }

    public void setBoldDot(int currentBoldDot) {
        //update the instance variable
        this.currentBoldDot = currentBoldDot;
        //redraw the view
        invalidate();
        requestLayout();
    }
}
