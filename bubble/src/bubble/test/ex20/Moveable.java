package bubble.test.ex20;

/**
 * default 인터페이스도 몸체가 있는 메서드를 만들 수 있다
 * 다중상속이 안되는 것이 많기 때문에 (높은버전 자바에서 나옴)
 * 그래서 어댑터 패턴보다는 default를 사용하는 것이 좋다.
 * left.right,up은 캐릭터 이동, 물방울 이동시 필요 
 * down은 캐릭터 이동시에만 필요 down을 분리하여 상속받기위해 작업중..
 */

//캐릭터 이동범위 설정 인터페이스
public interface Moveable {
	public abstract void left();
	public abstract void right();
	public abstract void up();
	default public void down() {}; 
	default public void attack() {}; 
	default public void attack(Enemy e) {}; 
}
