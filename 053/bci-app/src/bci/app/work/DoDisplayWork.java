package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.exceptions.UnknownWorkIdException;

/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
        addIntegerField("key", Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException, NoSuchWorkException {
        int _Id = integerField("key");
        try {
            _display.addLine(_receiver.displayWork(_Id));
        } catch (UnknownWorkIdException e) {
            throw new NoSuchWorkException(_Id);
        }
    }
}