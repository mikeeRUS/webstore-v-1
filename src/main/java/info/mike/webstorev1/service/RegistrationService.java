package info.mike.webstorev1.service;

import info.mike.webstorev1.commands.UserCommand;

public interface RegistrationService {

    void sendActivationEmailToUser(UserCommand userCommand);
}
