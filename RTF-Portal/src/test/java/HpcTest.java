public class HpcTest {

	/*
	public ExerciseInstance getCompleteExerciseInstanceFull(Integer id) {
		HibernateSessionTransactionWrapper hb = openSessionTransaction();

		ExerciseInstance ei = (ExerciseInstance) hb.localSession.createQuery("from ExerciseInstance ei "
				+ "where "
				+ "ei.idExerciseInstance = :instanceId "
				+ "and ei.status >= 3")
				.setParameter( "instanceId", id)
				.getSingleResult();
		Hibernate.initialize(ei.getResultFile());
		Hibernate.initialize(ei.getUsedHints());
		Hibernate.initialize(ei.getUser());
		for(ExerciseResult er : ei.getResults()){
			Hibernate.initialize(er);
		}
		closeSessionTransaction(hb);

		return ei;
	}
	
	*
	*private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public void cloneExerciseInstance(Integer exerciseId, Integer userId) {
		
		User newUser = this.getUserFromUserId(userId);
		HibernateSessionTransactionWrapper hb = openSessionTransaction();
		
		try{
			ExerciseInstance ei = (ExerciseInstance) hb.localSession.createQuery("from ExerciseInstance ei "
					+ "where "
					+ "ei.idExerciseInstance = :instanceId "
					+ "and ei.status >= 3")
					.setParameter( "instanceId", exerciseId)
					.getSingleResult();
			Hibernate.initialize(ei.getResultFile());
			Hibernate.initialize(ei.getUsedHints());
			Hibernate.initialize(ei.getReviewer());
			Hibernate.initialize(ei.getUser());
			List<ExerciseResult> tmp = new LinkedList<ExerciseResult>();
			for(ExerciseResult er : ei.getResults()){
				Hibernate.initialize(er);
				hb.localSession.detach(er);
				er.setIdResult(null);
				tmp.add(er);
			}
			hb.localSession.detach(ei);
			ei.setIdExerciseInstance(null);
			ei.setResults(tmp);
			ei.setGuac(null);
			ei.setDuration(getRandomNumberInRange(8,23));
			ei.setUser(newUser);
			ExerciseScore newScore = new ExerciseScore();
			newScore.setResult(ei.getScore().getResult());
			newScore.setTotal(ei.getScore().getTotal());
			ei.setScore(newScore);
			ei.setChallengeId(null);
			ei.setOrganization(newUser.getDefaultOrganization());
			hb.localSession.persist(ei);
			closeSessionTransaction(hb);
			newUser.setExercisesRun(newUser.getExercisesRun()+1);
			newUser.setScore(newUser.getScore()+ei.getScore().getTotal());
			updateUserInfo(newUser);
		}catch(Exception e){
			logger.error(e.getMessage());
			closeSessionTransaction(hb);
			
		}
	}
	*
	*
	*
	*/
	
}
