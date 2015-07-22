package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Board {

	public static final int SIZE=4;
	int[][] board;
	int points, max;
	boolean over;
	private Game g;
	
	public Board(Game g, int[][] b) {
		this.g=g;
		board=b;
	}
	
	public  int[][] copy(){
		int[][] r=new int[SIZE][SIZE];
		for(int i=0 ; i<SIZE ; i++)
			for(int j=0 ; j<SIZE ; j++)
				r[i][j]=board[i][j];
		return r;
	}
	
	public void addRandom(){
		List<Point> r= new ArrayList<Point>();
		for(int i=0 ; i<SIZE ; i++)
			for (int j=0 ; j<SIZE ; j++){
				if(board[i][j]==0)
					r.add(new Point(i,j));
				if(board[i][j]>max){
					max=board[i][j];
					g.changedMax();
				}
			}
		
		Point p=r.get((int)(Math.random()*r.size()));
		if(Math.random()>0.9) board[p.x][p.y]=4;
		else board[p.x][p.y]=2;
		
		if(r.size()<2){
			over=checkForLose();
			if(over){
				if(points>g.highscore) g.highscore=points;
				for(int i=0 ; i<SIZE ; i++)
					for (int j=0 ; j<SIZE ; j++)
						if(board[i][j]>g.maxTile) g.maxTile=board[i][j];
			}
		}
	}
	
	private boolean checkForLose(){
		for(int i=0 ; i<SIZE ; i++){
			for(int j=0 ;j<SIZE-1 ;j++)
				if(board[i][j]==board[i][j+1]) return false;
		}
		for(int j=0 ; j<SIZE ; j++){
			for(int i=0 ;i<SIZE-1 ;i++)
				if(board[i][j]==board[i+1][j]) return false;
		}
		return true;
	}
	
	public boolean moveUp(){
		boolean moved=false;
		for(int i=0 ; i<SIZE ; i++){
			for(int j=0 ; j<SIZE-1 ; j++){
				if(board[i][j]==0){
					boolean flag=true;
					for(int k=j+1 ; k<SIZE && flag ; k++){
						if(board[i][k]!=0){
							swap(i,j,i,k);
							flag=false;
							moved=true;
						}
					}
				}if(board[i][j]!=0){
					boolean flag=true;
					for(int k=j+1 ; k<SIZE && flag; k++){
						if(board[i][k]==board[i][j]){
							board[i][k]=0;
							board[i][j]*=2;
							points+=board[i][j];
							flag=false;
							moved=true;
						}else if(board[i][k]!=0){
							flag=false;
						}
					}
				}
			}
		}
		if(moved) addRandom();
		return moved;
	}
	
	public boolean moveDown(){
		boolean moved=false;
		for(int i=0 ; i<SIZE ; i++){
			for(int j=SIZE-1 ; j>0 ; j--){
				if(board[i][j]==0){
					boolean flag=true;
					for(int k=j-1 ; k>=0 && flag ; k--){
						if(board[i][k]!=0){
							swap(i,j,i,k);
							flag=false;
							moved=true;
						}
					}
				}if(board[i][j]!=0){
					boolean flag=true;
					for(int k=j-1 ; k>=0 && flag; k--){
						if(board[i][k]==board[i][j]){
							board[i][k]=0;
							board[i][j]*=2;
							points+=board[i][j];
							flag=false;
							moved=true;
						}else if(board[i][k]!=0){
							flag=false;
						}
					}
				}
			}
		}
		if(moved) addRandom();
		return moved;
	}
	
	public boolean moveRight(){
		boolean moved=false;
		for(int j=0 ; j<SIZE ; j++){
			for(int i=SIZE-1 ; i>0 ; i--){
				if(board[i][j]==0){
					boolean flag=true;
					for(int k=i-1 ; k>=0 && flag ; k--){
						if(board[k][j]!=0){
							swap(i,j,k,j);
							flag=false;
							moved=true;
						}
					}
				}if(board[i][j]!=0){
					boolean flag=true;
					for(int k=i-1 ; k>=0 && flag; k--){
						if(board[i][j]==board[k][j]){
							board[k][j]=0;
							board[i][j]*=2;
							points+=board[i][j];
							flag=false;
							moved=true;
						}else if(board[k][j]!=0){
							flag=false;
						}
					}
				}
			}
		}
		if(moved) addRandom();
		return moved;
	}
	
	public boolean moveLeft(){
		boolean moved=false;
		for(int j=0 ; j<SIZE ; j++){
			for(int i=0 ; i<SIZE-1 ; i++){
				if(board[i][j]==0){
					boolean flag=true;
					for(int k=i+1 ; k<SIZE && flag ; k++){
						if(board[k][j]!=0){
							swap(i,j,k,j);
							flag=false;
							moved=true;
						}
					}
				}if(board[i][j]!=0){
					boolean flag=true;
					for(int k=i+1 ; k<SIZE && flag; k++){
						if(board[i][j]==board[k][j]){
							board[k][j]=0;
							board[i][j]*=2;
							points+=board[i][j];
							flag=false;
							moved=true;
						}else if(board[k][j]!=0){
							flag=false;
						}
					}
				}
			}
		}
		if(moved) addRandom();
		return moved;
	}
	
	private void swap(int x1, int y1, int x2, int y2){
		int aux=board[x1][y1];
		board[x1][y1]=board[x2][y2];
		board[x2][y2]=aux;
	}
	
	public int getPoints(){
		return points;
	}
}
