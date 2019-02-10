class Ground{
  
  float YPos;
  
  Ground(float groundY){
    YPos = groundY;
  }
  
  void drawGround(){    
    line(0, YPos, width, YPos);
  }
  
}
