package com.demo.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest<T> {
    private Integer page;
    private Integer size;
    private T requestData;

    public static <PO> Page<PO> buildPage(PageRequest<PO> pageRequest){
        if(null == pageRequest.getPage() || null == pageRequest.getSize()){
            pageRequest.setPage(1);
            pageRequest.setSize(1);
        }
        return new Page<>(pageRequest.getPage(), pageRequest.getSize());
    }
}
