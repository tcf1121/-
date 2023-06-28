package Datarelated;

public class Monster {
	private int Strength;
	private int Hp;
	private int Exp;
	public Monster(int Str, int Hp, int Exp){
		setStr(Str);
		setHp(Hp);
		setExp(Exp);
	}
	
	public void setStr(int Strength) {
		this.Strength = Strength;
	}
	public int getStr() {
		return this.Strength;
	}
	public void setHp(int Hp) {
		this.Hp = Hp;
	}
	public int getHp() {
		return this.Hp;
	}
	public void setExp(int Exp) {
		this.Exp = Exp;
	}
	public int getExp() {
		return this.Exp;
	}
	public void damage(int Str) {
		this.Hp -= Str;
		if(this.Hp <0) {
			this.Hp =0;
		}
	}
}
