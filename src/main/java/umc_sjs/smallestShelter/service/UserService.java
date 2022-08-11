package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.FavoriteAnimal;
import umc_sjs.smallestShelter.domain.User;
import umc_sjs.smallestShelter.dto.animal.AnimalRes;
import umc_sjs.smallestShelter.dto.animal.GetAnimalsRes;
import umc_sjs.smallestShelter.dto.user.*;
import umc_sjs.smallestShelter.repository.AnimalRepository;
import umc_sjs.smallestShelter.repository.FavoriteAnimalRepository;
import umc_sjs.smallestShelter.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponseStatus;

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
    public GetPrivatePageRes privatePage(Long userIdx) {

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

    @Transactional(readOnly = true) // 마이페이지 관심동물 - 개인
    public GetAnimalsRes privateAnimals(int page, Long userIdx) {

        GetAnimalsRes getAnimalsRes = new GetAnimalsRes();

        Pageable pageable = PageRequest.of(page, 2, Sort.Direction.DESC, "idx");

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

    @Transactional(readOnly = true) // 마이페이지 - 단체
    public GetOrganizationPageRes organizationPage(Long userIdx) {

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

    @Transactional(readOnly = true) // 마이페이지 등록동물 - 단체
    public GetAnimalsRes organizationAnimals(int page, Long userIdx) {

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

    @Transactional // 회원정보수정 - 개인
    public Long updatePrivate(Long userIdx, PatchUserReq patchUserReq) {
        Optional<User> user = userRepository.findById(userIdx);

        user.get().setName(patchUserReq.getName());
        user.get().setPhoneNumber(patchUserReq.getPhoneNumber());
        user.get().setAddress(patchUserReq.getAddress());
        user.get().setEmail(patchUserReq.getEmail());

        userRepository.save(user.get());

        return user.get().getIdx();
    }

    @Transactional // 회원정보수정 - 단체
    public Long updateOrganization(Long userIdx, PatchUserReq patchUserReq) {
        Optional<User> user = userRepository.findById(userIdx);

        user.get().setName(patchUserReq.getName());
        user.get().setPhoneNumber(patchUserReq.getPhoneNumber());
        user.get().setAddress(patchUserReq.getAddress());
        user.get().setEmail(patchUserReq.getEmail());

        userRepository.save(user.get());

        return user.get().getIdx();
    }

    @Transactional // 회원탈퇴
    public void outUser(Long userIdx) {

        Optional<User> user = userRepository.findById(userIdx);

        userRepository.delete(user.get());
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
}

