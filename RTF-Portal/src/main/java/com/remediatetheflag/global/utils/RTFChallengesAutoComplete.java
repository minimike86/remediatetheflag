/*
 *  
 * REMEDIATE THE FLAG
 * Copyright 2018 - Andrea Scaduto 
 * remediatetheflag@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.remediatetheflag.global.utils;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ChallengeStatus;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;

public class RTFChallengesAutoComplete implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(RTFChallengesAutoComplete.class);
	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void run() {
		List<Challenge> expired = hpc.getAllExpiredChallenges();
		for(Challenge expiredChallenge : expired) {
			expiredChallenge.setStatus(ChallengeStatus.FINISHED);
			expiredChallenge.setLastActivity(new Date());
			hpc.updateChallenge(expiredChallenge);
			logger.debug("Updating status for challenge "+expiredChallenge.getName()+" to COMPLETED");
		}	
		List<Challenge> notStarted = hpc.getAllNotStartedChallenges();
		for(Challenge notStartedChallenge : notStarted) {
			notStartedChallenge.setStatus(ChallengeStatus.IN_PROGRESS);
			notStartedChallenge.setLastActivity(new Date());
			hpc.updateChallenge(notStartedChallenge);
			logger.debug("Updating status for challenge "+notStartedChallenge.getName()+" to IN PROGRESS");
		}
		
	}
}
