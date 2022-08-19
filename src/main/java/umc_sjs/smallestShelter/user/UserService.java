package umc_sjs.smallestShelter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.FavoriteAnimal;
import umc_sjs.smallestShelter.domain.User;
import umc_sjs.smallestShelter.animal.animalDto.AnimalRes;
import umc_sjs.smallestShelter.animal.animalDto.GetAnimalsRes;
import umc_sjs.smallestShelter.animal.AnimalRepository;
import umc_sjs.smallestShelter.animal.FavoriteAnimalRepository;

import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponseStatus;
import umc_sjs.smallestShelter.user.userDto.GetOrganizationPageRes;
import umc_sjs.smallestShelter.user.userDto.GetPrivatePageRes;
import umc_sjs.smallestShelter.user.userDto.JoinDto;
import umc_sjs.smallestShelter.user.userDto.PatchUserReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final FavoriteAnimalRepository favoriteAnimalRepository;
    private final AnimalRepository animalRepository;

    @Transactional
    public void join(JoinDto joinDto) throws BaseException {
        // 아이디 중복 확인
        if(checkUsername(joinDto.getUsername())){
            throw new BaseException(USERS_EXISTS_USERNAME);
        }

        // 이메일 중복 확인
        if(checkEmail(joinDto.getEmail())){
            throw new BaseException(USERS_EXISTS_EMAIL);
        }

        String encodePassword;
        try{
            //암호화
            encodePassword = bCryptPasswordEncoder.encode(joinDto.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try{
            User user = User.builder()
                    .name(joinDto.getName())
                    .username(joinDto.getUsername())
                    .password(encodePassword)
                    .phoneNumber(joinDto.getPhoneNumber())
                    .profileImgUrl(joinDto.getProfileImgUrl())
                    .email(joinDto.getEmail())
                    .address(joinDto.getAddress())
                    .createDate(joinDto.getCreateDate())
                    .organizationName(joinDto.getOrganizationName())
                    .role(joinDto.getRole()).build();

            userRepository.save(user);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true) // 마이페이지 - 개인
    public GetPrivatePageRes privatePage(Long userIdx) throws BaseException {
        try{
            GetPrivatePageRes getPrivatePageRes = new GetPrivatePageRes();

            Optional<User> user = userRepository.findById(userIdx);

            getPrivatePageRes.setUserIdx(user.get().getIdx());
            getPrivatePageRes.setName(user.get().getName());
            getPrivatePageRes.setPhoneNumber(user.get().getPhoneNumber());
            getPrivatePageRes.setAddress(user.get().getAddress());
            getPrivatePageRes.setEmail(user.get().getEmail());
            getPrivatePageRes.setRole(user.get().getRole().toString());
            getPrivatePageRes.setProfileImgUrl(user.get().getProfileImgUrl());

            return getPrivatePageRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true) // 마이페이지 관심동물 - 개인
    public GetAnimalsRes privateAnimals(int page, Long userIdx) throws BaseException {
        try{
            GetAnimalsRes getAnimalsRes = new GetAnimalsRes();

            Pageable pageable = PageRequest.of(page, 6, Sort.Direction.DESC, "idx");

//            List<FavoriteAnimal> favoriteAnimals = favoriteAnimalRepository.findByUserIdx(userIdx, pageable);


            List<FavoriteAnimal> favoriteAnimals = favoriteAnimalRepository.findByLikeUserIdx(userIdx, pageable);

            List<AnimalRes> animalResList = new ArrayList<>();


            for (FavoriteAnimal fa : favoriteAnimals) {
                Animal animal = animalRepository.findAnimalById(fa.getIdx());
                AnimalRes animalRes = new AnimalRes(animal.getIdx(), animal.getMainImgUrl(), animal.getName(), animal.getSpecies(), animal.getGender(), animal.getIsAdopted(), animal.getAge());


                animalResList.add(animalRes);
            }

            getAnimalsRes.setAnimalResList(animalResList);

            return getAnimalsRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true) // 마이페이지 - 단체
    public GetOrganizationPageRes organizationPage(Long userIdx) throws BaseException {
        try{
            GetOrganizationPageRes getOrganizationPageRes = new GetOrganizationPageRes();

            Optional<User> user = userRepository.findById(userIdx);

            getOrganizationPageRes.setUserIdx(user.get().getIdx());
            getOrganizationPageRes.setName(user.get().getName());
            getOrganizationPageRes.setPhoneNumber(user.get().getPhoneNumber());
            getOrganizationPageRes.setAddress(user.get().getAddress());
            getOrganizationPageRes.setEmail(user.get().getEmail());
            getOrganizationPageRes.setRole(user.get().getRole().toString());
            getOrganizationPageRes.setProfileImgUrl(user.get().getProfileImgUrl());
            getOrganizationPageRes.setOrganizationName(user.get().getOrganizationName().toString());

            return getOrganizationPageRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true) // 마이페이지 등록동물 - 단체
    public GetAnimalsRes organizationAnimals(int page, Long userIdx) throws BaseException {
        try{
            GetAnimalsRes getAnimalsRes = new GetAnimalsRes();

            //Pageable pageable = PageRequest.of(page, 2, Sort.Direction.DESC, "idx");

            List<Animal> animals = animalRepository.findByUserIdx(userIdx, page);

            List<AnimalRes> animalResList = new ArrayList<>();

            for (Animal a : animals) {
                AnimalRes animalRes = new AnimalRes(a.getIdx(), a.getMainImgUrl(), a.getName(), a.getSpecies(), a.getGender(), a.getIsAdopted(), a.getAge());

                animalResList.add(animalRes);
            }

            getAnimalsRes.setAnimalResList(animalResList);

            return getAnimalsRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional // 회원정보수정 - 개인
    public Long updatePrivate(Long userIdx, PatchUserReq patchUserReq) throws BaseException {
        try{
            Optional<User> user = userRepository.findById(userIdx);

            if (patchUserReq.getName() == null) {
                patchUserReq.setName(user.get().getName()); }
            if (patchUserReq.getPhoneNumber() == null) {
                patchUserReq.setPhoneNumber(user.get().getPhoneNumber()); }
            if (patchUserReq.getAddress() == null) {
                patchUserReq.setAddress(user.get().getAddress()); }
            if (patchUserReq.getEmail() == null) {
                patchUserReq.setEmail(user.get().getEmail()); }

            user.get().setName(patchUserReq.getName());
            user.get().setPhoneNumber(patchUserReq.getPhoneNumber());
            user.get().setAddress(patchUserReq.getAddress());
            user.get().setEmail(patchUserReq.getEmail());

            userRepository.save(user.get());

            return user.get().getIdx();
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional // 회원정보수정 - 단체
    public Long updateOrganization(Long userIdx, PatchUserReq patchUserReq) throws BaseException {
        try{
            Optional<User> user = userRepository.findById(userIdx);

            if (patchUserReq.getName() == null) {
                patchUserReq.setName(user.get().getName()); }
            if (patchUserReq.getPhoneNumber() == null) {
                patchUserReq.setPhoneNumber(user.get().getPhoneNumber()); }
            if (patchUserReq.getAddress() == null) {
                patchUserReq.setAddress(user.get().getAddress()); }
            if (patchUserReq.getEmail() == null) {
                patchUserReq.setEmail(user.get().getEmail()); }

            user.get().setName(patchUserReq.getName());
            user.get().setPhoneNumber(patchUserReq.getPhoneNumber());
            user.get().setAddress(patchUserReq.getAddress());
            user.get().setEmail(patchUserReq.getEmail());

            userRepository.save(user.get());

            return user.get().getIdx();
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional // 회원탈퇴
    public void outUser(Long userIdx) throws BaseException {
        try {
            Optional<User> user = userRepository.findById(userIdx);

            userRepository.delete(user.get());
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean checkUsername(String username) throws BaseException{
        try{
            return userRepository.existsByUsername(username);
        } catch (Exception exception){
            throw new BaseException(BaseResponseStatus.USERS_EXISTS_USERNAME);
        }
    }

    public boolean checkEmail(String email) throws BaseException {
        try{
            return userRepository.existsByEmail(email);
        } catch (Exception exception){
            throw new BaseException(BaseResponseStatus.USERS_EXISTS_EMAIL);
        }
    }

    //건호 추가
    public User findUser(Long userIdx) throws BaseException{
        try {
            Optional<User> findUser = userRepository.findById(userIdx);
            if (findUser == null) {
                throw new BaseException(BaseResponseStatus.NON_EXISTING_USER);
            }
            return findUser.get();
        } catch (BaseException e) {
            throw new BaseException(e.getStatus());
        }catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

