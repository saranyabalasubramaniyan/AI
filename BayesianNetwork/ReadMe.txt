Name: Saranya Balasubramaniyan
UTA ID: 1001909644
Programming language : Java

Omega Compatible:No
 
Code Structure:
Implemented a probability calculator for the Bayesian Network establishing relations between events on burglary-earthquake-alarm domain
Providing the combination of events as an input would yield the probabilityof the event based on the JDT given in the assignment
	1. Read the command line and read the argument, display help if any error exists in the input arguments.
	2. Analyse the input arguments to see if a conditional probability is requested.
	3. If conditional probabbility is asked, with the "given" tag in the input, then formula, P(A|B) = P(A,B)/P(B) is implemented
	4. In such case, probabbility of numerator and denominator are calculated separately and then the final value is computed.
	5. Else, all the input paramters are taken into consideration for calculation of probability.
	6. In both the above cases, computeProbability mehod is called. 
	7. If all the events are specified in the input, then the probability is calculated based on the Bayesian formals, P(X1,...,Xn) = Product of [P(X1| Parent of X1)....P(Xn| Parent of Xn)
	8. In case there are missing attributes, enumeration by inference is implemented and all the values are summed up to give the probability.
	9. The program has implemented Bayesian Formula, formula for conditional probability and Enumeration by Inference.
	 
Compilation instruction:
	javac bnet.java

How to run:
	jar cvfe bnet.jar bnet *.class
	
	java -jar bnet.jar <event1><value1> <event2><value2> [given] [<event3><value3>]
	eg: java -jar bnet.jar Bt Af given Mf
	java -jar bnet.jar Af Et
