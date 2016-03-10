package colorJump;

import java.util.Random;

import javax.swing.JPanel;

/*
 * double array - numbers, random generated
 * colors = number
 * 
 * need to keep track of start position and destination position
 * 
 * check all 4 directions:
 * if peg next to it exist and not same color
 * 	then check if one next to that 
 * 				put on stack
 * 				if exists and same color as previous 
 * 					then go to next and repeat
 * 				else if doesn't exist 
 * 					then possible move 
 * else 
 * 	check next direction
 * else 
 * 	if no possible moves in either direction 
 * 		then disable button
 * */

public class Board extends JPanel {

	private int[][] numbers;
	private Random random;

	public Board() {
		numbers = new int[7][7];
		random = new Random();

		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[0].length; j++) {
				// 6 different colors and 0 = null
				numbers[i][j] = random.nextInt(7);
			}
		}
		numbers[3][3] = 0;
	}

	public int getValue(int x, int y) {
		return numbers[x][y];
	}

	
	public int move(int fromX, int fromY, int toX, int toY){
		numbers[toX][toY] = numbers[fromX][fromY];
		numbers[fromX][fromY] = 0;

		int dif, score = 0;
		
		if(toX == fromX){
			dif = Math.abs(fromY - toY);
		}
		else{
			dif = Math.abs(fromX - toX);
		}

		switch(dif){
		case 1:
			score = 10;
			break;
		case 2:
			score = 40;
			break;
		case 3:
			score = 130;
			break;
		case 4:
			score = 400;
			break;
		case 5:
			score = 700;
			break;
		}
		return score;
	}

	public int[][] getBoard(){
		return numbers;
	}



}