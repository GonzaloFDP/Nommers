import java.util.*;
import java.lang.*;

class Nommer {


	private String weakness;
	private String resistance;
	private String hasFainted = "no";
	private int turnsWithFX = 0;

	private String name;
	private String type;
	private int health, startingHealth;
	private String attack, stringStartingAttackDamage;
	private int attackDamage, startingAttackDamage, numberOfAttacks;
	private String attackType;
	private String attackEffect = "none";
	private String selfEffect = "none";
	private boolean isZapped = false;

	private String[] attackList;
	private int[] attackDamageList;
		private String[] stringAttackDamageList;
	private String[] attackTypeList;
	private String[] attackEffectList;
	private String[] selfEffectList;

	/*
	private String isBurned = "no";
	private int burnCounter = 0;
	private String isPoisoned = "no";
	private int poisonCounter = 0;
	*/
	private ArrayList <String> statusEffects = new ArrayList();
	/*private ArrayList <String> attackList = new ArrayList();
	private ArrayList <String> attackDamageList = new ArrayList();
	private ArrayList <String> attackTypeList = new ArrayList();
	private ArrayList <String> attackEffectList = new ArrayList();
	private ArrayList <String> selfEffectList = new ArrayList();*/

	HashMap<String, Integer> attackMap = new HashMap<>();
	
	public Nommer(String takenVals) {
		String [] NommerValueArray = takenVals.split(", ");
		
		name = NommerValueArray[0];
		startingHealth = Integer.parseInt(NommerValueArray[1]);
		health = startingHealth;
		weakness = NommerValueArray[2];
		resistance = NommerValueArray[3];
		type = NommerValueArray[4];
		numberOfAttacks = Integer.parseInt(NommerValueArray[5]);
		attack = NommerValueArray[6];
		//startingAttackDamage;
		stringStartingAttackDamage = NommerValueArray[7];
		attackDamage = startingAttackDamage;
		attackType = NommerValueArray[8];
		attackEffect = NommerValueArray[9];
		selfEffect = NommerValueArray[10];

		if(numberOfAttacks == 1){

			startingAttackDamage = Integer.parseInt(NommerValueArray[7]);

			attackList = new String[1]; //AttackName
			attackList[0] = attack;

			attackDamageList = new int[1];
			attackDamageList[0] = startingAttackDamage;

			attackTypeList = new String[1]; 
			attackTypeList[0] = attackType;

			attackEffectList = new String[1]; //
			attackEffectList[0] = attackEffect;

			selfEffectList = new String[1]; //
			selfEffectList[0] = selfEffect;

			System.out.println(attackList[0]);
			System.out.println(attackDamageList[0]);
		}

//String[] arrOfStr = str.split("@", 2);

		//NEW CODE
		else if(numberOfAttacks > 1){

			attackList = new String[numberOfAttacks];
			attackDamageList = new int[numberOfAttacks];
			stringAttackDamageList = new String[numberOfAttacks];
			attackTypeList = new String[numberOfAttacks];
			attackEffectList = new String[numberOfAttacks];
			selfEffectList = new String[numberOfAttacks];

		
			System.out.println("Nommer's attacks");

				attackList = attack.split("/ ");

				// string attack values split
				stringAttackDamageList = stringStartingAttackDamage.split("/ ");
				
				//convert string to int

				for(int i = 0; i < numberOfAttacks; i++){
					attackDamageList[i] = Integer.parseInt(stringAttackDamageList[i]);
				}

				attackTypeList = attackType.split("/ ");
				attackEffectList = attackEffect.split("/ ");
				selfEffectList = selfEffect.split("/ ");


				System.out.println(attackList[0]);
				System.out.println(attackList[1]);
				System.out.println(attackDamageList[0]);
				System.out.println(attackDamageList[1]);
				
	  }
		
		//OLD CODE

		/*for(int i = 0; i< numberOfAttacks; i++){
			int attackParseCounter = (i+1) * 5 -1;
			attackMap.put(NommerValueArray[attackParseCounter], Integer.parseInt(NommerValueArray[attackParseCounter+1]));
		}*/
	}

	public String returnDescription(){
		return name + ". A " + type + " type, weak against " + weakness + " types and resistance against " + resistance + " types. It has " + startingHealth + " health.";
	}

	public String returnAttacks(){
		String attak;
		String concatAttak;

		if(numberOfAttacks == 1){
			 return "It's main attack is " + attack + ", which deals " + attackDamage + " base damage. This attack's effect on the enemy is " + attackEffect + ". It's effect on the user is " + selfEffect + ".";
		} else {
			 attak = "It has " +  numberOfAttacks + " attacks.";
			 for(int i = 0; i < numberOfAttacks; i++){
				 int iPlus1 = i+= 1;
				 concatAttak = " It's attack #" + iPlus1 + " is " + attackList[i] + ", which deals " + attackDamageList[i] + " base damage. This attack's effect on the enemy is " + attackEffectList[i] + ". It's effect on the user is " + selfEffectList[i] + ".";
				 attak = attak.concat(concatAttak);
				 
			 }
			 return attak;
		}
		
	}

	//status effects
	public int burn(){
		if(turnsWithFX<1){
			health -= health/10;
			System.out.println("\n" + name + " got burned. 1/10 of their health got taken. They now have " + health + " health\n");
			turnsWithFX++;
			return health;
		}
		else if(turnsWithFX < 6){
			System.out.println("\n" + name + " got burned. 1/10 of their health got taken. They now have " + health + " health\n");
			health -= health/10;
			turnsWithFX++;
			return health;
		} else {
			System.out.println("\n" + name + " extinguished the fire");
			int FXRemover = statusEffects.indexOf("burned");
			statusEffects.remove(FXRemover);
			turnsWithFX = 0;
		}
		return 73;
	}
	public int poison(){
		if(turnsWithFX<1){
			health -= health/7;
			System.out.println("\n" + name + " got poisoned. 1/7 of their health got taken. They now have " + health + " health\n");
			turnsWithFX++;
			return health;
		}
		else if(turnsWithFX < 4){
			System.out.println("\n" + name + " got poisoned. 1/7 of their health got taken. They now have " + health + " health\n");
			health -= health/7;
			turnsWithFX++;
			return health;
		} else {
			System.out.println("\n" + name + " made the bad poison go away\n");
			int FXRemover = statusEffects.indexOf("poison");
			statusEffects.remove(FXRemover);
			turnsWithFX = 0;
		}
		return 73;
	}

	public int zap(){
		if(turnsWithFX<1){
			isZapped = true;
			System.out.println("\n" + name + " got zapped. They might not be able to move during the next 3 attacks\n");
			turnsWithFX++;
		}
		if(turnsWithFX < 3){
			isZapped = true;
			turnsWithFX++;
		} else {
			System.out.println("\n" + name + " cut the current\n");
			int FXRemover = statusEffects.indexOf("zap");
			statusEffects.remove(FXRemover);
			turnsWithFX = 0;
			isZapped = false;
		}
		return 73;
	}

	public int recoil(double damageDealt){
		health -= (int)damageDealt/2;
		System.out.println("\n" + name + " got damaged by the recoil. They now have " + health + " health");
		return health;
	}

	public int heal(double damageDealt){
		health += (int) damageDealt/3;
		System.out.println("\n" + name + " healed themselves. They now have " + health + " health");
		return health;
	}
	
	public int repeat(double damageDealt){
		Random randomRepeat = new Random();
		int timesHit = randomRepeat.nextInt(5) + 1;
		int totalDamageDealt = timesHit * (int) damageDealt;
		System.out.println("\nThe attack hits " + timesHit + " more time(s). It deals " + totalDamageDealt + " more damage");
		return totalDamageDealt;
	}


	//getters

	public String getName(){
		return name;
	}

	public int getCurrentHealth(){
		return health;
	}

	public String getType(){
		return type;
	}

	public int getMaxHealth(){
		return startingHealth;
	}

	public String getAttack(int i){
		return attackList[i];
	}

	public String getAttackListString(){
		return Arrays.toString(attackList);
	}

	public String[] getAttackList(){
		return attackList;
	}

	public int getBaseAttackDamage(int i){
		return attackDamageList[i];
	}

	public String getAttackDamageListString(){
		String attackDamageListToString = Arrays.toString(attackDamageList);
		return attackDamageListToString;
	}

	public int[] getAttackDamageList(){
		return attackDamageList;
	}

	public HashMap<String, Integer> getAttackMap(){
		return attackMap;
	}

/*	public String getAttackType(){
		return attackType;
	}*/

	public String getWeakness(){
		return weakness;
	}

	public String getResistance(){
		return resistance;
	}

	public String getHasFainted(){
		return hasFainted;
	}

	public String getAttackEffect(int i){
		return attackEffectList[i];
	}

	public String getSelfEffect(int i){
		return selfEffectList[i];
	}

	public int getTurnsWithEffect(){
		return turnsWithFX;
	}

	public boolean getIsZapped(){
		if(isZapped){
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<String> getStatusEffects(){
		return statusEffects;
	}


	//setters

	public void setHealth(int h){
		health = h;
	}

	public void setHasFainted(String f){
		hasFainted = f;
	}

	public void setStatusEffect(String s){
		statusEffects.add(s);
	}

	public void clearStatusEffect(){
		statusEffects.clear();
	}


	// toString

	public String toString (){
		return name;
	}

}