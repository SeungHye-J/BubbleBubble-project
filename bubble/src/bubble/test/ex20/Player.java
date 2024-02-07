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
	private GameOver gameOver;
	
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
	
	private int state;// 0 : live , 1 : die

	private ImageIcon playerR, playerL, playerLDie, playerRDie;// 캐릭터가 오른쪽을볼지 왼쪽을볼지 상태
	
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
			while (left && getState() ==0) {
				
				setIcon(playerL);
				x = x - SPEED;
				setLocation(x, y);
				
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
			while (right && getState() == 0) {
				setIcon(playerR);
				x = x + SPEED;
				setLocation(x, y);
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
		System.out.println("up");
		up = true;
		new Thread(() -> {
			for (int i = 0; i < 130 / JUMPSPEED; i++) {
				y = y - JUMPSPEED; // 왼쪽상단 좌표가 0,0이기때문에 up은 -(minus)해줘야함	
				setLocation(x, y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			up = false;
			down();
			
		}).start();
	}

	@Override
	public void down() {
//		System.out.println("down");
		down = true;
		new Thread(() -> {
			while (down) {
				y = y + JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			down = false;
		}).start();
	}
	
	//player가 죽었을때
	public void die() {
		new Thread(() -> {
			setState(1);
			setIcon(playerWay.RIGHT == playerWay ? playerRDie : playerLDie);
			new GameOverBGM();
			mContext.getBgm().stopBGM();
			
			try {
				if(!isUp() && !isDown()) {
					System.out.println("up으로 넘겨");
					up();
				}
				gameOver = new GameOver(mContext);
				mContext.add(gameOver);
				Thread.sleep(2000);
				mContext.remove(this);
				mContext.repaint();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("플레이어 사망.");
		}).start();
		
	}
	
}
