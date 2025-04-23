package com.rishi.rpf.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rishi.rpf.entity.Contact;
import com.rishi.rpf.entity.User;
import com.rishi.rpf.forms.ContactForm;
import com.rishi.rpf.forms.ContactSearchForm;
import com.rishi.rpf.helpers.AppConstants;
import com.rishi.rpf.helpers.Helper;
import com.rishi.rpf.helpers.Message;
import com.rishi.rpf.helpers.MessageType;
import com.rishi.rpf.services.ContactService;
import com.rishi.rpf.services.ImageService;
import com.rishi.rpf.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class contactController {

    Logger logger = LoggerFactory.getLogger(contactController.class); 

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;


    // add contact page : handlers
    @RequestMapping("/add")
    public String addContactView(Model model, Authentication authentication) {
        ContactForm contactForm = new ContactForm();
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);

        // ✅ Add this block:
        // String email = Helper.getEmailOfLoggedInUser(authentication);
        // User user = userService.getUserByEmail(email);
        // model.addAttribute("user", user);

        return "user/addContact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {
        // process the form data

        // validate form
        if (result.hasErrors()) {
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/addContact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact

        User user = userService.getUserByEmail(username);

         //2 process the contact picture

        //image process

    //    logger.info("file information : {}",contactForm.getContactImage().getOriginalFilename());

        //3 save the contact picture

        String filename = UUID.randomUUID().toString();
        String fileURL = imageService.uploadImage(contactForm.getContactImage(),filename);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());

        contact.setState(contactForm.getState());
        contact.setCity(contactForm.getCity());
        contact.setArea(contactForm.getArea());
        contact.setAge(contactForm.getAge());
        contact.setGender(contactForm.getGender());
        contact.setFoodType(contactForm.getFoodType());
        contact.setOccupation(contactForm.getOccupation());
        contact.setCollege(contactForm.getCollege());
        contact.setReligion(contactForm.getReligion());


        // contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);

        contactService.saveContact(contact);
        System.out.println(contactForm);

        //3. set the contact picture url

        //4. set message to be displayed on the view

        session.setAttribute("message", Message.builder()
                .content("You have successfully added a new contact")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts/add";
    }



    //view contacts 
    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model, Authentication authentication){
        //load all the user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());
        
        return "user/contacts";
    }

    
    
    //search handler

    @RequestMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model , Authentication authentication

    ){
        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm",contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        
        return "user/search";
    }



   // delete contact
   @RequestMapping("/delete/{contactId}")
   public String deleteContact(
           @PathVariable("contactId") String contactId,
           HttpSession session) {
           
       contactService.deleteContact(contactId);
       logger.info("contactId {} deleted", contactId);

       session.setAttribute("message",
               Message.builder()
                       .content("Contact is Deleted successfully !! ")
                       .type(MessageType.green)
                       .build()

       );

       return "redirect:/user/contacts";
   }





  // update contact form view
  @GetMapping("/view/{contactId}")
  public String updateContactFormView(
          @PathVariable("contactId") String contactId,
          Model model) {

      var contact = contactService.getById(contactId);
      ContactForm contactForm = new ContactForm();
      contactForm.setName(contact.getName());
      contactForm.setEmail(contact.getEmail());
      contactForm.setPhoneNumber(contact.getPhoneNumber());

        // ✅ Add new fields here
        contactForm.setState(contact.getState());
        contactForm.setCity(contact.getCity());
        contactForm.setArea(contact.getArea());
        contactForm.setAge(contact.getAge());
        contactForm.setGender(contact.getGender());
        contactForm.setFoodType(contact.getFoodType());
        contactForm.setOccupation(contact.getOccupation());
        contactForm.setCollege(contact.getCollege());
        contactForm.setReligion(contact.getReligion());

//      contactForm.setAddress(contact.getAddress());
      contactForm.setDescription(contact.getDescription());
      contactForm.setFavorite(contact.isFavorite());
      contactForm.setWebsiteLink(contact.getWebsiteLink());
      contactForm.setLinkedInLink(contact.getLinkedInLink());
      contactForm.setPicture(contact.getPicture());

    
      model.addAttribute("contactForm", contactForm);
      model.addAttribute("contactId", contactId);

      return "user/update_contact_view";
  }



  // update contact handler

  @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
  public String updateContact(@PathVariable("contactId") String contactId,
          @Valid @ModelAttribute ContactForm contactForm,
          BindingResult bindingResult,
          Model model) {

      // update the contact
      if (bindingResult.hasErrors()) {
          return "user/update_contact_view";
      }

      var con = contactService.getById(contactId);
      con.setId(contactId);
      con.setName(contactForm.getName());
      con.setEmail(contactForm.getEmail());
      con.setPhoneNumber(contactForm.getPhoneNumber());

    con.setState(contactForm.getState());
    con.setCity(contactForm.getCity());
    con.setArea(contactForm.getArea());
    con.setAge(contactForm.getAge());
    con.setGender(contactForm.getGender());
    con.setFoodType(contactForm.getFoodType());
    con.setOccupation(contactForm.getOccupation());
    con.setCollege(contactForm.getCollege());
    con.setReligion(contactForm.getReligion());
//    con.setAddress(contactForm.getAddress());

      // con.setAddress(contactForm.getAddress());
      con.setDescription(contactForm.getDescription());
      con.setFavorite(contactForm.isFavorite());
      con.setWebsiteLink(contactForm.getWebsiteLink());
      con.setLinkedInLink(contactForm.getLinkedInLink());


// //      con.setAddress(contactForm.getAddress());
//       con.setDescription(contactForm.getDescription());
//       con.setFavorite(contactForm.isFavorite());
//       con.setWebsiteLink(contactForm.getWebsiteLink());
//       con.setLinkedInLink(contactForm.getLinkedInLink());

      // process image:

      if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
          logger.info("file is not empty");
          String fileName = UUID.randomUUID().toString();
          String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
          con.setCloudinaryImagePublicId(fileName);
          con.setPicture(imageUrl);
          contactForm.setPicture(imageUrl);

      } else {
          logger.info("file is empty");
      }

      var updateCon = contactService.update(con);
      logger.info("updated contact {}", updateCon);

      model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

      return "redirect:/user/contacts/view/" + contactId;
  }
      
}
