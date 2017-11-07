package Processing;

import processing.core.PApplet;

public class Disk {

	protected float x;     // horizontal location of stripe
	protected float y;
	protected final int score;
	protected int white = 255;

	protected final int diameter;
	protected float speed; // speed of stripe
	protected String direction;

	protected final PApplet parent;
	protected float start_time;
	protected float milli_ttl;

	protected int r, g, b;

	int font_size;

	public Disk(PApplet p, float u_x, float u_y, int u_diameter, int u_score, 
			float u_speed, String u_direction, float ttl, int xa, int ya, int za) {
		parent = p;

		x = u_x;
		y = u_y;
		diameter = u_diameter;
		score = u_score;

		speed = u_speed; 
		direction = u_direction.toUpperCase();

		start_time = parent.millis();
		milli_ttl = ttl;
		font_size = 14;

		r = xa;
		g = ya;
		b = za;
	}
	
	public int get_score() {
		return this.score;
	}
	
	public boolean isInsideFrame() {
		// Check if this circle moved off the screen
		if (x > parent.width + diameter) return false;              // right side
		else if (x < 0 - diameter) return false;                    // left side
		else if (y < 0 - diameter) return false;                    // up side
		else if (y > parent.height + diameter) return false;        // down side

		return true; 
	}

	public boolean isExpired() {
		return parent.millis() - start_time > milli_ttl; 
	}
	
	public void set_expired() {
		milli_ttl = 0;
	}

	public boolean isInMouse() {
		if (parent.mouseX >= x-(diameter/2) && parent.mouseX <= x+(diameter/2)
			&& parent.mouseY <= y+(diameter/2) && parent.mouseY >= y-(diameter/2)) {
			return true;
		}

		return false;
	}

	public void display() {
		parent.noStroke();
		parent.fill(r, g, b);
		parent.ellipse(x, y, diameter, diameter);
		
		String score_label = Integer.toString(score);
		Text f = new Text(parent, score_label, x-8, y+4, font_size, white);
		f.display();
	}

	public void move() {
		switch(direction) {
		case "N":         // N
			y -= speed;
			break;
		case "NE":         // NE
			x += speed;
			y -= speed;
			break;
		case "E":         // E
			x += speed;
			break;
		case "SE":         // SE
			x += speed;
			y += speed;
			break;
		case "S":         // S
			y += speed;
			break;
		case "SW":         // SW
			x -= speed;
			y += speed;
			break;
		case "W":         // W
			x -= speed;
			break;
		case "NW":         // NW
			x -= speed;
			y -= speed;
			break;
		default:
			System.out.println("BAD STRING DIRECTION + " + direction);
		}
	}

}