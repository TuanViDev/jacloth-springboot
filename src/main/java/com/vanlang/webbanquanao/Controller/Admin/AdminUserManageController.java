package com.vanlang.webbanquanao.Controller.Admin;

import com.vanlang.webbanquanao.Model.Category;
import com.vanlang.webbanquanao.Model.User;
import com.vanlang.webbanquanao.Service.CategoryService;
import com.vanlang.webbanquanao.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminUserManageController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/usermanager")
    public String AdminUserManager() {
        return "redirect:/admin/usermanager/list";
    }

    @GetMapping("/admin/usermanager/list")
    public String AdminUserManagerList(Model model) {
        model.addAttribute("users", userService.getAllUser());
        return "/admin/usermanager/list";
    }

    @PostMapping("/admin/usermanager/updaterole")
    public String AdminUserManagerUpdateRole(@RequestParam("id") long id, @RequestParam("role") String role) {
        try {
            ResponseEntity<String> response = UpdateUserRoleAPI(id,role);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/admin/usermanager/list?updateRoleSuccess";
            } else {
                return "redirect:/admin/usermanager/list?updateRoleUnsuccess";
            }
        } catch (Exception e) {
            return "redirect:/admin/usermanager/list?unableToDelete";
        }
    }

    @PostMapping("/admin/usermanager/delete")
    public String AdminDeleteUser(@RequestParam("id") long id) {
        try {
            ResponseEntity<String> response = DeleteUserAPI(id);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "redirect:/admin/usermanager/list?deletedSuccess";
            } else {
                return "redirect:/admin/usermanager/list?deletedUnsuccess";
            }
        } catch (Exception e) {
            return "redirect:/admin/usermanager/list?unableToDelete";
        }
    }



    ////////////////////     API            ///////////////////
    @PutMapping("/api/admin/UpdateUserRole")
    public ResponseEntity<String> UpdateUserRoleAPI(long id, String role) {
        User user = userService.findUserById(id);
        user.setRole(role);
        userService.saveUser(user);

        if (user.getRole().equals(role)) {
            return ResponseEntity.ok().body("User role updated successfully");
        } else {
            return ResponseEntity.badRequest().body("User role updated unsuccessfully");
        }
    }

    @DeleteMapping("/api/admin/DeleteUser")
    public ResponseEntity<String> DeleteUserAPI(long id) {
        userService.deleteUserById(id);

        if (userService.findUserById(id) == null) {
            return ResponseEntity.ok().body("User deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("User deleted unsuccessfully");
        }
    }

}
