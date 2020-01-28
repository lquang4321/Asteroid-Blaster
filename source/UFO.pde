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
    l=82.5;
    h=42;
    r= sqrt(l*l + h*h)/2;
    dx=5;
    dy=0;
    counter=0;
    lr=true;
    hits=5;
    img = loadImage("UFO.png");
  }


  void display() {
    image(img, x, y, l, h);

  }

  void move() {    //UFO move like space invaders
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

  void shoot() {
    if (random(100)>50) {          //Probability UFO will shoot "50%"
      timer++;
      if (timer >= 10.5) {
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



