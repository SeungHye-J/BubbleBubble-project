package bubble.test.ex03;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//calss Player -> new 가능한 애들!! 게임에 존재할 수 있음(추상메서드를 가질 수 없다)
public class Player extends JLabel implements Moveable {

	//게임캐릭터 위치상태
	private int x;
	private int y;

	//움직임의 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private ImageIcon playerR, playerL;//캐릭터가 오른쪽을볼지 왼쪽을볼지 상태

	public Player() {
		initObject();
		initSetting();
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}

	//최초 상태
	private void initSetting() {
		x = 55;
		y = 535;

		//최초상태는 움직임이 없음
		left = false;
		right = false;
		up = false;
		down = false;
		
		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	@Override
	public void left() {
		setIcon(playerL);
		x = x - 10;
		setLocation(x, y);
	}

	@Override
	public void right() {
		setIcon(playerR);
		x= x + 10;
		setLocation(x, y);
	}

	@Override
	public void up() {

	}

	@Override
	public void down() {

	}
}
