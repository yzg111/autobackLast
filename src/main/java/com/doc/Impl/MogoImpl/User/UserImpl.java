package com.doc.Impl.MogoImpl.User;

import com.doc.Dao.MogoDao.ComDao.ComDao;
import com.doc.Entity.MogoEntity.Log.log;
import com.doc.Entity.MogoEntity.User.MogoUser;
import org.springframework.stereotype.Component;

/**
 * com.doc.Impl.MogoImpl.User 于2017/8/25 由Administrator 创建 .
 */
@Component("UserImpl")
public abstract class UserImpl extends ComDao<MogoUser> {
}
