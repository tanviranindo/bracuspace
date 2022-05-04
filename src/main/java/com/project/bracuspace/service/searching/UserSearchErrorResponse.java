package com.project.bracuspace.service.searching;

import com.project.bracuspace.service.user.UserDTOService;
import com.project.bracuspace.web.PagingSize;
import com.project.bracuspace.web.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class UserSearchErrorResponse {

    private final UserDTOService UserDTOService;

    @Autowired
    public UserSearchErrorResponse(UserDTOService UserDTOService) {
        this.UserDTOService = UserDTOService;
    }

    public ModelAndView respondToNumberFormatException(UserSearchResult userSearchResult, ModelAndView modelAndView) {
        Pager pager = new Pager(userSearchResult.getUserPage().getTotalPages(), userSearchResult.getUserPage().getNumber(), PagingSize.BUTTONS_TO_SHOW, userSearchResult.getUserPage().getTotalElements());

        modelAndView.addObject("numberFormatException", true);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("users", userSearchResult.getUserPage());
        modelAndView.setViewName("admin/user/users");
        return modelAndView;
    }

    public ModelAndView respondToEmptySearchResult(ModelAndView modelAndView, PageRequest pageRequest) {
        modelAndView.addObject("noMatches", true);
        modelAndView.addObject("users", UserDTOService.findAllPageable(pageRequest));
        return modelAndView;
    }
}
