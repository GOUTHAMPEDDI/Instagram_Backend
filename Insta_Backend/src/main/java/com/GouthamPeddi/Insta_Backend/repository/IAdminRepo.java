package com.GouthamPeddi.Insta_Backend.repository;

import com.GouthamPeddi.Insta_Backend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepo extends JpaRepository<Admin,Long> {
}
