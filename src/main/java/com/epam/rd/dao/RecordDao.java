package com.epam.rd.dao;

import com.epam.rd.dto.LoginDTO;
import com.epam.rd.dto.RecordDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.entity.Record;
import com.epam.rd.exception.*;

import java.util.List;

public interface RecordDao {
    Record addRecord(RecordDTO recordDTO) throws UnableToAddRecord, RecordAlreadyExistsException;

    Record findRecordBasedOnUrl(String url) throws NoRecordFoundForAccountBasedOnUrl;

    List<Record> findAllRecords() throws NoRecordFoundForAccount;

    Record updateRecord(RecordDTO recordDTO) throws UnableToUpdateRecord, NoRecordFoundForAccountBasedOnUrl;

    Record deleteRecordBasedOnUrl(String url) throws NoRecordFoundForAccountBasedOnUrl;

    void setAccount(LoginDTO loginDTO);

    Account getAccount();
}
