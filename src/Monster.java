enum Role{
	Artillery,
	Brute,
	Controller,
	Lurker,
	Skirmisher,
	Soldier
}

enum Difficulty {
	Normal,
	Minion,
	Elite,
	Solo
}

public class Monster {
	String mName;
	String mSource;
	String[] mTags;
	int mLevel;
	Difficulty mDifficulty;
	Role mRole;
	int mHP, mAC, mFortitude, mReflex, mWill;
	Attack[] mAttacks;
	public Monster(String name, int level, Difficulty difficulty, Role role, boolean useMM3Adjustment) {
		mName = name;
		mSource = "Custom";
		mTags = new String[0];
		mLevel = level;
		mDifficulty = difficulty;
		mRole = role;
		mAC = 14 + level;
		mFortitude = mReflex = mWill = 12 + level;
		int attackBonus = 5 + level;
		switch (role) {	// Adjust AC
		case Soldier:
			mAC += 2;
			break;
		case Brute:
		case Artillery:
			mAC -= 2;
			break;
		}
		if (!useMM3Adjustment) {
			switch (role) {	// Adjust to-hit bonus
			case Brute:
				attackBonus -= 2;
				break;
			case Soldier:
			case Artillery:
				attackBonus += 2;
				break;
			}
			switch (role) {	// HP
			
			}
		}
		else {
			mHP = 24 + 8 * level;
			switch (role) {	// HP
			case Brute:
				mHP += 2 + 2 * level;
				break;
			case Artillery:
			case Lurker:
				mHP -= 3 + 2 * level;
				break;
			}
		}
		mAttacks = new Attack[3];
		mAttacks[0].name = "Attack vs. AC";
		mAttacks[0].target = Target.AC;
		mAttacks[0].toHit = attackBonus;
		mAttacks[1].name = "Attack vs Other Defense";
		mAttacks[1].target = Target.Fortitude;
		mAttacks[1].toHit = useMM3Adjustment ? attackBonus : attackBonus - 2;
		mAttacks[2].name = "Attack vs Multiple Targets";
		mAttacks[2].target = Target.Reflex;
		mAttacks[2].toHit = attackBonus - 2;
		mAttacks[0].damageBonus = mAttacks[1].damageBonus = 8 + level;
		mAttacks[2].damageBonus = (8 + level) * 3 / 4;
	}
	public Monster(String name, String source, String[] tags,
			int level, Difficulty difficulty, Role role,
			int hp, int ac,
			int fortitude, int reflex, int will,
			Attack[] attacks) {
		mName = name;
		mSource = source;
		mTags = tags;
		mLevel = level;
		mDifficulty = difficulty;
		mRole = role;
		mHP = hp;
		mAC = ac;
		mFortitude = fortitude;
		mReflex = reflex;
		mWill = will;
		mAttacks = attacks;
	}
	public Monster getLevelAdjustedMonster(int newLevel, boolean useMM3Adjustment, boolean useBoHAdjustment) {
		int levelDiff = newLevel - mLevel;
		int hpPerLevel = 0;
		switch (mRole) {
		case Brute:
			hpPerLevel = 10;
			break;
		case Artillery:
		case Lurker:
			hpPerLevel = 6;
			break;
		default:
			hpPerLevel = 8;
			break;
		}
		Attack[] newAttacks = new Attack[mAttacks.length];
		for (int i = 0; i < mAttacks.length; i++) {
			newAttacks[i] = mAttacks[i];
			newAttacks[i].toHit += levelDiff;
			if (useMM3Adjustment)
				newAttacks[i].damageBonus += levelDiff;
			else
				newAttacks[i].damageBonus += levelDiff / 2;
		}
		return new Monster(mName, "Custom", mTags,
				newLevel, mDifficulty, mRole,
				(useBoHAdjustment) ? (mHP + levelDiff * hpPerLevel - newLevel * 3) : (mHP + levelDiff * hpPerLevel),	// HP
				mAC + levelDiff,	// AC
				mFortitude + levelDiff,	// Fortitude
				mReflex + levelDiff,	// Reflex
				mWill + levelDiff,		// Will
				newAttacks);
	}
	public Monster getDifficultyAdjustedMonster(Difficulty newDifficulty) {
		if (newDifficulty == mDifficulty)
			return this;
		switch (newDifficulty) {
		case Minion:
			break;
		case Normal:
			break;
		case Elite:
			break;
		case Solo:
			break;
		}
	}
}