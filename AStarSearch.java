import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Defines an A* search to be performed on a qualifying puzzle. Currently
 * supports 8puzzle and FWGC.
 * 
 * @author Michael Langston && Gabe Ferrer
 */
public class AStarSearch
{
	/**
	 * Initialization function for 8puzzle A*Search
	 * 
	 * @param board
	 *            - The starting state, represented as a linear array of length
	 *            9 forming 3 meta-rows.
	 */

	//by Jose Rivas Felix
	//This method came with the original code, but it was a void method.
	//I made it a SearchNode method to return the results of the
	//depth first search algorithm.
	public static SearchNode search(int[] board, boolean d, char heuristic)
	{
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Queue<SearchNode> q = new LinkedList<SearchNode>();
		q.add(root);
		
		

		int searchCount = 1; // counter for number of iterations

		//by Jose Rivas Felix
		//retrieve the serachNode from the queue without polling it
		//to initialize the temp searchNode.
		SearchNode tempNode = (SearchNode) q.peek();

		while (!q.isEmpty()) // while the queue is not empty
		{
			 tempNode = q.poll();


			 //by Jose Rivas Felix
			 //add searchNode counter here to every node.
			 tempNode.set_node_counter(searchCount);

			 //check if the algorithm to use is the A-Star(out of place).
			 if (heuristic == 'o') {
			 	//by Jose Rivas Felix
			 	//Add the name of the algorithm to every searchNode here.
			 	tempNode.set_algorithm_name("A-Star out of place");

			 }else 
			 {  //by Jose Rivas Felix
			 	//Add the name of the algorithm to every searchNode here.
			 	tempNode.set_algorithm_name("A-Star Manhattan distance"); 
			 }
			

			// if the tempNode is not the goal state
			if (!tempNode.getCurState().isGoal())
			{
				// generate tempNode's immediate successors
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.genSuccessors();
				ArrayList<SearchNode> nodeSuccessors = new ArrayList<SearchNode>();

				/*
				 * Loop through the successors, wrap them in a SearchNode, check
				 * if they've already been evaluated, and if not, add them to
				 * the queue
				 */
				for (int i = 0; i < tempSuccessors.size(); i++)
				{
					SearchNode checkedNode;
					// make the node
					if (heuristic == 'o')
					{

						//tempNode.set_algorithm_name("aso");
						/*
						 * Create a new SearchNode, with tempNode as the parent,
						 * tempNode's cost + the new cost (1) for this state,
						 * and the Out of Place h(n) value
						 */
						checkedNode = new SearchNode(tempNode,
								tempSuccessors.get(i), tempNode.getCost()
										+ tempSuccessors.get(i).findCost(),
								((EightPuzzleState) tempSuccessors.get(i))
										.getOutOfPlace());


					}//end of if-statement to check if the algorithm being used
					//is the A-Star(out of place) to make the respective serachNode.
					else
					{
						// See previous comment
						checkedNode = new SearchNode(tempNode,
								tempSuccessors.get(i), tempNode.getCost()
										+ tempSuccessors.get(i).findCost(),
								((EightPuzzleState) tempSuccessors.get(i))
										.getManDist());

					}//end of else-statement to make searchNode for the 
					//A-Star algorithm(manhattan distance).

					// Check for repeats before adding the new node
					if (!checkRepeats(checkedNode))
					{
						nodeSuccessors.add(checkedNode);
					}
				}//end of for-loop to iterate through the successors.

				// Check to see if nodeSuccessors is empty. If it is, continue
				// the loop from the top
				if (nodeSuccessors.size() == 0)
					continue;

				SearchNode lowestNode = nodeSuccessors.get(0);

				/*
				 * This loop finds the lowest f(n) in a node, and then sets that
				 * node as the lowest.
				 */
				for (int i = 0; i < nodeSuccessors.size(); i++)
				{
					if (lowestNode.getFCost() > nodeSuccessors.get(i)
							.getFCost())
					{
						lowestNode = nodeSuccessors.get(i);
					}
				}

				int lowestValue = (int) lowestNode.getFCost();

				// Adds any nodes that have that same lowest value.
				for (int i = 0; i < nodeSuccessors.size(); i++)
				{
					if (nodeSuccessors.get(i).getFCost() == lowestValue)
					{
						q.add(nodeSuccessors.get(i));
					}
				}

				searchCount++;
			}//end of if-statement to check if the searchNode contains the final state.
			else
			
			{
				//the final state has been found, so break out of the while-loop.
				break;
			}
		}//end of while-loop to search through the queue for the final state.

			//return the serachNode with the final state.
			return tempNode;
	}//end of search method.

	/*
	 * Helper method to check to see if a SearchNode has already been evaluated.
	 * Returns true if it has, false if it hasn't.
	 */
	private static boolean checkRepeats(SearchNode n)
	{
		boolean retValue = false;
		SearchNode checkNode = n;

		// While n's parent isn't null, check to see if it's equal to the node
		// we're looking for.
		while (n.getParent() != null && !retValue)
		{
			if (n.getParent().getCurState().equals(checkNode.getCurState()))
			{
				retValue = true;
			}
			n = n.getParent();
		}

		return retValue;
	}

}//end of AStarSearch class.
