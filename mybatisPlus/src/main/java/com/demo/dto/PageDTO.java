package com.demo.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> implements Serializable {
    private List<T> records;
    private long total;
    private long size;
    private long current;
    private String remark;

    public static <PO,VO> PageDTO<VO> buildOf(IPage<PO> page, Function<PO,VO> function){
        PageDTO<VO> vo = new PageDTO<>();
        vo.setCurrent(page.getCurrent());
        vo.setSize(page.getSize());
        vo.setTotal(page.getTotal());
        List<PO> records = page.getRecords();
        if(!CollectionUtils.isEmpty(records)){
            vo.setRecords(records.stream().map(function).collect(Collectors.toList()));
        }
        return vo;
    }

}
