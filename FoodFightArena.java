import java.io.*;
import java.util.*;
import java.lang.Thread;

class FoodFightArena {
	private static ArrayList <Nommer> Nommerary = new ArrayList <Nommer>();
	private static ArrayList <Nommer> team = new ArrayList <Nommer>();
	private static ArrayList <Nommer> enemy = new ArrayList <Nommer>();
	

	public static void	loadNommers(){
		int showDesc1Or2;
		Scanner showDesc = new Scanner(System.in);
		Scanner file = null;

		try {
			file  = new Scanner (new File("Nommers.txt"));
		}
		
		catch (IOException ex) {

		}
		int numNommers;
		numNommers = Integer.parseInt(file.nextLine());

		//print out the description for each Nommer with a wait time of 0.5 sec
		System.out.println("Do you want to see all the descriptions for the Nommers? (1 for yes, 2 for no)");
		showDesc1Or2 = showDesc.nextInt();

		try {
			for (int i = 0; i < numNommers; i++){

				String nommerVals = file.nextLine();
				Nommerary.add(new Nommer(nommerVals));
				if(showDesc1Or2 == 1){
					System.out.println(Nommerary.get(i).returnDescription() + "\n" + Nommerary.get(i).returnAttacks() + "\n");
					Thread.sleep(500);
				} else if(showDesc1Or2 == 2) {

				} else {
					
				}
			}
		}
		catch (Exception e){
			System.out.println(e);
		}
		
		
	}

	/* public static void nommeraryPrintNames(){
		for(int i = 0; i < Nommerary.size(); i++){
			System.out.println(Nommer);
		}
	} */

	public static void selectTeam(){
		System.out.printf("OI THERE CHAP!\n Name's Jimmothinian\n I got %d Nommers for ye to choose from.\nType in the number cooresponding to the Nommer you want.\n\nAlso, if you want to see type advantages and resistances, pop into the code and look at the advantage.draw\nand the resistance.draw files\n\n", Nommerary.size());
		Scanner nommerChoose = new Scanner(System.in);
		System.out.println(Nommerary);
		for(int i = 0; i < 4; i++){
			i++;
			System.out.println("Choose you nommer #" + i);
			i--;
			int chooser = nommerChoose.nextInt();
			team.add(Nommerary.get(chooser-1));
			System.out.println("You just chose " + Nommerary.get(chooser-1).getName());
			Nommerary.remove(chooser-1);
			if(i<3){
				System.out.println("Nommers left: " + Nommerary);
			}
		}
		Collections.shuffle(Nommerary);
		for(int j = Nommerary.size()-1; j>-1; j--){
			enemy.add(Nommerary.get(j));
			Nommerary.remove(j);
		}
		System.out.println("Your team: " + team);
		System.out.println("Enemy's team: " + enemy);
	}

	//getters

	public ArrayList <Nommer> getEnemies(){
		return enemy;
	}

	public ArrayList <Nommer> getTeam(){
		return team;
	}

	//setters

	public void shuffleEnemies(){
		Collections.shuffle(enemy);
	}
}