package bubble.test.ex19;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//메인스레드 바쁨 - 키보드 이벤트를 처리하기 바쁨.
//백그라운드에서 계속 관찰
public class BackGroundEnemyService implements Runnable {

	private BufferedImage image;
	private Enemy enemy;

	private int BOTTOMCOLOR = -131072;// 바닥색깔(빨강)
	private int FIRSTBOTTOM = 0;// 바닥도착여부 0, 1
	private int STATE = 0;//FIRSTBOTTOM 1일때(1층) 2번 벽에 부딪히면 up시켜주기 위함
	private int JUMPCOUNT = 0;// 점프카운트

	// 플레이어, 버블
	public BackGroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		while (enemy.getState() == 0) {

			// 색상확인
			Color leftColor = new Color(image.getRGB(enemy.getX() - 10, enemy.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
			// -2가 나온다는 뜻은 바닥에 색깔이 없이 흰색 (왼-1+오-1)
			int bottomColor = image.getRGB(enemy.getX() + 10, enemy.getY() + 50 + 5)// 적군의왼쪽하단
					+ image.getRGB(enemy.getX() + 50 - 10, enemy.getY() + 50 + 5);// 적군의오른쪽하단

			// 바닥 충돌 확인
			if (bottomColor != -2) {
				// System.out.println("bottom Color: " + bottomColor);
				// System.out.println("바닥에 충돌함");
				enemy.setDown(false);
			} else if (bottomColor != BOTTOMCOLOR) {
				if (!enemy.isUp() && !enemy.isDown()) {
					enemy.down();
				}
			}

			//바닥도착
			if (bottomColor == BOTTOMCOLOR) FIRSTBOTTOM = 1;
			
			//꼭대기 도착
			if(JUMPCOUNT >= 3) {
				JUMPCOUNT = 0;
				FIRSTBOTTOM = 0;
				STATE = 0;
			}
			
			//적군 꼭대기층으로 점프시키기
			if(STATE ==2
				&&JUMPCOUNT < 3
				&& FIRSTBOTTOM == 1
				&&!enemy.isUp() 
				&& !enemy.isDown()) {
					JUMPCOUNT++;
					if(JUMPCOUNT == 3 && enemy.isRight()) {
						enemy.setLeft(true);
						enemy.setRight(false);
						enemy.left();
					}else if(JUMPCOUNT == 3 && enemy.isLeft()) {
						enemy.setLeft(false);
						enemy.setRight(true);
						enemy.right();
					}
					enemy.up();
				
			}// 외벽 충돌 확인 
			else if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				enemy.setLeft(false);
				if (!enemy.isRight()) {
					enemy.right();
					if (FIRSTBOTTOM != 0)// 맨 밑바닥에서 벽 충돌 수 세기
						STATE++;
				}
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				enemy.setRight(false);
				if (!enemy.isLeft()) {
					enemy.left();
					if (FIRSTBOTTOM != 0) // 맨 밑바닥에서 벽 충돌 수 세기
						STATE++;
				}
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
