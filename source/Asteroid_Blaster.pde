import ddf.minim.*;
import gifAnimation.*;
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

void setup() {
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


void draw() {
  background(0);
  if (mode == 1) {
    image(space, width/2, height/2, 600, 700);
    space.play();
  }
  if (buttonListMain.get(0).mode != 2) {
    for (int u=0; u < buttonListMain.size (); u++) {
      buttonListMain.get(u).makeRect();
      buttonListMain.get(u).makeRect(150);

      buttonListMain.get(u).c = #000000;
      buttonListMain.get(u).c2 = #000000;

      if (buttonListMain.get(u).isInside() && mode == 1) {
        buttonListMain.get(u).c =  #342A2A;
      }

      if (buttonListMain.get(u).isInside() && mode == 1 && mousePressed) {
        mode = 2;
      }
      if (buttonListMain.get(u).isInside2() && mode == 1 && mousePressed) {
        buttonListMain.get(u).c2 =  #342A2A;
        mode = 3;
      } else if (buttonListMain.get(u).isInside3() && mousePressed && mode ==3) {
        mode = 1;
      } else if (buttonListMain.get(u).isInside4() && mousePressed && mode ==4) {
        setup();
      }
      if (buttonListMain.get(u).isInside2() && mode == 1) {
        buttonListMain.get(u).c2 =  #342A2A;
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



void keyPressed() {
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


void keyReleased() {
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

void diffIncrease() {
  if (score >= counter && score <700) {
    D -= 2;    //UFO spawn chance increase
    C -= 2;    //Asteroid spawn chance increase
    amountAsteroid=(int)random(3, 5);
    a2 -=5;
    u2 -=7;
    counter += 100;
    ship.A -= 0.075;  //Fire rate increase
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
      ship.A-= 0.1;
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

void gameOver() {
  mode=4;
}

void collisionCheck() {
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

