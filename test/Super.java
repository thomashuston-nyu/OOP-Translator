package test;

public class Super {
	
	int x;
	double y;
	String z;
	
	public Super(int x, double y) {
		setX(x);
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public static void main(String[] args) {
		Super s = new Super(5,8.0);
		int a = s.getX();
		if (a <= 0) {
			System.out.println(a);
		} else {
			for (int b = 0; b < a; b++) {
				System.out.print(b);
			}
		}
	}
	
}