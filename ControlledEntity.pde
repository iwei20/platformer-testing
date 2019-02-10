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
  
  void drawPlayer(){
    rect(XPos, YPos, Width, Height); 
  }
  
  void enableMovement(){
    YPos += speedY;
    XPos += speedX;
  }
  
  // Ground collision checking; climbing conflicts with this, so else if needed for climbing
  void enableGravity(){

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
  void enableClimb(){
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
  
  void enableJump(){
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
  void enableSideMove(){
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
  void stopSide(){     
    //Stops player when key is let go
    if(key == 'd' && speedX > 0){
      speedX = 0; //Stops player from moving right
    }else if(key == 'a' && speedX < 0){
      speedX = 0; //Stops player from moving left
    }
  }
}
