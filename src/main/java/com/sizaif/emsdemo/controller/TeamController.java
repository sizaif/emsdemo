package com.sizaif.emsdemo.controller;

import com.sizaif.emsdemo.dto.TeamVO;
import com.sizaif.emsdemo.pojo.Contest.Contest;
import com.sizaif.emsdemo.pojo.Contest.Team;
import com.sizaif.emsdemo.service.Contest.ContestService;
import com.sizaif.emsdemo.service.Team.TeamService;
import com.sizaif.emsdemo.service.User.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.sizaif.emsdemo.config.UserConfig.contestalonetype;
import static com.sizaif.emsdemo.config.UserConfig.contestteamtype;

/**
 * @author ：sizaif
 * @date ：Created in 2020/4/28 15:22
 * @description：组队控制层
 * @modified By：sizaif
 * @version: v1.0$
 */


@Controller
@RequestMapping("/team")
public class TeamController {


    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;
    @Autowired
    private ContestService contestService;

    @Autowired
    private MemberService memberService;







}
