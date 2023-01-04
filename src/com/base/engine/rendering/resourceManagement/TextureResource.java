package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;

public class TextureResource extends Resource {	
	private int[] textureID;
	private int numTextures;
	private int textureTarget;
	private int frameBuffer, renderBuffer;
	private int width, height;
	
	public TextureResource(int textureTarget, int width, int height, int numTextures, ByteBuffer[] data, float[] filters, int[] wrapModes, int[] attachments) {
		this.textureID = new int[numTextures];
		this.textureTarget = textureTarget;
		this.numTextures = numTextures;
		this.width = width;
		this.height = height;
		this.frameBuffer = 0;
		this.renderBuffer = 0;
		
		initTextures(data, filters, wrapModes);
		initRenderTargets(attachments);
	}
	
	private void initTextures(ByteBuffer[] data, float[] filters, int[] wrapModes) {
		for(int i = 0; i < numTextures; i++) {
			textureID[i] = glGenTextures();
			glGenerateMipmap(textureTarget);
			glBindTexture(textureTarget, textureID[i]);
			
			glTexParameteri(textureTarget, GL_TEXTURE_WRAP_S, wrapModes[i]);
			glTexParameteri(textureTarget, GL_TEXTURE_WRAP_T, wrapModes[i]);
			glTexParameterf(textureTarget, GL_TEXTURE_MIN_FILTER, filters[i]);
			glTexParameterf(textureTarget, GL_TEXTURE_MAG_FILTER, filters[i]);
			
			glTexImage2D(textureTarget, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data[i]);
		}
	}
	
	private void initRenderTargets(int[] attachments) {
		if(attachments == null)
			return;
		
		int[] drawBuffers = new int[32];
		assert(numTextures <= 32);
		
		boolean hasDepth = false;
		for(int i = 0; i < numTextures; i++) {
			if(attachments[i] == GL_DEPTH_ATTACHMENT) {
				drawBuffers[i] = GL_NONE;
				hasDepth = true;
			}else {
				drawBuffers[i] = attachments[i];
			}
			
			if(attachments[i] == GL_NONE)
				continue;
			
			if(frameBuffer == 0) {
				frameBuffer = glGenFramebuffers();
				glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
			}
			
			glFramebufferTexture2D(GL_FRAMEBUFFER, attachments[i], textureTarget, textureID[i], 0);
		}
		
		if(frameBuffer == 0)
			return;
		
		if(!hasDepth) {
			renderBuffer = glGenRenderbuffers();
			glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
			glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
			glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderBuffer);
		}
		
		for(int i = 0; i < numTextures; i++) {
			glDrawBuffers(drawBuffers[i]);
		}
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Framebuffer creation failed!");
			System.exit(1);
		}
		
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	@Override
	protected void finalize() {
		int i = numTextures;
		while(i > 0) { glDeleteTextures(textureID[i]); i--; }
		while(getInt("frameBuffer") != 0) {	glDeleteFramebuffers(getInt("frameBuffer")); }
		while(getInt("renderBuffer") != 0) { glDeleteRenderbuffers(getInt("renderBuffer")); }
	}
	
	public void bind(int textureIndex) {
		glBindTexture(textureTarget, textureID[textureIndex]);
	}
	
	public void bindAsRenderTarget() {
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
		glViewport(0, 0, width, height);
	}

	public void setTextureID(int index, int id) { textureID[index] = id; } 
	public int getTextureID(int index) { return textureID[index]; }
}
