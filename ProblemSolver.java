/************************************************************************************
 *
 * This class represents the user's interface, retrieves results
 * from the other classes that process the initial state input, 
 * and it also calculates who the winner is and outputs the results.
 *
 * Whatever was contributed by me will be marked (Jose Rivas Felix) and
 * commented for explanatory purposes.
 *
 *
 * There were some of the initial functionality methods that were deleted in order to 
 * accomplished the desired result, those deletions won't be noted or explained here
 * to avoid any confusion.
 **********************************************************************************/









import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Scanner;


public class ProblemSolver
{

	/*
	 * We expect arguments in the form:
	 * 
	 * ./ProblemSolver <-d> <initial state> 
	 * 
	 * Example: ./ProblemSolver -d 0 1 2 3 4 5 6 7 8
	 * 
	 * See Readme for more information.
	 */
	public static void main(String[] args)
	{
		// Numbers to be adjusted if the debug toggle is present, as components
		// of args will be in different locations if it is.



/**********************************************************************************/
		//by Jose Rivas Felix

		// Print out correct usage and end the program if there aren't the
			// right amount of parameters
			if (args.length < 10)
			{
				printUsage();
			}

		//my code added to detect initial given states that
		//do not have a solution
		boolean is_solvable = false;

		//read the given initial state to solve (which starts at location 1
		// in the args array) using the method "solution_detector" to determine
		//whether or not this initial state is solvable.
		is_solvable = solution_detector(args, 1);

		//Check if the initial given state is solvable.
		//If not, let the user know.
		//If yes, find results.
		if (is_solvable)
		{

			//by Jose Rivas Felix
			//check if the initial given state is the same as the 
			//goal state, if so then there is not point in doing any computations
			//so the program will just let the user know the he/she should try
			//a different initial state.
			if (is_same(args, 1))
			{
				//message for the end user
				System.out.println("\nThe initial state you have provided is the " +
					"same as the goal state. \n Try a different initial state!");

				//halt
				System.exit(-1);
			}

			//integer to store the index number for the beginning of the initial 
			//passed in the user's input array.
			int eightPuzzleDebug = 1;
			boolean debug = false;

			

			// Check for debug toggle
			if (args[0].equals("-d"))
			{	//
				eightPuzzleDebug = 1;
				debug = true;
				
			}
			//retrieve initial state from the user's input.
			int[] startingStateBoard = dispatchEightPuzzle(args,
												eightPuzzleDebug);

			//by Jose Rivas Felix
			//create searchnode array to store the results from the 
			//4 algorithms.
			SearchNode[] results = new SearchNode[4];

			//by Jose Rivas Felix
			//SearchNode dfs_results 
			results[0] = (SearchNode) DFSearch.search(startingStateBoard, debug);

			//by Jose Rivas Felix
			//SearchNode bfs_results 
			results[1] = (SearchNode) BFSearch.search(startingStateBoard, debug);
		
			//by Jose Rivas Felix
			//SearchNode aso_results 
			results[2] = (SearchNode) AStarSearch.search(startingStateBoard, debug, 'o');
			
			//by Jose Rivas Felix
			//SearchNode asm_results 
			results[3] = (SearchNode) AStarSearch.search(startingStateBoard, debug, 'm');


			//by Jose Rivas Felix
			//benchmark: the least amount of search nodes used by some of the
			//algorithms.
			int lowest_search = least_nodes_searched(results);

			//by Jose Rivas Felix
			//how many winners
			//retrieve the number of winners
			int list_size_of_winners = how_many_winners(results, lowest_search);

			
			//by Jose Rivas Felix
			//create array of winners
			SearchNode[] winning_list = new SearchNode[list_size_of_winners];

			winning_list = winner(results);

			//by Jose Rivas Felix
			//print the name of the winners
			System.out.println("And the winner(s): ");
			for (int i = 0; i < winning_list.length; i++)
			{
				System.out.println(winning_list[i].get_algorithm_name() + " algorithm.");
				System.out.println();

			}
				

			//by Jose Rivas Felix
			//ask the end user if she/he would like to see the winner's solution.
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.println("Would you like to see the solution(y/n): ");
			String answer_user = reader.next();

			if (answer_user.equals("y"))
			{	
				print_result(winning_list);
			}


			//by Jose Rvas Felix
			//ask the end user if she/he would like to see the rest of the solutions.
			Scanner reader2 = new Scanner(System.in);  // Reading from System.in
			System.out.println("Would you like to see all the solutions(y/n): ");
			String answer_user2 = reader2.next();

			if (answer_user2.equals("y"))
			{
				print_result(results);
			}


		
	 	}else{
	 			//The initial given state in not solvable, so let the user know by outputting a
	 			//message saying so.
	 			System.out.println("The initial state you have provided is not solvable!");
	 			System.exit(-1);
	 	}//end of if-statement to check if there is
	 			//a solution to the initial given state
	

	}//end of main method



	//by Jose Rivas Felix
	// Helper method to print a meesage to let the usewr know that the input
	//format is wrong.
	private static void printUsage()
	{
		System.out.println("Incorrect input format!");
		System.exit(-1);
	}

	//
	// Helper method to build our initial 8-puzzle state passed in through args
	private static int[] dispatchEightPuzzle(String[] a, int d)
	{
		int[] initState = new int[9];
		// i -> loop counter
		for (int i = d; i < a.length; i++)
		{
			initState[i - d] = Integer.parseInt(a[i]);
		}
		return initState;
	}



	//by Jose Rivas Felix
	//method that contains the algorithm to detect unsolvable patterns
	//(initial given states that are not solvable by the system).
	public static boolean solution_detector(String[] args, int start)
	{

		int[] temp_initial_puzzle = new int[9];

		//get the user's given initial puzzle
		//into the temporary puzzle.
		//For this particular method we don't need the
		//zero/blank tile, so we won't include it.
		for (int i = start; i < args.length; i++) 
		{	
				temp_initial_puzzle[i - start] = Integer.parseInt(args[i]);
		   		 
		}//end of for-loop


		//test output.
		//uncomment to see the parsed initial state that will
		//be used to determine if the initial given state
		//has a solution.
		/*for (int i = 0; i < 9; i++) {
			System.out.println(temp_initial_puzzle[i] + " ");
		}*/

		//In this next step we need to check if the initial 
		//puzzle provided by the end-user is solvable.
		//We will accomplish thi by using a method
		//that keeps track of the number of tiles, in front
		//of the current tile being examined, thet are smaller
		//than the current tile.
		//If the final sum of the counting results in an odd
		//number, then the puzzle is not solvable, otherwise
		//(if it is even) it is.

		//counter to add how many inversions there are in the initial given state.
		int counter = 0;

		//for-loop to iterate through the array of the initial given state
		//Two nested for-loops are used here to compare if the value of a
		//given item is bigger/smaller than values in front of it.
		for (int i = 0; i < temp_initial_puzzle.length - 1; i++)
		{

			for (int j = i + 1; j < temp_initial_puzzle.length; j++)
			{
					
				if (temp_initial_puzzle[j] != 0)
				{
						
					if (temp_initial_puzzle[i] > temp_initial_puzzle[j])
					{

						counter++;

					}//end of if-statement to count incersions

				}//end of if-statement to disregard the item in the array
				//that is equals to zero (which is the empty tile).	
					

			}//end of the inside-most for-loop

		}//end of the outter-most for-loop

			//test output
			//uncomment to see the caunter result
			//System.out.println(counter);

		//check if the result is either odd or even
		//odd:not solvable   even:solvable
		if ((counter % 2) == 0)
		{
			return true;
		}else {
			return false;
		}

	}//end of solution_detector method


	//by Jose Rivas Felix
	//bollean method to detect if the initial given state is the
	//same as the goal state.
	public static boolean is_same (String [] args, int start)
	{
		//goal state array to compare with the initial given state.
		int[] goal_state = new int [] {1,2,3,4,5,6,7,8,0};

		//temporary integer array for the initial given state.
		int[] temp_initial_puzzle = new int[9];

		//get the user's given initial puzzle
		//into the temporary puzzle.
		//For this particular method we don't need the
		//zero/blank tile, so we won't include it.
		for (int i = start; i < args.length; i++) 
		{	
				temp_initial_puzzle[i - start] = Integer.parseInt(args[i]);
		   		 
		}//end of for-loop

		//for-loop to do the comparison between the initial given state 
		//and the goal state.
		for (int j = 0; j < 9; j++)
		{
			//check if the if every element from the initial given 
			//state srray is the same as the element is the goal
			//state array.
			if (!(temp_initial_puzzle[j] == goal_state[j]))
			{
				//element not the same, so return false.
				return false;
			}

		}//end of for-loop to do the comparisons

		//every element in both arrays were quals, so return true.
		return true;

	}//end of is_same method


	//by Jose Rivas Felix
	//method to find the smallest number of nodes searched
	//by either of the algorithms
	public static int least_nodes_searched(SearchNode[] args)
	{

		int num_nodes_searched = args[0].get_node_counter();

		for (int i = 1; i < args.length; i++) 
		{
			if (args[i].get_node_counter() < num_nodes_searched) 
			{
				num_nodes_searched = args[i].get_node_counter();
			}

		}

		return num_nodes_searched;

	}//end of least_nodes_searched


	//by Jose Rivas Felix
	//method to determine how many winners (algortihms) are there by 
	//comparing the least number of nodes found previously with the
	//total number of nodes searched by the algorithms.
	//This result will be used to create the size of the array containing the
	//winning algorithm(s).
	public static int how_many_winners(SearchNode[] args, int win_num) {

		int counter = 0;

		for (int i = 0; i < args.length; i++) 
		{

			if (args[i].get_node_counter() == win_num) 
			{
				counter++;
			}
		}
		return counter;

	}//end of how_many_winners



	//by Jose Rivas Felix
	//function to find the winner(s).
	//This method returns an array containing the winning
	//algorithm(s).
	public static SearchNode[] winner(SearchNode[] args)
	 {

		int counter = 0;

		int bench_mark = least_nodes_searched(args);

		int num_of_winners = how_many_winners(args, bench_mark);

		
		SearchNode[] winning_algorithms = new SearchNode[num_of_winners];

		for (int i = 0; i < args.length; i++)
		 {
			if (args[i].get_node_counter() == bench_mark) 
			{

				winning_algorithms[counter] = args[i];

				++counter;
			}//end of if-statement
		}//end of for-loop

		return winning_algorithms;

	}//end of winner



	//by Jose Rivas Felix
	//method to print winners
	public static void print_result(SearchNode[] args)
	{

		System.out.println("Algorithm with less searches performed: ");
		System.out.println();
		

		Stack<SearchNode> solutionPath = new Stack<SearchNode>();

		for (int i = 0; i < args.length; i++) 
		{
			solutionPath.push(args[i]);
			args[i] = args[i].getParent();

			while (args[i].getParent() != null)
			{
				solutionPath.push(args[i]);
				args[i] = args[i].getParent();
			}
				
			solutionPath.push(args[i]);

			int loopSize = solutionPath.size();

			SearchNode tempNode = (SearchNode) solutionPath.peek();

			System.out.println(tempNode.get_algorithm_name() + " algorithm: ");
			System.out.println();

			for (int j = 0; j < loopSize; j++) 
			{	
				tempNode = solutionPath.pop();
				tempNode.getCurState().printState();
				System.out.println();
				System.out.println();
			}//end of inner most for-loop
					
			System.out.println("The cost was: " + tempNode.getCost());

			System.out.println();
					
			System.out.println("The number of nodes examined: "
								+ tempNode.get_node_counter());
			System.out.println();
			System.out.println();



		}//End of outter-most for-loop



	}//end of print_result method



}//end of problemSolver class
