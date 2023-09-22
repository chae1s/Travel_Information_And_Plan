package com.example.eodi.controller;


//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/users")
public class ProfileController {

    // UserService로 통합, 더이상 사용하지 않는 Cotroller


//    private final ProfileService profileService;


//    // 본인 프로필 조회
//    // GET /users/me/profile
//    @GetMapping("/me/profile")
//    public ResponseEntity<ProfileDto> readMyProfile(Authentication auth) {
//        return ResponseEntity.status(HttpStatus.OK).body(profileService.readMyProfile(auth.getName()));
//    }
//
//    // 프로필 조회
//    // GET /users/profile/{userId}
//    @GetMapping("/profile/{userId}")
//    public ResponseEntity<ProfileDto> readProfile(@PathVariable("userId") Long userId) {
//        return ResponseEntity.status(HttpStatus.OK).body(profileService.readProfile(userId));
//    }
//
//
//    //프로필 수정
//    // PUT /users/me/profile
//    @PutMapping("/me/profile")
//    public ResponseEntity<ResponseDto> updateProfile(
//            @RequestPart(value = "profile", required = false) ProfileDto dto,
//            @RequestParam(value = "image", required = false) MultipartFile profileImage,
//            Authentication auth) {
//        profileService.updateProfile(dto, profileImage, auth.getName());
//        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.getMessage("프로필이 수정되었습니다."));
//    }

}
