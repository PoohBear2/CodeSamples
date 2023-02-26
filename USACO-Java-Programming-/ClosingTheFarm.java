import java.io.*;
import java.util.*;
public class ClosingTheFarm {

	public static Vertex[] barns;
	public static Queue<Integer> orderOfClosing;

	public static void main(String[] args) throws Exception{

		Scanner sc = new Scanner(new File("closing.in"));
		//Scanner sc = new Scanner(new File("/Users/jerryzhou/eclipse-workspace/USACO/src/Closingthebarn"));
		PrintWriter pw = new PrintWriter(new FileWriter("closing.out"));
		int n = sc.nextInt();
		int m = sc.nextInt();
		barns = new Vertex[n + 1]; //every index represents the barn num, which is why n+1 (Largest index  = n)

		for(int i = 0; i < barns.length; i++) {
			barns[i] = new Vertex(i);
		}
		for(int i = 0; i < m; i++) {
			int barn1 = sc.nextInt();
			int barn2 = sc.nextInt();
			barns[barn1] = new Vertex(barn2);
			barns[barn2] = new Vertex(barn1);
		}
		orderOfClosing = new LinkedList();
		for(int i = 0; i < n; i++) {
			orderOfClosing.add(sc.nextInt());
		}

		//Check initial state
		if(isConnected(barns, n))
			pw.println("YES");
		else
			pw.println("NO");

		//Check state after closing barn
		ArrayList<Integer> currClosed = new ArrayList<Integer>();
		for(int i = 0; i < n - 1; i++) {
			int temp = orderOfClosing.poll();
			currClosed.add(temp);
			
			for(int val: barns[temp].getNeighbourList()) {
				barns[val].removeNeighbour(temp);
			}
			
			barns[temp].clearNeighbour();
			if(currClosed.size() < n -1) {
				if(isConnected(barns, n - currClosed.size())) {
					pw.println("YES");
				}
				else
					pw.println("NO");
			}
			else {
				pw.println("YES");
			}

		}
		sc.close();
		pw.close();
	}

	public static boolean isConnected(Vertex[] nodes, int numBarns) {
		int startInd = 0;
		if(numBarns == 1)
			return true;
		while(startInd < nodes.length && nodes[startInd].getNeighbourList().size() ==  0) {
			startInd++;
		}
		if(startInd == nodes.length)
			return false;
		else {
			int numPaths = dfs(startInd, nodes);
			return numPaths >= numBarns;
		}

	}

	public static int dfs(int pos, Vertex nodes[]) {
		int ans = 1;
		nodes[pos].setVisited(true);
		Vertex temp = nodes[pos];
		for(int v: temp.getNeighbourList()) {
			if(!nodes[v].isVisited()) {			
				ans += dfs(v, nodes);
			}
		}
		return ans;
	} 

	/*public static void dfs(Vertex[] vertexList, Stack<Vertex> stack) {
		for(Vertex v: vertexList) {
			if(!v.isVisited()) {
				v.setVisited(true);
				dfsWithStack(v, stack);
			}
		}
	}

	public static void dfsWithStack(Vertex rootVertex, Stack<Vertex> stack) { 
		stack.add(rootVertex);
		rootVertex.setVisited(true);
		while(!stack.isEmpty()) {
			Vertex curr = stack.pop();
			System.out.print(curr + " "); //the node that we visited

			//Below, we begin to traverse down the path
			for(Vertex v: curr.getNeighbourList()) {
				if(!v.isVisited()) {
					v.setVisited(true);
					stack.push(v); //inserts it into the top of the stack
				}
			}
		}
	}*/
}

class Vertex {

	private int name;
	private boolean visited;
	private List<Integer> neighbourList;

	public String toString() {
		return Integer.toString(name);
	}

	public Vertex(int inName) {
		name = inName;
		neighbourList = new ArrayList<>();
	}

	public void addNeighbour(Integer vertex) {
		neighbourList.add(vertex);
	}

	public void clearNeighbour() {
		neighbourList.clear();
	}

	public void removeNeighbour(int pos) {
		neighbourList.remove(pos);
	}

	//-------Getters and Setters-------
	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}


	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public List<Integer> getNeighbourList() {
		return neighbourList;
	}
}

