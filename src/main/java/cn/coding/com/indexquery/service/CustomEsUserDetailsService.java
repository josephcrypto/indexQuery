package cn.coding.com.indexquery.service;

import cn.coding.com.indexquery.model.EsUser;
import cn.coding.com.indexquery.repository.EsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomEsUserDetailsService implements UserDetailsService {


    @Autowired
    private EsUserRepository esUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         EsUser esUser = esUserRepository.findFirstByUserName(username);
         if (esUser == null) {
             throw new UsernameNotFoundException("User not found!");
         }
         return new CustomEsUserDetails(esUser);
    }
}
