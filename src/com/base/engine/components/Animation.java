package com.base.engine.components;

import java.util.ArrayList;
import com.base.engine.core.Transform;

public class Animation extends GameComponent {
	private int currentFrame;
	private boolean loop;
	private boolean playing;
	private ArrayList<KeyFrame> frames;
	private float milliseconds;
	
	public Animation() {
		this(false);
	}
	
	public Animation(boolean loop) {
		frames = new ArrayList<KeyFrame>();
		this.loop = loop;
		currentFrame = 0;
		milliseconds = 0;
		playing = false;
	}
	
	public void start() {
		playing = true;
	}
	
	public void stop() {
		reset();
		pause();
	}
	
	public void pause() {
		playing = false;
	}
	
	public void reset() {
		milliseconds = 0;
		currentFrame = 0;
	}
	
	@Override
	public void update(float delta) {
		if(playing) {
			milliseconds += delta*1000;
			Transform t = new Transform();
			t.setParent(getTransform());
			
			if(currentFrame > 0) {
				if(currentFrame < frames.size()) {
					if(Math.round(milliseconds) < frames.get(currentFrame).getMillisecond()) {
						float adj = (milliseconds - frames.get(currentFrame-1).getMillisecond())/(frames.get(currentFrame).getMillisecond() - frames.get(currentFrame-1).getMillisecond());
						t.setPos(frames.get(currentFrame).getTransform().getPos().sub(frames.get(currentFrame-1).getTransform().getPos()).mul(adj).add(frames.get(currentFrame-1).getTransform().getPos()));
						t.setRot(frames.get(currentFrame).getTransform().getRot().sub(frames.get(currentFrame-1).getTransform().getRot()).mul(adj).add(frames.get(currentFrame-1).getTransform().getRot()));
						t.setScl(getTransform().getScl().sub(1).mul(adj).add(1));
					}else if(Math.round(milliseconds) == frames.get(currentFrame).getMillisecond()){
						t = frames.get(currentFrame).getTransform();
						currentFrame++;
					}else {
						currentFrame++;
						return;
					}
				}else {
					if(loop) {
						reset();
						return;
					}
					else stop();
				}
			}else {
				if(frames.size() > 0)
					t = frames.get(0).getTransform();
				currentFrame++;
			}
			
			getTransform().setPos(t.getPos());
			getTransform().setRot(t.getRot());
			getTransform().setScl(t.getScl());
		}
	}
	
	public void addKeyFrame(int millisecond, Transform t) {
		KeyFrame keyFrame = new KeyFrame(millisecond);
		keyFrame.setTransform(t);
		frames.add(keyFrame);
	}

	public int getCurrentFrame() { return currentFrame; }
}
