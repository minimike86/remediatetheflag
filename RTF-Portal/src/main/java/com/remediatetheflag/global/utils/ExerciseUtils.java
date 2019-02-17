package com.remediatetheflag.global.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseResult;
import com.remediatetheflag.global.model.ExerciseResultStatus;
import com.remediatetheflag.global.model.ExerciseScoringMode;
import com.remediatetheflag.global.model.Flag;
import com.remediatetheflag.global.model.FlagQuestion;
import com.remediatetheflag.global.model.FlagQuestionHint;
import com.remediatetheflag.global.model.SelfCheckResult;

public class ExerciseUtils {

	public static Integer getHintPercentageReduction(FlagQuestionHint hint, ExerciseInstance exerciseInstance) {
		for(FlagQuestionHint usedHint : exerciseInstance.getUsedHints()) {
			if(usedHint.getId().equals(hint.getId())) {
				return hint.getScoreReducePercentage();
			}
		}
		return null;
	}

	public static Boolean isInScope(String name, List<Flag> flags) {
		for(Flag f :flags) {
			for(FlagQuestion fq : f.getFlagQuestionList()) {
				if(fq.getSelfCheckAvailable() && fq.getSelfCheckName().equals(name))
					return true;
			}
		}
		return false;
	}
	public static Integer updateExerciseResults(ExerciseInstance instance, List<SelfCheckResult> results, Challenge exerciseChallenge) {
		Integer totalScore = 0;
		for(ExerciseResult orig : instance.getResults()){
			for(SelfCheckResult rev : results){
				if(rev.getName().equals(orig.getName()) && orig.getStatus().equals(ExerciseResultStatus.VULNERABLE) && !rev.getIsVulnerable()) {
					orig.setStatus(ExerciseResultStatus.NOT_VULNERABLE);
					FlagQuestion fq = getFlagQuestionFromSelfcheckName(instance,rev.getName());
					if(fq!=null) {
						Integer hintPercentageReduction = ExerciseUtils.getHintPercentageReduction(fq.getHint(),instance);
						if(null==hintPercentageReduction) {
							orig.setScore(fq.getMaxScore());
						}
						else {
							orig.setHint(true);
							orig.setScore(fq.getMaxScore() - (int)Math.round((fq.getMaxScore() * hintPercentageReduction) / 100.0));
						}
					}
					if(null!=exerciseChallenge) {
						Integer challengePosition = ChallengeUtils.getFlagPositionInChallenge(exerciseChallenge, fq);
						Integer challengePositionIncrease = ChallengeUtils.getIncreaseResultFromPosition(exerciseChallenge, challengePosition);
						if(null!=challengePositionIncrease) {
							switch(challengePosition) {
							case 0:
								orig.setFirstForFlag(true);
								break;
							case 1:
								orig.setSecondForFlag(true);
								break;
							case 2:
								orig.setThirdForFlag(true);
								break;
							}	
							orig.setScore(orig.getScore() + (int)Math.ceil((fq.getMaxScore() * challengePositionIncrease) / 100.0));
						}
					}
					orig.setLastChange(new Date());
					totalScore+=orig.getScore();
					break;
				}
			}
		}
		return totalScore;
	}
	public static FlagQuestion getFlagQuestionFromSelfcheckName(ExerciseInstance instance, String name) {
		for(Flag f : instance.getAvailableExercise().getQuestionsList()) {
			for(FlagQuestion fq : f.getFlagQuestionList()) {
				if(fq.getSelfCheckAvailable() && fq.getSelfCheckName().equals(name)){
					return fq;
				}
			}
		}
		return null;
	}

	public static ExerciseResult getExerciseResult(ExerciseInstance instance, Flag flag, FlagQuestion fq, List<SelfCheckResult> results, Challenge exerciseChallenge ) {
		for(SelfCheckResult rev : results){
			if(fq.getSelfCheckAvailable() && fq.getSelfCheckName().equals(rev.getName())){
				ExerciseResult result = new ExerciseResult();
				if(rev.getIsVulnerable()) {
					result.setStatus(ExerciseResultStatus.VULNERABLE);
					result.setScore(0);
				}
				else {
					result.setStatus(ExerciseResultStatus.NOT_VULNERABLE);
					Integer hintPercentageReduction = ExerciseUtils.getHintPercentageReduction(fq.getHint(),instance);
					if(null==hintPercentageReduction) {
						result.setScore(fq.getMaxScore());
					}
					else {
						result.setHint(true);
						result.setScore(fq.getMaxScore() - (int)Math.ceil((fq.getMaxScore() * hintPercentageReduction) / 100.0));
					}
					if(null!=exerciseChallenge) {
						Integer challengePosition = ChallengeUtils.getFlagPositionInChallenge(exerciseChallenge, fq);
						Integer challengePositionIncrease = ChallengeUtils.getIncreaseResultFromPosition(exerciseChallenge, challengePosition);
						if(null!=challengePositionIncrease) {
							switch(challengePosition) {
							case 0:
								result.setFirstForFlag(true);
								break;
							case 1:
								result.setSecondForFlag(true);
								break;
							case 2:
								result.setThirdForFlag(true);
								break;
							}	
							result.setScore(result.getScore() + (int)Math.ceil((fq.getMaxScore() * challengePositionIncrease) / 100.0));
						}

					}

				}
				result.setComment(null);
				result.setLastChange(new Date());
				result.setVerified(false);
				if(instance.getScoring().equals(ExerciseScoringMode.MANUAL_REVIEW)) {
					result.setAutomated(false);
				}
				else {
					result.setAutomated(true);
				}
				result.setName(rev.getName());
				result.setCategory(flag.getCategory());
				return result;
			}
		}
		return null;
	}

	public static ExerciseResult getExerciseResult(ExerciseInstance instance,Challenge exerciseChallenge, Map.Entry<String, JsonElement> entry) {
		for(Flag flag : instance.getAvailableExercise().getFlags()){
			for(FlagQuestion fq : flag.getFlagQuestionList()){
				if(fq.getSelfCheckAvailable() && fq.getSelfCheckName().equals(entry.getKey())){
					ExerciseResult r = new ExerciseResult();
					r.setName(entry.getKey());
					r.setCategory(flag.getCategory());
					r.setVerified(false);

					r.setLastChange(new Date());
					if(instance.getScoring().equals(ExerciseScoringMode.MANUAL_REVIEW))
						r.setAutomated(false);
					else
						r.setAutomated(true);

					Boolean status = entry.getValue().getAsBoolean();
					if(status.equals(true)){
						r.setStatus(ExerciseResultStatus.VULNERABLE);
						r.setScore(0);
					}
					else{
						r.setStatus(ExerciseResultStatus.NOT_VULNERABLE);
						Integer hintPercentageReduction = ExerciseUtils.getHintPercentageReduction(fq.getHint(),instance);
						if(null==hintPercentageReduction) {
							r.setScore(fq.getMaxScore());
						}
						else {
							r.setHint(true);
							r.setScore(fq.getMaxScore() - (int)Math.ceil((fq.getMaxScore() * hintPercentageReduction) / 100.0));
						}
						if(null!=exerciseChallenge) 	{
							Integer challengePosition = ChallengeUtils.getFlagPositionInChallenge(exerciseChallenge, fq);
							Integer challengePositionIncrease = ChallengeUtils.getIncreaseResultFromPosition(exerciseChallenge, challengePosition);
							if(null!=challengePositionIncrease) {
								switch(challengePosition) {
								case 0:
									r.setFirstForFlag(true);
									break;
								case 1:
									r.setSecondForFlag(true);
									break;
								case 2:
									r.setThirdForFlag(true);
									break;
								}	
								r.setScore(r.getScore() + (int)Math.ceil((fq.getMaxScore() * challengePositionIncrease) / 100.0));
							}
						}
					}
					return r;
				}
			}
		}
		return null;
	}
}