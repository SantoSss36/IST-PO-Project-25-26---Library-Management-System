package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.UserIsActiveException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.exceptions.InvalidUserStatusException;
import bci.exceptions.UnknownUserIdException;
import bci.user.User;

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException, UserIsActiveException {
        int userId = integerField("userId");
        try {
            _receiver.payFine(userId);
        } catch (UnknownUserIdException e) {
            throw new NoSuchUserException(userId);
        } catch (InvalidUserStatusException e) {
            throw new UserIsActiveException(userId);
        }
    }
}
