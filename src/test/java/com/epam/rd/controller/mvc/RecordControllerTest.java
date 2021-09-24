package com.epam.rd.controller.mvc;

import com.epam.rd.dto.LoginDTO;
import com.epam.rd.dto.RecordDTO;
import com.epam.rd.entity.Record;
import com.epam.rd.exception.*;
import com.epam.rd.service.GroupServiceImpl;
import com.epam.rd.service.RecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RecordController.class)
class RecordControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    RecordServiceImpl recordServiceImpl;
    @MockBean
    GroupServiceImpl groupServiceImpl;
    @Mock
    HttpServletRequest request;

    @Test
    public void testGetEditRecordFormRedirectToLogoutWhenSessionNotActive() throws Exception {
        Record record = new Record();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        when(recordServiceImpl.findRecordBasedOnUrl(any())).thenReturn(record);

        this.mvc.perform(get("/record/showEditRecordForm?url=www.master.com")
                        .session(session))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testGetEditRecordFormReturnToUpdateRecordFormWhenValidationPassed() throws Exception {
        Record record = new Record();
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(recordServiceImpl.findRecordBasedOnUrl(any())).thenReturn(record);

        this.mvc.perform(get("/record/showEditRecordForm?url=www.master.com")
                        .session(session))
                .andExpect(view().name("updaterecord"));
    }

    @Test
    public void testGetNewRecordFormRedirectToAddRecordForm() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        MockHttpServletRequestBuilder builder = get("/showNewRecordForm")
                .session(session).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder)
                .andExpect(view().name("addrecord"));
    }

    @Test
    public void testGetNewRecordFormThrowExceptionWhenNoGroupFoundForAccount() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        MockHttpServletRequestBuilder builder = get("/showNewRecordForm")
                .session(session).contentType(MediaType.APPLICATION_JSON);
        when(groupServiceImpl.findAllGroups()).thenThrow(new NoGroupFoundForAccount());

        this.mvc.perform(builder)
                .andExpect(view().name("home"));
    }

    @Test
    public void testGetNewRecordFormRedirectToLogoutWhenSessionNotActive() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);
        MockHttpServletRequestBuilder builder = get("/showNewRecordForm")
                .session(session).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder)
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testAddRecordRedirectToLogoutWhenSessionNotActive() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        this.mvc.perform(post("/record/addRecord")
                        .session(session).contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testAddRecordRedirectToAddRecordFormWhenValidationFailed() throws Exception {
        RecordDTO recordDTO = new RecordDTO("KR009517@#$ ", "Vishi834019", "http://www.master.com", "record adding ");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        this.mvc.perform(post("/record/addRecord")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("recordDTO", recordDTO))
                .andExpect(view().name("addrecord"));
    }

    @Test
    public void testAddRecordThrowExceptionWhenRecordAlreadyExists() throws Exception {
        RecordDTO recordDTO = new RecordDTO("KR009517", "Vishi834019", "http://www.master.com", "record adding ");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(recordServiceImpl.addRecord(any())).thenThrow(new RecordAlreadyExistsException());

        this.mvc.perform(post("/record/addRecord")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("recordDTO", recordDTO))
                .andExpect(view().name("addrecord"))
                .andExpect(model().attribute("error", "Oops! Record already exists for url!!"));
    }

    @Test
    public void testAddRecordDoesNotThrowExceptionWhileRecordAddedSuccessfully() throws Exception {
        RecordDTO recordDTO = new RecordDTO("KR009517", "Vishi834019", "http://www.master.com", "record adding ");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        Record addedRecord = new Record();
        when(recordServiceImpl.addRecord(any())).thenReturn(addedRecord);

        this.mvc.perform(post("/record/addRecord")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("recordDTO", recordDTO))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("success", "Record added successfully!!"));
    }

    @Test
    public void testUpdateRecordThrowExceptionWhenNoRecordFoundForAccountBasedOnUrl() throws Exception {
        RecordDTO recordDTO = new RecordDTO("KR009517", "Vishi834019", "http://www.master.com", "record adding ");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(recordServiceImpl.updateRecord(any())).thenThrow(new NoRecordFoundForAccountBasedOnUrl());

        this.mvc.perform(post("/record/updateRecord")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("recordDTO", recordDTO))
                .andExpect(view().name("redirect:/record"))
                .andExpect(model().attribute("error", "Oops! No Record Found For Input Url!!"));
    }

    @Test
    public void testUpdateRecordRedirectToLogoutWhenSessionNotActive() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        this.mvc.perform(post("/record/updateRecord")
                        .session(session).contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testUpdateRecordRedirectToUpdateRecordWhenValidationFailedWithError() throws Exception {
        RecordDTO recordDTO = new RecordDTO("KR009517##%# ", "Vishi834019", "http://www.master.com", "record adding ");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        this.mvc.perform(post("/record/updateRecord")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("recordDTO", recordDTO))
                .andExpect(view().name("updaterecord"));
    }

    @Test
    public void testUpdateRecordDoesNotThrowExceptionWhileUpdatingRecordSuccessfully() throws Exception {
        RecordDTO recordDTO = new RecordDTO("KR009517", "Vishi834019", "http://www.master.com", "record adding ");
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        Record updatedRecord = new Record();
        when(recordServiceImpl.updateRecord(any())).thenReturn(updatedRecord);

        this.mvc.perform(post("/record/updateRecord")
                        .session(session).contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("recordDTO", recordDTO))
                .andExpect(view().name("redirect:/record"));
    }

    @Test
    public void testGetRecordListRedirectToLogoutWhenSessionNotActive() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        this.mvc.perform(get("/record")
                        .session(session).contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testGetRecordListThrowExceptionWhenNoRecordFoundForAccount() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        when(recordServiceImpl.findAllRecords()).thenThrow(new NoRecordFoundForAccount());

        this.mvc.perform(get("/record")
                        .session(session))
                .andExpect(view().name("redirect:/record"))
                .andExpect(model().attribute("error", "Oops! No Record Found!!"));
    }

    @Test
    public void testGetRecordListDoesNotThrowExceptionWhileRetrivingRecordList() throws Exception {
        List<Record> recordList = List.of(new Record("KR009517", "Vishi834019", "http://www.master.com", "record adding "));
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(recordServiceImpl.findAllRecords()).thenReturn(recordList);

        this.mvc.perform(get("/record")
                        .session(session))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("message", session.getAttribute("accountName")))
                .andExpect(model().attribute("recordList", recordList));
    }

    @Test
    public void testDeleteRecordRedirectToLogoutWhenSessionNotActive() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", null);

        this.mvc.perform(post("/record/deleteRecord")
                        .session(session).contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("redirect:/logout"));
    }

    @Test
    public void testDeleteRecordThrowExceptionWhenNoRecordFoundForAccountBasedOnUrl() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        when(recordServiceImpl.deleteRecordBasedOnUrl(any())).thenThrow(new NoRecordFoundForAccountBasedOnUrl());
        when(request.getParameter("url_id")).thenReturn(any());

        this.mvc.perform(post("/record/deleteRecord")
                        .session(session))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("error", "Oops! No Record Found For Input Url!!"));
    }

    @Test
    public void testDeleteRecordDoesNotThrowExceptionWhileDeletingRecordSuccessfully() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);
        Record record = new Record();
        when(recordServiceImpl.deleteRecordBasedOnUrl(any())).thenReturn(record);
        when(request.getParameter("url_id")).thenReturn(any());

        this.mvc.perform(post("/record/deleteRecord")
                        .session(session))
                .andExpect(view().name("redirect:/record"));
    }

    @Test
    public void testDeleteRecordRedirectToUserRecordWhenUnableToDeleteRecord() throws Exception {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", loginDTO);

        Record record = new Record();
        when(recordServiceImpl.deleteRecordBasedOnUrl(any())).thenReturn(record);
        when(request.getParameter("url_id")).thenReturn(any());

        this.mvc.perform(post("/record/deleteRecord")
                        .session(session))
                .andExpect(view().name("redirect:/record"));
    }
}