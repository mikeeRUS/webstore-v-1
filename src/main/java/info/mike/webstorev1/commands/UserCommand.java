package info.mike.webstorev1.commands;

import info.mike.webstorev1.validators.FieldMatch;

import javax.validation.constraints.*;

@FieldMatch.List({
        @FieldMatch(field = "password", fieldMatch = "confirmPassword", message = "Podane hasła nie są takie same!"),
        @FieldMatch(field = "email", fieldMatch = "confirmEmail", message = "Podane emaile nie są takie same!")})
public class UserCommand {

    @NotBlank(message = "Pole nie może być puste.")
    private String firstName;

    @NotBlank(message = "Pole nie może być puste.")
    private String lastName;

    @Size(min = 6, message = "Hasło musi zawierać minimum 6 znaków.")
    private String password;

    private String confirmPassword;

    @Email(message = "Niepoprawny adres email")
    @NotBlank(message = "Pole nie może być puste.")
    private String email;

    private String confirmEmail;

    @AssertTrue
    private Boolean terms;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }
}
