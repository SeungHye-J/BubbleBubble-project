package bubble.test.ex15;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BubbleFrame extends JFrame {

	private BubbleFrame mContext = this;
	private JLabel backgroundMap;
	private Player player;
	private Enemy enemy;
	
	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);
	}
	
	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap); // JPannel - >JLabel로 변경
		player = new Player(mContext);
		add(player);
		enemy = new Enemy(mContext);
		add(enemy);
		
		//JPannel 안에 JLabel있는 버전
//		backgroundMap.setSize(100,100);
//		backgroundMap.setLocation(300,300);
//		backgroundMap.setSize(1000,600);
//		add(backgroundMap);//JFrame에 JLabel이 그려진다.
	}
	
	private void initSetting() {
		setSize(1000,640);
		setLayout(null);;//absoulte 레이아웃(자유롭게 그림을 그릴 수 있다)
		
		setLocationRelativeTo(null);//JFrame 가운데 배치하기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//x버튼으로 창을 끌 때 JVM 같이 종료하기
	}
	
	private void initListener() { //리스너=키보드를 바라봄.
		addKeyListener(new KeyAdapter() {
			
			//키보드 클릭이벤트 핸들러
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 
						if(!player.isLeft() && !player.isLeftWallCrash()) {
							player.left();
						}
						break;
					case KeyEvent.VK_RIGHT:
						if(!player.isRight()&& !player.isRightWallCrash()) {
							player.right();
						}
						break;
					case KeyEvent.VK_UP: 
						if(!player.isUp()&&!player.isDown()) {
							player.up();
						}
						break;
					case KeyEvent.VK_SPACE: 
						player.attack();
						break;
				}
			}
			
			
			//키보드 해제 이벤트 핸들러
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					player.setRight(false);
					break;
				}
			}
			
		});
	}
	
	public static void main(String[] args) {
		new BubbleFrame();
	}

}
