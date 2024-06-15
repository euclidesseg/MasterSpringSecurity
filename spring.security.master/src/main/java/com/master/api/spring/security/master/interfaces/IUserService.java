package com.master.api.spring.security.master.interfaces;


import java.util.Optional;

import com.master.api.spring.security.master.dto.SaveUser;
import com.master.api.spring.security.master.persistance.entity.Security.User;

public interface IUserService {
      public User createOneCustomer(SaveUser user);
      public Optional<User>findOneByUsername(String username);
}
