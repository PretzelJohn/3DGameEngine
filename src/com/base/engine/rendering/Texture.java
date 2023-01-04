package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.base.engine.core.Util;
import com.base.engine.rendering.resourceManagement.TextureResource;

public class Texture {
	public static final int A_NONE = GL_NONE;
	public static final int A_COLOR = GL_COLOR_ATTACHMENT0;
	public static final int A_DEPTH = GL_DEPTH_ATTACHMENT;
	public static final int A_STENCIL = GL_STENCIL_ATTACHMENT;
	
	public static final int F_NONE_M_NONE = GL_NEAREST;
	public static final int F_LINEAR_M_NONE = GL_LINEAR;
	public static final int F_NONE_M_SHARP = GL_NEAREST_MIPMAP_NEAREST;
	public static final int F_NONE_M_SMOOTH = GL_NEAREST_MIPMAP_LINEAR;
	public static final int F_BILINEAR_M_SHARP = GL_LINEAR_MIPMAP_NEAREST;
	public static final int F_TRILINEAR_M_SMOOTH = GL_LINEAR_MIPMAP_LINEAR;
	
	public static final int T_2D = GL_TEXTURE_2D;
	
	public static final int W_REPEAT = GL_REPEAT;
	public static final int W_MIRRORED_REPEAT = GL_MIRRORED_REPEAT;
	public static final int W_CLAMP_EDGE = GL_CLAMP_TO_EDGE;
	public static final int W_CLAMP_BORDER = GL_CLAMP_TO_BORDER;
	
	private static HashMap<String, TextureResource> resources = new HashMap<String, TextureResource>();
	
	private TextureResource resource;
	private String fileName;
	
	public Texture(String fileName) { this(fileName,T_2D);	}
	public Texture(String fileName, int textureTarget) { this(fileName,textureTarget, F_NONE_M_NONE); }
	public Texture(String fileName, int textureTarget, float filter) { this(fileName,textureTarget, filter, W_REPEAT); }
	public Texture(String fileName, int textureTarget, float filter, int wrapMode) { this(fileName,textureTarget, filter, wrapMode, A_NONE); }
	
	public Texture(String fileName, int textureTarget, float filter, int wrapMode, int attachment) {
		this.fileName = fileName;
		
		TextureResource oldResource = resources.get(fileName);
		if(oldResource != null) {
			resource = oldResource;
			resource.addReference();
		}else {
			try {
				BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
				int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
				
				ByteBuffer buffer = Util.createByteBuffer(image.getWidth() * image.getHeight() * 4);
				boolean hasAlpha = image.getColorModel().hasAlpha();
				
				for(int y = 0; y < image.getHeight(); y++) {
					for(int x = 0; x < image.getWidth(); x++) {
						int pixel = pixels[y * image.getWidth() + x];
						buffer.put((byte)((pixel >> 16) & 0xFF));
						buffer.put((byte)((pixel >> 8) & 0xFF));
						buffer.put((byte)((pixel) & 0xFF));
						if(hasAlpha)
							buffer.put((byte)((pixel >> 24) & 0xFF));
						else
							buffer.put((byte)(0xFF));
					}
				}
				buffer.flip();
				
				resource = new TextureResource(textureTarget, image.getWidth(), image.getHeight(), 1, new ByteBuffer[]{buffer}, new float[]{filter}, new int[] {wrapMode}, new int[] {attachment});
			}catch(Exception e) {
				e.printStackTrace(); System.exit(1);
			}
			resources.put(fileName, resource);
		}
	}
	
	public Texture(int width, int height, ByteBuffer data, int textureTarget, int filter, int wrapMode, int attachment) {
		this.fileName = "";
		resource = new TextureResource(textureTarget, width, height, 1, new ByteBuffer[]{data}, new float[]{filter}, new int[] {wrapMode}, new int[] {attachment});
	}
	
	@Override
	protected void finalize() {
		if(resource.removeReference() && !fileName.isEmpty())
			resources.remove(fileName);
	}
	
	public void bind(int samplerSlot) {
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		resource.bind(0);
	}
	
	public void bindAsRenderTarget() {
		resource.bindAsRenderTarget();
	}
}
