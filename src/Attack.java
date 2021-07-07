enum Target {
	AC,
	Fortitude,
	Reflex,
	Will
}

enum Condition {
	Blinded,
	Dazed,
	Deafened,
	Dominated,
	Dying,
	Helpless,
	Immobilized,
	Insubstantial,
	Marked,
	Petrified,
	Prone,
	Restrained,
	Slowed,
	Stunned,
	Surprised,
	Unconscious,
	Weakened
}

public class Attack {
	String name;
	String description;
	int toHit;
	Target target;
	int damageDie, numDie, damageBonus;
	Condition[] conditions;
	int averageDamage() {
		return (numDie * damageDie + numDie) / 2 + damageBonus;
	}
}