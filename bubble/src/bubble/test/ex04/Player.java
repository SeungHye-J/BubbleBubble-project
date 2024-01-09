package bubble.test.ex04;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

//calss Player -> new 가능한 애들!! 게임에 존재할 수 있음(추상메서드를 가질 수 없다)
@Getter
@Setter
public class Player extends JLabel implements Moveable {

	// 게임캐릭터 위치상태
	private int x;
	private int y;

	// 움직임의 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	private ImageIcon playerR, playerL;// 캐릭터가 오른쪽을볼지 왼쪽을볼지 상태

	public Player() {
		initObject();
		initSetting();
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}

	// 최초 상태
	private void initSetting() {
		x = 55;
		y = 535;

		// 최초상태는 움직임이 없음
		left = false;
		right = false;
		up = false;
		down = false;

		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	// 이벤트 핸들러
	@Override
	public void left() {
		System.out.println("left");
		left = true;
		
		new Thread(() -> {
			while (left) {
				setIcon(playerL);
				x = x - 1;
				setLocation(x, y);
				
				try {
					Thread.sleep(10); //0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public void right() {
		System.out.println("right");
		right = true;
		
		new Thread(() -> {
			while(right) {
				setIcon(playerR);
				x = x + 1;
				setLocation(x, y);
				
				try {
					Thread.sleep(10); //0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public void up() {
		System.out.println("점프");
	}

	@Override
	public void down() {

	}
}
