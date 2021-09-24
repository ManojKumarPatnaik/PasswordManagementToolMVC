package com.epam.rd.controller.mvc;

import com.epam.rd.dto.GroupDTO;
import com.epam.rd.dto.RecordDTO;
import com.epam.rd.entity.Record;
import com.epam.rd.exception.*;
import com.epam.rd.service.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.epam.rd.entity.Group;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = {"", "group"})
public class GroupController {
    @Autowired
    GroupServiceImpl groupServiceImpl;

    @GetMapping(value = "/viewGroups")
    public ModelAndView getGroupList(HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        try {
            List<Group> groupList = groupServiceImpl.findAllGroups();
            return new ModelAndView("home", "recordDTO", new RecordDTO())
                    .addObject("message", session.getAttribute("accountName"))
                    .addObject("groupList", groupList);
        } catch (NoGroupFoundForAccount e) {
            return handleNoGroupFoundForAccountException(e);
        }
    }

    @GetMapping(value = "showAddGroupForm")
    public ModelAndView getAddGroupForm(HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        return new ModelAndView("addgroup", "groupDTO", new GroupDTO());
    }

    @PostMapping(value = "/addGroup")
    public ModelAndView addGroup(@Valid @ModelAttribute("groupDTO") GroupDTO groupDTO, BindingResult bindingResult, HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        if (bindingResult.hasErrors()) {
            return new ModelAndView("addgroup");
        }
        try {
            Group addedGroup = groupServiceImpl.addGroup(groupDTO);
            return new ModelAndView("home")
                    .addObject("success", "Group added successfully!!");
        } catch (GroupAlreadyExistsException | UnableToAddGroup e) {
            return handleGroupException(e);
        }
    }

    public ModelAndView handleGroupException(Exception ex) {
        return new ModelAndView("addgroup", "groupDTO", new GroupDTO())
                .addObject("error", ex.getMessage());
    }

    @GetMapping(value = "findRecordByGroup/{name}")
    public ModelAndView getRecordByGroup(@PathVariable String name, HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        try {
            List<Record> recordList = groupServiceImpl.findAllRecordByGroupName(name);
            List<Group> groupList = groupServiceImpl.findAllGroups();
            return new ModelAndView("home", "recordDTO", new RecordDTO())
                    .addObject("message", session.getAttribute("accountName"))
                    .addObject("recordList", recordList)
                    .addObject("groupList", groupList);
        } catch (NoRecordFoundForGroup | NoGroupFoundForAccount e) {
            return handleNoGroupFoundForAccountException(e);
        }
    }

    @GetMapping(value = "showEditGroupForm/{name}")
    public ModelAndView getEditGroupForm(@PathVariable String name, HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        try {
            Group fetchGroup = groupServiceImpl.findGroupByName(name);
            return new ModelAndView("updategroup", "groupDTO", new GroupDTO())
                    .addObject("group", fetchGroup);
        } catch (NoGroupFoundForAccount e) {
            return handleNoGroupFoundForAccountException(e);
        }
    }

    @PostMapping(value = "/updateGroup")
    public ModelAndView updateGroup(@Valid @ModelAttribute("groupDTO") GroupDTO groupDTO, BindingResult bindingResult, HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        if (bindingResult.hasErrors()) {
            return new ModelAndView("updategroup");
        }
        try {
            Group updateGroup = groupServiceImpl.updateGroup(groupDTO);
            return new ModelAndView("home")
                    .addObject("message", session.getAttribute("accountName"))
                    .addObject("success", "Group updated successfully!!");
        } catch (NoGroupFoundForAccount | UnableToUpdateGroup e) {
            return handleNoGroupFoundForAccountException(e);
        }
    }

    @PostMapping(value = "/deleteGroup")
    public ModelAndView deleteGroup(HttpServletRequest httpServletRequest, HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        String groupName = httpServletRequest.getParameter("group_name");
        try {
            if (groupServiceImpl.deleteGroup(groupName)) {
                return new ModelAndView("home")
                        .addObject("message", session.getAttribute("accountName"))
                        .addObject("success", "Group deleted successfully!!");
            } else {
                return new ModelAndView("home")
                        .addObject("message", session.getAttribute("accountName"))
                        .addObject("error", "unable to delete record!!");
            }
        } catch (NoGroupFoundForAccount | GroupShouldNotContainsRecords e) {
            return handleNoGroupFoundForAccountException(e);
        }
    }

    public ModelAndView handleNoGroupFoundForAccountException(Exception ex) {
        return new ModelAndView("home")
                .addObject("error", ex.getMessage());
    }
}
