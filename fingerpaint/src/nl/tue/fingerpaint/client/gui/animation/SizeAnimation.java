package nl.tue.fingerpaint.client.gui.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A {@code SizeAnimation} can animate the size of an element.
 * 
 * @author Group Fingerpaint
 */
public class SizeAnimation extends Animation {

	/**
	 * Constant to indicate that both sides (height and width) should be
	 * animated on hide/show.
	 */
	public static final int ANIMATE_BOTH = 2;
	/**
	 * Constant to indicate that only the height should be animated.
	 */
	public static final int ANIMATE_HEIGHT = 0;
	/**
	 * Constant to indicate that only the width should be animated.
	 */
	public static final int ANIMATE_WIDTH = 1;

	/** Panel that is animated. */
	private Element subject;
	/** Axis to animate */
	private int axis;
	/** If animation is in progress. */
	private boolean running = false;
	/** If element is hidden or not. */
	private boolean hidden = false;
	/** Original height of the element. */
	private int elOrigHeight;
	/** Original width of the element. */
	private int elOrigWidth;
	/**
	 * Callback on finish of animation. {@code null} when no callback is
	 * present.
	 */
	private AsyncCallback<Boolean> onCompleteCallback;

	/**
	 * Construct a new object that can be used to animate the given element.
	 * 
	 * @param animationSubject
	 *            The element to be animated.
	 * @param animationAxis
	 *            Which axis to animate. Use {@link #ANIMATE_BOTH},
	 *            {@link #ANIMATE_HEIGHT} or {@link #ANIMATE_WIDTH} here.
	 */
	public SizeAnimation(Element animationSubject, int animationAxis) {
		this.subject = animationSubject;
		setAxis(animationAxis);
		refreshSize();
	}

	/**
	 * Internally update the size of the element that is animated.
	 */
	public void refreshSize() {
		this.elOrigWidth = this.subject.getClientWidth();
		this.elOrigHeight = this.subject.getClientHeight();
	}

	/**
	 * Change which axis are animated. Use {@link #ANIMATE_BOTH},
	 * {@link #ANIMATE_HEIGHT} or {@link #ANIMATE_WIDTH} here. When not one of
	 * these constants, {@link #ANIMATE_WIDTH} is used.
	 * 
	 * @param axis
	 *            The new axis.
	 */
	public void setAxis(int axis) {
		if (!running) {
			if (axis != ANIMATE_BOTH && axis != ANIMATE_HEIGHT
					&& axis != ANIMATE_WIDTH) {
				axis = ANIMATE_WIDTH;
			}

			this.axis = axis;
		}
	}

	/**
	 * Perform a sliding animation to show the element, that is, restore its
	 * position before a call to {@link #doHide(int)}. If the element is not
	 * hidden by such a call, nothing happens.
	 * 
	 * @param durationMillis
	 *            Time the animation takes.
	 */
	public void doShow(int durationMillis) {
		doShow(durationMillis, null);
	}

	/**
	 * Perform a sliding animation to show the element, that is, restore its
	 * position before a call to {@link #doHide(int)}. If the element is not
	 * hidden by such a call, nothing happens.
	 * 
	 * @param durationMillis
	 *            Time the animation takes.
	 * @param onComplete
	 *            Callback to call when animation is done. Ignored when
	 *            {@code null}. This callback will (guaranteed) only be called
	 *            with a {@code true} argument on the
	 *            {@code AsyncCallback#onSuccess(Object)} callback.
	 */
	public void doShow(int durationMillis, AsyncCallback<Boolean> onComplete) {
		if (subject.getClientHeight() < elOrigHeight
				|| subject.getClientWidth() < elOrigWidth) {
			hidden = true;
			this.onCompleteCallback = onComplete;
			run(durationMillis);
		}
	}

	/**
	 * Perform a sliding animation to hide the element on the selected side of
	 * the screen.
	 * 
	 * @param durationMillis
	 *            Time the animation takes.
	 */
	public void doHide(int durationMillis) {
		doHide(durationMillis, null);
	}

	/**
	 * Perform a sliding animation to hide the element on the selected side of
	 * the screen.
	 * 
	 * @param durationMillis
	 *            Time the animation takes.
	 * @param onComplete
	 *            Callback to call when animation is done. Ignored when
	 *            {@code null}. This callback will (guaranteed) only be called
	 *            with a {@code true} argument on the
	 *            {@code AsyncCallback#onSuccess(Object)} callback.
	 */
	public void doHide(int durationMillis, AsyncCallback<Boolean> onComplete) {
		if (subject.getClientHeight() > 0 && subject.getClientWidth() > 0) {
			hidden = false;
			this.onCompleteCallback = onComplete;
			run(durationMillis);
		}
	}

	/**
	 * Return the height of the element as is saved in this animation.
	 * 
	 * @return the (original) height of the element as internally saved
	 */
	public int getInternalHeight() {
		return elOrigHeight;
	}

	/**
	 * Return the width of the element as is saved in this animation.
	 * 
	 * @return the (original) width of the element as internally saved
	 */
	public int getInternalWidth() {
		return elOrigWidth;
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		if (onCompleteCallback != null) {
			onCompleteCallback.onSuccess(true);
		}
		running = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		running = true;
	}

	@Override
	protected void onUpdate(double progress) {
		if (!hidden) {
			progress = 1.0 - progress;
		}

		switch (axis) {
		case ANIMATE_BOTH:
			subject.getStyle().setHeight(elOrigHeight * progress, Unit.PX);
			subject.getStyle().setWidth(elOrigWidth * progress, Unit.PX);
			break;
		case ANIMATE_HEIGHT:
			subject.getStyle().setHeight(elOrigHeight * progress, Unit.PX);
			break;
		case ANIMATE_WIDTH:
			subject.getStyle().setWidth(elOrigWidth * progress, Unit.PX);
			break;
		}
	}

}
