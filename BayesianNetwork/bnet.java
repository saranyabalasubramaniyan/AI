/***************************************************************************
 * 							AI - Optional Assignment 1
 * Author: 				Saranya Balasubramaniyan
 * Course and section : CSE5360 - 900
 * Net ID: 				sxb9644
 * 
 * The below class is a probability calculator for the Bayesian Network 
 * establishing relations between events on burglary-earthquake-alarm domain
 * Providing the combination of events as an input would yield the probability
 * of the event based on the JDT given in the assignment
 * 
 * The values of joint probability distribution table as in the task description
 * are hardcoded in the program. The program is built to behave in such a way that 
 * the parent child relation between the classes are kept in consideration
 * during the implementation to avoid overhead
 ****************************************************************************/


import java.util.ArrayList;
import java.util.HashMap;

public class bnet 
{
	/** Mapping for the input parameter characters*/
	public static final String BURGLARY = "B";
	public static final String EARTHQUAKE = "E";
	public static final String ALARM = "A";
	public static final String JOHNCALLS = "J";
	public static final String MARYCALLS = "M";

	/**Mapping for the input argument values*/
	public static final String TRUESTR = "t";
	public static final String FALSESTR = "f";

	/**Probability values from the Joint distribution table in the task*/
	public static double P_B = 0.001;
	public static double P_E = 0.002;
	public static HashMap<Boolean, HashMap<Boolean, Double>> alarmProbMap = new HashMap<>();
	public static HashMap<Boolean, Double> johncallsProbMap = new HashMap<>();
	public static HashMap<Boolean, Double> marycallsProbMap = new HashMap<>();

	public static ArrayList<String> containerList = new ArrayList<>();
	public static String inputArgString = "";

	public static void main(String args[])
	{
		if(args != null && args.length >= 1 && args.length <=6)
		{
			/** Loading the probability values in the map to read them during calculation*/
			loadMaps();  
			
			/** Map that holds the events for which probability has to be calculated*/
			HashMap<String, String> inputMap = new HashMap<>();
			/** Map that holds the conditional events*/
			HashMap<String, String> givenMap = new HashMap<>();
			boolean isGivenArg = false;
			for(String arg:args)
			{
				if(arg != null)
				{
					if(arg.equals("given"))
					{
						inputArgString = inputArgString + "|";
						isGivenArg = true;
						continue;
					}

					else
					{
						if(arg.length() == 2)
						{
							inputArgString = " " +inputArgString + arg;
							inputMap.put(String.valueOf(arg.charAt(0)), String.valueOf(arg.charAt(1)));
							if(isGivenArg)
							{
								givenMap.put(String.valueOf(arg.charAt(0)), String.valueOf(arg.charAt(1)));
							}
						}
						else
						{
							System.out.println("Incorrect  argument passed : " + arg);
							printHelp();
							return;
						}
					}
				}
			}

			if(inputMap != null && inputMap.size() > 0)
			{
				/** Computing the probability of the events specified in the input argument*/
				containerList = new ArrayList<>();
				double numerator = computeProbability(inputMap);
				double denominator = 1;
				
				/** In case of conditional probability, the conditional probability formula is implemented
				 * P(A| B) = P(A, B)/P(B)
				 */
				if(givenMap.size() > 0)
				{
					containerList = new ArrayList<>();
					denominator = computeProbability(givenMap);
				}
				
				/*System.out.println("Numerator : " + String.format("%.15f", numerator));
				System.out.println("Denominator : " + String.format("%.15f", denominator));*/
				
				double jointProbability = numerator/denominator;
				System.out.println("P(" + inputArgString + ") = " + String.format("%.15f", jointProbability));
				/** end of the computation*/
			}
			else
			{
				System.out.println("Incorrect arguments passed");
				printHelp();
				return;
			}
		}
		else
		{
			System.out.println("Incorrect number of arguments passed");
			printHelp();
			return;
		}
	}

	/**
	 * Compute Probability - The method calculates the probability of the given event
	 * Implementing Enumeration by Inference
	 * If all the classes are present in the input, then the probability is computed
	 * based on the values specified in the input argument.
	 * If there are any missing attributes, then the sum of all possible combination 
	 * of missing attributes is performed
	 * This method calls the Bayesian calculator internally to compute each of the
	 * combination of events.
	 * This method covers all possible combination of attributes for 5 events
	 * @param inputMap Map containing the input events for calculating probability
	 * @return computed probability value
	 */
	public static double computeProbability(HashMap<String, String> inputMap)
	{
		/** Processing the input list so as to read the input parameters*/
		HashMap<String, ArrayList<Boolean>> computationMap = new HashMap<>();

		boolean burglary = false;
		ArrayList<Boolean> burgList = new ArrayList<>();
		if(inputMap.containsKey(BURGLARY))
		{
			if(inputMap.get(BURGLARY).equals(TRUESTR))
			{
				burglary = true;
			}
			else if(inputMap.get(BURGLARY).equals(FALSESTR))
			{
				burglary = false;
			}

			burgList.add(burglary);
		}
		else
		{
			burgList.add(true);
			burgList.add(false);
		}
		computationMap.put(BURGLARY, burgList);

		ArrayList<Boolean> equakeList = new ArrayList<>();
		boolean earthquake = false;
		if(inputMap.containsKey(EARTHQUAKE))
		{
			if(inputMap.get(EARTHQUAKE).equals(TRUESTR))
			{
				earthquake = true;
			}
			else if(inputMap.get(EARTHQUAKE).equals(FALSESTR))
			{
				earthquake = false;
			}
			equakeList.add(earthquake);
		}
		else
		{
			equakeList.add(true);
			equakeList.add(false);
		}
		computationMap.put(EARTHQUAKE, equakeList);

		ArrayList<Boolean> alarmList = new ArrayList<>();
		boolean alarm = false;
		if(inputMap.containsKey(ALARM))
		{
			if(inputMap.get(ALARM).equals(TRUESTR))
			{
				alarm = true;
			}
			else if(inputMap.get(ALARM).equals(FALSESTR))
			{
				alarm = false;
			}
			alarmList.add(alarm);
		}
		else
		{
			alarmList.add(true);
			alarmList.add(false);
		}
		computationMap.put(ALARM, alarmList);

		ArrayList<Boolean> johncallsList = new ArrayList<>();
		boolean johncalls = false;
		if(inputMap.containsKey(JOHNCALLS))
		{
			if(inputMap.get(JOHNCALLS).equals(TRUESTR))
			{
				johncalls = true;
			}
			else if(inputMap.get(JOHNCALLS).equals(FALSESTR))
			{
				johncalls = false;
			}
			johncallsList.add(johncalls);
		}
		else
		{
			johncallsList.add(true);
			johncallsList.add(false);
		}
		computationMap.put(JOHNCALLS, johncallsList);

		ArrayList<Boolean> marycallsList = new ArrayList<>();
		boolean marycalls = false;
		if(inputMap.containsKey(MARYCALLS))
		{
			if(inputMap.get(MARYCALLS).equals(TRUESTR))
			{
				marycalls = true;
			}
			else if(inputMap.get(MARYCALLS).equals(FALSESTR))
			{
				marycalls = false;
			}
			marycallsList.add(marycalls);
		}
		else
		{
			marycallsList.add(true);
			marycallsList.add(false);
		}
		computationMap.put(MARYCALLS, marycallsList);

		//Case 1
		//If all the attributes are given, then on implementing the Bayesian formula, need to multiply each value given parents.
		if(inputMap.size() == 5)
		{
			return computeProbability(burglary, earthquake, alarm, johncalls, marycalls);
		}

		else if(inputMap.size() < 5)
		{
			ArrayList<Double> computedValues = new ArrayList<>();

			//Initial
			boolean bBurglary = computationMap.get(BURGLARY).get(0);
			boolean bEarthquake = computationMap.get(EARTHQUAKE).get(0);
			boolean bAlarm = computationMap.get(ALARM).get(0);
			boolean bJohnCalls = computationMap.get(JOHNCALLS).get(0);
			boolean bMaryCalls = computationMap.get(MARYCALLS).get(0);

			computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));

			if(!inputMap.containsKey(BURGLARY))
			{
				bBurglary = computationMap.get(BURGLARY).get(1);
				bEarthquake = computationMap.get(EARTHQUAKE).get(0);
				bAlarm = computationMap.get(ALARM).get(0);
				bJohnCalls = computationMap.get(JOHNCALLS).get(0);
				bMaryCalls = computationMap.get(MARYCALLS).get(0);
				computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));		 
			}

			if(!inputMap.containsKey(EARTHQUAKE))
			{
				bBurglary = computationMap.get(BURGLARY).get(0);
				bEarthquake = computationMap.get(EARTHQUAKE).get(1);
				bAlarm = computationMap.get(ALARM).get(0);
				bJohnCalls = computationMap.get(JOHNCALLS).get(0);
				bMaryCalls = computationMap.get(MARYCALLS).get(0);
				computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));

				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
			}

			if(!inputMap.containsKey(ALARM))
			{
				bBurglary = computationMap.get(BURGLARY).get(0);
				bEarthquake = computationMap.get(EARTHQUAKE).get(0);
				bAlarm = computationMap.get(ALARM).get(1);
				bJohnCalls = computationMap.get(JOHNCALLS).get(0);
				bMaryCalls = computationMap.get(MARYCALLS).get(0);
				computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));

				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}

				if(!inputMap.containsKey(EARTHQUAKE))
				{
					bEarthquake = computationMap.get(EARTHQUAKE).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
			}

			if(!inputMap.containsKey(JOHNCALLS))
			{
				bBurglary = computationMap.get(BURGLARY).get(0);
				bEarthquake = computationMap.get(EARTHQUAKE).get(0);
				bAlarm = computationMap.get(ALARM).get(0);
				bJohnCalls = computationMap.get(JOHNCALLS).get(1);
				bMaryCalls = computationMap.get(MARYCALLS).get(0);
				computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));

				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}

				if(!inputMap.containsKey(EARTHQUAKE))
				{
					bEarthquake = computationMap.get(EARTHQUAKE).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}

				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(EARTHQUAKE))
				{
					bEarthquake = computationMap.get(EARTHQUAKE).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
			}
			if(!inputMap.containsKey(MARYCALLS))
			{
				bBurglary = computationMap.get(BURGLARY).get(0);
				bEarthquake = computationMap.get(EARTHQUAKE).get(0);
				bAlarm = computationMap.get(ALARM).get(0);
				bJohnCalls = computationMap.get(JOHNCALLS).get(0);
				bMaryCalls = computationMap.get(MARYCALLS).get(1);
				computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));

				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}

				if(!inputMap.containsKey(EARTHQUAKE))
				{
					bEarthquake = computationMap.get(EARTHQUAKE).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}

				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));	
				}

				if(!inputMap.containsKey(EARTHQUAKE))
				{
					bEarthquake = computationMap.get(EARTHQUAKE).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(JOHNCALLS))
				{
					bJohnCalls = computationMap.get(JOHNCALLS).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(EARTHQUAKE))
				{
					bEarthquake = computationMap.get(EARTHQUAKE).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(1);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(ALARM))
				{
					bAlarm = computationMap.get(ALARM).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
				if(!inputMap.containsKey(BURGLARY))
				{
					bBurglary = computationMap.get(BURGLARY).get(0);
					computedValues.add(computeProbability(bBurglary, bEarthquake, bAlarm, bJohnCalls, bMaryCalls));
				}
			}
			if(computedValues != null && computedValues.size() > 0)
			{
				double jointProb = 0;
				for(Double prob:computedValues)
				{
					jointProb = jointProb + prob;
				}
				return jointProb;
			}
		}
		return 0;
	}

	/**
	 * The method reads each of the input and gets the corresponding probability value from the
	 * Joint distribution table. Multiplies them to compute the probability and return the value
	 * @param burglary boolean true or false based on input or from missing attribute calculation
	 * @param earthquake boolean true or false based on input or from missing attribute calculation
	 * @param alarm boolean true or false based on input or from missing attribute calculation
	 * @param johncalls boolean true or false based on input or from missing attribute calculation
	 * @param marycalls boolean true or false based on input or from missing attribute calculation
	 * @return Computed probability value
	 */
	public static double computeProbability(boolean burglary, boolean earthquake, boolean alarm, boolean johncalls, boolean marycalls)
	{
		double probability = 0;
	//	System.out.println("burglary : " + String.valueOf(burglary)+ " earthquake :" + String.valueOf(earthquake) + " alarm : " + String.valueOf(alarm) + " johncalls: " + String.valueOf(johncalls) + " marycalls: " + String.valueOf(marycalls));

		String str = String.valueOf(String.valueOf(burglary).charAt(0))+String.valueOf(String.valueOf(earthquake).charAt(0))+String.valueOf(String.valueOf(alarm).charAt(0))+String.valueOf(String.valueOf(johncalls).charAt(0))+String.valueOf(String.valueOf(marycalls).charAt(0));

		if(!(containerList.contains(str)))
		{
			containerList.add(str);

			//No Parents for Burglary
			double prob_burg = 0;
			if(burglary)
			{
				prob_burg = P_B;
			}
			else
			{
				prob_burg = 1-P_B;
			}

			//No Parents for Earthquake
			double prob_equake = 0;

			if(earthquake)
			{
				prob_equake = P_E;
			}
			else
			{
				prob_equake = 1-P_E;
			}

			//Alarm - parents are Burglary and earthquake
			double prob_alarm = 0;
			if(alarm)
			{
				prob_alarm = alarmProbMap.get(burglary).get(earthquake);
			}
			else
			{
				prob_alarm = 1-alarmProbMap.get(burglary).get(earthquake);
			}

			double prob_johncalls = 0;
			if(johncalls)
			{
				prob_johncalls = johncallsProbMap.get(alarm);
			}
			else
			{
				prob_johncalls = 1 - johncallsProbMap.get(alarm);
			}

			double prob_marycalls = 0;
			if(marycalls)
			{
				prob_marycalls = marycallsProbMap.get(alarm);
			}
			else
			{
				prob_marycalls = 1 - marycallsProbMap.get(alarm);
			}

			/*System.out.println(prob_burg);
			System.out.println(prob_equake);
			System.out.println(prob_alarm);
			System.out.println(prob_johncalls);
			System.out.println(prob_marycalls);*/
			probability = prob_burg * prob_equake * prob_alarm * prob_johncalls * prob_marycalls;
		}
		return probability;
	}

	/**
	 * Loads the values of the Joint Probability distriibution table into the Map
	 * for the purpose of ease of extraction
	 */
	public static void loadMaps()
	{
		HashMap<Boolean, Double> burgTrueMap = new HashMap<>();
		burgTrueMap.put(true, 0.95);
		burgTrueMap.put(false, 0.94);
		HashMap<Boolean, Double> burgFalseMap = new HashMap<>();
		burgFalseMap.put(true, 0.29);
		burgFalseMap.put(false, 0.001);
		alarmProbMap.put(true, burgTrueMap);
		alarmProbMap.put(false, burgFalseMap);

		johncallsProbMap.put(true, 0.90);
		johncallsProbMap.put(false, 0.05);

		marycallsProbMap.put(true, 0.70);
		marycallsProbMap.put(false, 0.01);
	}
	
	/**
	 * Help section - printed when incorrect arguments are passed.
	 */
	public static void printHelp()
	{
		System.out.println("**** You have reached the help section ****");
		System.out.println();
		System.out.println("Please use the below format to execute this application");
		System.out.println();
		System.out.println("To find probability : provide the input in the following format: bnet Bt Af");
		System.out.println();
		System.out.println("B-Burglary, E-Earthquake, A-Alarm, J-JohnCalls, M-MaryCalls, t-true, f-false");
		System.out.println();
		System.out.println("To find conditional probability: bnet Jt Mf given At");
		System.out.println("Keyword 'given' is used to specify conditional probability");
		System.out.println();
		System.out.println("**** End of the help section ****");
	}
}
