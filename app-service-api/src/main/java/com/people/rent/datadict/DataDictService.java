package com.people.rent.datadict;

import com.people.rent.convert.DataDictConvert;
import com.rent.model.CommonResult;
import com.rent.model.bo.DataDictBO;
import com.rent.model.dataobject.DataDictDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataDictService {

    @Autowired
    private DataDictMapper dataDictMapper;


    public List<DataDictBO> selectDataDictList() {
        List<DataDictDO> dataDicts = dataDictMapper.selectList();
        return DataDictConvert.INSTANCE.convert(dataDicts);
    }

    public CommonResult<DataDictBO> getDataDict(String dictKey, Object dictValue) {
        DataDictDO dataDictDO = dataDictMapper.selectByEnumValueAndValue(dictKey, String.valueOf(dictValue));
        DataDictBO dataDictBO = DataDictConvert.INSTANCE.convert(dataDictDO);
        return CommonResult.success(dataDictBO);
    }


    public CommonResult<List<DataDictBO>> getDataDict(String dictKey) {
        List<DataDictDO> dataDictDOList = dataDictMapper.selectByEnumValue(dictKey);
        List<DataDictBO> dataDictBOList = DataDictConvert.INSTANCE.convert(dataDictDOList);
        return CommonResult.success(dataDictBOList);
    }


    public CommonResult<List<DataDictBO>> getDataDictList(String dictKey, Collection<?> dictValueList) {
        Set<String> convertDictValueList = dictValueList.stream().map(String::valueOf).collect(Collectors.toSet());
        List<DataDictDO> dataDictDOList = dataDictMapper.selectByEnumValueAndValues(dictKey, convertDictValueList);
        List<DataDictBO> dataDictBOList = DataDictConvert.INSTANCE.convert(dataDictDOList);
        return CommonResult.success(dataDictBOList);
    }
}
