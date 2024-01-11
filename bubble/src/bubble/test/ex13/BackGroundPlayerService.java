package bubble.test.ex13;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthScrollBarUI;

//메인스레드 바쁨 - 키보드 이벤트를 처리하기 바쁨.
//백그라운드에서 계속 관찰
public class BackGroundPlayerService implements Runnable {

	private BufferedImage image;
	private Player player;

	public BackGroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (true) {
			// 색상확인
			Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
			// -2가 나온다는 뜻은 바닥에 색깔이 없이 흰색 (왼-1+오-1)
			int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5)// 왼쪽하단
					+ image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5);// 오른쪽하단

			// 바닥 충돌 확인
			if (bottomColor != -2) {
//				System.out.println("bottom Color: " + bottomColor);
//				System.out.println("바닥에 충돌함");
				player.setDown(false);
			}else {
				if(!player.isUp() && !player.isDown()) {
					player.down();
				}
				
			}

			// 외벽 충돌 확인
			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
//				System.out.println("왼쪽벽에 충돌함");
				player.setLeftWallCrash(true);
				player.setLeft(false);
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//				System.out.println("오른쪽벽에 충돌함");
				player.setRightWallCrash(true);
				player.setRight(false);
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
