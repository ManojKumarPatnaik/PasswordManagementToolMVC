package com.epam.rd.dao;

import com.epam.rd.dto.GroupDTO;
import com.epam.rd.dto.LoginDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.entity.Group;
import com.epam.rd.entity.Record;
import com.epam.rd.exception.*;
import com.epam.rd.repository.GroupRepository;
import com.epam.rd.repository.RecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupDaoTest {
    @InjectMocks
    GroupDaoImpl groupDaoImpl;

    @Mock
    GroupRepository groupRepository;
    @Mock
    RecordRepository recordRepository;

    @Test
    void testAddGroupThrowExceptionWhenGroupAlreadyExists() {
        GroupDTO groupDTO = new GroupDTO("Google", "first group");
        Group existGroup = new Group();
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(existGroup);
        Assertions.assertThrows(GroupAlreadyExistsException.class, () -> groupDaoImpl.addGroup(groupDTO));
    }

    @Test
    void testAddGroupDoesNotThrowExceptionWhileInsertingNewGroup() {
        GroupDTO groupDTO = new GroupDTO("Google", "first group");
        Group addedGroup = new Group();
        when(groupRepository.save(any())).thenReturn(addedGroup);
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(null);
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.addGroup(groupDTO));
    }

    @Test
    void testFindAllRecordByGroupNameThrowExceptionWhenNoRecordExistForSearchedGroupName() {
        Group existGroup = new Group();
        when(recordRepository.findByGroupAndAccount(any(), any())).thenReturn(Collections.emptyList());
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(existGroup);
        Assertions.assertThrows(NoRecordFoundForGroup.class, () -> groupDaoImpl.findAllRecordByGroupName("Google"));
    }

    @Test
    void testFindAllRecordByGroupNameDoesNotThrowExceptionWhileRecordsFound() {
        List<Record> recordList = List.of(new Record("KGR009517","Vishal834019","htt://www.master.com","data123"));
        Group existGroup = new Group();
        when(recordRepository.findByGroupAndAccount(any(), any())).thenReturn(recordList);
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(existGroup);
        Assertions.assertEquals(recordList, Assertions.assertDoesNotThrow(() -> groupDaoImpl.findAllRecordByGroupName("Google")));
    }

    @Test
    void testUpdateGroupThrowExceptionWhenNoGroupExist() {
        GroupDTO groupDTO = new GroupDTO("Google", "first group");
        when(groupRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoGroupFoundForAccount.class, () -> groupDaoImpl.updateGroup(groupDTO));
    }

    @Test
    void testUpdateGroupDoesNotThrowExceptionWhileUpdatingGroupSuccessfully() {
        GroupDTO groupDTO = new GroupDTO("Google", "first group");
        Optional<Group> optionalGroup;
        Group fetchGroup = new Group();
        optionalGroup = Optional.of(fetchGroup);
        when(groupRepository.save(any())).thenReturn(fetchGroup);
        when(groupRepository.findById(any())).thenReturn(optionalGroup);
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.updateGroup(groupDTO));
    }

    @Test
    void testFindAllGroupThrowExceptionWhenNoGroupExist() {
        when(groupRepository.findByAccount(any())).thenReturn(Collections.emptyList());
        Assertions.assertThrows(NoGroupFoundForAccount.class, () -> groupDaoImpl.findAllGroups());
    }

    @Test
    void testFindAllGroupDoesNotThrowExceptionWhileGroupExist() {
        List<Group> groupList = List.of(new Group(1, "Google", "data group"));
        when(groupRepository.findByAccount(any())).thenReturn(groupList);
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.findAllGroups());
    }

    @Test
    void testFindGroupByNameThrowExceptionWhenNoGroupExist() {
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(null);
        Assertions.assertThrows(NoGroupFoundForAccount.class, () -> groupDaoImpl.findGroupByName("Google"));
    }

    @Test
    void testFindGroupByNameDoesNotThrowExceptionWhileGroupExist() {
        Group existGroup = new Group();
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(existGroup);
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.findGroupByName("Google"));
    }

    @Test
    void testDeleteGroupThrowExceptionWhenGroupHasRecords() {
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(null);
        Assertions.assertThrows(NoGroupFoundForAccount.class, () -> groupDaoImpl.deleteGroup("Google"));
    }

    @Test
    void testDeleteGroupThrowExceptionWhenNoGroupExistsWithRecords() {
        Group existGroup = new Group();
        List<Record> recordList = List.of(new Record("KGR009517", "Vishal834019", "http://www.google.com", "data group"));
        when(recordRepository.findByGroupAndAccount(any(), any())).thenReturn(recordList);
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(existGroup);
        Assertions.assertThrows(GroupShouldNotContainsRecords.class, () -> groupDaoImpl.deleteGroup("Google"));
    }

    @Test
    void testDeleteGroupDoesNotThrowExceptionWhileDeletingGroupWithoutRecords() {
        Group existGroup = new Group();
        when(recordRepository.findByGroupAndAccount(any(), any())).thenReturn(Collections.emptyList());
        when(groupRepository.findByNameAndAccount(any(), any())).thenReturn(existGroup);
        Assertions.assertDoesNotThrow(() -> groupDaoImpl.deleteGroup("Google"));
    }

    @Test
    void testAccount() {
        LoginDTO loginDTO = new LoginDTO("KGR009517", "Vishal834019");
        groupDaoImpl.setAccount(loginDTO);
        Account account = new Account("KGR009517", "Vishal834019");
        Assertions.assertEquals(account.getUserName(), groupDaoImpl.getAccount().getUserName());
    }

}
