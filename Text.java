package Processing;

import processing.core.PApplet;

public class Text {

    float x;
    float y;
    String text;
    float speed; // speed of stripe
    PApplet parent;

    public Text(PApplet p, String u_text, float u_x, float u_y, int font_size, int gray_scale) {
        // All stripes start at 0
        parent = p;
        text = u_text;

        x = u_x;
        y = u_y;

        p.textSize(font_size);
        parent.fill(gray_scale);         // Specify font color
        parent.noStroke();
    }

    void display() {
        parent.text(text, x, y);
    }

}
