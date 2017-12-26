/**************************************************************************
Depth first search algorithm class.






**************************************************************************/


import java.util.ArrayList;
import java.util.Stack;

/**
 * Defines a Depth-First search to be performed on a qualifying puzzle.
 * Currently supports 8puzzle and FWGC.
 * 
 * @author Michael Langston && Gabe Ferrer
 */
public class DFSearch
{
	/**
	 * Initialization function for 8puzzle DFSearch
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
		Stack<SearchNode> stack = new Stack<SearchNode>();

		stack.add(root);

		//return the results.
		return performSearch(stack, d);
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
	 * Performs a DFSearch using q as the search space
	 * 
	 * @param s
	 *            - A SearchNode queue to be populated and searched
	 */


	//by Jose Rivas Felix
	//This method came with the original code, but it was a void method.
	//I made it a SearchNode method to return the results of the
	//depth first search algorithm.
	public static SearchNode performSearch(Stack<SearchNode> s, boolean d)
	{
		int searchCount = 1; // counter for number of iterations

		//extract the the top element from the stack without popping it
		//to initialize the temp searchNode.
		SearchNode tempNode = (SearchNode) s.peek();

		
		while (!s.isEmpty()) // while the queue is not empty
		{
			
			 tempNode = s.pop();


			//by Jose Rivas Felix
			//add search count to nodes here
			tempNode.set_node_counter(searchCount);
			//add the name of the algorithm to each searchNode
			tempNode.set_algorithm_name("Depth-First Search");


			// if tempNode is not the goal state
			if (!tempNode.getCurState().isGoal())
			{
				
				// generate tempNode's immediate successors
				ArrayList<State> tempSuccessors = tempNode.getCurState()
						.genSuccessors();

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
						s.add(newNode);


					}
				}
				searchCount++;


				//by Jose Rivas Felix
				//stop searching if the count grows too large.
				//This means that it probably won't find the answer anyway.
				//Make counter extra large so that the algortihm does not
				//have a chance at winning
				if (searchCount == 30000)
				{
					searchCount = 1000000;
					tempNode.set_node_counter(searchCount);
					//return current searchNode which may or may not contain
					//the final state.
					return tempNode;
				}//end of if-statement to halt the search

			}else{
			//the final state has been found, so break out of the while-loop.	
			break;}
				
		}//end of while-loop to search through the stack of searchNodes.

		//return the current searchNode which contains the final state.
		return tempNode;
	}//end of performSearch method
}//end of DFS class
