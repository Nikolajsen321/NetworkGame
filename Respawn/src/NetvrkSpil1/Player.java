package NetvrkSpil1;

public class Player {
	String name;
	int xpos;
	int ypos;
	int point;
	String direction;

	public Player(String name, int xpos, int ypos, String direction) {
		this.name = name;
		this.xpos = xpos;
		this.ypos = ypos;
		this.direction = direction;
		this.point = 0;
	}

	public int getXpos() {
		return xpos;
	}
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}
	public int getYpos() {
		return ypos;
	}
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public void addPoints(int p) {
		point+=p;
	}
	public String toString() {
		return name+":   "+point;
	}

    public void respawn(int x,int y) {
		if(x==9 && y == 4){
			setXpos(6);
			setYpos(12);
		}else{
			setXpos(9);
			setYpos(4);
		}
		setDirection("RIGHT");
		getDirection();
    }
}
