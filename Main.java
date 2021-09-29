import java.io.*;
import java.util.*;

class Main {

	static FoodFightArena arena = new FoodFightArena();
	static Fight fight = new Fight();
	
	public static boolean winOrLoseBreaker(){
			if(fight.mutualEndOfRoundChecker() == 0){
				return false;
			} else if (fight.mutualEndOfRoundChecker() == 1){
				System.out.println("You lost");
				return true;
			} else if (fight.mutualEndOfRoundChecker() == 2){
				System.out.println("Dang you won");
				return false;
			} else {
				return false;
			}
		}

  public static void main(String[] args) {
		
		arena.loadNommers();
		arena.selectTeam();
		
		fight.fightPrep();

		fight.fightStart();

		

		for(int i = 0 ; i<100; i++){

			
			fight.playerFaintOrSwitchCheck();

			if(winOrLoseBreaker()){
				break;
			}

			fight.enemyFaintOrSwitchCheck();
			
			if(winOrLoseBreaker()){
				break;
			}
			System.out.println("\n----------------------------------------------------------------------------------------------\n");
			fight.playerTurn();

			fight.playerFaintOrSwitchCheck();

			if(winOrLoseBreaker()){
				break;
			}

			fight.enemyFaintOrSwitchCheck();

			if(winOrLoseBreaker()){
				break;
			}
			
			if(winOrLoseBreaker()){
				break;
			}

			fight.enemyTurn();

			fight.playerEndOfRoundChecker();
			fight.enemyEndOfRoundChecker();

			
		} 
  }
}