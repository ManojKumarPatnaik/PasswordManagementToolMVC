package com.epam.rd.service;

import com.epam.rd.dto.GroupDTO;
import com.epam.rd.dto.LoginDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.entity.Group;
import com.epam.rd.entity.Record;
import com.epam.rd.exception.*;

import java.util.List;

public interface GroupService {
    Group addGroup(GroupDTO groupDTO) throws UnableToAddGroup, GroupAlreadyExistsException;
    List<Record> findAllRecordByGroupName(String name) throws NoRecordFoundForGroup, NoGroupFoundForAccount;
    Group updateGroup(GroupDTO groupDTO) throws UnableToUpdateGroup, NoGroupFoundForAccount;
    List<Group> findAllGroups() throws NoGroupFoundForAccount;
    Group findGroupByName(String name) throws NoGroupFoundForAccount;
    boolean deleteGroup(String name) throws NoGroupFoundForAccount, GroupShouldNotContainsRecords;
    void setAccount(LoginDTO loginDTO);
    Account getAccount();
}
