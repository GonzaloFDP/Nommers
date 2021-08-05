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
			fight.enemyTurn();
		}
  }
}