import java.io.*;
import java.util.*;
public class Moocast {
	
	public static Vertex1[] cows;
	
	public static void main(String[] args) throws Exception{
		
		//String inputFile = "moocast.in";
		String inputFile = "/Users/jerryzhou/eclipse-workspace/USACO/src/moocast";
		Scanner sc = new Scanner(new File(inputFile));
		PrintWriter pw = new PrintWriter(new FileWriter("moocast.in"));
		
		int n = sc.nextInt();
		cows = new Vertex1[n];
		
		for(int i = 0; i < cows.length; i++) {
			cows[i] = new Vertex1(-1, -1, -1);
		}
		for(int i = 0; i < n; i++) {
			int x1 = sc.nextInt();
			int y1 = sc.nextInt();
			int pow = sc.nextInt();
			cows[i] = new Vertex1(x1, y1, pow);
		}
		sc.close();
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(findDistance(cows[i].getPosX(), cows[i].getPosY(), cows[j].getPosX(), cows[j].getPosY()) <= cows[i].getPow())
					cows[i].addNeighbour(j);
			}
		}
		
		int max = -1;
		for(int i = 0; i < n; i++) {
			int curr = dfs(cows, i, 0);
			max = Math.max(curr, max);
		}
		
		System.out.print(max);
		pw.println(max);
		pw.close();
	}
	
	public static double findDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static int dfs(Vertex1[] cow, int pos, int ans) {
		ans++;
		Vertex1 curr = cow[pos];
		cow[pos].setVisited(true);
		for(Integer v: curr.getNeighbour()) {
			if(!cow[v].getVisited())
				dfs(cow, v, ans);	
		}
		return ans;
	}
}

class Vertex1{
	
	private ArrayList<Integer> neighbours;
	private int posX;
	private int posY;
	private int power;
	private boolean visited;
	
	public Vertex1(int x, int y, int p) {
		posX = x;
		posY = y;
		neighbours = new ArrayList<Integer>();
		power = p;
		visited = false;
	}
	
	public void addNeighbour(Integer pt) {
		neighbours.add(pt);
	}
	
	public ArrayList<Integer> getNeighbour(){
		return neighbours;
	}
	
	public void clearNeighbour() {
		neighbours.clear();
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public int getPow() {
		return power;
	}
	
	public void setVisited(boolean x) {
		visited = x;
	}
	
	public boolean getVisited() {
		return visited;
	}
}
