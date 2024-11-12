import java.io.*;
import java.util.*;

public class find_route
{
	public static int iNodeCounter = 0;
	public static class Node
	{
		/** Node Attributes*/
		double dDistance = 0;
		double dTotalDistance = 0;
		int iDepth = 0;	
		String sCity = "";
		Node ndParent = null;
		double dHeuristicVal = 0;

		/** Node Constructor*/
		public Node(String cCity, double cDistance, double cTotalDistance, int cDepth, Node cParent)
		{
			sCity = cCity;
			ndParent = cParent;
			dDistance = cDistance;
			dTotalDistance = cTotalDistance;
			iDepth = cDepth;
			iNodeCounter++;
		}

		/** Getters and Setters */
		public double getdHeuristicVal()
		{
			return dHeuristicVal;
		}
		public void setdHeuristicVal(double dHeuristicVal) 
		{
			this.dHeuristicVal = dHeuristicVal;
		}
		public double getiDistance()
		{
			return dDistance;
		}
		public void setiDistance(double dDistance) 
		{
			this.dDistance = dDistance;
		}
		public double getdTotalDistance()
		{
			return dTotalDistance;
		}
		public void setiTotalDistance(double dTotalDistance) 
		{
			this.dTotalDistance = dTotalDistance;
		}
		public int getiDepth()
		{
			return iDepth;
		}
		public void setiDepth(int iDepth)
		{
			this.iDepth = iDepth;
		}
		public String getsCity()
		{
			return sCity;
		}
		public void setsCity(String sCity)
		{
			this.sCity = sCity;
		}
		public Node getNdParent()
		{
			return ndParent;
		}
		public void setNdParent(Node ndParent) 
		{
			this.ndParent = ndParent;
		}
	}

	public static void main(String[] args)
	{
		int iNodeExpansionCounter = 0;
		if(args.length == 0 || (args.length == 1 && args[0].equals("-h")))
		{
			System.out.println("**** You have reached the help section ****");
			System.out.println();
			System.out.println("Please use the below format to execute this application");
			System.out.println();
			System.out.println("To run an Uninformed search : java -jar find_route.jar <input_file> <origin_city> <destination_city>");
			System.out.println();
			System.out.println("To run an Informed search : java -jar find_route.jar <input_file> <origin_city> <destination_city> <heuristic_file>");
			System.out.println();
			System.out.println("<input_file> : A text file containing the details of city and the distance between them like - city1 city2 distance");
			System.out.println("<heuristic_file> : A text file containing the heuristic details of city and the distance like - city distance");
			System.out.println("The text 'END OF FILE' specifies the last line of the text file in both the above files");
			System.out.println();
			System.out.println("<origin_city> : Starting point(initial state) of the search");
			System.out.println("<destination_city> : End point(Goal) of the search");
			System.out.println();
			System.out.println("**** End of the help section ****");
		}
		else if(args.length >= 3)
		{
			boolean isAStarSearch = false;

			/** Reading input arguments*/
			String sInputFile= args[0];
			String sOrigin = args[1];
			String sDestination = args[2];
			String sHeuristicFile = "";

			if(args.length == 4)
			{
				sHeuristicFile = args[3];
				isAStarSearch = true;
			}
			/** Reading input file*/
			Scanner scScanner = null;
			try
			{
				scScanner = new Scanner(new FileReader(sInputFile));
			}
			catch(FileNotFoundException ex)
			{
				ex.printStackTrace();
			}

			Scanner scHeuristicScanner = null;
			HashMap<String, Double> hmHeuristicValues = null;
			if(isAStarSearch)
			{
				try
				{
					scHeuristicScanner = new Scanner(new FileReader(sHeuristicFile));
				}
				catch(FileNotFoundException ex)
				{
					ex.printStackTrace();
				}

				hmHeuristicValues = new HashMap<String, Double>();

				while(scHeuristicScanner.hasNext())
				{
					String sCityName = scHeuristicScanner.next();
					String sHeuristicValue = scHeuristicScanner.next();

					/** Reading till the end of input file*/
					if(!(sCityName.equals("END")) && (!(sHeuristicValue.equals("OF"))))
					{
						try
						{
							hmHeuristicValues.put(sCityName, Double.parseDouble(sHeuristicValue));
						}
						catch(NumberFormatException e)
						{
							/**If the logic reaches this point, then the heuristic file has to be checked for any incorrect values*/
							System.out.println("Please verify the heuristic file. Incorrect distance value specified against "+ sCityName);
						}
					}
					else
					{
						break;
					}
				}
				scHeuristicScanner.close();
			}

			ArrayList<String> alCity1 = new ArrayList<String>();
			ArrayList<String> alCity2 = new ArrayList<String>();
			ArrayList<Double> alDistance = new ArrayList<Double>();

			/** Reading each line of the input file*/
			while(scScanner.hasNext())
			{
				String sCity1 = scScanner.next();
				String sCity2 = scScanner.next();
				String sDistance = scScanner.next(); 

				/** Reading till the end of input file*/
				if(!(sCity1.equals("END")) && (!(sCity2.equals("OF"))) && (!(sDistance.equals("INPUT"))))
				{
					alCity1.add(sCity1);
					alCity2.add(sCity2);
					try
					{
						alDistance.add(Double.parseDouble(sDistance));
					}
					catch(NumberFormatException e)
					{
						/**If the logic reaches this point, then the input file has to be checked for any incorrect values of distance*/
						System.out.println("Please verify the input file. Incorrect distance value specified against "+ sCity1 + " and " + sCity2);
					}
				}
			}
			scScanner.close();

			/** Searching for the Origin and Destination cities */

			LinkedList<Node> llFringe = new LinkedList<Node>();
			ArrayList<String> alTravelled = new ArrayList<String>();


			/** If the Origin city is in city1 list and destination city is in city2 list or both origin and destination cities are in city1 list */
			if((alCity1.contains(sOrigin) && alCity2.contains(sDestination)) || (alCity1.contains(sOrigin) && alCity1.contains(sDestination)))
			{
				try
				{
					Node ndRootNode = new Node(sOrigin, 0, 0, 0, null);
					if(isAStarSearch && hmHeuristicValues.containsKey(sOrigin))
					{
						ndRootNode.setdHeuristicVal(hmHeuristicValues.get(sOrigin) + ndRootNode.getdTotalDistance());
					}
					llFringe.add(ndRootNode);

					/** Expanding root node*/
					/** Size of alCity1 and alCity2 and alDistance are going to be the same*/
					for(int inx = 0; inx < alCity1.size(); inx++)
					{ 
						String sCity1 = alCity1.get(inx);
						if(sCity1.equals(sOrigin))
						{
							Node ndSuccNode = new Node(alCity2.get(inx),alDistance.get(inx), (alDistance.get(inx) + ndRootNode.getiDistance()), ndRootNode.getiDepth() + 1, ndRootNode);
							if(isAStarSearch && hmHeuristicValues.containsKey(ndSuccNode.getsCity()))
							{
								ndSuccNode.setdHeuristicVal(hmHeuristicValues.get(ndSuccNode.getsCity()) + ndSuccNode.getdTotalDistance());
							}
							llFringe.addLast(ndSuccNode);
						}

						String sCity2 = alCity2.get(inx);
						if(sCity2.equals(sOrigin) && !alTravelled.contains(sOrigin))
						{
							Node ndSuccNode = new Node(alCity1.get(inx),alDistance.get(inx), (alDistance.get(inx) + ndRootNode.getiDistance()), ndRootNode.getiDepth() + 1, ndRootNode);
							if(isAStarSearch && hmHeuristicValues.containsKey(ndSuccNode.getsCity()))
							{
								ndSuccNode.setdHeuristicVal(hmHeuristicValues.get(ndSuccNode.getsCity()) + ndSuccNode.getdTotalDistance());
							}
							llFringe.addLast(ndSuccNode);
						}
					}
					iNodeExpansionCounter++;

					/** checking if the destination is already reached*/
					for(Node ndNodeIns : llFringe)
					{
						if(ndNodeIns.getsCity().equals(sDestination))
						{
							System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
							System.out.println("Nodes Generated: "+ iNodeCounter);
							System.out.println("Distance: " + ndNodeIns.getdTotalDistance() + " km");
							System.out.println("route: ");
							System.out.print(sOrigin + " to " + sDestination +", " + ndNodeIns.getdTotalDistance());
							System.exit(0);
						}
					}

					ArrayList<Node> alNodeData = new ArrayList<Node>();
					boolean isGoal = false;

					while(!isGoal)
					{
						alNodeData.add(llFringe.getFirst());

						alTravelled.add(llFringe.getFirst().getsCity());

						if(alTravelled.contains(sDestination))
						{
							isGoal = true;
							continue;
						}
						llFringe.removeFirst();

						if(isAStarSearch)
						{
							/** Sorting the Fringe list based on heuristic and total distance*/
							for(int inx=0; inx < llFringe.size(); inx++)
							{
								for(int jnx=0; jnx < llFringe.size()-inx-1; jnx++)
								{
									if(llFringe.get(jnx).getdHeuristicVal() > llFringe.get(jnx + 1).getdHeuristicVal())
									{
										Node nd1 = llFringe.get(jnx);
										llFringe.set(jnx, llFringe.get(jnx + 1));
										llFringe.set(jnx + 1, nd1);
									}
								}
							}
						}
						else
						{
							/** Sorting the Fringe list based on total distance*/
							for(int inx=0; inx < llFringe.size(); inx++)
							{
								for(int jnx=0; jnx < llFringe.size()-inx-1; jnx++)
								{
									if(llFringe.get(jnx).getdTotalDistance() > llFringe.get(jnx + 1).getdTotalDistance())
									{
										Node nd1 = llFringe.get(jnx);
										llFringe.set(jnx, llFringe.get(jnx + 1));
										llFringe.set(jnx + 1, nd1);
									}
								}
							}
						}

						/** Expanding the branches to find the route */
						Node ndNode1 = llFringe.getFirst();
						for(int inx =0; inx < alCity1.size(); inx++)
						{
							if(alCity1.get(inx).equals(ndNode1.getsCity()) && !(alTravelled.contains(ndNode1.getsCity())))
							{
								if(!alTravelled.contains(alCity2.get(inx)))
								{
									Node ndNode2 = new Node(alCity2.get(inx), alDistance.get(inx), alDistance.get(inx) + (ndNode1.getdTotalDistance()), ndNode1.getiDepth() + 1, ndNode1);
									if(isAStarSearch && hmHeuristicValues.containsKey(ndNode2.getsCity()))
									{
										ndNode2.setdHeuristicVal(hmHeuristicValues.get(ndNode2.getsCity()) + ndNode2.getdTotalDistance());
									}
									llFringe.addLast(ndNode2);				
								}
							}
							if( alCity2.get(inx).equals(ndNode1.getsCity()) && !alTravelled.contains(ndNode1.getsCity()))
							{
								if(!alTravelled.contains(alCity1.get(inx)))
								{
									Node ndNode2 = new Node(alCity1.get(inx), alDistance.get(inx), alDistance.get(inx) + (ndNode1.getdTotalDistance()), ndNode1.getiDepth() + 1, ndNode1);
									if(isAStarSearch && hmHeuristicValues.containsKey(ndNode2.getsCity()))
									{
										ndNode2.setdHeuristicVal(hmHeuristicValues.get(ndNode2.getsCity()) + ndNode2.getdTotalDistance());
									}
									llFringe.addLast(ndNode2);				
								}
							}
						}
						iNodeExpansionCounter++;
					}

					if(isGoal)
					{
						ArrayList<String> alRouteCities = new ArrayList<String>();
						
						Node ndPathNode = null;
						for(Node ndNode : alNodeData)
						{
							if(ndNode.getsCity().equals(sDestination))
							{
								ndPathNode = ndNode;
								System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
								System.out.println("Nodes Generated: "+ iNodeCounter);
								System.out.println("distance: "+ ndNode.getdTotalDistance() + " km");
								break;
							}
						}	
						System.out.println("route:");  

						while(ndPathNode != null)
						{
							alRouteCities.add(ndPathNode.getsCity());
							ndPathNode = ndPathNode.getNdParent();
						}
						
						Collections.reverse(alRouteCities);

						for(int inx = 0; inx < alRouteCities.size()-1; inx++)
						{
							for(int jnx =0; jnx < alCity1.size() ; jnx++)
							{
								if( alCity1.get(jnx).equals(alRouteCities.get(inx)) && alCity2.get(jnx).equals(alRouteCities.get(inx+1)))
								{
									System.out.println(alCity1.get(jnx) + " to "+ alCity2.get(jnx)+", " +  alDistance.get(jnx) + "km");									
								}
								if( alCity2.get(jnx).equals(alRouteCities.get(inx)) && alCity1.get(jnx).equals(alRouteCities.get(inx+1)))
								{
									System.out.println(alCity2.get(jnx) + " to "+ alCity1.get(jnx)+", " + alDistance.get(jnx) + "km");	
								}
							}
						}
					}
					else if(!isGoal)
					{
						System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
						System.out.println("Nodes Generated: "+ iNodeCounter);
						System.out.println("distance: infinity\nroute:\nnone");
					}
				}
				catch(NoSuchElementException ex)
				{
					System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
					System.out.println("Nodes Generated: "+ iNodeCounter);
					System.out.println("distance: infinity\nroute:\nnone");
				}
			}
			else if((alCity2.contains(sOrigin) && alCity1.contains(sDestination)) || (alCity2.contains(sOrigin) && alCity2.contains(sDestination)))
			{
				try
				{
					Node ndRootNode = new Node(sOrigin, 0, 0, 0, null);
					if(isAStarSearch && hmHeuristicValues.containsKey(sOrigin))
					{
						ndRootNode.setdHeuristicVal(hmHeuristicValues.get(sOrigin) + ndRootNode.getdTotalDistance());
					}
					llFringe.add(ndRootNode);

					for(int inx = 0; inx < alCity2.size(); inx++)
					{
						String sCity1 = alCity2.get(inx);
						if(sCity1.equals(sOrigin))
						{
							Node ndSuccNode = new Node(alCity1.get(inx),alDistance.get(inx), (alDistance.get(inx) + ndRootNode.getiDistance()), ndRootNode.getiDepth() + 1, ndRootNode);
							if(isAStarSearch && hmHeuristicValues.containsKey(ndSuccNode.getsCity()))
							{
								ndSuccNode.setdHeuristicVal(hmHeuristicValues.get(ndSuccNode.getsCity()) + ndSuccNode.getdTotalDistance());
							}
							llFringe.addLast(ndSuccNode);
						}

						String sCity2 = alCity1.get(inx);
						if(sCity2.equals(sOrigin) && !alTravelled.contains(sOrigin))
						{
							Node ndSuccNode = new Node(alCity2.get(inx),alDistance.get(inx), (alDistance.get(inx) + ndRootNode.getiDistance()), ndRootNode.getiDepth() + 1, ndRootNode);
							if(isAStarSearch && hmHeuristicValues.containsKey(ndSuccNode.getsCity()))
							{
								ndSuccNode.setdHeuristicVal(hmHeuristicValues.get(ndSuccNode.getsCity()) + ndSuccNode.getdTotalDistance());
							}
							llFringe.addLast(ndSuccNode);
						}
					}
					iNodeExpansionCounter++;

					for(Node ndNodeIns : llFringe)
					{
						if(ndNodeIns.getsCity().equals(sDestination))
						{
							System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
							System.out.println("Nodes Generated: "+ iNodeCounter);
							System.out.println("Distance: " + ndNodeIns.getdTotalDistance() + " km");
							System.out.println("route: ");
							System.out.print(sOrigin + " to " + sDestination +", " + ndNodeIns.getdTotalDistance());
							System.exit(0);
						}
					}

					ArrayList<Node> alNodeData = new ArrayList<Node>();
					boolean isGoal = false;

					while(!isGoal)
					{
						//Dequeue root
						alNodeData.add(llFringe.getFirst());

						alTravelled.add(llFringe.getFirst().getsCity());

						if(alTravelled.contains(sDestination))
						{
							isGoal = true;
							continue;
						}
						llFringe.removeFirst();

						if(isAStarSearch)
						{
							/** Sorting the Fringe list based on heuristic and total distance*/
							for(int inx=0; inx < llFringe.size(); inx++)
							{
								for(int jnx=0; jnx < llFringe.size()-inx-1; jnx++)
								{
									if(llFringe.get(jnx).getdHeuristicVal() > llFringe.get(jnx + 1).getdHeuristicVal())
									{
										Node nd1 = llFringe.get(jnx);
										llFringe.set(jnx, llFringe.get(jnx + 1));
										llFringe.set(jnx + 1, nd1);
									}
								}
							}
						}
						else
						{
							/** Sorting the Fringe list based on total distance*/
							for(int inx=0; inx < llFringe.size(); inx++)
							{
								for(int jnx=0; jnx < llFringe.size()-inx-1; jnx++)
								{
									if(llFringe.get(jnx).getdTotalDistance() > llFringe.get(jnx + 1).getdTotalDistance())
									{
										Node nd1 = llFringe.get(jnx);
										llFringe.set(jnx, llFringe.get(jnx + 1));
										llFringe.set(jnx + 1, nd1);
									}
								}
							}
						}

						/** Expanding the branches to find the route */
						Node ndNode1 = llFringe.getFirst();
						for(int inx =0; inx < alCity1.size(); inx++)
						{
							if(alCity1.get(inx).equals(ndNode1.getsCity()) && !(alTravelled.contains(ndNode1.getsCity())))
							{
								if(!alTravelled.contains(alCity2.get(inx)))
								{
									Node ndNode2 = new Node(alCity2.get(inx), alDistance.get(inx), alDistance.get(inx) + (ndNode1.getdTotalDistance()), ndNode1.getiDepth() + 1, ndNode1);
									if(isAStarSearch && hmHeuristicValues.containsKey(ndNode2.getsCity()))
									{
										ndNode2.setdHeuristicVal(hmHeuristicValues.get(ndNode2.getsCity()) + ndNode2.getdTotalDistance());
									}
									llFringe.addLast(ndNode2);				
								}
							}
							if( alCity2.get(inx).equals(ndNode1.getsCity()) && !alTravelled.contains(ndNode1.getsCity()))
							{
								if(!alTravelled.contains(alCity1.get(inx)))
								{
									Node ndNode2 = new Node(alCity1.get(inx), alDistance.get(inx), alDistance.get(inx) + (ndNode1.getdTotalDistance()), ndNode1.getiDepth() + 1, ndNode1);
									if(isAStarSearch && hmHeuristicValues.containsKey(ndNode2.getsCity()))
									{
										ndNode2.setdHeuristicVal(hmHeuristicValues.get(ndNode2.getsCity()) + ndNode2.getdTotalDistance());
									}
									llFringe.addLast(ndNode2);				
								}
							}
						}
						iNodeExpansionCounter++;
					}


					if(isGoal)
					{
						ArrayList<String> alRouteCities = new ArrayList<String>();
						
						Node ndPathNode = null;
						
						for(Node ndNode : alNodeData)
						{
							if(ndNode.getsCity().equals(sDestination))
							{
								ndPathNode = ndNode;
								System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
								System.out.println("Nodes Generated: "+ iNodeCounter);
								System.out.println("distance: "+ ndNode.getdTotalDistance() + " km");
								break;
							}
						}	
						System.out.println("route:");  

						while(ndPathNode != null)
						{
							alRouteCities.add(ndPathNode.getsCity());
							ndPathNode = ndPathNode.getNdParent();
						}
						Collections.reverse(alRouteCities);

						for(int inx = 0; inx < alRouteCities.size()-1; inx++)
						{
							for(int jnx =0; jnx < alCity1.size() ; jnx++)
							{
								if( alCity1.get(jnx).equals(alRouteCities.get(inx)) && alCity2.get(jnx).equals(alRouteCities.get(inx+1)))
								{
									System.out.println(alCity1.get(jnx) + " to "+ alCity2.get(jnx)+", " +  alDistance.get(jnx) + "km");									
								}
								if( alCity2.get(jnx).equals(alRouteCities.get(inx)) && alCity1.get(jnx).equals(alRouteCities.get(inx+1)))
								{
									System.out.println(alCity2.get(jnx) + " to "+ alCity1.get(jnx)+", " + alDistance.get(jnx) + "km");	
								}
							}
						}
					}
					else if(!isGoal)
					{
						System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
						System.out.println("Nodes Generated: "+ iNodeCounter);
						System.out.println("distance: infinity\nroute:\nnone");
					}
				}
				catch(NoSuchElementException ex)
				{
					System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
					System.out.println("Nodes Generated: "+ iNodeCounter);
					System.out.println("distance: infinity\nroute:\nnone");
				}
			}
			else
			{
				System.out.println("Nodes Expanded: "+ iNodeExpansionCounter);
				System.out.println("Nodes Generated: "+ iNodeCounter);
				System.out.println("distance: infinity\nroute:\nnone");
			}
		}
		else
		{
			/** If the logic reaches this point, then the command line argument provided to run the program is incorrect*/
			System.out.println("Incorrect arguments passed in the command line. Please pass the arguments in the format,");
			System.out.println("find_route input_filename origin_city destination_city heuristic_filename");
			System.out.println("heuristic_filename is an optional argument");
		}
	}

} 

