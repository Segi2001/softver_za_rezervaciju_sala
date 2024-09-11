package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Roles;

public interface RoleRepository extends JpaRepository<Roles,Integer> {
}
