import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import gifAnimation.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Asteroid_Blaster extends PApplet {



Gif space;
Minim minim;
AudioSample bang;
AudioSample file;
AudioPlayer music;
Ship ship;
ArrayList <Button> buttonListMain; // main button array
ArrayList <UFO> ufolist;
ArrayList <Asteroid> alist;
ArrayList<Bullets> blist;

int clock, u2;      //UFO timing
int watch, a2;      //asteroid timing
int A;              //Amount of ufo per countdown
int B;              //Amount of asteroid per countdown
int score;          // Total score
int counter;        // Difficulty increase counter
int C;              //Asteroid spawn chance modifier
int D;              //UFO spawn chance modifier
int amountAsteroid;
int amountUFO;
int mode;

public void setup() {
  size(600, 700);
  space = new Gif(this, "space.gif");
  clock = 300;
  watch = 100;
  score = 0;
  A=0;
  B=0;
  a2=0;
  u2=0;
  amountUFO=1;
  amountAsteroid=7;
  counter=100;
  mode = 1;
  minim = new Minim(this);
  bang = minim.loadSample("bangLarge.wav");
  file = minim.loadSample("fire.wav");
  music = minim.loadFile("02-mind-s-eye.mp3");
  ufolist = new ArrayList <UFO> ();
  alist = new ArrayList <Asteroid> ();
  buttonListMain = new ArrayList <Button> ();

  for (int i=0; i<3; i++) {
    alist.add(new Asteroid ());
  }
  ship = new Ship();
  blist = new ArrayList<Bullets>();

  for (int i=0; i<2; i++) {
    buttonListMain.add(new Button());
  }
  music.play();
  music.loop();
}


public void draw() {
  background(0);
  if (mode == 1) {
    image(space, width/2, height/2, 600, 700);
    space.play();
  }
  if (buttonListMain.get(0).mode != 2) {
    for (int u=0; u < buttonListMain.size (); u++) {
      buttonListMain.get(u).makeRect();
      buttonListMain.get(u).makeRect(150);

      buttonListMain.get(u).c = 0xff000000;
      buttonListMain.get(u).c2 = 0xff000000;

      if (buttonListMain.get(u).isInside() && mode == 1) {
        buttonListMain.get(u).c =  0xff342A2A;
      }

      if (buttonListMain.get(u).isInside() && mode == 1 && mousePressed) {
        mode = 2;
      }
      if (buttonListMain.get(u).isInside2() && mode == 1 && mousePressed) {
        buttonListMain.get(u).c2 =  0xff342A2A;
        mode = 3;
      } else if (buttonListMain.get(u).isInside3() && mousePressed && mode ==3) {
        mode = 1;
      } else if (buttonListMain.get(u).isInside4() && mousePressed && mode ==4) {
        setup();
      }
      if (buttonListMain.get(u).isInside2() && mode == 1) {
        buttonListMain.get(u).c2 =  0xff342A2A;
        cursor(HAND);
      } else if (buttonListMain.get(u).isInside()) {
        cursor(HAND);
      } else if (buttonListMain.get(u).isInside3()&& mode == 3) {
        cursor(HAND);
      } else if (buttonListMain.get(u).isInside4()&& mode == 4) {
        cursor(HAND);
      } else {
        cursor(ARROW);
      }
      if (mode == 3) {
        buttonListMain.get(u).control();
      }
      if (mode == 4) {
      }
    }
  }

  if (mode==4) {
    background(0);
    fill(255);
    music.pause();
    textSize(40);
    text("GAME OVER", width/2-125, height/2-100);
    textSize(25);
    text("YOUR SCORE:"+score, width/2-100, height/2);
    buttonListMain.get(0).overButton();
  }

  if ( mode == 2) {
    background(0);
    textSize(16);
    space.stop();
    text("PLAYER", 25, 645);
    text("SCORE:" + score, 25, 660);
    text("LIVES:" + ship.lives, 25, 675);
    for (int i=0; i < alist.size (); i++) {
      alist.get(i).display();
      alist.get(i).move();
      alist.get(i).bounce();
    }
    for (int i=0; i < alist.size (); i++) {
      alist.get(i).display();
      alist.get(i).move();
      alist.get(i).bounce();
    }

    if (random(0, 100)>abs(80+D)+10 && A==0) {    //UFO spawn rate
      clock--;
      if (clock==0) {
        A=amountUFO;
        clock = 300+u2;
      } else if (clock >= 1) {
        A=0;
      }
    } else {
      A=0;
    }

    for (int i=0; i < A; i++) {
      ufolist.add(new UFO ());
    }

    if (random(0, 100)>abs(25+C)+25 && B==0) {    //Asteroid spawn rate
      watch--;
      if (watch==0) {
        B=amountAsteroid;
        watch = 100 + a2;
      } else if (watch >= 1) {
        B=0;
      }
    } else {
      B=0;
    }

    for (int i=0; i<B; i++) {
      alist.add(new Asteroid ());
    }


    for (int i=0; i < ufolist.size (); i++) {
      ufolist.get(i).display();
      ufolist.get(i).move();
      ufolist.get(i).shoot();
    }

    for (int i = 0; i < 1; i++) {
      ship.collide();
      ship.life();
      ship.death();
    }

    for (int j = 0; j < blist.size (); j++) {
      blist.get(j).make();
    }
    diffIncrease();
    collisionCheck();
  }
}



public void keyPressed() {
  if (ship.state == 0) {
    if (key == 'w' || key == 'W') {
      ship.w = true;
    }
    if (key == 's' || key == 'S') {
      ship.s = true;
    }
    if (key == 'a' || key == 'A') {
      ship.a = true;
    }
    if (key == 'd' || key == 'D') {
      ship.d = true;
    }
    if (key == ' ') {
      ship.v = true;
    }
  }
}


public void keyReleased() {
  if (key == 'w' || key == 'W') {
    ship.w = false;
  }
  if (key == 's' || key == 'S') {
    ship.s = false;
  }
  if (key == 'a' || key == 'A') {
    ship.a = false;
  }
  if (key == 'd' || key == 'D') {
    ship.d = false;
  }
  if (key == ' ') {
    ship.v = false;
  }
}

public void diffIncrease() {
  if (score >= counter && score <700) {
    D -= 2;    //UFO spawn chance increase
    C -= 2;    //Asteroid spawn chance increase
    amountAsteroid=(int)random(3, 5);
    a2 -=5;
    u2 -=7;
    counter += 100;
    ship.A -= 0.075f;  //Fire rate increase
  }

  if (score >= counter && score >=700) {
    amountUFO=2;
    amountAsteroid=(int)random(3, 8);
    D -= 3;
    C -= 2;
    constrain(a2 -= (int)random(3, 7), -30, -20);
    constrain(u2 -= (int)random(3, 7), -150, -100);
    counter += 225;
    if (ship.A>10) {
      ship.A-= 0.1f;
    }
  }
  if (score >= counter && score >=700) {
    constrain(amountAsteroid++, 10, 15);
    counter +=250;
  }
  if(score == 0) {
    ship.A = 15;
    D = 0;
    C = 0;
    amountUFO=1;
    amountAsteroid=7;
    a2 = 0;
    u2 = 0;
  }
}

public void gameOver() {
  mode=4;
}

public void collisionCheck() {
  boolean hit = false;                                   //Ship bullet hit UFO check collision

  for (int u = 0; u < blist.size (); u++) {

    for (int l = 0; l < ufolist.size () && !hit; l++) {  //Ship bullet & UFO check collision
      if (blist.get(u).isTouchingufo(ufolist.get(l))) {
        hit = true;
        if (hit == true && blist.get(u).b == 7) {    
          ufolist.get(l).hits = ufolist.get(l).hits -1;
          if (ufolist.get(l).hits <= 0) {
            if (blist.get(u).b == 7 && hit == true) {
              score = score + 25;
              bang.trigger();
              bang.setGain(2);
            }
            ufolist.remove(l);
            l--;
          }
          blist.remove(u);
          u--;
        }
      }
    }

    for (int i = 0; i < alist.size () && !hit; i++) {  //Ship bullet & Asteroid check collision
      if (blist.get(u).isTouching(alist.get(i))) {
        hit = true;
        if (blist.get(u).b == 7 && hit == true) {
          score = score + 5;
          bang.trigger();
          bang.setGain(2);
          alist.remove(i);
          blist.remove(u);
          u--;
          i--;
        }
      }
    }
  }
  for (int i = 0; i < alist.size () && !hit; i++) {      //Ship & Asteroid check collision
    if (ship.isTouching(alist.get(i))) {
      ship.state = 1;
      alist.remove(i);
      bang.trigger();
      bang.setGain(2);
      i--;
      hit = true;
    }
  }
  for ( int u = 0; u < blist.size () && !hit; u++) {   //Bullet remove if it leaves screen
    if (blist.get(u).y <= 7) {
      blist.remove(u);
      u--;
    }
  }
  for (int u = 0; u < blist.size () && !hit; u++) {
    if (blist.get(u).y >= height-7) {
      blist.remove(u);
      u--;
    }
  }
  for (int i = 0; i < alist.size () && !hit; i++) {
    if (alist.get(i).y >= height+20) {
      alist.remove(i);
      i--;
    }
  }
  for (int l = 0; l < ufolist.size () && !hit; l++) {
    if (ufolist.get(l).y >= height+60) {
      ufolist.remove(l);
      l--;
    }
  }
  for (int u = 0; u < blist.size () && !hit; u++) {    //Ship & UFO's bullet check collision
    if (ship.isTouching(blist.get(u))) {
      ship.state = 1;
      bang.trigger();
      bang.setGain(2);
      blist.remove(u);
      u--;
      hit = true;
    }
  }
  for (int l = 0; l < ufolist.size () && !hit; l++) {  //Ship & UFO check collision
    if (ship.isTouching(ufolist.get(l))) {
      ship.state = 1;
      bang.trigger();
      bang.setGain(2);
      ufolist.remove(l);
      l--;
      hit = true;
    }
  }
}

class Asteroid {
  float x;
  float y;
  float r;
  float dx;
  float dy;

  Asteroid() {
    x=random(50, width-50);
    y=random(-300, -20);
    r=20;
    dx=random(-3, 3);
    dy=random(1, 3);
  }

  public void display() {
    fill(0);
    stroke(255);
    strokeWeight(2);
    ellipse(x, y, r, r);
  }

  public void move() {
    x=x+dx;
    y=y+dy;
  }

  public void bounce () {
    if (x < r/2 || x >= width - r/2) {
      dx = -dx;
    }
  }
}



class Bullets {
  float x, y, b, r;  //b=direction of bullet depending on UFO/SHIP class.
  Bullets(float pausex, float pausey, float bullet) {
    x=pausex;
    y=pausey;
    b=bullet;
    r = 7;
  }
  public void make() {
    fill(255);
    ellipseMode(CENTER);
    ellipse(x, y, r, r);
    move();
    if(ship.state == 1) {
      r=3;
    }
  }
  public void move() {
    y = y - b;
  }
  public boolean isTouching(Asteroid check) {
    return dist(x, y, check.x, check.y)< r/2 + check.r;
  }
  public boolean isTouchingufo(UFO check) {
    if (b == -7) {
      return false;
    } else {
      return dist(x, y, check.x, check.y)< r/2 + check.r;
    }
  }
}



class Button {
  PImage logo;
  int c;
  int c2;
  int fc;
  int x =width/2 ;
  int y =height/2 ;
  int rW =300;
  int rH = 75;
  int mode;

  Button() {    
    fc =  0xff000000;
    c = 0xff000000;
    c2 = 0xff000000;
    mode=1;
    logo = loadImage("logo.png");
  }
  public void makeRect() {
    rectMode(CENTER);
    stroke(0xffffffff);
    fill(c);
    rect(x, y, rW, rH, 9);
    fill(0xffFFFFFF);
    textSize(60);
    text("Start", x-60, y+20);
    imageMode(CENTER);
    image(logo, x+50, 150);
  }
  public void makeRect(float Y) {
    rectMode(CENTER);
    fill(c2);
    rect(x, y + Y, rW, rH, 9);
    fill(0xffFFFFFF);
    textSize(55);
    text("Controls", x-110, y+170);
  }
  public void backButton() {
    rectMode(CENTER);
    stroke(0xffFFFFFF);
    fill(c2);
    rect(x, y + 245, rW, rH, 9);
    fill(0xffFFFFFF);
    textSize(55);
    text("Menu", x-80, y+260);
  }
  public void overButton() {
  rectMode(CENTER);
  stroke(0xffFFFFFF);
  fill(c2);
  rect(x, y + 245, rW, rH, 9);
  fill(0xffFFFFFF);
  textSize(55);
  text("Menu", x-80, y+260);
  }

  public boolean isInside() {
    return (mouseX >x-150 && mouseX < x+150 && mouseY > y -38 && mouseY < y+38);
  }
  public boolean isInside2() {
    return (mouseX >x-150 && mouseX < x+150 && mouseY > y + 110 && mouseY < y +190);
  }
  public boolean isInside3() {
    return (mouseX >x-150 && mouseX < x+150 && mouseY > y + 210 && mouseY < y + 285);
  }
   public boolean isInside4() {
    return (mouseX >x-150 && mouseX < x+150 && mouseY > y + 210 && mouseY < y + 285);
  }
  public void control() {
    background(0xff000000);
    noStroke();
    fill(0xffFFFFFF);
    textSize(40);
    text("Use WASD to move", x-width/2 + 30, y-height/2 + 200 );
    text("Press SPACE to shoot", x-width/2 + 30, y-height/2 + 300 );
    backButton();
  }
}

class Ship {
  PImage img;
  float x, y, dx, dy, timer, counter;
  boolean w, s, a, d, v;
  int l, h, r;
  int state;
  int lives;
  int A;

  Ship() {
    x = width/2;
    y = 550;
    l = 32;
    h = 38;
    state = 0;
    lives = 3;
    A = 15;
    img = loadImage("Ship.png");
  }

  public void display() {
    fill(255);
    stroke(1);
    imageMode(CENTER);
    image(img, x, y, l, h);
  }

  public void move() {
    x=x+dx;
    y=y+dy;
    if (w==true) {
      dy = -4;
    } else {
      dy = 0;
    }
    if (s==true) {
      dy = 4;
    }
    if (a==true) {
      dx = -4;
    } else {
      dx = 0;
    }
    if (d==true) {
      dx = 4;
    }
  }

  public void death() {
    if (state != 1) {
      display();
      move();
      shoot();
    }
  }
  public void life() {
    if (state == 1 && lives > 0) {
      counter++;
      blist.clear();
      ufolist.clear();
      alist.clear();
      if (counter >= 100) {
        lives = lives -1;
        state = 0;
        x = width/2;
        y = 550;
      }
    } else if (state == 1 && lives == 0) {
      gameOver();
    }
    if (counter >= 100) {
      counter = 0;
    }
  }

  public void shoot() {
    timer++;
    if (v==true) {
      if (timer >= A-0.1f) {
        blist.add(new Bullets(x, y - 15, 7));
        file.trigger();
        file.setGain(-7);
      }
      if (timer>=A) {
        timer=0;
      }
    }
  }

  public void collide() {      //Contain SHIP inside screen
    if ( x + 32> width) {
      x = width - 32;
    }
    if ( x <= 16) {
      x = x + 16;
    }
    if ( y <= 19) {
      y= y + 19;
    }
    if ( y + 38 >= height) {
      y = height - 38;
    }
  }
  public boolean isTouching(Asteroid check) {
    if (state == 1) {
      return false;
    } else {
      return dist(x, y, check.x, check.y)< sqrt(l^2 + h^2) + check.r;
    }
  }

  public boolean isTouching(Bullets check) {
    if (state == 1) {
      return false;
    } else {
      int i = blist.size()-1;
      if (blist.get(i).b == 7) {
        return false;
      } else {
        return dist(x, y, check.x, check.y)< sqrt(l^2 + h^2) + check.r;
      }
    }
  }


  public boolean isTouching(UFO check) {
    if (state == 1) {
      return false;
    } else {
      return dist(x, y, check.x, check.y) < sqrt(l^2 + h^2) + check.r;
    }
  }
}

class UFO {
  PImage img;
  float l, h;
  float x;
  float y;
  float r;
  float dx;
  float dy;
  float timer;
  float counter;
  boolean lr;
  int hits;  //UFO hp

  UFO() {
    x= random(30, width-50);
    y= -50;
    l=82.5f;
    h=42;
    r= sqrt(l*l + h*h)/2;
    dx=5;
    dy=0;
    counter=0;
    lr=true;
    hits=5;
    img = loadImage("UFO.png");
  }


  public void display() {
    image(img, x, y, l, h);

  }

  public void move() {    //UFO move like space invaders
    x += dx;
    y += dy;
    if ( counter > 0 && ( x + l/2 <= 0 || x - l/2 >= width)) {
      dy = 5;
      dx = 0;
      counter = counter - dy;
    } else if (lr==true) {
      dx = 5;
      dy = 0;
      counter = 100;
    } else if (lr==false) {
      dx = -5;
      dy = 0;
      counter = 100;
    }
    if ( x - l/2 >= width) {
      lr=false;
    } else if ( x + l/2 <= 0) {
      lr=true;
    }
  }

  public void shoot() {
    if (random(100)>50) {          //Probability UFO will shoot "50%"
      timer++;
      if (timer >= 10.5f) {
        blist.add(new Bullets(x, y + 23, -7));
        file.trigger();
        bang.setGain(-7);
      }
      if (timer>=11) {
        timer=0;
      }
    }
  }
}



  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Asteroid_Blaster" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
