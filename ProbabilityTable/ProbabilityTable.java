import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Data 
{
	String bbGameOnTV = "";
	String GWatchesTV = "";
	String oofCatFood = "";
	String GFeedsCat = "";
	public Data(String bbGameOnTV, String gWatchesTV, String oofCatFood, String gFeedsCat) {
		super();
		this.bbGameOnTV = bbGameOnTV;
		GWatchesTV = gWatchesTV;
		this.oofCatFood = oofCatFood;
		GFeedsCat = gFeedsCat;
	}
}

public class ProbabilityTable 
{
	public static void main(String[] args)
	{
		String sampleFilePath = "";
		ArrayList<Data> sampleDataList = new ArrayList<>();
		if(args != null && args.length > 0)
		{
			sampleFilePath = args[0];
		}
		if(sampleFilePath != null && sampleFilePath.length() > 0)
		{
			try
			{
				File sampleFile = new File(sampleFilePath);
				Scanner reader = new Scanner(sampleFile);
				while(reader.hasNextLine())
				{
					String data = reader.nextLine();
					String[] dataArray = data.split("\\t");
					if(dataArray.length == 4)
					{
						sampleDataList.add(new Data(dataArray[0], dataArray[1], dataArray[2],dataArray[3]));
					}
				}
				reader.close();
			}
			catch(FileNotFoundException ex)
			{
				ex.printStackTrace();
			}
		}
		calculate_P_BBGameOnTV(sampleDataList);
		calculate_P_oofCatFood(sampleDataList);
		calculate_P_GWatchesTV(sampleDataList);
		calculate_P_GFeedsCat(sampleDataList);
	}
	
	public static void calculate_P_BBGameOnTV(ArrayList<Data> sampleDataList)
	{
		//there is a baseball game on TV 1
		//no baseball game on TV 0
		
		int bbGameOnTVcounter = 0;
		for(Data sampleData:sampleDataList)
		{
			if(sampleData.bbGameOnTV.equals("1"))
			{
				bbGameOnTVcounter++;
			}
		}
		
		double value = (double)bbGameOnTVcounter/(sampleDataList.size());
		
		System.out.println("P(Baseball_game_on_TV) : " + String.format("%.5f",value));
		System.out.println();
	}
	
	public static void calculate_P_oofCatFood(ArrayList<Data> sampleDataList)
	{
		//out of cat food 1
		//not out of cat food 0
		
		int oofCatFoodcounter = 0;
		for(Data sampleData:sampleDataList)
		{
			if(sampleData.oofCatFood.equals("1"))
			{
				oofCatFoodcounter++;
			}
		}
		
		double value = (double)oofCatFoodcounter/(sampleDataList.size());
		
		System.out.println("P(out_of_cat_food) : " + String.format("%.5f",value));
		System.out.println();
	}
	
	public static void calculate_P_GWatchesTV(ArrayList<Data> sampleDataList)
	{
		//George watches TV 1
		//George does not watch TV 0
		
		int gWatchesTVcounter_Gameon = 0;
		int bbGameOnTVcounter = 0;
		int gWatchesTVcounter_noGame = 0;
		int nobbGameOnTVcounter = 0;
		
		for(Data sampleData:sampleDataList)
		{
			if(sampleData.GWatchesTV.equals("1") && sampleData.bbGameOnTV.equals("1"))
			{
				gWatchesTVcounter_Gameon++;
			}
			if(sampleData.bbGameOnTV.equals("1"))
			{
				bbGameOnTVcounter++;
			}
			if(sampleData.GWatchesTV.equals("1") && sampleData.bbGameOnTV.equals("0"))
			{
				gWatchesTVcounter_noGame++;
			}
			if(sampleData.bbGameOnTV.equals("0"))
			{
				nobbGameOnTVcounter++;
			}
		}
		
		double value1 = (double)gWatchesTVcounter_Gameon/bbGameOnTVcounter;
		System.out.println("P(GWatchesTV|BBGameOnTV) : " + String.format("%.5f",value1));
		
		double value2 = (double)gWatchesTVcounter_noGame/nobbGameOnTVcounter;
		System.out.println("P(GWatchesTV|not(BBGameOnTV)) : " + String.format("%.5f",value2));
		
		System.out.println();
	}
	
	public static void calculate_P_GFeedsCat(ArrayList<Data> sampleDataList)
	{
		//George feeds the cat 1
		//George does not feed the cat 0
		
		int gFeedsCatCounter1 = 0;
		int gFeedsCatCounter2 = 0;
		int gFeedsCatCounter3 = 0;
		int gFeedsCatCounter4 = 0;
		int gFeedsCatCounter5 = 0;
		int gFeedsCatCounter6 = 0;
		int gFeedsCatCounter7 = 0;
		int gFeedsCatCounter8 = 0;
		
		for(Data sampleData:sampleDataList)
		{
			if(sampleData.GFeedsCat.equals("1") && sampleData.GWatchesTV.equals("1") && sampleData.oofCatFood.equals("1"))
			{
				gFeedsCatCounter1++;
			}
			if(sampleData.oofCatFood.equals("1") && sampleData.GWatchesTV.equals("1"))
			{
				gFeedsCatCounter2++;
			}
			
			if(sampleData.GFeedsCat.equals("1") && sampleData.GWatchesTV.equals("1") && sampleData.oofCatFood.equals("0"))
			{
				gFeedsCatCounter3++;
			}
			if(sampleData.oofCatFood.equals("0") && sampleData.GWatchesTV.equals("1"))
			{
				gFeedsCatCounter4++;
			}
			
			if(sampleData.GFeedsCat.equals("1") && sampleData.GWatchesTV.equals("0") && sampleData.oofCatFood.equals("1"))
			{
				gFeedsCatCounter5++;
			}
			if(sampleData.oofCatFood.equals("1") && sampleData.GWatchesTV.equals("0"))
			{
				gFeedsCatCounter6++;
			}
			
			if(sampleData.GFeedsCat.equals("1") && sampleData.GWatchesTV.equals("0") && sampleData.oofCatFood.equals("0"))
			{
				gFeedsCatCounter7++;
			}
			if(sampleData.oofCatFood.equals("0") && sampleData.GWatchesTV.equals("0"))
			{
				gFeedsCatCounter8++;
			}
		}
		
		double value1 = (double)gFeedsCatCounter1/gFeedsCatCounter2;
		System.out.println("P(GFeedsCat|GWatchesTV,oofCatFood) : " + String.format("%.5f",value1));
		
		double value2 = (double)gFeedsCatCounter3/gFeedsCatCounter4;
		System.out.println("P(GFeedsCat|GWatchesTV,not(oofCatFood)) : " + String.format("%.5f",value2));
		
		double value3 = (double)gFeedsCatCounter5/gFeedsCatCounter6;
		System.out.println("P(GFeedsCat|not(GWatchesTV),oofCatFood) : " + String.format("%.5f",value3));
		
		double value4 = (double)gFeedsCatCounter7/gFeedsCatCounter8;
		System.out.println("P(GFeedsCat|not(GWatchesTV),not(oofCatFood)) : " + String.format("%.5f",value4));
	}
}
