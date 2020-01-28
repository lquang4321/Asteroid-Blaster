class Button {
  PImage logo;
  color c;
  color c2;
  color fc;
  int x =width/2 ;
  int y =height/2 ;
  int rW =300;
  int rH = 75;
  int mode;

  Button() {    
    fc =  #000000;
    c = #000000;
    c2 = #000000;
    mode=1;
    logo = loadImage("logo.png");
  }
  void makeRect() {
    rectMode(CENTER);
    stroke(#ffffff);
    fill(c);
    rect(x, y, rW, rH, 9);
    fill(#FFFFFF);
    textSize(60);
    text("Start", x-60, y+20);
    imageMode(CENTER);
    image(logo, x+50, 150);
  }
  void makeRect(float Y) {
    rectMode(CENTER);
    fill(c2);
    rect(x, y + Y, rW, rH, 9);
    fill(#FFFFFF);
    textSize(55);
    text("Controls", x-110, y+170);
  }
  void backButton() {
    rectMode(CENTER);
    stroke(#FFFFFF);
    fill(c2);
    rect(x, y + 245, rW, rH, 9);
    fill(#FFFFFF);
    textSize(55);
    text("Menu", x-80, y+260);
  }
  void overButton() {
  rectMode(CENTER);
  stroke(#FFFFFF);
  fill(c2);
  rect(x, y + 245, rW, rH, 9);
  fill(#FFFFFF);
  textSize(55);
  text("Menu", x-80, y+260);
  }

  boolean isInside() {
    return (mouseX >x-150 && mouseX < x+150 && mouseY > y -38 && mouseY < y+38);
  }
  boolean isInside2() {
    return (mouseX >x-150 && mouseX < x+150 && mouseY > y + 110 && mouseY < y +190);
  }
  boolean isInside3() {
    return (mouseX >x-150 && mouseX < x+150 && mouseY > y + 210 && mouseY < y + 285);
  }
   boolean isInside4() {
    return (mouseX >x-150 && mouseX < x+150 && mouseY > y + 210 && mouseY < y + 285);
  }
  void control() {
    background(#000000);
    noStroke();
    fill(#FFFFFF);
    textSize(40);
    text("Use WASD to move", x-width/2 + 30, y-height/2 + 200 );
    text("Press SPACE to shoot", x-width/2 + 30, y-height/2 + 300 );
    backButton();
  }
}

