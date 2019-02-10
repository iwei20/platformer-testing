Vine middleVine = new Vine(500, 880, 700);
ControlledEntity player = new ControlledEntity(140, 100, 20, 50);
Ground ground = new Ground(900);

void setup() {
  size(1000, 1000);
}

void draw() {
  background(64);
  stroke(255);  
  ground.drawGround();
  middleVine.drawVine();
  player.drawPlayer();
  player.enableMovement();
  player.enableGravity();
  player.enableClimb(); // The reason why this is not in keyPressed is that it requires constant checking to see if it is on the vine, not only when a key is pressed.
}

void keyPressed(){
  player.enableSideMove();
  player.enableJump();
}

void keyReleased(){
  player.stopSide();
}
