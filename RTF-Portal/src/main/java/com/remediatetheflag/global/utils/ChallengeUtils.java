package com.remediatetheflag.global.utils;

import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.Flag;
import com.remediatetheflag.global.model.FlagQuestion;

public class ChallengeUtils {

	public static Integer getFlagPositionInChallenge(Challenge challenge, FlagQuestion fq) {

		Integer instances = -1;
		for(ExerciseInstance i : challenge.getRunExercises()) {
			if(hasRun(i, fq)) {
				instances++;
			}
		}
		return instances;
	}

	public static Integer getIncreaseResultFromPosition(Challenge challenge, Integer position) {
		Integer result = null;
		switch(position) {
		case 0:
			result = challenge.getFirstInFlag();
			break;
		case 1:
			result = challenge.getSecondInFlag();
			break;
		case 2:
			result = challenge.getThirdInFlag();
			break;
		}	
		if(result==0)
			return null;
		else
			return result;
	}

	private static Boolean hasRun(ExerciseInstance i, FlagQuestion fq) {
		for(Flag f : i.getAvailableExercise().getFlags()) {
			for(FlagQuestion flq : f.getFlagQuestionList()) {
				if(flq.getSelfCheckAvailable() && flq.getSelfCheckName().equals(fq.getSelfCheckName())) {
					return true;
				}
			}
		}
		return false;
	}

	
}
