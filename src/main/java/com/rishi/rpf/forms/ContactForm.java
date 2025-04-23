package com.rishi.rpf.forms;

import org.springframework.web.multipart.MultipartFile;

import com.rishi.rpf.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContactForm {

    @NotBlank(message ="Name is required")
    @Size(min = 3, message="Minimum 3 Characters is required")
    private String name;

    @NotBlank(message ="email is required")
    @Email(message="Invalid email")
    private String email;

    @NotBlank(message ="phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Area is required")
    private String area;


    private Integer age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Food type is required")
    private String foodType;

    @NotBlank(message = "Occupation is required")
    private String occupation;

    @NotBlank(message = "College is required")
    private String college;


    @NotBlank(message = "Religion is required")
    private String religion;



    // @NotBlank(message ="address is required")
    // private String address;

    private String description;

    private boolean favorite;

    private String websiteLink;

    private String linkedInLink;

    // annotation create karenge jo file validate
    // size
    // resolution

   @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;

    private String picture;
    
}
