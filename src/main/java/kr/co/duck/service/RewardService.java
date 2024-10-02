package kr.co.duck.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.duck.beans.RewardBean;
import kr.co.duck.domain.Member;
import kr.co.duck.domain.MemberGameStats;
import kr.co.duck.domain.QuizCommand;
import kr.co.duck.domain.Reward;
import kr.co.duck.repository.RewardRepository;

@Service
public class RewardService {

	private final RewardRepository rewardRepository;
	private final QuizCommand quizCommand;

	// 생성자
	public RewardService(RewardRepository rewardRepository, QuizCommand quizCommand) {
		this.rewardRepository = rewardRepository;
		this.quizCommand = quizCommand;
	}

	@Transactional
	public boolean createWinReward(Member member) {
		MemberGameStats stats = member.getMemberGameStats(); // 게임 통계 정보 가져오기
		if (stats.getWinNum() == 1) {
			Reward reward = new Reward("병아리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (stats.getWinNum() == 40) {
			Reward reward = new Reward("오리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (stats.getWinNum() == 60) {
			Reward reward = new Reward("봉황", member);
			quizCommand.saveReward(reward);
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean createLoseReward(Member member) {
		MemberGameStats stats = member.getMemberGameStats(); // 게임 통계 정보 가져오기
		if (stats.getLoseNum() == 1) {
			Reward reward = new Reward("병든 병아리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (stats.getLoseNum() == 40) {
			Reward reward = new Reward("병든 오리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (stats.getLoseNum() == 60) {
			Reward reward = new Reward("그는 좋은 오리이었습니다...", member);
			quizCommand.saveReward(reward);
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean createTotalGameReward(Member member) {
		MemberGameStats stats = member.getMemberGameStats(); // 게임 통계 정보 가져오기
		if (stats.getTotalGameNum() == 1) {
			Reward reward = new Reward("게임에 중독된 오리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (stats.getTotalGameNum() == 40) {
			Reward reward = new Reward("게임에 중독된 오리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (stats.getTotalGameNum() == 60) {
			Reward reward = new Reward("이제 그만 인생을 살아가세요 휴먼...", member);
			quizCommand.saveReward(reward);
			return true;
		} else {
			return false;
		}
	}

	public RewardBean allRewardList(Member member) {
		List<Reward> rewardList = rewardRepository.findByMember(member);
		RewardBean rewardBean = new RewardBean();
		for (Reward reward : rewardList) {
			rewardBean.addReward(reward.getRewardName()); // 메서드 이름 변경에 따라 수정
		}
		return rewardBean;
	}
}
