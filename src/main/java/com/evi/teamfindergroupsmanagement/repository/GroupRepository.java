package com.evi.teamfindergroupsmanagement.repository;

import com.evi.teamfindergroupsmanagement.domain.GroupRoom;
import com.evi.teamfindergroupsmanagement.security.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupRoom, Long>, JpaSpecificationExecutor<GroupRoom> {


    GroupRoom findGroupRoomByJoinCodeAndDeletedFalse(String joinCode);

    boolean existsByJoinCodeAndDeletedFalse(String joinCode);

    Optional<GroupRoom> findByIdAndDeletedFalse(Long id);
    List<GroupRoom> findAllByDeletedTrue();

    List<GroupRoom> findAllByDeletedFalse();
    Page<GroupRoom> findAllByGameNameAndOpenIsTrueAndDeletedFalse(String name, Pageable pageable);
    List<GroupRoom> findAllByUsersContainingAndDeletedFalse(User user);


}
