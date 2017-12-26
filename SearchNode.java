


/*
 *
 *
 *
 * Class to represent a SearchNode. This will be a wrapper for a State, and
 * track the cost to get to that state and the state's parent node.
 * 
 * @author Michael Langston && Gabe Ferrer
 * 
 */
public class SearchNode
{

	private State curState;
	private SearchNode parent;
	private double cost; // cost to get to this state
	private double hCost; // heuristic cost
	private double fCost; // f(n) cost

	//by Jose Rivas Felix
	//added counter for nodes searched
	private int node_count = 0; 
	//searchNodes are to contain the name of the search
	//algorithm the belong to.
	private String name = "";

	/**
	 * Constructor for the root SearchNode
	 * 
	 * @param s
	 *            the state passed in
	 */
	public SearchNode(State s)
	{
		curState = s;
		parent = null;
		cost = 0;
		hCost = 0;
		fCost = 0;
		
	}

	/**
	 * Constructor for all other SearchNodes
	 * 
	 * @param prev
	 *            the parent node
	 * @param s
	 *            the state
	 * @param c
	 *            the g(n) cost to get to this node
	 * @param h
	 *            the h(n) cost to get to this node
	 */
	public SearchNode(SearchNode prev, State s, double c, double h)
	{
		parent = prev;
		curState = s;
		cost = c;
		hCost = h;
		fCost = cost + hCost;
		
	}

	/**
	 * @return the curState
	 */
	public State getCurState()
	{
		return curState;
	}

	/**
	 * @return the parent
	 */
	public SearchNode getParent()
	{
		return parent;
	}

	/**
	 * @return the cost
	 */
	public double getCost()
	{
		return cost;
	}

	/**
	 * 
	 * @return the heuristic cost
	 */
	public double getHCost()
	{
		return hCost;
	}

	/**
	 * 
	 * @return the f(n) cost for A*
	 */
	public double getFCost()
	{
		return fCost;
	}


	//by Jose Rivas Felix
	//void method used to stored the searchNode counter
	//in every searchNode.
	public void set_node_counter(int count) {

		node_count = count;

	}

	//by Jose Rivas Felix 
	//integer method used to retrieved the 
	//current searchNode counter given the 
	//searchNode.
	public int get_node_counter() {
		return node_count;
	}

	//by Jose Rivas Felix
	//void method used to stored the name of the search
	//algorithm that the searchNode belongs to.
	public void set_algorithm_name(String args) {

		name = args;

	}

	//by Jose Rivas Felix
	//string method used to retrieved the name of the search
	//algorithm that the searchNode belongs to.
	public String get_algorithm_name() {

		return name;
	}

}
