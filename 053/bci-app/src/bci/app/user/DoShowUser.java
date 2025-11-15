package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.exceptions.UnknownUserIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.exceptions.UnknownUserIdException;

/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

    DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
        addIntegerField("key", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException, NoSuchUserException {
        int _Id = integerField("key");
        try {
            _display.addLine(_receiver.displayUser(_Id));
        } catch (UnknownUserIdException e) {
            throw new NoSuchUserException(_Id);
        }
        
    }

}
