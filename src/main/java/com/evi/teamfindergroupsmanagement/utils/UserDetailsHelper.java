package com.evi.teamfindergroupsmanagement.utils;


import com.evi.teamfindergroupsmanagement.domain.GroupRoom;
import com.evi.teamfindergroupsmanagement.exception.UserNotFoundException;
import com.evi.teamfindergroupsmanagement.security.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserDetailsHelper {

    public static User getCurrentUser() {
        if(SecurityContextHolder.getContext().getAuthentication()!=null) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        throw new UserNotFoundException("Cant load security context");
    }

    public static boolean checkPrivilages(GroupRoom groupRoom){
        User user = getCurrentUser();
        return groupRoom.getGroupLeader().getId().equals(user.getId()) || user.getRole().getName().equals("ROLE_ADMIN");
    }

}
