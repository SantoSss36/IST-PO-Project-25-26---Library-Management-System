package bci.app.user;

import java.security.UnrecoverableEntryException;

import bci.LibraryManager;
import bci.app.exceptions.UserRegistrationFailedException;
import bci.exceptions.UnknownUserRegistrationException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

    DoRegisterUser(LibraryManager receiver) {
        super(Label.REGISTER_USER, receiver);
        addStringField("userName", Prompt.userName());
        addStringField("userEmail", Prompt.userEMail());
    }

    @Override
    protected final void execute() throws UserRegistrationFailedException {
        try {
            _receiver.createUser(stringField("userName"), stringField("userEmail"));
            _display.addLine(Message.registrationSuccessful(_receiver.getUserID()));
        } catch (UnknownUserRegistrationException e) {
            throw new UserRegistrationFailedException(stringField("userName"), stringField("userEmail"));
        }
             
    }
}