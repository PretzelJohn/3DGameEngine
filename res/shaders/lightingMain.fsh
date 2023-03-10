#include "sampling.glh"

void main()
{
	vec3 directionToEye = normalize(C_eyePos - worldPos0);
	vec2 texCoords = CalcParallaxTexCoords(dispMap, tbnMatrix, directionToEye, texCoord0, dispMapScl, dispMapBias);
	
	vec3 normal = normalize(tbnMatrix * (255.0/128.0 * texture2D(normalMap, texCoords.xy).xyz - 1));
	gl_FragColor = texture2D(diffuse, texCoords.xy) * CalcLightingEffect(normal, worldPos0);
}