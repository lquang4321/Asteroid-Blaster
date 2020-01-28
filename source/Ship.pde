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

  void display() {
    fill(255);
    stroke(1);
    imageMode(CENTER);
    image(img, x, y, l, h);
  }

  void move() {
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

  void death() {
    if (state != 1) {
      display();
      move();
      shoot();
    }
  }
  void life() {
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

  void shoot() {
    timer++;
    if (v==true) {
      if (timer >= A-0.1) {
        blist.add(new Bullets(x, y - 15, 7));
        file.trigger();
        file.setGain(-7);
      }
      if (timer>=A) {
        timer=0;
      }
    }
  }

  void collide() {      //Contain SHIP inside screen
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
  boolean isTouching(Asteroid check) {
    if (state == 1) {
      return false;
    } else {
      return dist(x, y, check.x, check.y)< sqrt(l^2 + h^2) + check.r;
    }
  }

  boolean isTouching(Bullets check) {
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


  boolean isTouching(UFO check) {
    if (state == 1) {
      return false;
    } else {
      return dist(x, y, check.x, check.y) < sqrt(l^2 + h^2) + check.r;
    }
  }
}

