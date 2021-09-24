package com.epam.rd.controller.mvc;

import com.epam.rd.dto.RecordDTO;
import com.epam.rd.entity.Group;
import com.epam.rd.entity.Record;
import com.epam.rd.exception.*;
import com.epam.rd.service.GroupServiceImpl;
import com.epam.rd.service.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"", "record"})
public class RecordController {
    @Autowired
    RecordServiceImpl recordServiceImpl;
    @Autowired
    GroupServiceImpl groupServiceImpl;

    @GetMapping
    public ModelAndView getRecordList(HttpSession session) throws NoRecordFoundForAccount {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        List<Record> recordList = recordServiceImpl.findAllRecords();
        return new ModelAndView("home", "recordDTO", new RecordDTO())
                .addObject("message", session.getAttribute("accountName"))
                .addObject("recordList", recordList);
    }

    @GetMapping(value = "showEditRecordForm{url}")
    public ModelAndView getEditRecordForm(@RequestParam("url") String url, HttpSession session) throws NoRecordFoundForAccountBasedOnUrl {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        Record fetchRecord = recordServiceImpl.findRecordBasedOnUrl(url);
        return new ModelAndView("updaterecord", "recordDTO", new RecordDTO()).addObject("record", fetchRecord);
    }

    @ExceptionHandler({NoRecordFoundForAccountBasedOnUrl.class, NoRecordFoundForAccount.class})
    public ModelAndView handleNoRecordFoundBasedOnUrlForUpdate(Exception ex) {
        return new ModelAndView("redirect:/record").addObject("error", ex.getMessage());
    }

    @PostMapping(value = "/updateRecord")
    public ModelAndView updateRecord(@Valid @ModelAttribute("recordDTO") RecordDTO recordDTO, BindingResult bindingResult, HttpSession session) throws NoRecordFoundForAccountBasedOnUrl {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        if (bindingResult.hasErrors()) {
            return new ModelAndView("updaterecord");
        }
        Record updatedRecord = recordServiceImpl.updateRecord(recordDTO);
        return new ModelAndView("redirect:/record", "recordDTO", new RecordDTO())
                .addObject("success", "Record updated successfully!!");
    }

    @GetMapping(value = "showNewRecordForm")
    public ModelAndView getNewRecordForm(HttpSession session) throws NoGroupFoundForAccount {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        List<String> groupNameList = getGroupNameList();
        return new ModelAndView("addrecord", "recordDTO", new RecordDTO())
                .addObject("groupNameList", groupNameList);
    }

    private List<String> getGroupNameList() throws NoGroupFoundForAccount {
        List<Group> groupList = groupServiceImpl.findAllGroups();
        return groupList.stream().map(Group::getName).collect(Collectors.toList());
    }

    @ExceptionHandler(NoGroupFoundForAccount.class)
    public ModelAndView handleNoGroupFoundForAccountException(Exception ex) {
        return new ModelAndView("home", "recordDTO", new RecordDTO())
                .addObject("error", ex.getMessage());
    }

    @PostMapping(value = "/addRecord")
    public ModelAndView addRecord(@Valid @ModelAttribute("recordDTO") RecordDTO recordDTO, BindingResult bindingResult, HttpSession session) throws NoGroupFoundForAccount {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        if (bindingResult.hasErrors()) {
            List<String> groupNameList = getGroupNameList();
            return new ModelAndView("addrecord")
                    .addObject("groupNameList", groupNameList);
        }
        try {
            Record addedRecord = recordServiceImpl.addRecord(recordDTO);
            return new ModelAndView("home")
                    .addObject("success", "Record added successfully!!");
        } catch (RecordAlreadyExistsException e) {
            return handleRecordAlreadyExistsException(e);
        }
    }

    public ModelAndView handleRecordAlreadyExistsException(Exception ex) {
        return new ModelAndView("addrecord", "recordDTO", new RecordDTO()).addObject("error", ex.getMessage());
    }

    @PostMapping(value = "/deleteRecord")
    public ModelAndView deleteRecord(HttpServletRequest httpServletRequest, HttpSession session) {
        if (session.getAttribute("account") == null) {
            return new ModelAndView("redirect:/logout");
        }
        String url = httpServletRequest.getParameter("url_id");
        try {
            recordServiceImpl.deleteRecordBasedOnUrl(url);
            return new ModelAndView("redirect:/record", "recordDTO", new RecordDTO());
        } catch (NoRecordFoundForAccountBasedOnUrl e) {
            return handleNoRecordFoundBasedOnUrlExceptionForDelete(e);
        }
    }

    public ModelAndView handleNoRecordFoundBasedOnUrlExceptionForDelete(Exception ex) {
        return new ModelAndView("home", "recordDTO", new RecordDTO()).addObject("error", ex.getMessage());
    }
}
