package beanUtilsDraft;

public class Stu{
	private String name;
	private String Pass;
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "Stu [name=" + name + ", Pass=" + Pass + "]";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return Pass;
	}
	public void setPass(String pass) {
		Pass = pass;
	}
}
