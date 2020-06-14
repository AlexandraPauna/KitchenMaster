package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;

public interface RoleService {
    Role findRoleByUserName(String role_name);
}
