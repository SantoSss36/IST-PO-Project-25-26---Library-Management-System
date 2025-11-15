package bci.app.request;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.BorrowingRuleFailedException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.InvalidRuleException;
import bci.exceptions.UnknownRequestException;
import bci.exceptions.UnknownUserIdException;
import bci.exceptions.UnknownWorkIdException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        int workId = integerField("workId");

        try {
            _receiver.requestWork(userId, workId);
            _display.addLine(Message.workReturnDay(workId, _receiver.getRequestLimitDate(userId, workId)));
        } catch (InvalidRuleException e) {
            if (e.getRuleId() == 3) {
                if (Form.confirm(Prompt.returnNotificationPreference())) {
                    _receiver.registerNotification(userId, workId);
                }
            }
            else {
                throw new BorrowingRuleFailedException(userId, workId, e.getRuleId());
            }
        } catch (UnknownUserIdException ex) {
            throw new NoSuchUserException(userId);
        } catch (UnknownWorkIdException exc) {
            throw new NoSuchWorkException(workId);
        } catch (UnknownRequestException excp) {
            throw new NoSuchWorkException(workId);
        }
    }
}