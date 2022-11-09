package com.tilted.Laba3.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tilted.Laba3.Models.UserDTO;
import com.tilted.Laba3.Models.ValidationError;
import com.tilted.Laba3.Services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import java.util.HashMap;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping()
    public List<UserDTO> getAllUsers() {
       return usersService.GetAll();
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        var userDTO = usersService.GetById(id);
        return userDTO != null
                ? new ResponseEntity<>(userDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        return usersService.DeleteById(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user) {
        var createdUser = usersService.Create(user);
        var responseModel = new HashMap<String, Integer>();
        responseModel.put("id", createdUser.Id);

        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @Valid @RequestBody UserDTO user) {
        if (usersService.GetById(id) == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.Id = id;
        usersService.Update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(produces = "application/json-patch+json", value="/{id}")
    public ResponseEntity<?> patchUser(@PathVariable int id, @RequestBody JsonPatch jsonPatch) {
        var foundUser = usersService.GetById(id);
        if (foundUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            var objMapper = new ObjectMapper();
            var node = jsonPatch.apply(objMapper.convertValue(foundUser, JsonNode.class));
            foundUser = objMapper.treeToValue(node, UserDTO.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            var constraints = validator.validate(foundUser);

            if (!constraints.isEmpty()) {

                var validationError = new ValidationError();
                validationError.Status = "Bad request";
                constraints.forEach(x -> validationError.Errors.put(x.getPropertyPath().toString(), x.getMessage()));

                return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
            }

            usersService.Update(foundUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationError handleValidationErrors(MethodArgumentNotValidException ex) {
        var validationError = new ValidationError();
        validationError.Status = "Bad request";

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            validationError.Errors.put(fieldName, errorMessage);
        });

        return validationError;
    }
}
