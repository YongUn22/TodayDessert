package com.final_project.TodayDessert.controller;

import com.final_project.TodayDessert.dto.MemberRegisterFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/members")
@Controller
@RequiredArgsConstructor
public class MyPageController {

    @GetMapping(value = "/myPage")
    public String myPage(Model model) {
        MemberRegisterFormDto memberRegisterFormDto = new MemberRegisterFormDto();
        memberRegisterFormDto.setMemberId(memberRegisterFormDto.getMemberId());
        model.addAttribute("memberRegisterFormDto", memberRegisterFormDto);
        return "myPage";

    }

}
