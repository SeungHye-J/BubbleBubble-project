package bubble.test.ex20;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

//calss Player -> new 가능한 애들!! 게임에 존재할 수 있음(추상메서드를 가질 수 없다)
@Getter
@Setter
public class Enemy extends JLabel implements Moveable {

	private BubbleFrame mContext;
	private Player player;
	
	//위치상태
	private int x;
	private int y;
	
	//적군의 방향
	private EnemyWay enemyWay;

	// 움직임의 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private int state;// 0(살아있는 상태),1(물방울에 갇힌 상태)


	//적군의 속도 상태
	private final int SPEED = 3; 
	private final int JUMPSPEED = 1; // up,down

	private ImageIcon enemyR, enemyL;//적군의 방향상태
	

	public Enemy(BubbleFrame mContext, EnemyWay enemyWay) {
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		
		initObject();
		initSetting();
		initBackGroundEnemyService();
		initEnemyDirection(enemyWay);
	}

	private void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}

	// 최초 상태
	private void initSetting() {
		x = 480;
		y = 178;

		// 최초상태는 움직임이 없음
		left = false;
		right = false;
		up = false;
		down = false;
		
		state = 0;

		setSize(50, 50);
		setLocation(x, y);
	}
	
	private void initEnemyDirection(EnemyWay enemyWay) {
		if(enemyWay == enemyWay.RIGHT) {
			enemyWay = enemyWay.RIGHT;
			setIcon(enemyR);
			right();
		}else {
			enemyWay = enemyWay.LEFT;
			x = 480;
			y = 295;
			setIcon(enemyL);
			left();
		}
	}

	private void initBackGroundEnemyService() {
		new Thread(new BackGroundEnemyService(this)).start();
	}
	

	// 이벤트 핸들러
	@Override
	public void left() {
//		System.out.println("left");
		enemyWay = EnemyWay.LEFT;
		left = true;

		new Thread(() -> {
			while (left) {
				setIcon(enemyL);
				x = x - SPEED;
				setLocation(x, y);
				
				if (Math.abs(x - player.getX()) < 10 && // X좌표검사
						(Math.abs(y - player.getY()) > 0 && Math.abs(y - player.getY()) < 50))// Y좌표검사
				{
					// bubble의X값과 enenmy의 X값
					if(player.getState() == 0) {
						player.setState(1);
						player.up();
						System.out.println("적왼쪽뷰딪힘 attack");
					}
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
		enemyWay = EnemyWay.RIGHT;
		right = true;

		new Thread(() -> {
			while (right) {
				setIcon(enemyR);
				x = x + SPEED;
				setLocation(x, y);
				
				if (Math.abs(x - player.getX()) < 10 && // X좌표검사
						(Math.abs(y - player.getY()) > 0 && Math.abs(y - player.getY()) < 50))// Y좌표검사
				{
					// bubble의X값과 enenmy의 X값
					if(player.getState() == 0) {
						player.setState(1);
						player.up();
						System.out.println("적오른쪽뷰딪힘 attack");
					}
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
	public void up() {
//		System.out.println("up");
		up = true;

		new Thread(() -> {
			for (int i = 0; i < 130 / JUMPSPEED; i++) {
				y = y - JUMPSPEED; // 왼쪽상단 좌표가 0,0이기때문에 up은 -(minus)해줘야함
				setLocation(x, y);
				
				if (Math.abs(x - player.getX()) < 10 && // X좌표검사
						(Math.abs(y - player.getY()) > 0 && Math.abs(y - player.getY()) < 50))// Y좌표검사
				{
					// bubble의X값과 enenmy의 X값
					if(player.getState() == 0) {
						player.setState(1);
						player.up();
						System.out.println("적윗쪽뷰딪힘 attack");
					}
				}
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
				
				if (Math.abs(x - player.getX()) < 10 && // X좌표검사
						(Math.abs(y - player.getY()) > 0 && Math.abs(y - player.getY()) < 50))// Y좌표검사
				{
					// bubble의X값과 enenmy의 X값
					if(player.getState() == 0) {
						player.setState(1);
						player.up();
						System.out.println("적아래쪽뷰딪힘 attack");
					}
				}
				
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();
	}
	
}
