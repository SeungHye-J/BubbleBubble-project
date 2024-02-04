package bubble.test.ex20;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BGM {
	public Clip clip;
	
	public void stopBGM(){
		if(clip != null){
			clip.stop();
			System.out.println("음악이 멈추었습니다.");
		}
	}
	
	public BGM() {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/bgm.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			
			// 소리설정
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				
			// 볼륨조절
			gainControl.setValue(-30.0f);
			
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
