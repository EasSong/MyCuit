package cn.cuit.dao;

import cn.cuit.model.User;
import java.util.List;

/**
 * Created by Esong on 2017/6/7.
 */
public interface CourseDao {
    List getCourseByUserNumber(User user);
}
