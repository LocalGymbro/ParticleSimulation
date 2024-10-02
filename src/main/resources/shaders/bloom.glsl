#version 140

in vec4 v_colour;
in vec2 tex_coord;

uniform sampler2D t0; // original image
uniform float bloom_spread = 1.0; // blur radius
uniform float bloom_intensity = 2.0; // bloom strength

void main() {
    ivec2 size = textureSize(t0, 0);
    float uv_x = tex_coord.x * size.x;
    float uv_y = tex_coord.y * size.y;

    vec4 sum = vec4(0.0);
    for (int n = 0; n < 9; ++n) {
        uv_y = (tex_coord.y * size.y) + (bloom_spread * float(n - 4));
        vec4 h_sum = vec4(0.0);
        h_sum += texelFetch(t0, ivec2(uv_x - (4.0 * bloom_spread), uv_y), 0);
        // ... (similar calculations for other samples)
        sum += h_sum / 9.0;
    }

    pixel = texture(t0, tex_coord) - ((sum / 9.0) * bloom_intensity);
}