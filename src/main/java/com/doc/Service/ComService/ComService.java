package com.doc.Service.ComService;

import java.util.Collection;
import java.util.List;

/**
 * com.doc.Service.ComService 于2017/8/29 由Administrator 创建 .
 */
public interface  ComService<T> {
    public void save(T bean);

    public void saveList(Collection<T> beans);

    public void updateList(Collection<T> beans);

}
