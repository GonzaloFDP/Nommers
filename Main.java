import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) {
		FoodFightArena arena = new FoodFightArena();
		Fight fight = new Fight();
		arena.loadNommers();
		arena.selectTeam();
		
		fight.fightPrep();

		fight.fightStart();

		for(int i = 0 ; i<100; i++){

			fight.playerTurn();

			fight.playerFaintOrSwitchCheck();
			fight.enemyFaintOrSwitchCheck();

			fight.enemyTurn();

			fight.playerFaintOrSwitchCheck();
			fight.enemyFaintOrSwitchCheck();

			fight.playerEndOfRoundChecker();
			fight.enemyEndOfRoundChecker();

			if(fight.mutualEndOfRoundChecker() == 0){

			} else if (fight.mutualEndOfRoundChecker() == 1){
				System.out.println("You lost");
				break;
			} else if (fight.mutualEndOfRoundChecker() == 2){
				System.out.println("Dang you won");
				break;
			}
		} 
  }
}