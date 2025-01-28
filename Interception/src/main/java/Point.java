package Interception.src.main.java;

/*
 * Code for creating points with x and y-coordinates in the cartesian plane. 
 * 
 */




 public class Point {
     
     public int x; 
     public int y;
     
     public Point(int x, int y){
         this.x = x;
         this.y=  y;
 
     }
 
     
     public int getX(){
         return x;
     }
     public int getY(){
         return y;
     }
     public void setX(int x){
         this.x = x;
     }
     public void setY(int y){
         this.y = y;
     }
 
 
     public boolean equals(Point s){
        if(this.x == s.x && this.y == s.y ){
         return true;
        }else{
         return false;
        }
     }
     
     
 
     public double distance(Point s){
         double diff_x = (this.x - s.x);
         double dix_y = (this.y - s.y);
 
         return (Math.sqrt((diff_x*diff_x)+(dix_y*dix_y)));
     }
 

 }
 