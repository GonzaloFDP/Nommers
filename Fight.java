import java.util.*;
import java.lang.*;

class Fight{

	private ArrayList<Nommer> defeatedEnemies = new ArrayList<Nommer>();
	private ArrayList<Nommer> defeatedPlayerNommers = new ArrayList<Nommer>();
	FoodFightArena fightArena = new FoodFightArena();
	int playerFaintSum = 0;
	int enemyFaintSum = 0;
	int nowNommer = 0;
	int enemyNowNommer = 0;
	int nommerChosenToFight;
	int faintedSwitchInLooper = 0;
	int damageModifier;
	int playerRandZap;
	int enemyRandZap;
	double damageDealtLocker = 0;
	Random rand = new Random();
	String whoseTurn = "";

	public Nommer currentNommer(){
		return fightArena.getTeam().get(nowNommer);
	} 

	public Nommer currentEnemyNommer(){
	//	System.out.println(fightArena.getEnemies());
		return fightArena.getEnemies().get(enemyNowNommer);
	} 

	public void fightPrep(){
		fightArena.shuffleEnemies();
		//System.out.println(fightArena.getEnemies());
		System.out.println("\n\n==================== Fight ====================\n\nThe enemy Nommer is " + fightArena.getEnemies().get(0));
		System.out.println("What will your Nommer be? (Here are your Nommers: " + fightArena.getTeam() + ") \nDon't forget to type in their cooresponding number, not their name!");
		Scanner nommerFightChoose = new Scanner(System.in);
		nommerChosenToFight = nommerFightChoose.nextInt();
		nowNommer = nommerChosenToFight - 1;
		enemyNowNommer = 0;
		System.out.printf("You choose %s!\n", fightArena.getTeam().get(nommerChosenToFight-1));
		//System.out.printf("You choose %s!\n", playerTeam.(currentNommer));
	}

	public void fightStart(){
		//System.out.println(fightArena.getEnemies());
		System.out.println("\n\n----------------------------------------------------------------------------------------------\n\n");
		//System.out.println(currentNommer().getCurrentHealth());
		System.out.printf("Your %s has %d health. The enemy %s has %d health\n", currentNommer(), currentNommer().getCurrentHealth(), currentEnemyNommer(), currentEnemyNommer().getCurrentHealth());
	}

	public void playerTurn(){
		whoseTurn = "player";
		System.out.println("Do you want to attack (1) or switch out your current Nommer (2)"); //once I make more attacks this will take in attack ArrayList

		Scanner playerDecision = new Scanner(System.in);
		int playerDecisionChosen = playerDecision.nextInt();

		if(playerDecisionChosen == 1){
			attackPhase("player");
		} else if (playerDecisionChosen == 2){
			switchOut("player",1);
			// show Nommers
			// Ask which ones to use (don't allow if the Nommer is dead)
			// Set it to the current Nommer
		}
		//playerEndOfRoundChecker();
		//enemyEndOfRoundChecker();

	}

	public void enemyTurn(){
		whoseTurn = "enemy";
		//funky AI stuff future me will do
		//i already did B) g3t r3kt
		attackPhase("enemy");
		//playerEndOfRoundChecker();
		//enemyEndOfRoundChecker();
	}



	//------------------------------ Mutual End of Round Checker ------------------------------
	
	public int mutualEndOfRoundChecker(){
		//----------- Player lose check -----------
			playerFaintSum = 0;
			for(int i = 0; i < fightArena.getTeam().size(); i++){
				if(fightArena.getTeam().get(i).getHasFainted().equals("yes")){
					playerFaintSum ++;
				}
			}
			//System.out.println("\nPlayer faintSum = " + playerFaintSum);
			if(playerFaintSum >= fightArena.getTeam().size()){
				//return player lost
				return 1;
			} else {
				//nothing
			}
		

		//----------- Enemy lose check -----------
			enemyFaintSum = 0;
			for(int i = 0; i < fightArena.getEnemies().size(); i++){
				if(fightArena.getEnemies().get(i).getHasFainted().equals("yes")){
					enemyFaintSum ++;
				}
			}
			//System.out.println("\nEnemy faintSum = " + enemyFaintSum);
			if(enemyFaintSum >= fightArena.getEnemies().size()){
				//return enemy lost
				return 2;
			} else {
				//nothing
				return 0;
			}
		

	}

	public void attackPhase(String whoAttacks){

		System.out.println("\n\n----------------------------------------------------------------------------------------------\n\n");

		String playerWeakness = currentNommer().getWeakness();
		String enemyWeakness = currentEnemyNommer().getWeakness();
		String playerResistance = currentNommer().getResistance();
		String enemyResistance = currentEnemyNommer().getResistance();
		
		if(whoAttacks.equals("player")){
			playerRandZap = 0;
			if(currentNommer().getIsZapped()){
				playerRandZap = rand.nextInt(10);
			}

			System.out.println("Player RandZap: " + playerRandZap);

			if(!currentNommer().getIsZapped() || playerRandZap < 4){
				System.out.println("Attack(s): " + currentNommer().getAttackListString());
				System.out.println("Cooresponding Damage Amount(s): " + currentNommer().getAttackDamageListString());
				System.out.println("Which attack do you want to choose? (Select its cooresponding number)\n");
				Scanner attackChoose = new Scanner(System.in);				
				int playerAttackChosen;
				playerAttackChosen = attackChoose.nextInt() - 1;

				System.out.println(currentNommer().getAttackList().length + "\n");
				System.out.println(playerAttackChosen);

				while (playerAttackChosen < 0 && playerAttackChosen >= currentNommer().getAttackList().length - 1){
					System.out.println("no");
					playerAttackChosen = attackChoose.nextInt() - 1;
				}

				System.out.println("\n");

				if(currentNommer().getType().equals(enemyWeakness)){ //weakness
					System.out.println(currentNommer() + " used " + currentNommer().getAttack(playerAttackChosen) + "!");
					double randFight = (double)rand.nextDouble() + 0.6;
					randFight *= 2;
					randFight = Math.round(randFight * 100);
					randFight /= 100;
					double damageDealt = currentNommer().getBaseAttackDamage(playerAttackChosen) * randFight;
					damageDealt = (int)Math.round(damageDealt);
					System.out.println("Attack is stronger than usual. \nAttack Damage multiplied by " + randFight + ".\n");
					currentEnemyNommer().setHealth(currentEnemyNommer().getCurrentHealth() - (int)Math.round(damageDealt));
					System.out.println(currentNommer() + " did " + damageDealt + " damage to " + currentEnemyNommer()+ ". The latter now has " + currentEnemyNommer().getCurrentHealth() + " health\n");
					damageDealtLocker = damageDealt;
				} else if(currentNommer().getType().equals(enemyResistance)){ //resistance
					System.out.println(currentNommer() + " used " + currentNommer().getAttack(playerAttackChosen) + "!");
					double randFight = (double)rand.nextDouble() + 0.6;
					randFight /= 2;
					randFight = Math.round(randFight * 100);
					randFight /= 100;
					double damageDealt = currentNommer().getBaseAttackDamage(playerAttackChosen) * randFight;
					damageDealt = (int)Math.round(damageDealt);
					System.out.println("Attack is weaker than usual. \nAttack Damage multiplied by " + randFight + ".\n");
					currentEnemyNommer().setHealth(currentEnemyNommer().getCurrentHealth() - (int)Math.round(damageDealt));
					System.out.println(currentNommer() + " did " + damageDealt + " damage to " + currentEnemyNommer()+ ". The latter now has " + currentEnemyNommer().getCurrentHealth() + " health\n");
					damageDealtLocker = damageDealt;
				} else { //none
					System.out.println(currentNommer() + " used " + currentNommer().getAttack(playerAttackChosen) + "!");
					double damageDealt = currentNommer().getBaseAttackDamage(playerAttackChosen);
					damageDealt = (int)Math.round(damageDealt);
					currentEnemyNommer().setHealth(currentEnemyNommer().getCurrentHealth() - (int)Math.round(damageDealt));
					System.out.println(currentNommer() + " did " + damageDealt + " damage to " + currentEnemyNommer()+ ". The latter now has " + currentEnemyNommer().getCurrentHealth() + " health\n");
					damageDealtLocker = damageDealt;
				}
			//status effect stuff
				if(!currentNommer().getAttackEffect(playerAttackChosen).equals("none") && currentEnemyNommer().getStatusEffects().size() == 0){
						if(rand.nextInt()%2 == 0){
						//attack effects
								if(!currentNommer().getAttackEffect(playerAttackChosen).equals("none")){
									currentEnemyNommer().setStatusEffect(currentNommer().getAttackEffect(playerAttackChosen));
									System.out.println("Wait a second! This move is causing a(n) " + currentNommer().getAttackEffect(playerAttackChosen) + " effect");
								}
						}
				}
				if(!currentNommer().getSelfEffect(playerAttackChosen).equals("none")){
					if(currentNommer().getSelfEffect(playerAttackChosen).equals("recoil")){
						currentNommer().recoil(damageDealtLocker);
					} else if (currentNommer().getSelfEffect(playerAttackChosen).equals("heal")){
						currentNommer().heal(damageDealtLocker);
					} else if (currentNommer().getSelfEffect(playerAttackChosen).equals("repeat")){
						currentEnemyNommer().setHealth(currentEnemyNommer().getCurrentHealth() - currentNommer().repeat(damageDealtLocker));
						System.out.println(currentEnemyNommer() + " now has " + currentEnemyNommer().getCurrentHealth() + " health.");
					}
				}
			} else {
				System.out.println("The attack missed!");
			}
		

	} else if (whoAttacks.equals("enemy")){
		enemyRandZap = 0;
		if(currentEnemyNommer().getIsZapped()){
				enemyRandZap = rand.nextInt(10);
		}
		System.out.println("Enemy RandZap: " + enemyRandZap);
		if(!currentEnemyNommer().getIsZapped() || enemyRandZap < 4){
				int enemyAttackChosen;
				int numOfAttacks = currentEnemyNommer().getAttackList().length;				
				Random randEnemyAttack = new Random();
				enemyAttackChosen = randEnemyAttack.nextInt(numOfAttacks);
				if(currentEnemyNommer().getType().equals(playerWeakness)){ //weakness
					System.out.println(currentEnemyNommer() + " used " + currentEnemyNommer().getAttack(enemyAttackChosen) + "!");
					double randFight = (double)rand.nextDouble() + 0.6;
					randFight *= 2;
					randFight = Math.round(randFight * 100);
					randFight /= 100;
					double damageDealt = currentEnemyNommer().getBaseAttackDamage(enemyAttackChosen) * randFight;
					damageDealt = (int)Math.round(damageDealt);
					System.out.println("Attack is stronger than usual. \nAttack Damage multiplied by " + randFight + ".\n");
					currentNommer().setHealth(currentNommer().getCurrentHealth() - (int)Math.round(damageDealt));
					System.out.println(currentEnemyNommer() + " did " + damageDealt + " damage to " + currentNommer()+ ". The latter now has " + currentNommer().getCurrentHealth() + " health\n");
					damageDealtLocker = damageDealt;
				} else if(currentEnemyNommer().getType().equals(playerResistance)){ //resistance
					System.out.println(currentEnemyNommer() + " used " + currentEnemyNommer().getAttack(enemyAttackChosen) + "!");
					double randFight = (double)rand.nextDouble() + 0.6;
					randFight /= 2;
					randFight = Math.round(randFight * 100);
					randFight /= 100;
					double damageDealt = currentEnemyNommer().getBaseAttackDamage(enemyAttackChosen) * randFight;
					damageDealt = (int)Math.round(damageDealt);
					System.out.println("Attack is weaker than usual. \nAttack Damage multiplied by " + randFight + ".\n");
					currentNommer().setHealth(currentNommer().getCurrentHealth() - (int)Math.round(damageDealt));
					System.out.println(currentEnemyNommer() + " did " + damageDealt + " damage to " + currentNommer()+ ". The latter now has " + currentNommer().getCurrentHealth() + " health\n");
					damageDealtLocker = damageDealt;
				} else { //none
					System.out.println(currentEnemyNommer() + " used " + currentEnemyNommer().getAttack(enemyAttackChosen) + "!");
					double damageDealt = currentEnemyNommer().getBaseAttackDamage(enemyAttackChosen);
					damageDealt = (int)Math.round(damageDealt);
					currentNommer().setHealth(currentNommer().getCurrentHealth() - (int)Math.round(damageDealt));
					System.out.println(currentEnemyNommer() + " did " + damageDealt + " damage to " + currentNommer()+ ". The latter now has " + currentNommer().getCurrentHealth() + " health\n");
					damageDealtLocker = damageDealt;
				}
			//status effect stuff
			if(!currentEnemyNommer().getAttackEffect(enemyAttackChosen).equals("none") && currentNommer().getStatusEffects().size() == 0){
						if(rand.nextInt()%2 == 0){
						//attack effects
								if(!currentEnemyNommer().getAttackEffect(enemyAttackChosen).equals("none")){
									currentNommer().setStatusEffect(currentEnemyNommer().getAttackEffect(enemyAttackChosen));
									System.out.println("Wait a second! This move is causing an " + currentEnemyNommer().getAttackEffect(enemyAttackChosen) + " effect");
								}
						}
				}
				if(!currentEnemyNommer().getSelfEffect(enemyAttackChosen).equals("none")){

					if(currentEnemyNommer().getSelfEffect(enemyAttackChosen).equals("recoil")){
						currentEnemyNommer().recoil(damageDealtLocker);
					} else if (currentEnemyNommer().getSelfEffect(enemyAttackChosen).equals("heal")){
						currentEnemyNommer().heal(damageDealtLocker);
					} else if (currentEnemyNommer().getSelfEffect(enemyAttackChosen).equals("repeat")){
						currentNommer().setHealth(currentNommer().getCurrentHealth() - currentEnemyNommer().repeat(damageDealtLocker));
						System.out.println(currentNommer() + " now has " + currentNommer().getCurrentHealth() + " health.");
					}
				}
			} else {
				System.out.println("The attack missed!");
			}
			
		}
	}

//switch and fainting shtuff

	public void switchOut(String whoSwitches, int faintOrSwitch){
		
		if(whoSwitches.equals("player")){

			Nommer prevNommer = currentNommer() ;

			if(faintOrSwitch == 0){ //faint
				System.out.println("\n" + currentNommer() + " fainted!");
				System.out.println("Player Status Effects cleared");
				currentNommer().clearStatusEffect();
			} else if (faintOrSwitch == 1) { //switch
		 		System.out.println("\n" + currentNommer() + " switched out!");
			}

			while(faintedSwitchInLooper == 0){
	  		System.out.println("Who will replace " + currentNommer() + " in the arena?\n(Here are your Nommers: " + fightArena.getTeam() + " \nDon't forget to type in their cooresponding number, not their name!)");
	  		Scanner nommerFightChoose = new Scanner(System.in);
				nommerChosenToFight = nommerFightChoose.nextInt();
			if(fightArena.getTeam().get(nommerChosenToFight-1).getHasFainted().equals("yes")){
				System.out.println("That Nommer has already fainted, please choose another one\n");
				faintedSwitchInLooper = 0;
			} else if (!fightArena.getTeam().get(nommerChosenToFight-1).getHasFainted().equals("yes")){
				faintedSwitchInLooper = 1;
				}
			}
			if(fightArena.getTeam().get(nommerChosenToFight-1).getName().equals(currentNommer().getName())){
				nowNommer = nommerChosenToFight - 1;
				System.out.println("Player Status Effects not cleared, since it is the same Nommer");
			} else {
				nowNommer = nommerChosenToFight - 1;
				currentNommer().clearStatusEffect();
				System.out.println("Player Status Effects cleared");
			}
			System.out.printf("You choose %s!\n", fightArena.getTeam().get(nommerChosenToFight-1));
			System.out.printf("Your %s has %d health. The enemy %s has %d health\n", currentNommer(), currentNommer().getCurrentHealth(), currentEnemyNommer(), currentEnemyNommer().getCurrentHealth());
			faintedSwitchInLooper = 0;

		} else if (whoSwitches.equals("enemy")){

			if(faintOrSwitch == 0){ //faint
			System.out.println(currentEnemyNommer() + " fainted!");
			} else if (faintOrSwitch == 1) { //switch
			System.out.println(currentEnemyNommer() + " switched out!");
			}

			int enemyRandint = rand.nextInt(5);
			//System.out.println(enemyRandint);
			//System.out.println(currentEnemyNommer().getHasFainted());


				if(enemyRandint < 2){
					for(int i = 0; i < fightArena.getEnemies().size(); i++){
						if(fightArena.getEnemies().get(i).getType().equals(currentNommer().getWeakness()) && !fightArena.getEnemies().get(i).getHasFainted().equals("yes")){
							System.out.println(fightArena.getEnemies().get(i).getHasFainted());
							enemyNowNommer = i;
							System.out.println(currentEnemyNommer() + " switched in!");
							break;
						} else{

						}
					} 
					if(currentEnemyNommer().getHasFainted().equals("yes")) {
						//System.out.println("None found for selected class. Parsing all enemy Nommers");
						for(int j = 0; j < fightArena.getEnemies().size(); j++){
							if(!fightArena.getEnemies().get(j).getHasFainted().equals("yes")){
								enemyNowNommer = j;
								System.out.println("\n" + currentEnemyNommer() + " switched in!");
								break;
							} else {
										
							}
						}
								//System.out.println("You won :D");
				 }

				}	else if (enemyRandint < 4){
						for(int i = 0; i < fightArena.getEnemies().size(); i++){
							if(!fightArena.getEnemies().get(i).getHasFainted().equals("yes")){
								//System.out.println(fightArena.getEnemies().get(i).getHasFainted());
								enemyNowNommer = i;
								System.out.println(currentEnemyNommer() + " switched in!");
								break;
							} else {
								
							}
						}
				} else if (enemyRandint == 4){
						for(int i = 0; i < fightArena.getEnemies().size(); i++){
							if(fightArena.getEnemies().get(i).getType().equals(currentNommer().getResistance()) && !fightArena.getEnemies().get(i).getHasFainted().equals("yes")){
								//System.out.println(fightArena.getEnemies().get(i).getHasFainted());
								enemyNowNommer = i;
								System.out.println(currentEnemyNommer() + " switched in!");
								break;
							} else {

							}
						}
						if(currentEnemyNommer().getHasFainted().equals("yes")) {
							//System.out.println("None found for selected class. Parsing all enemy Nommers");
								for(int j = 0; j < fightArena.getEnemies().size(); j++){
									if(!fightArena.getEnemies().get(j).getHasFainted().equals("yes")){
										enemyNowNommer = j;
										System.out.println(currentEnemyNommer() + " switched in!");
										break;
									} else {
										
									}
								}
							//	System.out.println("You won :D");
						}
						
					}
				currentEnemyNommer().clearStatusEffect();
				System.out.println("Enemy Status Effects cleared");
				} 

			
		}
	

	public void playerFaintOrSwitchCheck(){
		System.out.println("Player faint or switch check");
		System.out.println("Player health: " + currentNommer().getCurrentHealth());
		if(currentNommer().getCurrentHealth() <= 0){
			currentNommer().setHasFainted("yes");
			mutualEndOfRoundChecker();

			if(playerFaintSum>3){
			
			} else {
				switchOut("player",0);
			}
		}

		
	}

	public void playerEndOfRoundChecker(){
		System.out.println("Turns with effects: " + currentNommer().getTurnsWithEffect());
		if(currentNommer().getStatusEffects().contains("burn")){
			currentNommer().burn();
		} else if(currentNommer().getStatusEffects().contains("poison")){
			currentNommer().poison();
		} else if(currentNommer().getStatusEffects().contains("zap")){
			currentNommer().zap();
		} else {

		}
		
	}

	public void enemyFaintOrSwitchCheck(){
		System.out.println("Enemy faint or switch check");
		System.out.println("Enemy health: " + currentEnemyNommer().getCurrentHealth());
		if(currentEnemyNommer().getCurrentHealth() <= 0){
			currentEnemyNommer().setHasFainted("yes");
			mutualEndOfRoundChecker();

			if(enemyFaintSum>3){
			
			} else {
				switchOut("enemy",0);
			}
		}

		
	}

	public void enemyEndOfRoundChecker(){
		System.out.println("Turns with effects: " + currentEnemyNommer().getTurnsWithEffect());
		if(currentEnemyNommer().getStatusEffects().contains("burn")){
			currentEnemyNommer().burn();
		} else if(currentEnemyNommer().getStatusEffects().contains("poison")){
			currentEnemyNommer().poison();
		} else if(currentEnemyNommer().getStatusEffects().contains("zap")){
			currentEnemyNommer().zap();
		} else {

		}

		System.out.println("\n" + currentNommer().getName() + " has " + currentNommer().getCurrentHealth() + " current health and a " + currentNommer().getStatusEffects() + " status effect");
		System.out.println(currentEnemyNommer().getName() + " has " + currentEnemyNommer().getCurrentHealth() + " current health and a " + currentEnemyNommer().getStatusEffects() + " status effect");
	}

	


}