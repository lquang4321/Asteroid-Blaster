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

  void display() {
    fill(0);
    stroke(255);
    strokeWeight(2);
    ellipse(x, y, r, r);
  }

  void move() {
    x=x+dx;
    y=y+dy;
  }

  void bounce () {
    if (x < r/2 || x >= width - r/2) {
      dx = -dx;
    }
  }
}



