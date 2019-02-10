import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GroundVineGame extends PApplet {

Vine middleVine = new Vine(500, 880, 700);
ControlledEntity player = new ControlledEntity(140, 100, 20, 50);
Ground ground = new Ground(900);

public void setup() {
  
}

public void draw() {
  background(64);
  stroke(255);  
  ground.drawGround();
  middleVine.drawVine();
  player.drawPlayer();
  player.enableMovement();
  player.enableGravity();
  player.enableClimb(); // The reason why this is not in keyPressed is that it requires constant checking to see if it is on the vine, not only when a key is pressed.
}

public void keyPressed(){
  player.enableSideMove();
  player.enableJump();
}

public void keyReleased(){
  player.stopSide();
}




class ControlledEntity{
  
  float XPos;
  float YPos;
  float Width;
  float Height;
  float speedY;
  float speedX;
  boolean jumping;
  boolean climbing;
  
  ControlledEntity(float XPos, float YPos, float Width, float Height){
    this.XPos = XPos;
    this.YPos = YPos;
    this.Width = Width;
    this.Height = Height;
    this.speedY = 0;
    this.speedX = 0;
    jumping = false; // When false, allows jumping
    climbing = false; // Prevents gravity when on vine
  }  
  
  public void drawPlayer(){
    rect(XPos, YPos, Width, Height); 
  }
  
  public void enableMovement(){
    YPos += speedY;
    XPos += speedX;
  }
  
  // Ground collision checking; climbing conflicts with this, so else if needed for climbing
  public void enableGravity(){

    // Ground collision check
    if(YPos + Height > ground.YPos) {
      YPos = ground.YPos - Height;
      speedY = 0;
      jumping = false;
    // Vine check
    }else if(climbing){
        jumping = false;
        speedY = 0;
    // Gravity if both return false
    }else{
      speedY++;
    }
  }
  
  // Both enables climb and checks if it's at the top.  
  public void enableClimb(){
    // Within X of the vine check
    if(middleVine.vineX > XPos && middleVine.vineX < XPos + Width){
      // Top (over) vine check
      if(YPos + Height <= middleVine.vineYTop && !jumping){
        speedY = 0;
        YPos = middleVine.vineYTop - Height;
        climbing = true;
      // Climbing enabled
      }else if(keyPressed && (key == 'w' || key == 'W') && YPos < middleVine.vineY && YPos + Height > middleVine.vineYTop){
        speedY = -4;
        climbing = true;
      }
    // This isn't in the nested if, because we don't want the player falling off the vine.  We do want it not to climb when 
    }else{
      climbing = false;
    }
  }
  
  public void enableJump(){
    // Spacebar check
    if(key == ' '){
      if (!jumping) {
        speedY = -15;
        jumping = true;   
        climbing = false;
      }
    }
  }
  
  // Uses WASD movement
  public void enableSideMove(){
    // Right movement
    if(key == 'd'||key == 'D'){
      speedX = 3;
    }
    // Left movement
    if(key == 'a'||key == 'A'){
      speedX = -3;    
    }
  }
  
  // TODO: Optimize this
  public void stopSide(){     
    //Stops player when key is let go
    if(key == 'd' && speedX > 0){
      speedX = 0; //Stops player from moving right
    }else if(key == 'a' && speedX < 0){
      speedX = 0; //Stops player from moving left
    }
  }
}
class Ground{
  
  float YPos;
  
  Ground(float groundY){
    YPos = groundY;
  }
  
  public void drawGround(){    
    line(0, YPos, width, YPos);
  }
  
}
class Vine{
  
  float vineX;
  float vineY;
  float vineYTop;  
  boolean climbing;
  
  Vine(float vineX, float vineY, float vineYTop){
    this.vineX = vineX;
    this.vineY = vineY;
    this.vineYTop = vineYTop;
  }
  
  public void drawVine(){
    line(vineX, vineY, vineX, vineYTop);
  }
}
  public void settings() {  size(1000, 1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GroundVineGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
