package com.epam.rd.dao;

import com.epam.rd.dto.GroupDTO;
import com.epam.rd.dto.LoginDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.entity.Group;
import com.epam.rd.entity.Record;
import com.epam.rd.exception.*;

import java.util.List;

public interface GroupDao {
    Group addGroup(GroupDTO groupDTO) throws UnableToAddGroup, GroupAlreadyExistsException;
    List<Record> findAllRecordByGroupName(String name) throws NoRecordFoundForGroup, NoGroupFoundForAccount;
    Group updateGroup(GroupDTO groupDTO) throws UnableToUpdateGroup, NoGroupFoundForAccount;
    List<Group> findAllGroups() throws NoGroupFoundForAccount;
    boolean deleteGroup(String name) throws NoGroupFoundForAccount, GroupShouldNotContainsRecords;
    void setAccount(LoginDTO LoginDTO);
    Group findGroupByName(String name) throws NoGroupFoundForAccount;
    Account getAccount();
}
