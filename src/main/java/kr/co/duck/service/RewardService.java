package kr.co.duck.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.duck.beans.RewardBean;
import kr.co.duck.domain.Member;
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
		if (member.getWinNum() == 1) {
			Reward reward = new Reward("병아리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (member.getWinNum() == 40) {
			Reward reward = new Reward("닭", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (member.getWinNum() == 60) {
			Reward reward = new Reward("봉황", member);
			quizCommand.saveReward(reward);
			return true;
		} else {
			return false;
		}
	}

	public boolean createLoseReward(Member member) {
		if (member.getLoseNum() == 1) {
			Reward reward = new Reward("병든 병아리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (member.getLoseNum() == 40) {
			Reward reward = new Reward("병든 닭", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (member.getLoseNum() == 60) {
			Reward reward = new Reward("그는 좋은 닭이었습니다...", member);
			quizCommand.saveReward(reward);
			return true;
		} else {
			return false;
		}
	}

	public boolean createTotalGameReward(Member member) {
		if (member.getTotalGameNum() == 1) {
			Reward reward = new Reward("게임에 중독된 병아리", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (member.getTotalGameNum() == 40) {
			Reward reward = new Reward("게임에 중독된 닭", member);
			quizCommand.saveReward(reward);
			return true;
		} else if (member.getTotalGameNum() == 60) {
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
