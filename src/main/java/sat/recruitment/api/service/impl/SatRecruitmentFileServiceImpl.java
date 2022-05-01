package sat.recruitment.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sat.recruitment.api.dto.UserDTO;
import sat.recruitment.api.enums.UserTypeEnum;
import sat.recruitment.api.service.SatRecruitmentFileService;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SatRecruitmentFileServiceImpl implements SatRecruitmentFileService {

    private final static String FILENAME = "users.txt";
    private final static String NORMAL_HIGHER_PERCENTAGE = "0.12";
    private final static String NORMAL_LOWER_PERCENTAGE = "0.8";
    private final static String SUPERUSER_PERCENTAGE = "0.20";
    private List<UserDTO> users = new ArrayList<>();

    @Override
    public List<UserDTO> findAll() {
        Comparator<UserDTO> orderById = (UserDTO u1, UserDTO u2) -> u1.getId().compareTo(u2.getId());
        return users.stream()
                .sorted(orderById)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<UserDTO> userOpt = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();

        if (!userOpt.isPresent()) {
            log.info("User with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        log.info("User with id {} is", id, userOpt.get());
        return userOpt.get();
    }

    @Override
    public List<UserDTO> createUser(UserDTO newUser) {

        log.info("User type: {}", newUser.getUserType());
        if (newUser.getUserType().equals(UserTypeEnum.Normal.name())) {
            newUser.setMoney(userTypeNormal(newUser));
        } else if (newUser.getUserType().equals(UserTypeEnum.SuperUser.name())) {
            newUser.setMoney(userTypeSuperUser(newUser));
        } else if (newUser.getUserType().equals(UserTypeEnum.Premium.name())) {
            newUser.setMoney(userTypePremium(newUser));
        }

        if (validateExistingUser(users, newUser)) {
            log.info("User is duplicated: {}", newUser);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is duplicated");
        }

        List<UserDTO> listUsers = new ArrayList<>();
        try {
            writeObjectToFile(newUser, getFile());
            listUsers = readObjectFromFile(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listUsers;
    }

    private static boolean validateExistingUser(List<UserDTO> users, UserDTO user) {
        return users.stream()
                .filter(x -> Objects.equals(x.getEmail(), user.getEmail())
                        || Objects.equals(x.getPhone(), user.getPhone())
                        || Objects.equals(x.getId(), user.getId()))
                .count() > 0;
    }

    private Double userTypeNormal(UserDTO user) {
        if (Double.valueOf(user.getMoney()) > 100) {
            var gif = Double.valueOf(user.getMoney()) * Double.valueOf(NORMAL_HIGHER_PERCENTAGE);
            return user.getMoney() + gif;
        } else if (Double.valueOf(user.getMoney()) < 100) {
            if (Double.valueOf(user.getMoney()) > 10) {
                var gif = Double.valueOf(user.getMoney()) * Double.valueOf(NORMAL_LOWER_PERCENTAGE);
                return user.getMoney() + gif;
            }
        }
        return user.getMoney();
    }

    private Double userTypeSuperUser(UserDTO user) {
        if (Double.valueOf(user.getMoney()) > 100) {
            Double gif = Double.valueOf(user.getMoney()) * Double.valueOf(SUPERUSER_PERCENTAGE);
            return user.getMoney() + gif;
        }
        return user.getMoney();
    }

    private Double userTypePremium(UserDTO user) {
        if (Double.valueOf(user.getMoney()) > 100) {
            return user.getMoney() + (Double.valueOf(user.getMoney()) * 2);
        }
        return user.getMoney();
    }

    private File getFile() {
        File file = null;
        URL resource = getClass().getClassLoader().getResource(FILENAME);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        }
        try {
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        log.info("accessing the file: {}", file.getName());
        return file;
    }

    // Save object into a file.
    public static void writeObjectToFile(UserDTO obj, File file) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
            objOut.writeObject(obj);
            objOut.flush();
            log.info("user {} written to file successfully", obj.getName());
        }
    }

    // Get object from a file.
    public List<UserDTO> readObjectFromFile(File file) throws IOException, ClassNotFoundException {
        UserDTO result = null;
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            result = (UserDTO) objIn.readObject();
            log.info("user read from file: {}", result);
            users.add(result);
        }
        return users;
    }

}
