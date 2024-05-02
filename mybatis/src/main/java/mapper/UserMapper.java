package mapper;

import entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User selectUser(@Param("id") Long id);
}
