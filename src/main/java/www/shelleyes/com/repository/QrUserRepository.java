package www.shelleyes.com.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.shelleyes.com.entity.QrUser;




@Repository
public interface QrUserRepository extends JpaRepository<QrUser, Long> {

   QrUser findQrUserById(Long id);


}
