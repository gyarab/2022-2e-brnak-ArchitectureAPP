boolean rectClicked = false;

int defaultX = 400;
int defaultY = 400;
int defaultZ = 400;

int moveX;
int moveY;

int x = 100;
int y = 20;
int z = 100;

int pokojX = 1000;
int pokojY = 1000;
int pokojZ = 1000;

//Objekty
int box1 = 20;
int plocha1X = 90;
int plocha1Y = 10;
int plocha1Z = 40;

//Scanner naicst pokoj
//Budovy


void setup() {
  size(1700,1100,P3D);
  background(50);
  
}
void draw(){
  
  fill(204, 102, 0);
  stroke(204, 102, 0);
  rect(100,50,0,0);
  
   /*camera(30.0, mouseY, 220.0, // eyeX, eyeY, eyeZ
         0.0, 0.0, 0.0, // centerX, centerY, centerZ
         0.0, 1.0, 0.0); // upX, upY, upZ
         */
  
  //rect(pokojX,pokojY,pokojZ,100,100);
  
  
  if(mouseX <= 100 && mouseY <= 50 && mousePressed==true){
     
     moveX = defaultX;
     moveY = defaultY;
     noFill();
     translate(defaultX, defaultY);
    rect(box1,box1,box1,moveX,moveY);
    if (mouseX < moveX && mouseX > moveX-box1 && mouseY > moveY && mouseY < moveY-box1 && mousePressed) {
      moveX += mouseX;
      moveY += mouseY;
    } 
 }
  else if(mouseX <= 100 && mouseY <= 100 && mouseY > 50 && mousePressed==true){
    noFill();
    translate(defaultX, defaultY);
    rect(250,150,defaultX,defaultY);
  }
  else{
   
  } 
 }
