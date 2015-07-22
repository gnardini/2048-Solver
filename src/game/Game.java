package game;

import java.util.HashMap;
import java.util.Map;


public class Game {

	public static final int SIZE=4;
	public Board b;
	int highscore, total, maxTile,allMaxTile,totalP;
	private int lastMove, consec, currentMoves;
	private Map<Integer,Integer> stats;
	private boolean n,aux;
	private Game gn;
	
	public Game(int[][] board) {
		b=new Board(this, board);
		b.addRandom();
		b.addRandom();
		stats=new HashMap<Integer,Integer>();
		int n=2;
		for(int i=0 ; i<12;i++){
			stats.put(n, 0);
			n*=2;
		}
	}
	
	public void start(){
		gn=new Game(new int[SIZE][SIZE]);
		stats();
	}
	
	public void resetGame(){
		for(int i=0 ; i<SIZE ; i++)
			for(int j=0 ; j<SIZE ; j++)
				b.board[i][j]=0;
		totalP+=b.points;
		b.points=0;
		b.max=0;
		b.over=false;
		total++;
		b.addRandom();
		b.addRandom();
	}
	public void stats(){
		int m=0;
		while(m<10000){
			m++;
			playOrdered();
			stats.put(maxTile, stats.get(maxTile)+1);
			if(maxTile>allMaxTile) allMaxTile=maxTile;
			maxTile=0; 
			resetGame();
		}
		for(Integer i: stats.keySet()) System.out.println(i + ": " + stats.get(i));
		System.out.println("Average: " + totalP/total);
	}
	
	public boolean moveN(int n){
		if(aux)currentMoves++;
		int a=(int)(Math.log10(b.max)/Math.log10(4))*20;
		if(currentMoves>a) {aux=false;currentMoves=0;}
		switch(n){
		case 0:return b.moveDown();
		case 1:return b.moveUp();
		case 2:return b.moveLeft();
		case 3:return b.moveRight();
		default: return false;
		}
	}
	
	private void playOrdered(){
		while(!b.over){
			moveOrdered();
		}
	}
	

	
	public void moveOrdered(){
		int v=verticalSum(),h=horizontalSum();
		if(!lineComplete(SIZE-1)) n=true;
		else n=moveLeftorRight();
		if(lastMove==1) h=-1;
		if(lastMove!=3 || lineComplete(SIZE-1)){
			if(((consec<=0 && aux) ||v>h)&& moveN(0)){
				lastMove=0;
				consec++;
			}else if((n||linePossible(SIZE-1)) &&  moveN(2)){
				lastMove=2;
				consec=0;
			}
			else if(!n &&  moveN(3)) {
				lastMove=3;
				consec=0;
			}else if( moveN(2)){
				lastMove=2;
				consec=0;
			}
			else if( moveN(3)) {
				lastMove=3;
				consec=0;
			}else if( moveN(0)){
				lastMove=0;
				consec++;
			}
			else if( moveN(1)){
				lastMove=1;
				consec=0;
			}
		}else if(lastMove==3){
			consec=0;
			if( moveN(2))lastMove=0;
			else if( moveN(0))lastMove=2;
			else if( moveN(3)) lastMove=3;
			else if( moveN(1))lastMove=1;
		}
	}
	
	private boolean lineComplete(int n){
		for(int i=0 ; i<SIZE ; i++)
			if(b.board[i][n]==0) return false;
		return true;
	}
	
	private boolean linePossible(int n){
		for(int i=0 ; i<SIZE-1 ; i++)
			if(b.board[i][n]==b.board[i+1][n]) return true;
		return false;
	}
	public int horizontalSum(){
		int rta=0;
		for(int j=0;j<SIZE;j++)
		for(int i=0 ; i<SIZE-1 ; i++)
			if(b.board[i][j]==b.board[i+1][j]) {rta+=b.board[i][j]+2;i++;}
		return rta;
	}
	public int verticalSum(){
		int rta=0;
		for(int j=0;j<SIZE;j++)
		for(int i=0 ; i<SIZE-1 ; i++)
			if(b.board[j][i]==b.board[j][i+1]) {rta+=b.board[j][i]+2;i++;}
		return rta;
	}
	private boolean moveLeftorRight(){
		Board b1=new Board(gn,b.copy()),b2=new Board(gn,b.copy());
		b1.moveRight();b2.moveLeft();b1.moveDown();b2.moveDown();
		if(b2.points>=b1.points) return true;
		else return false;
	}
	
	public void playDeep(){
		while(!b.over){
			moveDeep();
		}
	}
	public void playCombined(){
		while(!b.over){
			if(b.points<15000) moveOrdered();
			else moveDeep();
		}
	}
	public void moveDeep(){
		Board D=new Board(gn,b.copy());D.moveDown();
		Board R=new Board(gn,b.copy());R.moveRight();
		Board L=new Board(gn,b.copy());L.moveLeft();
		int d=deepPoints(D, 4),r=deepPoints(R, 4),l=deepPoints(L, 4);
		int m=max(d,-1,l,r);
		if(m==d && b.moveDown()) return;
		else if(m==l && b.moveLeft()) return;
		else if(m==r && b.moveRight()) return;
		else if(b.moveDown()) return;
		else if(b.moveLeft()) return;
		else if(b.moveRight()) return;
		else if(b.moveUp()) return;
	}
	
	private int deepPoints(Board b, int depth){
		if(depth==0)return b.points;
		depth--;
		Board D=new Board(gn,b.copy());D.moveDown();
		Board R=new Board(gn,b.copy());R.moveRight();
		Board L=new Board(gn,b.copy());L.moveLeft();
		return max(deepPoints(D,depth), deepPoints(R,depth), deepPoints(L,depth),-1);
	}
	/*
	private void playRandom(){
		while(!b.over){
			moveN((int)(Math.random()*4));
		}
	}*/
	
	public void changedMax(){
		aux=true;
	}
	
	public boolean isOver(){
		return b.over;
	}
	
	public int getHighScore(){
		return highscore;
	}
	public int getTotal(){
		return total;
	}
	public int getmaxTile(){
		return allMaxTile;
	}
	public int getPoints(){
		return b.points;
	}
	
	private int max(int i1, int i2, int i3, int i4){
		return Math.max(i1, Math.max(i2,Math.max(i3, i4)));
	}
}
