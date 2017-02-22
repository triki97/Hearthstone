package GameLogic;

import Minions.*;

public class MinionCreator {
	public static Minions createMinion(int id){
		if (id==1) return new CS103StudentMinion();
		if (id==2) return new CS104StudentMinion();
		if (id==6) return new LeaveysRevengeMinion();
		if (id==7) return new CS201StudentMinion();
		if (id==9) return new RecruiterMinion();
		if (id==10) return new AngryBackPackMinion();
		if (id==13) return new ProphetTAMinion();
		if (id==16) return new ABadGEMinion();
		if (id==17) return new StressedOutStudentMinion();
		
		if (id==18) return new CS201MidtermMinion();
		if (id==19) return new ProgrammingAssignmentMinion();
		if (id==21) return new SadGithubAvatarMinion();
		if (id==24) return new PopQuizMinion();
		if (id==25) return new AaronCoteMinion();
		if (id==27) return new MichaelShindlerMinion();
		if (id==28) return new MarkRedekoppMinion();
		if (id==30) return new LeonardAdlemanMinion();
		if (id==31) return new JeffreyMillerPHDMinion();
		if (id==34) return new PHDStudentMinion();
		if (id==36) return new SatyRaghavacharyMinion();
		if (id==37) return new TheFinalMinion();
		if (id==38) return new VirusMinion();
		
		return null;
	}
}
