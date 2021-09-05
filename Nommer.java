import java.util.*;

class Nommer {


	private String weakness;
	private String resistance;
	private String hasFainted = "no";
	private int turnsWithFX = 0;

	private String name;
	private String type;
	private int health, startingHealth;
	private String attack;
	private int attackDamage, startingAttackDamage, numberOfAttacks;
	private String attackType;
	private String attackEffect = "none";
	private String selfEffect = "none";
	private boolean isZapped = false;
	/*
	private String isBurned = "no";
	private int burnCounter = 0;
	private String isPoisoned = "no";
	private int poisonCounter = 0;
	*/
	private ArrayList <String> statusEffects = new ArrayList();
	private ArrayList <String> attacks = new ArrayList();
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
		startingAttackDamage = Integer.parseInt(NommerValueArray[7]);
		attackDamage = startingAttackDamage;
		attackType = NommerValueArray[8];
		attackEffect = NommerValueArray[9];
		selfEffect = NommerValueArray[10];

		//I'll have to implement multiple attacks later lol
		for(int i = 0; i< numberOfAttacks; i++){
			int attackParseCounter = (i+1) * 5 -1;
			attackMap.put(NommerValueArray[attackParseCounter], Integer.parseInt(NommerValueArray[attackParseCounter+1]));
		}
	}

	public String returnDescription(){
		return name + ". A " + type + " type, weak against " + weakness + " types and resistance against " + resistance + " types. It has " + startingHealth + " health. It's main attack is " + attack + ", which deals " + attackDamage + " base damage. This attack's effect on the enemy is " + attackEffect + ". It's effect on the user is " + selfEffect + ".";
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
		health -= (int)damageDealt/3;
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

	public String getAttack(){
		return attack;
	}

	public int getBaseAttackDamage(){
		return startingAttackDamage;
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

	public String getAttackEffect(){
		return attackEffect;
	}

	public String getSelfEffect(){
		return selfEffect;
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