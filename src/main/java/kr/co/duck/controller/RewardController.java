package kr.co.duck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.duck.beans.RewardBean;
import kr.co.duck.service.RewardService;
import kr.co.duck.util.ResponseUtil;
import kr.co.duck.util.UserDetailsImpl;

@RestController
public class RewardController {
	private final RewardService rewardService;

	// 생성자
	public RewardController(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	// 모든 리워드 조회

	@GetMapping("/rewards")
	public ResponseEntity<RewardBean> allReward(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseUtil.response(rewardService.allRewardList(userDetails.getMember()));
	}
}
