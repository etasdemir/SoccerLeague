package com.elacqua.soccerleague.customview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.SoundEffectConstants
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Checkable
import androidx.core.content.res.ResourcesCompat
import com.elacqua.soccerleague.R

class DayNightSwitch : View, Checkable {
    private var switchOnColor: Int
    private var switchOffColor: Int
    private var spotOnColor: Int
    private var spotOnColorIn: Int
    private var spotOffColor: Int
    private var spotOffColorIn: Int
    private var switchOnStrokeColor: Int
    private var switchOffStrokeColor: Int
    private var spotPadding: Int
    private var currentPos = 0f
    private var mChecked = false
    private var mBroadcasting = false
    private var isMoving = false
    var duration: Int
    var onCheckedChangeListener: OnCheckedChangeListener? = null
    private var valueAnimator: ValueAnimator? = null

    private enum class State {
        SWITCH_ANIMATION_OFF, SWITCH_ANIMATION_ON, SWITCH_ON, SWITCH_OFF
    }

    private var state: State

    constructor(context: Context?) : super(context) {
        switchOnColor = DEFAULT_SWITCH_ON_COLOR
        switchOffColor = DEFAULT_SWITCH_OFF_COLOR
        spotOnColor = DEFAULT_SPOT_ON_COLOR
        spotOnColorIn = DEFAULT_SPOT_ON_COLOR_IN
        spotOffColor = DEFAULT_SPOT_OFF_COLOR
        spotOffColorIn = DEFAULT_SPOT_OFF_COLOR_IN
        spotPadding = dp2px(DEFAULT_SPOT_PADDING.toFloat())
        switchOnStrokeColor = switchOnColor
        switchOffStrokeColor = switchOffColor
        duration = ANIMATION_DURATION
        state = if (mChecked) State.SWITCH_ON else State.SWITCH_OFF
        isClickable = true
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.DayNightSwitch)
        switchOnColor =
            a.getColor(R.styleable.DayNightSwitch_switchOnColor, DEFAULT_SWITCH_ON_COLOR)
        switchOffColor =
            a.getColor(R.styleable.DayNightSwitch_switchOffColor, DEFAULT_SWITCH_OFF_COLOR)
        spotOnColor = a.getColor(R.styleable.DayNightSwitch_spotOnColor, DEFAULT_SPOT_ON_COLOR)
        spotOnColorIn = a.getColor(R.styleable.DayNightSwitch_spotOnColor, DEFAULT_SPOT_ON_COLOR_IN)
        spotOffColor = a.getColor(R.styleable.DayNightSwitch_spotOffColor, DEFAULT_SPOT_OFF_COLOR)
        spotOffColorIn =
            a.getColor(R.styleable.DayNightSwitch_spotOnColor, DEFAULT_SPOT_OFF_COLOR_IN)
        spotPadding = a.getDimensionPixelSize(
            R.styleable.DayNightSwitch_spotPadding, dp2px(
                DEFAULT_SPOT_PADDING.toFloat()
            )
        )
        switchOnStrokeColor =
            a.getColor(R.styleable.DayNightSwitch_switchOnStrokeColor, switchOnColor)
        switchOffStrokeColor =
            a.getColor(R.styleable.DayNightSwitch_switchOffStrokeColor, switchOffColor)
        duration = a.getInteger(R.styleable.DayNightSwitch_duration, ANIMATION_DURATION)
        mChecked = a.getBoolean(R.styleable.DayNightSwitch_checked, false)
        a.recycle()
        state = if (mChecked) State.SWITCH_ON else State.SWITCH_OFF
        isClickable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        var width: Int = dp2px(DEFAULT_WIDTH.toFloat()) + paddingLeft + paddingRight
        var height: Int = dp2px(DEFAULT_HEIGHT.toFloat()) + paddingTop + paddingBottom
        if (widthSpecMode != MeasureSpec.AT_MOST) {
            width = Math.max(width, widthSpecSize)
        }
        if (heightSpecMode != MeasureSpec.AT_MOST) {
            height = Math.max(height, heightSpecSize)
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w: Int = width
        val h: Int = height
        val pl: Int = paddingLeft
        val pt: Int = paddingTop
        val pr: Int = paddingRight
        val pb: Int = paddingBottom
        val wp = w - pl - pr
        val hp = h - pt - pb
        val sw = dp2px(DEFAULT_WIDTH.toFloat())
        val sh = dp2px(DEFAULT_HEIGHT.toFloat())
        val dx = pl + (wp - sw) / 2
        val dy = pt + (hp - sh) / 2
        canvas.translate(dx.toFloat(), dy.toFloat())
        when (state) {
            State.SWITCH_ON -> drawSwitchOn(canvas)
            State.SWITCH_OFF -> drawSwitchOff(canvas)
            State.SWITCH_ANIMATION_ON -> drawSwitchOnAnim(canvas)
            State.SWITCH_ANIMATION_OFF -> drawSwitchOffAnim(canvas)
        }
    }

    private fun drawSwitchOn(canvas: Canvas) {
        val rectAttrs = compRoundRectAttr(SWITCH_OFF_POS.toFloat())
        drawRoundRect(canvas, switchOnColor, rectAttrs)
        val ovalAttrs = compOvalAttr(SWITCH_ON_POS.toFloat())
        drawOval(canvas, spotOnColor, ovalAttrs)
        drawOvalIn(canvas, spotOnColorIn, ovalAttrs)
        drawCloud(canvas, 1f)
        drawRoundRectStroke(canvas, DEFAULT_SWITCH_ON_COLOR_OUT)
    }

    private fun drawSwitchOff(canvas: Canvas) {
        val rectAttrs = compRoundRectAttr(SWITCH_OFF_POS.toFloat())
        drawRoundRect(canvas, switchOffColor, rectAttrs)
        val ovalAttrs = compOvalAttr(SWITCH_OFF_POS.toFloat())
        drawOval(canvas, spotOffColor, ovalAttrs)
        drawOvalIn(canvas, spotOffColorIn, ovalAttrs)
        drawCircleDot(canvas, spotOffColor, spotOffColorIn, 1f, ovalAttrs)
        drawCircleDot2(canvas, spotOffColor, spotOffColorIn, 1f, ovalAttrs)
        drawCircleDot3(canvas, spotOffColor, spotOffColorIn, 1f, ovalAttrs)
        drawStar(canvas, DEFAULT_SPOT_OFF_COLOR_IN, 1f)
        drawRoundRectStroke(canvas, DEFAULT_SWITCH_OFF_COLOR_OUT)
    }

    private fun drawSwitchOnAnim(canvas: Canvas) {
        val rectAttrs = compRoundRectAttr(SWITCH_OFF_POS.toFloat())
        drawRoundRect(canvas, switchOnColor, rectAttrs)

        val ovalShadeOnAttrs = compRoundRectShadeOnAttr(currentPos * 3 / 2)
        val ovalAttrs = compOvalAttr(currentPos * 3 / 2)
        val color = compColor(currentPos, DEFAULT_SPOT_OFF_COLOR, DEFAULT_SPOT_ON_COLOR)
        val colorIn = compColor(currentPos, DEFAULT_SPOT_OFF_COLOR_IN, DEFAULT_SPOT_ON_COLOR_IN)
        drawRoundRect(canvas, color, ovalShadeOnAttrs)
        drawOval(canvas, color, ovalAttrs)
        drawOvalIn(canvas, colorIn, ovalAttrs)
        if (currentPos > 0.6) {
            drawCloud(canvas, currentPos)
        }
        val strokeColor =
            compColor(currentPos, DEFAULT_SWITCH_OFF_COLOR_OUT, DEFAULT_SWITCH_ON_COLOR_OUT)
        drawRoundRectStroke(canvas, strokeColor)
    }

    private fun drawSwitchOffAnim(canvas: Canvas) {
        val rectAttrs = compRoundRectAttr(SWITCH_OFF_POS.toFloat())
        if (currentPos != 1f) {
            drawRoundRect(canvas, switchOffColor, rectAttrs)
        }

        drawRoundRect(canvas, switchOffColor, rectAttrs)
        val ovalAttrs: FloatArray = if (currentPos > 2.0 / 3) {
            compOvalAttr(0f)
        } else {
            compOvalAttr(1 - currentPos * 3 / 2)
        }
        val ovalShadeOffAttrs = compRoundRectShadeOffAttr(1 - currentPos * 3 / 2)
        val color = compColor(currentPos, DEFAULT_SPOT_ON_COLOR, DEFAULT_SPOT_OFF_COLOR)
        val colorIn = compColor(currentPos, DEFAULT_SPOT_ON_COLOR_IN, DEFAULT_SPOT_OFF_COLOR_IN)
        drawRoundRect(canvas, color, ovalShadeOffAttrs)
        drawOval(canvas, color, ovalAttrs)
        drawOvalIn(canvas, colorIn, ovalAttrs)
        if (currentPos > 2.0 / 3) {
            drawCircleDot(canvas, DEFAULT_SPOT_OFF_COLOR, DEFAULT_SPOT_OFF_COLOR_IN, 1f, ovalAttrs)
            drawCircleDot2(canvas, DEFAULT_SPOT_OFF_COLOR, DEFAULT_SPOT_OFF_COLOR_IN, 1f, ovalAttrs)
            drawCircleDot3(canvas, DEFAULT_SPOT_OFF_COLOR, DEFAULT_SPOT_OFF_COLOR_IN, 1f, ovalAttrs)
        } else {
            drawCircleDot(
                canvas,
                DEFAULT_SPOT_OFF_COLOR,
                DEFAULT_SPOT_OFF_COLOR_IN,
                currentPos * 3 / 2,
                ovalAttrs
            )
            drawCircleDot2(
                canvas,
                DEFAULT_SPOT_OFF_COLOR,
                DEFAULT_SPOT_OFF_COLOR_IN,
                currentPos * 3 / 2,
                ovalAttrs
            )
            drawCircleDot3(
                canvas,
                DEFAULT_SPOT_OFF_COLOR,
                DEFAULT_SPOT_OFF_COLOR_IN,
                currentPos * 3 / 2,
                ovalAttrs
            )
        }
        if (currentPos > 0.6) {
            drawStar(canvas, DEFAULT_SPOT_OFF_COLOR_IN, currentPos)
        }
        val strokeColor =
            compColor(currentPos, DEFAULT_SWITCH_ON_COLOR_OUT, DEFAULT_SWITCH_OFF_COLOR_OUT)
        drawRoundRectStroke(canvas, strokeColor)
    }

    private fun drawRoundRect(canvas: Canvas, color: Int, attrs: FloatArray) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.strokeCap = Paint.Cap.ROUND
        val rectF = RectF()
        paint.color = color
        rectF[attrs[0], attrs[1], attrs[2]] = attrs[3]
        canvas.drawRoundRect(rectF, attrs[4], attrs[4], paint)
    }

    private fun drawRoundRectStroke(canvas: Canvas, color: Int) {
        val sw = dp2px(DEFAULT_WIDTH.toFloat())
        val sh = dp2px(DEFAULT_HEIGHT.toFloat())
        val left = dp2pxFloat(2.4.toFloat())
        val right = sw - left
        val top = dp2pxFloat(2.4.toFloat())
        val bottom = sh - top
        val radius = (bottom - top) * 0.5f
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.color = color
        paint.strokeWidth = dp2pxFloat(3.6.toFloat())
        val rectF = RectF()
        rectF[left, top, right] = bottom
        canvas.drawRoundRect(rectF, radius, radius, paint)
    }

    private fun drawOvalIn(canvas: Canvas, color: Int, attrs: FloatArray) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.color = color
        val borderWidth = dp2px(DEFAULT_BORDER_WIDTH.toFloat())
        val rectFIn = RectF(
            attrs[0] + borderWidth,
            attrs[1] + borderWidth,
            attrs[2] - borderWidth,
            attrs[3] - borderWidth
        )
        canvas.drawOval(rectFIn, paint)
    }

    private fun drawOval(canvas: Canvas, color: Int, attrs: FloatArray) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.color = color
        val rectF = RectF(attrs[0], attrs[1], attrs[2], attrs[3])
        canvas.drawOval(rectF, paint)
    }

    private fun drawCircleDot(
        canvas: Canvas,
        color: Int,
        colorIn: Int,
        pos: Float,
        attrs: FloatArray
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        val rad = attrs[2] - dp2px(9f) - (attrs[0] + attrs[2]) / 2
        val x = attrs[2] - dp2px(9f) - rad + (rad * Math.cos(pos * Math.PI / 3)).toFloat()
        val y = (attrs[1] + attrs[3]) / 2 - (rad * Math.sin(pos * Math.PI / 3)).toFloat()
        paint.color = color
        val rectF = RectF(x - dp2px(7f), y - dp2px(7f), x + dp2px(7f), y + dp2px(7f))
        canvas.drawOval(rectF, paint)
        paint.color = colorIn
        val rectFIn = RectF(x - dp2px(3f), y - dp2px(3f), x + dp2px(3f), y + dp2px(3f))
        canvas.drawOval(rectFIn, paint)
    }

    private fun drawCircleDot2(
        canvas: Canvas,
        color: Int,
        colorIn: Int,
        pos: Float,
        attrs: FloatArray
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        val rad = attrs[2] - dp2px(7f) - (attrs[0] + attrs[2]) / 2
        val x =
            attrs[2] - dp2px(7f) - rad + (rad * Math.cos(Math.PI * 5 / 12 + pos * Math.PI * 5 / 12)).toFloat()
        val y =
            (attrs[1] + attrs[3]) / 2 - (rad * Math.sin(Math.PI * 5 / 12 + pos * Math.PI * 5 / 12)).toFloat()
        paint.color = color
        val rectF = RectF(x - dp2px(5f), y - dp2px(5f), x + dp2px(5f), y + dp2px(5f))
        canvas.drawOval(rectF, paint)
        paint.color = colorIn
        val rectFIn = RectF(x - dp2px(1f), y - dp2px(1f), x + dp2px(1f), y + dp2px(1f))
        canvas.drawOval(rectFIn, paint)
    }

    private fun drawCircleDot3(
        canvas: Canvas,
        color: Int,
        colorIn: Int,
        pos: Float,
        attrs: FloatArray
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        val rad = attrs[2] - dp2px(9f) - (attrs[0] + attrs[2]) / 2
        val x =
            attrs[2] - dp2px(9f) - rad + (rad * Math.cos(Math.PI * 16 / 12 + pos * Math.PI * 5 / 12)).toFloat()
        val y =
            (attrs[1] + attrs[3]) / 2 - (rad * Math.sin(Math.PI * 16 / 12 + pos * Math.PI * 5 / 12)).toFloat()
        paint.color = color
        val rectF = RectF(x - dp2px(5f), y - dp2px(5f), x + dp2px(5f), y + dp2px(5f))
        canvas.drawOval(rectF, paint)
        paint.color = colorIn
        val rectFIn = RectF(x - dp2px(1f), y - dp2px(1f), x + dp2px(1f), y + dp2px(1f))
        canvas.drawOval(rectFIn, paint)
    }

    private fun drawCloud(canvas: Canvas, pos: Float) {
        val sw = dp2px(DEFAULT_WIDTH.toFloat())
        val sh = dp2px(DEFAULT_HEIGHT.toFloat())
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isDither = true
        paint.isFilterBitmap = true
        val cloudBitmap = (ResourcesCompat.getDrawable(
            resources,
            R.drawable.cloud,
            null
        ) as BitmapDrawable).bitmap
        val cloudWidth = cloudBitmap.width
        val cloudHeight = cloudBitmap.height
        val mSrcRect = Rect(0, 0, cloudWidth, cloudHeight)
        val mDestRect: RectF = if (pos <= 0.9) {
            val t = pos * 10 - 6
            RectF(
                (sw / 2 - dp2px(18f) - dp2px(t)).toFloat(),
                (sh / 2 - dp2px(4f) - dp2px(t)).toFloat(),
                (sw / 2 + dp2px(18f) + dp2px(t)).toFloat(),
                (sh / 2 + dp2px(20f) + dp2px(t)).toFloat()
            )
        } else {
            val t = 2 * (pos * 10 - 9)
            RectF(
                (sw / 2 - dp2px(22f) + dp2px(t)).toFloat(),
                (sh / 2 - dp2px(8f) + dp2px(t)).toFloat(),
                (sw / 2 + dp2px(22f) - dp2px(t)).toFloat(),
                (sh / 2 + dp2px(24f) - dp2px(t)).toFloat()
            )
        }
        canvas.drawBitmap(cloudBitmap, mSrcRect, mDestRect, paint)
    }

    private fun drawStar(canvas: Canvas, color: Int, pos: Float) {
        val sw = dp2px(DEFAULT_WIDTH.toFloat())
        val sh = dp2px(DEFAULT_HEIGHT.toFloat())
        val stars = Array(7) {
            FloatArray(
                2
            )
        }
        stars[0][0] = (sw / 2.0).toFloat()
        stars[0][1] = (sh / 5.0).toFloat()
        stars[1][0] = (sw * 3 / 4.0).toFloat()
        stars[1][1] = (sh / 5.0).toFloat()
        stars[2][0] = (sw * 5 / 8.0).toFloat()
        stars[2][1] = (sh * 2 / 5.0).toFloat()
        stars[3][0] = (sw * 27 / 40.0).toFloat()
        stars[3][1] = (sh * 3 / 5.0).toFloat()
        stars[4][0] = (sw * 5 / 6.0).toFloat()
        stars[4][1] = (sh * 9 / 20.0).toFloat()
        stars[5][0] = (sw * 4 / 5.0).toFloat()
        stars[5][1] = (sh * 7 / 10.0).toFloat()
        stars[6][0] = (sw * 11 / 20.0).toFloat()
        stars[6][1] = (sh * 3 / 4.0).toFloat()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.color = color
        var t = 10 * pos - 6
        if (pos > 0.8) {
            t = 10 - 10 * pos
        }
        canvas.drawCircle(stars[0][0], stars[0][1], 6 + 2 * t, paint)
        canvas.drawCircle(stars[1][0], stars[1][1], 5 + 2 * t, paint)
        canvas.drawCircle(stars[2][0], stars[2][1], 5 + 2 * t, paint)
        canvas.drawCircle(stars[3][0], stars[3][1], 4 + 2 * t, paint)
        canvas.drawCircle(stars[4][0], stars[4][1], 8 - 2 * t, paint)
        canvas.drawCircle(stars[5][0], stars[5][1], 7 - 2 * t, paint)
        canvas.drawCircle(stars[6][0], stars[6][1], 7 - 2 * t, paint)
    }

    private fun compRoundRectAttr(pos: Float): FloatArray {
        val sw = dp2px(DEFAULT_WIDTH.toFloat())
        val sh = dp2px(DEFAULT_HEIGHT.toFloat())
        val left = sw * pos
        val right = sw - left
        val top = sh * pos
        val bottom = sh - top
        val radius = (bottom - top) * 0.5f
        return floatArrayOf(left, top, right, bottom, radius)
    }

    private fun compRoundRectShadeOnAttr(pos: Float): FloatArray {
        val sw = dp2px(DEFAULT_WIDTH.toFloat())
        val sh = dp2px(DEFAULT_HEIGHT.toFloat())
        val oh = sh - 2 * spotPadding
        val left: Float
        val right: Float
        val top: Float
        val bottom: Float
        if (pos < 0.35) {
            left = 0f
            right = spotPadding + (sw - sh) * pos + oh
            top = spotPadding.toFloat()
            bottom = oh + top
        } else {
            left = spotPadding + (sw - sh) * pos * 2 / 3
            right = spotPadding + (sw - sh) * pos * 2 / 3 + oh
            top = spotPadding.toFloat()
            bottom = oh + top
        }
        val radius = (bottom - top) * 0.5f
        return floatArrayOf(left, top, right, bottom, radius)
    }

    private fun compRoundRectShadeOffAttr(pos: Float): FloatArray {
        val sw = dp2px(DEFAULT_WIDTH.toFloat())
        val sh = dp2px(DEFAULT_HEIGHT.toFloat())
        val oh = sh - 2 * spotPadding
        val left: Float
        val right: Float
        val top: Float
        val bottom: Float
        if (pos > 0.65) {
            left = spotPadding + (sw - sh) * pos
            right = (sw - spotPadding).toFloat()
            top = spotPadding.toFloat()
            bottom = oh + top
        } else {
            left = spotPadding + (sw - sh) * (2 * pos + 1) / 3
            right = spotPadding + (sw - sh) * (2 * pos + 1) / 3 + oh
            top = spotPadding.toFloat()
            bottom = oh + top
        }
        val radius = (bottom - top) * 0.5f
        return floatArrayOf(left, top, right, bottom, radius)
    }

    private fun compOvalAttr(pos: Float): FloatArray {
        var pos = pos
        if (pos > 1) {
            pos = 1f
        }
        val sw = dp2px(DEFAULT_WIDTH.toFloat())
        val sh = dp2px(DEFAULT_HEIGHT.toFloat())
        val oh = sh - 2 * spotPadding
        val left = spotPadding + (sw - sh) * pos
        val right = left + oh
        val top = spotPadding.toFloat()
        val bottom = oh + top
        return floatArrayOf(left, top, right, bottom)
    }

    private fun compColor(fraction: Float, startColor: Int, endColor: Int): Int {
        return ArgbEvaluator().evaluate(fraction, startColor, endColor) as Int
    }

    override fun performClick(): Boolean {
        toggle()
        val handled: Boolean = super.performClick()
        if (!handled) {
            // View only makes a sound effect if the onClickListener was
            // called, so we'll need to make one here instead.
            playSoundEffect(SoundEffectConstants.CLICK)
        }
        return handled
    }

    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    private fun dp2pxFloat(dpValue: Float): Float {
        val scale: Float = resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    override fun setChecked(checked: Boolean) {
        if (isMoving) {
            return
        }
        if (mChecked != checked) {
            mChecked = checked

            if (mBroadcasting) {
                return
            }
            mBroadcasting = true
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener!!.onCheckedChanged(this, mChecked)
            }
            mBroadcasting = false
            state = if (mChecked) {
                State.SWITCH_ANIMATION_ON
            } else {
                State.SWITCH_ANIMATION_OFF
            }
            if (isAttachedToWindow && isLaidOut) {
                animateToCheckedState()
            } else {
                cancelPositionAnimator()
                currentPos = 0f
            }
        }
    }

    private fun cancelPositionAnimator() {
        if (valueAnimator != null) {
            valueAnimator!!.cancel()
        }
    }

    fun animateToCheckedState() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = this@DayNightSwitch.duration.toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animation ->
                currentPos = animation.animatedValue as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    isMoving = true
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    isMoving = false
                }
            })
            if (!isRunning) {
                start()
                currentPos = 0f
            }
        }
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a switch has changed.
         *
         * @param s         The switch whose state has changed.
         * @param isChecked The new checked state of switch.
         */
        fun onCheckedChanged(s: DayNightSwitch?, isChecked: Boolean)
    }

    companion object {
        private const val ANIMATION_DURATION = 300
        private const val DEFAULT_WIDTH = 70 //width of SwitchButton
        private const val DEFAULT_HEIGHT = DEFAULT_WIDTH / 2
        private const val DEFAULT_SPOT_PADDING = 6
        private const val DEFAULT_BORDER_WIDTH = 4
        private const val DEFAULT_SWITCH_ON_COLOR = -0x611c05
        private const val DEFAULT_SWITCH_ON_COLOR_OUT = -0x793c29
        private const val DEFAULT_SWITCH_OFF_COLOR = -0xc3bebb
        private const val DEFAULT_SWITCH_OFF_COLOR_OUT = -0xe3e3e4
        private const val DEFAULT_SPOT_ON_COLOR = -0x1e3cb8
        private const val DEFAULT_SPOT_ON_COLOR_IN = -0x2093
        private const val DEFAULT_SPOT_OFF_COLOR = -0x1c1839
        private const val DEFAULT_SPOT_OFF_COLOR_IN = -0x1
        private const val SWITCH_OFF_POS = 0
        private const val SWITCH_ON_POS = 1
    }
}