package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.exceptions.UnknownWorkIdException;
import bci.exceptions.InvalidValueException;

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
        addIntegerField("identifier", Prompt.workId());
        addIntegerField("value", Prompt.amountToUpdate());
    }

    @Override
    protected final void execute() throws CommandException {
        int id = integerField("identifier");
        int val = integerField("value");

        try {
            _receiver.updateStock(id, val);
        } catch (UnknownWorkIdException e) {
            throw new NoSuchWorkException(id);
        } catch (InvalidValueException ex) {
            _display.addLine(Message.notEnoughInventory(id, val));
        }
    }

}
