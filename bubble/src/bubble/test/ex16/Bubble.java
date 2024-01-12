package bubble.test.ex16;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Moveable {

	// 의존성 컴포지션
	private BubbleFrame mContext;//mtContext라는 뜻
	private Player player;
	private Enemy enemy;
	private BackgroundBubbleService backgroundBubbleService;

	// 위치상태
	private int x;
	private int y;

	// 움직임의 상태
	private boolean left;
	private boolean right;
	private boolean up;

	// 적군을 맞춘 상태
	private int state;// 0(물방울),1(적을 가둔 물방울)

	private ImageIcon bubble;// 물방울
	private ImageIcon bubbled;// 적을 가둔 물방울
	private ImageIcon bomb; // 물방울이 터진 상태

	public Bubble(BubbleFrame mContext) {
		//this.player = player; // player가 있어야x와y좌표를 알 수 있음.
		this.mContext = mContext;
		this.player = mContext.getPlayer();
		this.enemy = mContext.getEnemy();
		initObject();
		initSetting();
	}

	private void initObject() {
		bubble = new ImageIcon("image/bubble.png");
		bubbled = new ImageIcon("image/bubbled.png");
		bomb = new ImageIcon("image/bomb.png");

		backgroundBubbleService = new BackgroundBubbleService(this);

	}

	private void initSetting() {
		left = false;
		right = false;
		up = false;

		x = player.getX();
		y = player.getY();

		setIcon(bubble);
		setSize(50, 50);

		state = 0;
	}

	@Override
	public void left() {
		left = true;
		for (int i = 0; i < 400; i++) {
			x--;
			setLocation(x, y);

			if (backgroundBubbleService.leftWall()) {
				left = false;
				break;
			}
			
			/**
			 * 적군과 물방울이 맞닿을때
			 * 40과 60위 범위 절대값. 
			 * 물방울/적군크기: 50
			 * 물방울X좌표-적군X좌표 =50일때 맞닿은것. 
			 * 오차범위+-10으로 해준것
			 */
			if((Math.abs(x - enemy.getX()) > 40 && Math.abs(x - enemy.getX()) < 60 )&&//X좌표검사
			    (Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50 )  )//Y좌표검사
			 {
				//bubble의X값과 enenmy의 X값 
				System.out.println("물방울이 적군과 충돌");
				attack();
			}

			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void right() {
		right = true;
		for (int i = 0; i < 400; i++) {
			x++;
			setLocation(x, y);

			if (backgroundBubbleService.leftWall()) {
				right = false;
				break;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		up();
	}

	@Override
	public void up() {
		up = true;
		while (up) {
			y--;
			setLocation(x, y);

			if (backgroundBubbleService.topWall()) {
				up = false;
				break;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		clearBubble(); //천장에 버블이 도착하고 나서 3초 후에 메모리에서 소멸
	}
	
	@Override
	public void attack() {
		state = 1;
		setIcon(bubbled);
	}
	
	//행위 -> clear (동사) -> bubble(목적어)
	public void clearBubble() {
		try {
			Thread.sleep(3000);
			setIcon(bomb);
			Thread.sleep(500);
			mContext.remove(this); // BubbleFrame의 bubble이 메모리에서 소멸된다.
			mContext.repaint();//BubbleFrame의 전체를 다시그린다.(메모리에서 없는건 그리지 않음)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
