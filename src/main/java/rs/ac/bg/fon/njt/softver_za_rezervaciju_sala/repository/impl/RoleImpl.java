package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.RoleRepository;

public class RoleImpl {


    private RoleRepository roleRepository;


    @Autowired
    public RoleImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


}
