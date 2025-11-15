package bci.app.request;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.exceptions.UnknownUserIdException;
import bci.exceptions.UnknownWorkIdException;
import bci.exceptions.UnknownRequestException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        int workId = integerField("workId");
        
        try {
            _receiver.returnWork(userId, workId);
            int fine = _receiver.showFine(userId);
            if (fine > 0) {
                _display.addLine(Message.showFine(userId, fine));
                _display.display();
                if (Form.confirm(Prompt.finePaymentChoice())) {
                    _receiver.updateUserFine(userId, fine);
                }
            }
        } catch (UnknownUserIdException ex) {
            throw new NoSuchUserException(userId);
        } catch (UnknownWorkIdException exc) {
            throw new NoSuchWorkException(workId);
        } catch (UnknownRequestException e) {
            throw new WorkNotBorrowedByUserException(workId, userId);
        }
    }

}
