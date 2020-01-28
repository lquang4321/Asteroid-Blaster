class Bullets {
  float x, y, b, r;  //b=direction of bullet depending on UFO/SHIP class.
  Bullets(float pausex, float pausey, float bullet) {
    x=pausex;
    y=pausey;
    b=bullet;
    r = 7;
  }
  void make() {
    fill(255);
    ellipseMode(CENTER);
    ellipse(x, y, r, r);
    move();
    if(ship.state == 1) {
      r=3;
    }
  }
  void move() {
    y = y - b;
  }
  boolean isTouching(Asteroid check) {
    return dist(x, y, check.x, check.y)< r/2 + check.r;
  }
  boolean isTouchingufo(UFO check) {
    if (b == -7) {
      return false;
    } else {
      return dist(x, y, check.x, check.y)< r/2 + check.r;
    }
  }
}



