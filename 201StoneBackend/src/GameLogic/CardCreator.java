package GameLogic;

import Spells.AskOnPiazzaSpell;
import Spells.BallmerPeakSpell;
import Spells.MomsCookingSpell;
import Spells.NewChalkSpell;
import Spells.PublishAPaperSpell;
import Spells.*;
public class CardCreator {
	public static Card createCard(int id){
		if (id==3) return new AskOnPiazzaSpell();
		if (id==4) return new BallmerPeakSpell();
		if (id==5) return new MomsCookingSpell();
		if (id==8) return new FightOnSpell();
		if (id==11) return new BrainBlastSpell();
		if (id==12) return new OfficeHoursSpell();
		if (id==14) return new FreeTshirtSpell();
		if (id==15) return new TheHearthstoneRuleSpell();
		
		if (id==20) return new PublishAPaperSpell();
		if (id==22) return new CatchColludingStudentsSpell();
		if (id==23) return new NewChalkSpell();
		if (id==26) return new ShowOffThePHDSpell();
		if (id==29) return new MajorBreakthroughSpell();
		if (id==32) return new ANiceSuitSpell();
		if (id==33) return new AdviseInIndustrySpell();
		if (id ==35) return new TakeA5MinuteBreakSpell();

		System.out.println("NOT FOUND ID: "+ id);
		return new Card();
	}
}
