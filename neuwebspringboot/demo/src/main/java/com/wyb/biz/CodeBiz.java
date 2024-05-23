package com.wyb.biz;

import com.wyb.entity.Code;
import com.wyb.entity.User;
import com.wyb.mapper.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeBiz {
    @Autowired
    private CodeMapper codeMapper;

    public List<Code> findAll(int userId){
        return codeMapper.selectCodes(userId);
    }

    public void addCode(Code code){
        this.codeMapper.insertCode(code);
    }

    public boolean delCode(int codeId){
        return this.codeMapper.deleteCodeById(codeId) > 0;
    }

    public boolean update(String title , String code , int codeId){
        return codeMapper.updateCodeById(title , code , codeId) > 0;
    }
    public List<Code> getAll(){
        return codeMapper.selectCodesAll();
    }

    public List<Code> getUserCodeByTitle(int userId , String title){
        return codeMapper.selectUserCodeByTitle(userId , title);
    }

    public Code selectCode(int codeId){
        return codeMapper.selectCodeById(codeId);
    }

    public List<Code> selectCodeByTitle(String title){
        return codeMapper.selectCodeByTitle(title);
    }

    public void setCodeMapper(CodeMapper codeMapper) {
        this.codeMapper = codeMapper;
    }
}
