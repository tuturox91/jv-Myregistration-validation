package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        return storageDao.add(validateUser(user));
    }

    private User validateUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();
        if (user.getAge() == null) {
            throw new InvalidUserException("Age can't be null");
        } else if (user.getAge() < 18) {
            throw new InvalidUserException("User age can't be less than 18");
        }
        if (userLogin == null) {
            throw new InvalidUserException("User login can't be null");
        } else if (userLogin.length() < 6) {
            throw new InvalidUserException("User login length can't be less than 6 characters");
        } else if (storageDao.get(userLogin) != null) {
            throw new InvalidUserException("User already exists");
        }
        if (userPassword == null) {
            throw new InvalidUserException("User password can't be null");
        } else if (userPassword.length() < 6) {
            throw new InvalidUserException("User password must be at least 6 characters");
        }
        return user;
    }
}
