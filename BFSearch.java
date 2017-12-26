import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Defines a Bredth-First search to be performed on a qualifying puzzle.
 * Currently supports 8puzzle and FWGC.
 * 
 * @author Michael Langston && Gabe Ferrer
 */
public class BFSearch
{
	/**
	 * Initialization function for 8puzzle BFSearch
	 * 
	 * @param board
	 *            - The starting state, represented as a linear array of length
	 *            9 forming 3 meta-rows.
	 */

	//by Jose Rivas Felix
	//This method came with the original code, but it was a void method.
	//I made it a SearchNode method to return the results of the
	//depth first search algorithm.
	public static SearchNode search(int[] board, boolean d)
	{
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Queue<SearchNode> queue = new LinkedList<SearchNode>();

		queue.add(root);
		//return the results.
		return performSearch(queue, d);
	}

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

	/**
	 * Performs a BFSearch using q as the search space
	 * 
	 * @param q
	 *            - A SearchNode queue to be populated and searched
	 */


	//by Jose Rivas Felix
	//This method came with the original code, but it was a void method.
	//I made it a SearchNode method to return the results of the
	//depth first search algorithm.
	public static SearchNode performSearch(Queue<SearchNode> q, boolean d)
	{
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
			 //Add the name of the algorithm to every searchNode here.
			 tempNode.set_algorithm_name("Breadth-First Search");

			if (!tempNode.getCurState().isGoal()) // if tempNode is not the goal
													// state
			{
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.genSuccessors(); // generate tempNode's immediate
											// successors

				/*
				 * Loop through the successors, wrap them in a SearchNode, check
				 * if they've already been evaluated, and if not, add them to
				 * the queue
				 */
				for (int i = 0; i < tempSuccessors.size(); i++)
				{
					// second parameter here adds the cost of the new node to
					// the current cost total in the SearchNode
					SearchNode newNode = new SearchNode(tempNode,
							tempSuccessors.get(i), tempNode.getCost()
									+ tempSuccessors.get(i).findCost(), 0);

					if (!checkRepeats(newNode))
					{
						q.add(newNode);
					}
				}
				searchCount++;
			}
			else
				//the final state has been found, so break out of the while-loop.
			{ 
				break;
			}//end of if-statement
		}//end of while-loop to search through the queue for the final state.

		//return the serachNode with the final state.
		return tempNode;
	}//end of performSearch method.


}//end of BFsearch class.
