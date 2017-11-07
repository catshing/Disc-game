
package Processing;

import processing.core.*;
import java.util.ArrayList;


public class MyGame extends PApplet {

	// Game time
    protected static int milli_game_time = 60000;
    float start_time;
    float three_sec_time;
    
    // Game spawn properties
    private static int spawn_time = 1260;
    private static int start_spawn = 75;
    private static final int max_random_spawn = 12;
    private static final int max_speed = 3;
    
    // Circle object properties
    ArrayList<Disk> shapes = new ArrayList<Disk>();
    private static final int[] scores = {10, 20, 50, 100};
    private static final String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    int score = 0;
    private static int black = 0;
    
    boolean end_game;

    public static void main(String[] args){
        PApplet.main(new String[] {"Processing.MyGame"} );
    }

    public void settings(){
        size(480, 470);
    }

    public void setup() {

        // Initialize all Stripe objects
        for (int i = 0; i < start_spawn; i++) {
        	shapes.add(createRandomCircle());
        }
        start_time = millis();
        three_sec_time = start_time;
        end_game = false;
    }

    public Disk createRandomCircle() {
        float rand_x = random(0, width);
        float rand_y = random(0, height);
        int rand_diameter = (int) random(28,100);
        float rand_speed = random(max_speed);
        int rand_score = scores[(int) random(0, scores.length)];
        String rand_direction = directions[(int) random(0, directions.length)];
        float rand_ttl = random(1200, 30000);
        
        int r = (int) random(100, 255);
        int g = (int) random(100, 255);
        int b = (int) random(100, 255);
        
        Disk new_circle = new Disk(this, rand_x, rand_y, rand_diameter, rand_score, 
        							   rand_speed, rand_direction, rand_ttl, r, g, b);
        return new_circle;
    }
    
    public void display_final_score() {
        background(255);
        
        String score_label = Integer.toString(score);
        
        Text f1 = new Text(this, "Your score is: " + score_label, 110,100, 30, black);
        Text f2 = new Text(this, "Press Enter to Exit!", 90, 135, 30, black);
        f1.display();   
        f2.display();
    }
    
    public void random_spawn_generator() {
    	int amount_circles = (int) random(1, max_random_spawn);
    	for (int i=0; i<amount_circles; ++i) {
    		shapes.add(createRandomCircle());
    	}
    }
    
    public void mouseClicked() {
    	if (end_game)
    		return;
    	
    	Disk cur_disk;
    	
    	// Since last object drawn is on top, remove the last object in ArrayList where mouse is in.
        for (int i = shapes.size()-1; i >=0 ; --i) {
        	cur_disk = shapes.get(i);
           
            if (cur_disk.isInMouse()) {
                System.out.println("User clicked on score: " + cur_disk.get_score());       // TEMP REMOVE
                System.out.println("Total score: " + score);
                score += cur_disk.get_score();
                cur_disk.set_expired();
//            	circles.remove(i);
            	break;
            }
        }

    }

    public void draw() {
        float this_time = millis();
        
        if (this_time - start_time > milli_game_time) {      
        	end_game = true;
            display_final_score();
            
            if (key == ENTER)
                exit();

            return;
        } else if (this_time - three_sec_time > spawn_time ) {
            three_sec_time = this_time;
            random_spawn_generator();
        }

        background(100);
        Disk cur_disk;

        // Check  each each circle and check if it's clicked on else move it then display.
        for (int i = 0; i < shapes.size(); i++) {
            cur_disk = shapes.get(i);
            cur_disk.move();
            
            if (!cur_disk.isInsideFrame()) {
             shapes.remove(i);
            } else if (cur_disk.isExpired()) {
//                System.out.println("Circle expired! ");                                 // TEMP REMOVE
             shapes.remove(i);
            } else {
             cur_disk.display();
            }
        }
    }

}