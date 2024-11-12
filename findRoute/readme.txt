Name: Saranya Balasubramaniyan
UTA ID: 1001909644
Programming language : Java

Omega Compatible:No
 
Code Structure:
	1. Read the command line and confirm if uninformed or informed search has to be performed.
	2. Read the input files and store them in respective Collections.
	3. Search for the initial state(origin city) in the Collections and create a root node.
	4. Add the root node to the fringe.
	5. Expand the node and find the successor cities and create nodes for them.
	6. Add the successor nodes to the fringe
	7. Check if any of these successor nodes are the goal(destination city)
	8. If yes, terminate the program and print details of node creation, expansion, distance and route taken.
	9. Else, Remove the first node from the fringe and sort the fringe.
`	10. In case of uninformed search, sort will happen based on the total distance.
	11. In case of Informed search, sort will be done based on the sum of total distance and heuristic value.
	12. Take first node from the sorted fringe and expand. Repeat from Step 5.
	13. If a path cannot be found, a distance of infinity would be printed for the same.
	14. If an incorrect city name is provided as input, a distance of infinity would be printed.
	15. In case, the command line arguments are incorrect, a help section will be printed.

	In this implementation, UCS is designed to behave optimally in such a way that, whenever a new node is generated,
before adding to the fringe, it will be verified against the closed set if the state in the node is already travelled. 


Compilation instruction:
	javac find_route.java

How to run:
	jar cvfe find_route.jar find_route *.class

	Uninformed Search
	java -jar find_route.jar input1.txt city1 city2
	eg: java -jar find_route.jar input1.txt Bremen Kassel

	Informed Search
	java -jar find_route.jar input1.txt city1 city2 heuristicfile.txt
	eg: java -jar find_route.jar input1.txt Bremen Kassel h_kassel.txt