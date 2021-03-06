/* autogenerated by Processing revision 1282 on 2022-03-25 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import peasy.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class snake_3d extends PApplet {





int pix = 60;
PeasyCam cam;



//                          varriable du jeux
int vx = 1;
int vy = 0;
int vz = 0;


int ax = 5;
int ay = 5;
int az = 3;

int nbcorp = 3;

int[][] snake = {{2,0,0},{1,0,0},{0,0,0}};


int time;


boolean menu = true;


 public void setup() {
  /* size commented out by preprocessor */;
  ortho();
  cam = new PeasyCam(this, 800);
}

 public void draw() {
  
  //if (menu) {
  //  menu();
  //} else {
    //                                                              on draw le background
    translate(-width/2,-height/2);
    rotateX(0.1f);
    rotateY(-0.1f);
    background(0);
    render();
  //}
}





//                                                                            function pour bouger les corps et calculer leurs possitions en 3s

 public int[] mouve (int old_x, int old_y, int old_z, int vx, int vy, int vz) {
  
  int x = old_x + vx;
  int y = old_y + vy;
  int z = old_z + vz;
  
  if (x > 9) {
    x = 0;
  } 
  else if (x < 0) {
   x = 10;
  }
  else if (y > 9) {
    y = 0;
  } 
  else if (y < 0) {
   y = 10;
  }
  else if (z > 4) {
    z = 0;
  } 
  else if (z < 0) {
   z = 5;
  }
  
  int[] list = {x ,y ,z };
  return list;
}

    public int[] position(int x, int y, int z) {
    int newx = x * pix + pix/2;
    int newy = y * pix + pix/2;
    int newz = z * pix + pix/2;
    int[] pos = {newx, newy, newz};
    return  pos;
  }
  
   public long rnb(int max) {
    long valeur;
    valeur = Math.round(Math.random() * max);
    return valeur;
}






//                                                                        render
 public void render() { 
  for (int ix = 0; ix < 11; ix++) {
    for (int iy = 0; iy < 11; iy++) {
      for (int iz = 0; iz < 6; iz++) {
        stroke(127);
        //point(ix*60,iy*60,iz*60);
        line(ix*pix,iy*pix,iz*pix,600,iy*pix,iz*pix);
        line(ix*pix,iy*pix,iz*pix,ix*pix,600,iz*pix);
        line(ix*pix,iy*pix,iz*pix,ix*pix,iy*pix,300);
      }
    }
  }
  
  
  //                                                                  update les positions ?? chaque seconde
  // le premier corps est update avec 
  // la vitesse et les autres prennent 
  // juste l'anci??ne position du corps devant eux.
  if (time != second()) {
    for (int n = nbcorp-1; n > -1; n--) {
      if (n == 0) {
        int[] update = mouve(snake[0][0],snake[0][1],snake[0][2],vx,vy,vz);
        snake[0][0] = update[0];
        snake[0][1] = update[1];
        snake[0][2] = update[2];
      }
      else {
        snake[n][0] = snake[n-1][0];
        snake[n][1] = snake[n-1][1];
        snake[n][2] = snake[n-1][2];
      }
    }
    //                                                                  check si le snake se mange lui m??me
   
   for (int n = nbcorp-1; n > 0; n--) {
     if (snake[0][0] == snake[n][0] && snake[0][1] == snake[n][1] && snake[0][2] == snake[n][2]) {
       menu = true;
     }
   }
  }
  time = second();
  
     //                                                        check si le snake mange une pomme 
   
   if (snake[0][0] == ax && snake[0][1] == ay && snake[0][2] == az) {
     int n = nbcorp - 1;
     int nx;
     int ny;
     int nz;
     
     if (snake[n][0] - vx < 0){
       nx = 10;
       ny = snake[n][1] - vy;
       nz = snake[n][2] - vz;
     } else if (snake[n][1] - vy < 0) {
       nx = snake[n][0] - vx;
       ny = 10;
       nz = snake[n][2] - vz;
     } else if (snake[n][2] - vz < 0) {
       nx = snake[n][0] - vx;
       ny = snake[n][1] - vy;
       nz = 5;
     } else { 
       nx = snake[n][0] - vx;
       ny = snake[n][1] - vy;
       nz = snake[n][2] - vz;
     }
     
     int[][] nsnake = Arrays.copyOf(snake, snake.length + 1);
     snake = nsnake;

     int[] corp = {nx,ny,nz};
     snake[nbcorp] = corp;

     ax = (int) rnb(9);
     ay = (int) rnb(9);
     az = (int) rnb(4);
     nbcorp += 1;
   }
   
   
   
   
   
   
   //                                                                  recup??re les touches et modifie les d??placements en fonction
   
  if (keyPressed) {
    if (key == 'z' || key == 'Z') {
      vx = 0;
      vy =-1;
      vz = 0;
   }
   else if (key == 's' || key == 'S') {
      vx = 0;
      vy = 1;
      vz = 0; 
   }
   else if (key == 'd' || key == 'D') {
      vx = 1;
      vy = 0;
      vz = 0; 
   }
   else if (key == 'q' || key == 'Q') {
      vx =-1;
      vy = 0;
      vz = 0; 
   }
   else if (key == 'a' || key == 'A') {
      vx = 0;
      vy = 0;
      vz = 1; 
   }
   else if (key == 'e' || key == 'E') {
      vx = 0;
      vy = 0;
      vz =-1;
   }
  }
  
  
  //                                                                       desine les corps du snake dans le monde en 3d 
  for (int n = 0; n < nbcorp; n++) {
    int[] list = {};
    list = position(snake[n][0],snake[n][1],snake[n][2]);
    translate(list[0],list[1],list[2]);
    fill(255);
    box(60,60,60);
    translate(-list[0],-list[1],-list[2]);
  }
  
  int[] list = {};
  list = position(ax,ay,az);
  translate(list[0],list[1],list[2]);
  fill(255,0,0);
  box(60,60,60);
  translate(-list[0],-list[1],-list[2]);
  
}



//void menu() {
//  background(0);
//  text("bonjours" , 50 , 50);
//}
  
  


  public void settings() { size(600, 600, P3D); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "snake_3d" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
