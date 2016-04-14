package colorJump;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class HighScoreInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] highScore;
	private String[] highScoreNames;

	public HighScoreInfo() {
		highScore = new int[3];
		highScoreNames = new String[3];
		for (int i = 0; i < highScore.length; i++) {
			highScore[i] = 0;
		}
		for (int i = 0; i < highScoreNames.length; i++) {
			highScoreNames[i] = "Player " + i;
		}
	}

	public int[] getHighScore() {
		return highScore;
	}

	public void setHighScore(int[] highScore) {
		this.highScore = highScore;
	}

	public String[] getHighScoreNames() {
		return highScoreNames;
	}

	public void setHighScoreNames(String[] highScoreNames) {
		this.highScoreNames = highScoreNames;
	}

	public int lowestScore() {
		// TODO Auto-generated method stub
		return highScore[2];
	}

	public void setNewHighScore(int scoreNum, String name) {
		// TODO Auto-generated method stub
		if(scoreNum>highScore[0]){
			highScore[2]=highScore[1];
			highScore[1]=highScore[0];
			highScoreNames[2]=highScoreNames[1];
			highScoreNames[1]=highScoreNames[0];
			highScore[0]=scoreNum;
			highScoreNames[0]=name;
		}else if(scoreNum> highScore[1]){
			highScore[2]=highScore[1];
			highScoreNames[2]=highScoreNames[1];
			highScore[1]=scoreNum;
			highScoreNames[1]=name;
		}else{
			highScore[2]= scoreNum;
			highScoreNames[2]=name;
		}
		
	}
}