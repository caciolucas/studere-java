package com.studere.studerejava.treinere.controllers;

import com.studere.studerejava.treinere.services.SetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sets")
public class SetController {
    private final SetService setRepository;
    private final SetService setService;

    public SetController(SetService setRepository, SetService setService) {
        this.setRepository = setRepository;
        this.setService = setService;
    }

//    @PostMapping("/")
//    public ResponseEntity<?> createSet() {
//        try {
//            Set set = setService.createSet
//            return ResponseEntity.ok(term);
//        } catch (BaseException e) {
//            throw e;
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
//        }
//    }

}
