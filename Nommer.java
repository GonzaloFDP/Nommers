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
		return name + ". A " + type + " type, weak against " + weakness + " types and resistance against " + resistance + " types. It has " + startingHealth + " health. It's main attack is " + attack + ", which is a " + attackType + " type attack that deals " + attackDamage + " base damage. This attack has " + attackEffect + " effect on the enemy. It has " + selfEffect + " effect on the user";
	}

	//status effects
	public int burn(){
		if(turnsWithFX < 6){
			health -= health/10;
			System.out.println(name + " got burned. 1/10 of their health got taken. They now have " + health + " health");
			turnsWithFX++;
			return health;
		} else {
			System.out.println(name + " extinguished the fire");
			int FXRemover = statusEffects.indexOf("burned");
			statusEffects.remove(FXRemover);
			
		}
		return 73;
	}
	public int poison(){
		if(turnsWithFX < 4){
			health -= health/7;
			System.out.println(name + " got poisoned. 1/7 of their health got taken. They now have " + health + " health");
			turnsWithFX++;
			return health;
		} else {
			System.out.println(name + " made the bad poison go away");
			int FXRemover = statusEffects.indexOf("poison");
			statusEffects.remove(FXRemover);
		}
		return 73;
	}

	public int freeze(){
		if(turnsWithFX < 3){
			health -= health/5;
			System.out.println(name + " got frozen. 1/5 of their health got taken. They now have " + health + " health");
			turnsWithFX++;
			return health;
		} else {
			System.out.println(name + " broke out of the glass");
			int FXRemover = statusEffects.indexOf("freeze");
			statusEffects.remove(FXRemover);
		}
		return 73;
	}

	public int recoil(double damageDealt){
		health -= (int)damageDealt/3;
		System.out.println(name + " got damaged by the recoil. They now have " + health + " health");
		return health;
	}

	public int heal(double damageDealt){
		health += (int) damageDealt/3;
		System.out.println(name + " healed themselves. They now have " + health + " health");
		return health;
	}
	
	public int repeat(){
		Random randomRepeat = new Random();
		int timesHit = randomRepeat.nextInt(5) + 2;
		System.out.println(timesHit);
		return timesHit;
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


	// toString

	public String toString (){
		return name;
	}

}