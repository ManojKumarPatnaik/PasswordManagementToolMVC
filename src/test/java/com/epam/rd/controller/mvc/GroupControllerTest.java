package com.epam.rd.controller.mvc;

import com.epam.rd.dto.GroupDTO;
import com.epam.rd.dto.LoginDTO;
import com.epam.rd.entity.Group;
import com.epam.rd.entity.Record;
import com.epam.rd.exception.*;
import com.epam.rd.repository.GroupRepository;
import com.epam.rd.service.GroupServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GroupController.class)
class GroupControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    GroupServiceImpl groupServiceImpl;

    @MockBean
    GroupRepository groupRepository;

    @Test
    public void testViewGroupsRedirectToLogoutWhenSessionNotActive() throws Exception {
        List<Group> groupList = List.of(new Group("Google", "adding google record"));
        when(groupServiceImpl.findAllGroups()).thenReturn(groupList);
        mvc.perform(get("/viewGroups"))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testViewGroupsRedirectToUserHomeWhenSessionIsValid() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        List<Group> groupList = List.of(new Group("Google", "adding google record"));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/viewGroups")
                .session(session);

        when(groupServiceImpl.findAllGroups()).thenReturn(groupList);

        this.mvc.perform(builder)
                .andExpect(view().name("home"))
                .andExpect(model().attribute("groupList", groupList));
    }

    @Test
    public void testViewGroupsThrowExceptionWhenNoGroupFound() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        MockHttpServletRequestBuilder builder = get("/viewGroups")
                .session(session).contentType(MediaType.APPLICATION_JSON);
        when(groupServiceImpl.findAllGroups()).thenThrow(new NoGroupFoundForAccount());

        this.mvc.perform(builder)
                .andExpect(view().name("home"))
                .andExpect(model().attribute("error", "Oops! No group found!!"));
    }

    @Test
    public void testShowAddGroupFormRedirectToAddGroupForm() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        MockHttpServletRequestBuilder builder = get("/showAddGroupForm")
                .session(session).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder)
                .andExpect(view().name("addgroup"));
    }

    @Test
    public void testShowAddGroupFormRedirectToLogoutWhenSessionNotActive() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);
        MockHttpServletRequestBuilder builder = get("/showAddGroupForm")
                .session(session).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder)
                .andExpect(view().name("redirect:/logout"));
    }


    @Test
    public void testAddGroupThrowExceptionWhenGroupAlreadyExists() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(groupServiceImpl.addGroup(any())).thenThrow(new GroupAlreadyExistsException());

        this.mvc.perform(post("/group/addGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("addgroup"))
                .andExpect(model().attribute("error", "Oops! Group already exists!!"));
    }

    @Test
    public void testAddGroupRedirectToAddGroupFormWhenValidationFailed() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google $#$", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        this.mvc.perform(post("/group/addGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("addgroup"));
    }

    @Test
    public void testAddGroupRedirectToLogoutPageWhenSessionIsInvalid() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        this.mvc.perform(post("/group/addGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testAddGroupThrowExceptionWhenUnableToAddGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(groupServiceImpl.addGroup(any())).thenThrow(new UnableToAddGroup());

        this.mvc.perform(post("/group/addGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("addgroup"))
                .andExpect(model().attribute("error", "Oops! Unable to Add Group!!"));
    }

    @Test
    public void testAddGroupDoesNotThrowExceptionWhileAddingNewGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        Group addedGroup = new Group();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(groupServiceImpl.addGroup(any())).thenReturn(addedGroup);

        this.mvc.perform(post("/group/addGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("success", "Group added successfully!!"));
    }

    @Test
    public void testGetRecordByGroupThrowExceptionWhenNoGroupFound() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(groupServiceImpl.findAllGroups()).thenThrow(new NoGroupFoundForAccount());

        this.mvc.perform(get("/group/findRecordByGroup/Group")
                        .session(session))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("error", "Oops! No group found!!"));
    }

    @Test
    public void testGetRecordByGroupRedirectToLogoutWhenSessionNotActive() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        when(groupServiceImpl.findAllGroups()).thenThrow(new NoGroupFoundForAccount());

        this.mvc.perform(get("/group/findRecordByGroup/Group")
                        .session(session))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testGetEditGroupFormRedirectToLogoutWhenSessionNotActive() throws Exception {
        Group group = new Group("Google", "adding google record");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        when(groupServiceImpl.findGroupByName(any())).thenReturn(group);

        this.mvc.perform(get("/group/showEditGroupForm/Group")
                        .session(session))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testGetEditGroupFormRedirectToUpdateGroupFormWhenValidationPassed() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        Group group = new Group("Google", "adding google record");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        session.setAttribute("group_id", 1);
        when(groupServiceImpl.findGroupByName(any())).thenReturn(group);

        this.mvc.perform(get("/group/showEditGroupForm/Group")
                        .session(session))
                .andExpect(view().name("updategroup"));
    }

    @Test
    public void testGetEditGroupFormThrowExceptionWhenNoGroupFoundForAccount() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        session.setAttribute("group_id", 1);
        when(groupServiceImpl.findGroupByName(any())).thenThrow(new NoGroupFoundForAccount());

        this.mvc.perform(get("/group/showEditGroupForm/Group")
                        .session(session))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("error", "Oops! No group found!!"));
    }


    @Test
    public void testGetRecordByGroupThrowExceptionWhenNoRecordFoundForGroup() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        List<Group> groupList = List.of(new Group("Google", "adding google record"));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        when(groupServiceImpl.findAllRecordByGroupName(any())).thenThrow(new NoRecordFoundForGroup());
        when(groupServiceImpl.findAllGroups()).thenReturn(groupList);

        this.mvc.perform(get("/group/findRecordByGroup/Group")
                        .session(session))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("error", "Oops! No Record found for group"));
    }

    @Test
    public void testGetRecordByGroupDoesNotThrowExceptionWhileRetrivingRecordList() throws Exception {
        List<Record> recordList = List.of(new Record("KR009517", "Vishi834019", "http://www.master.com", "record adding "));
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        List<Group> groupList = List.of(new Group("Google", "adding google record"));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        when(groupServiceImpl.findAllRecordByGroupName(any())).thenReturn(recordList);
        when(groupServiceImpl.findAllGroups()).thenReturn(groupList);

        this.mvc.perform(get("/group/findRecordByGroup/Group")
                        .session(session))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("recordList", recordList))
                .andExpect(model().attribute("groupList", groupList))
                .andExpect(model().attribute("message", session.getAttribute("accountName")));
    }

    @Test
    public void testUpdateGroupThrowExceptionWhenNoGroupFound() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        session.setAttribute("group_id", 1);

        when(groupServiceImpl.updateGroup(any())).thenThrow(new NoGroupFoundForAccount());
        this.mvc.perform(post("/group/updateGroup")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("error", "Oops! No group found!!"));
    }


    @Test
    public void testUpdateGroupRedirectToLogoutPageWhenSessionIsInvalid() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        this.mvc.perform(post("/group/updateGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testUpdateGroupReturnToUpdateUrlWhenValidationFailed() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google 12##", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        session.setAttribute("group_id", 1);

        this.mvc.perform(post("/group/updateGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("updategroup"));
    }

    @Test
    public void testUpdateGroupDoesNotThrowExceptionWhileUpdatingGroupSuccessfully() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        Group updatedGroup = new Group();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        session.setAttribute("group_id", 1);


        when(groupServiceImpl.updateGroup(any())).thenReturn(updatedGroup);
        this.mvc.perform(post("/group/updateGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("message", session.getAttribute("accountName")))
                .andExpect(model().attribute("success", "Group updated successfully!!"));
    }

    @Test
    public void testDeleteGroupThrowExceptionWhenNoGroupFoundForAccount() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(groupServiceImpl.deleteGroup(any())).thenThrow(new NoGroupFoundForAccount());
        this.mvc.perform(post("/group/deleteGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("error", "Oops! No group found!!"));
    }

    @Test
    public void testDeleteGroupRedirectToLogoutPageWhenSessionIsInvalid() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        when(groupServiceImpl.deleteGroup(any())).thenThrow(new NoGroupFoundForAccount());
        this.mvc.perform(post("/group/deleteGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("groupDTO", groupDTO))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testDeleteGroupThrowExceptionWhenUnableToDeleteGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(groupServiceImpl.deleteGroup(any())).thenThrow(new GroupShouldNotContainsRecords());
        this.mvc.perform(post("/group/deleteGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupDTO)))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("error", "Oops! Group should be empty before deletion!!"));
    }

    @Test
    public void testDeleteGroupDoesNotThrowExceptionWhileDeletingSuccessfully() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(groupServiceImpl.deleteGroup(any())).thenReturn(true);
        this.mvc.perform(post("/group/deleteGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupDTO)))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("message", session.getAttribute("accountName")))
                .andExpect(model().attribute("success", "Group deleted successfully!!"));
    }

    @Test
    public void testDeleteGroupRedirectToUserHomeWhenUnableToDeleteGroup() throws Exception {
        GroupDTO groupDTO = new GroupDTO("Google", "group added data");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(groupServiceImpl.deleteGroup(any())).thenReturn(false);
        this.mvc.perform(post("/group/deleteGroup")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupDTO)))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("message", session.getAttribute("accountName")))
                .andExpect(model().attribute("error", "unable to delete record!!"));
    }
}