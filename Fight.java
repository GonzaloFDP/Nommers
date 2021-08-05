import java.util.*;
import java.lang.*;

class Fight{

	private ArrayList<Nommer> defeatedEnemies = new ArrayList<Nommer>();
	private ArrayList<Nommer> defeatedPlayerNommers = new ArrayList<Nommer>();
	FoodFightArena fightArena = new FoodFightArena();
	int nowNommer = 0;
	int enemyNowNommer = 0;
	int nommerChosenToFight;
	int playerAttackChosen;
	int enemyAttackChosen;
	int faintedSwitchInLooper = 0;
	int damageModifier;
	double damageDealtLocker = 0;
	Random rand = new Random();

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
		playerEndOfRoundChecker();
		enemyEndOfRoundChecker();

	}

	public void enemyTurn(){
		//funky AI stuff future me will do
		attackPhase("enemy");
		playerEndOfRoundChecker();
		enemyEndOfRoundChecker();
	}

	public void attackPhase(String whoAttacks){

		System.out.println("\n\n----------------------------------------------------------------------------------------------\n\n");

		String playerWeakness = currentNommer().getWeakness();
		String enemyWeakness = currentEnemyNommer().getWeakness();
		String playerResistance = currentNommer().getResistance();
		String enemyResistance = currentEnemyNommer().getResistance();
		
		if(whoAttacks.equals("player")){
			if(currentNommer().getType().equals(enemyWeakness)){ //weakness
				System.out.println(currentNommer() + " used " + currentNommer().getAttack() + "!");
				double randFight = (double)rand.nextDouble() + 0.6;
				randFight *= 2;
				randFight = Math.round(randFight * 100);
				randFight /= 100;
				double damageDealt = currentNommer().getBaseAttackDamage() * randFight;
				damageDealt = (int)Math.round(damageDealt);
				System.out.println("Attack is stronger than usual. \nAttack Damage multiplied by " + randFight + ".\n");
				currentEnemyNommer().setHealth(currentEnemyNommer().getCurrentHealth() - (int)Math.round(damageDealt));
				System.out.println(currentNommer() + " did " + damageDealt + " damage to " + currentEnemyNommer()+ ". They now have " + currentEnemyNommer().getCurrentHealth() + " health\n");
				damageDealtLocker = damageDealt;
			} else if(currentNommer().getType().equals(enemyResistance)){ //resistance
				System.out.println(currentNommer() + " used " + currentNommer().getAttack() + "!");
				double randFight = (double)rand.nextDouble() + 0.6;
				randFight /= 2;
				randFight = Math.round(randFight * 100);
				randFight /= 100;
				double damageDealt = currentNommer().getBaseAttackDamage() * randFight;
				damageDealt = (int)Math.round(damageDealt);
				System.out.println("Attack is weaker than usual. \nAttack Damage multiplied by " + randFight + ".\n");
				currentEnemyNommer().setHealth(currentEnemyNommer().getCurrentHealth() - (int)Math.round(damageDealt));
				System.out.println(currentNommer() + " did " + damageDealt + " damage to " + currentEnemyNommer()+ ". They now have " + currentEnemyNommer().getCurrentHealth() + " health\n");
				damageDealtLocker = damageDealt;
			} else { //none
				System.out.println(currentNommer() + " used " + currentNommer().getAttack() + "!");
				double damageDealt = currentNommer().getBaseAttackDamage();
				damageDealt = (int)Math.round(damageDealt);
				currentEnemyNommer().setHealth(currentEnemyNommer().getCurrentHealth() - (int)Math.round(damageDealt));
				System.out.println(currentNommer() + " did " + damageDealt + " damage to " + currentEnemyNommer()+ ". They now have " + currentEnemyNommer().getCurrentHealth() + " health\n");
				damageDealtLocker = damageDealt;
			}
		//status effect stuff
			if(!currentNommer().getAttackEffect().equals("none") && currentEnemyNommer().getStatusEffects().size() == 0){
					if(rand.nextInt()%2 == 0){
						System.out.println("Wait a second! This move is causing an effect");
					//attack effects
							if(!currentNommer().getAttackEffect().equals("none")){
								currentEnemyNommer().setStatusEffect(currentNommer().getAttackEffect());
							}
					}
			}
			if(!currentNommer().getSelfEffect().equals("none")){
				if(currentNommer().getSelfEffect().equals("recoil")){
					currentNommer().recoil(damageDealtLocker);
				} else if (currentNommer().getSelfEffect().equals("heal")){
					currentNommer().heal(damageDealtLocker);
				}
			}
	} else if (whoAttacks.equals("enemy")){
			if(currentEnemyNommer().getType().equals(playerWeakness)){ //weakness
				System.out.println(currentEnemyNommer() + " used " + currentEnemyNommer().getAttack() + "!");
				double randFight = (double)rand.nextDouble() + 0.6;
				randFight *= 2;
				randFight = Math.round(randFight * 100);
				randFight /= 100;
				double damageDealt = currentEnemyNommer().getBaseAttackDamage() * randFight;
				damageDealt = (int)Math.round(damageDealt);
				System.out.println("Attack is stronger than usual. \nAttack Damage multiplied by " + randFight + ".\n");
				currentNommer().setHealth(currentNommer().getCurrentHealth() - (int)Math.round(damageDealt));
				System.out.println(currentEnemyNommer() + " did " + damageDealt + " damage to " + currentNommer()+ ". They now have " + currentNommer().getCurrentHealth() + " health\n");
			} else if(currentEnemyNommer().getType().equals(playerResistance)){ //resistance
				System.out.println(currentEnemyNommer() + " used " + currentEnemyNommer().getAttack() + "!");
				double randFight = (double)rand.nextDouble() + 0.6;
				randFight /= 2;
				randFight = Math.round(randFight * 100);
				randFight /= 100;
				double damageDealt = currentEnemyNommer().getBaseAttackDamage() * randFight;
				damageDealt = (int)Math.round(damageDealt);
				System.out.println("Attack is weaker than usual. \nAttack Damage multiplied by " + randFight + ".\n");
				currentNommer().setHealth(currentNommer().getCurrentHealth() - (int)Math.round(damageDealt));
				System.out.println(currentEnemyNommer() + " did " + damageDealt + " damage to " + currentNommer()+ ". They now have " + currentNommer().getCurrentHealth() + " health\n");
			} else { //none
				System.out.println(currentEnemyNommer() + " used " + currentEnemyNommer().getAttack() + "!");
				double damageDealt = currentEnemyNommer().getBaseAttackDamage();
				damageDealt = (int)Math.round(damageDealt);
				currentNommer().setHealth(currentNommer().getCurrentHealth() - (int)Math.round(damageDealt));
				System.out.println(currentEnemyNommer() + " did " + damageDealt + " damage to " + currentNommer()+ ". They now have " + currentNommer().getCurrentHealth() + " health\n");
			}
		//status effect stuff
		if(!currentEnemyNommer().getAttackEffect().equals("none") && currentNommer().getStatusEffects().size() == 0){
					if(rand.nextInt()%2 == 0){
						System.out.println("Wait a second! This move is causing an effect");
					//attack effects
							if(!currentEnemyNommer().getAttackEffect().equal("none")){
								currentNommer().setStatusEffect(currentEnemyNommer().getAttackEffect());
							}
					}
			}
			if(!currentEnemyNommer().getSelfEffect().equals("none")){

				if(currentEnemyNommer().getSelfEffect().equals("recoil")){
					currentEnemyNommer().recoil(damageDealtLocker);

				} else if (currentEnemyNommer().getSelfEffect().equals("heal")){
					currentEnemyNommer().heal(damageDealtLocker);
				}
			}
		}
	}


	public void switchOut(String whoSwitches, int faintOrSwitch){
		
		if(whoSwitches.equals("player")){

			if(faintOrSwitch == 0){ //faint
			System.out.println(currentNommer() + " fainted!");
			} else if (faintOrSwitch == 1) { //switch
			System.out.println(currentNommer() + " switched out!");
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
			nowNommer = nommerChosenToFight - 1;
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
			System.out.println(enemyRandint);
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
						System.out.println("None found for selected class. Parsing all enemy Nommers");
						for(int j = 0; j < fightArena.getEnemies().size(); j++){
							if(!fightArena.getEnemies().get(j).getHasFainted().equals("yes")){
								enemyNowNommer = j;
								System.out.println(currentEnemyNommer() + " switched in!");
								break;
							} else {
										
							}
						}
								//System.out.println("You won :D");
				 }

				}	else if (enemyRandint < 4){
						for(int i = 0; i < fightArena.getEnemies().size(); i++){
							if(!fightArena.getEnemies().get(i).getHasFainted().equals("yes")){
								System.out.println(fightArena.getEnemies().get(i).getHasFainted());
								enemyNowNommer = i;
								System.out.println(currentEnemyNommer() + " switched in!");
								break;
							} else {
								
							}
						}
				} else if (enemyRandint == 4){
						for(int i = 0; i < fightArena.getEnemies().size(); i++){
							if(fightArena.getEnemies().get(i).getType().equals(currentNommer().getResistance()) && !fightArena.getEnemies().get(i).getHasFainted().equals("yes")){
								System.out.println(fightArena.getEnemies().get(i).getHasFainted());
								enemyNowNommer = i;
								System.out.println(currentEnemyNommer() + " switched in!");
								break;
							} else {

							}
						}
						if(currentEnemyNommer().getHasFainted().equals("yes")) {
							System.out.println("None found for selected class. Parsing all enemy Nommers");
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
				} 

			
		}
	


	public void playerEndOfRoundChecker(){
		if(currentNommer().getStatusEffects().equals("burn")){
			currentNommer().burn();
		} else if(currentNommer().getStatusEffects().equals("poison")){
			currentNommer().poison();
		} else if(currentNommer().getStatusEffects().equals("freeze")){
			currentNommer().freeze();
		} else {

		}
		if(currentNommer().getCurrentHealth() <= 0){
			currentNommer().setHasFainted("yes");
			switchOut("player",0);
		}
	}

	public void enemyEndOfRoundChecker(){
		if(currentEnemyNommer().getStatusEffects().equals("burn")){
			currentEnemyNommer().burn();
		} else if(currentEnemyNommer().getStatusEffects().equals("poison")){
			currentEnemyNommer().poison();
		} else if(currentEnemyNommer().getStatusEffects().equals("freeze")){
			currentEnemyNommer().freeze();
		} else {

		}
		if(currentEnemyNommer().getCurrentHealth() <= 0){
			currentEnemyNommer().setHasFainted("yes");
			switchOut("enemy",0);
		}
	}


}