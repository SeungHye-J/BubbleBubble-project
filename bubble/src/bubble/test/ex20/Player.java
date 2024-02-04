package bubble.test.ex20;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

//calss Player -> new 가능한 애들!! 게임에 존재할 수 있음(추상메서드를 가질 수 없다)
@Getter
@Setter
public class Player extends JLabel implements Moveable {

	private BubbleFrame mContext;
	private List<Bubble> bubbleList;
	
	//위치상태
	private int x;
	private int y;
	
	//플레이어의 방향
	private PlayerWay playerWay;

	// 움직임의 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	// 벽에 충돌한 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;

	// 플레이어 속도 상태
	private final int SPEED = 4; // 상수대문자
	private final int JUMPSPEED = 2; // up,down

	private ImageIcon playerR, playerL;// 캐릭터가 오른쪽을볼지 왼쪽을볼지 상태
	private ImageIcon playerLDie, playerRDie;
	private int state;// 0(살아있는 상태),1(적군과 부딪힌 상태)
	
	
	public Player(BubbleFrame mContext) {
		this.mContext = mContext;

		initObject();
		initSetting();
		initBackGroundPlayerService();
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
		playerRDie = new ImageIcon("image/playerRDie.png");
		playerLDie = new ImageIcon("image/playerLDie.png");
		bubbleList = new ArrayList<>();
	}

	// 최초 상태
	private void initSetting() {
		x = 80;
		y = 535;

		// 최초상태는 움직임이 없음
		left = false;
		right = false;
		up = false;
		down = false;

		state = 0;
		
		leftWallCrash = false;
		rightWallCrash = false;

		playerWay = PlayerWay.RIGHT;
		
		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initBackGroundPlayerService() {
		new Thread(new BackGroundPlayerService(this)).start();
	}
	
	@Override
	public void attack() {
		new Thread(()->{
			Bubble bubble = new Bubble(mContext);
			mContext.add(bubble);
			bubbleList.add(bubble);
			if(playerWay == playerWay.LEFT) {
				bubble.left();
			}else {
				bubble.right();
			}
		}).start();
	}

	// 이벤트 핸들러
	@Override
	public void left() {
//		System.out.println("left");
		playerWay = PlayerWay.LEFT;
		left = true;

		new Thread(() -> {
			while (left) {
				
				if(state ==0) {
					setIcon(playerL);
					x = x - SPEED;
					setLocation(x, y);
				}else {
					setIcon(playerLDie);
					//System.out.println("left()");
					//die(playerLDie);
					
				}
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public void right() {
//		System.out.println("right");
		playerWay = PlayerWay.RIGHT;
		right = true;

		new Thread(() -> {
			while (right) {
				if(state ==0) {
					setIcon(playerR);
					x = x + SPEED;
					setLocation(x, y);
				}else {
					setIcon(playerRDie);
				//	System.out.println("right()");
					//die(playerRDie);
					
				}
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	// left + up , right + up
	@Override
	public void up() {
//		System.out.println("up");
		up = true;

		new Thread(() -> {
			try {
			if(state == 0) {
				for (int i = 0; i < 130 / JUMPSPEED; i++) {
					y = y - JUMPSPEED; // 왼쪽상단 좌표가 0,0이기때문에 up은 -(minus)해줘야함	
					setLocation(x, y);
					Thread.sleep(5);
				}
				
			}else {
				System.out.println("up");
				for (int i = 0; i < 130 / JUMPSPEED; i++) {
					y = y - JUMPSPEED/2 ;
					setLocation(x, y);
					Thread.sleep(5);
				}
				
			}
			
			up = false;
			down();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}).start();
	}

	@Override
	public void down() {
//		System.out.println("down");
		down = true;

		new Thread(() -> {
			while (down) {
				//if(state == 0) {
					y = y + JUMPSPEED;
					setLocation(x, y);
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				//}else {
					//die();
				//}
				
			}
			
			down = false;
		}).start();
	}
	
	//
//	public void die(Icon icon) {
//		System.out.println("die()");
//		//mContext.remove(p);
//		setIcon(icon);
//		mContext.repaint();
//	}
	
}
