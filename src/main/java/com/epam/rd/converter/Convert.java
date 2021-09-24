package com.epam.rd.converter;

import com.epam.rd.dto.GroupDTO;
import com.epam.rd.dto.LoginDTO;
import com.epam.rd.dto.RecordDTO;
import com.epam.rd.dto.RegisterDTO;
import com.epam.rd.entity.Account;
import com.epam.rd.entity.Record;
import com.epam.rd.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class Convert {
    public static Account convertToEntity(RegisterDTO registerDTO) {
        return new Account(registerDTO.getAccountName(), registerDTO.getUserName(), registerDTO.getPassword());
    }

    public static Account convertToEntity(LoginDTO loginDTO) {
        return new Account(loginDTO.getUserName(), loginDTO.getPassword());
    }

    public static Record convertToEntity(RecordDTO recordDTO) {
        return new Record(recordDTO.getUserName(), recordDTO.getPassword(), recordDTO.getUrl(), recordDTO.getNotes());
    }

    public static Group convertToEntity(GroupDTO groupDTO) {
        return new Group(groupDTO.getId(), groupDTO.getName(), groupDTO.getDescription());
    }
}
