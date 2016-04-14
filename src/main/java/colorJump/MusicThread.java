package colorJump;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import javax.inject.Singleton;

@Singleton
public class MusicThread extends Thread {

	private AudioClip clip;

	public MusicThread() {

	}

	public void run() {

		URL urlClick = getClass().getResource("/music_fun_times.mp3");
		clip = Applet.newAudioClip(urlClick);
		clip.play();
	}

	public void stopMusic() {
		clip.stop();
	}

}
