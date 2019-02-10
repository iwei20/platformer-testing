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
  
  void drawVine(){
    line(vineX, vineY, vineX, vineYTop);
  }
}
