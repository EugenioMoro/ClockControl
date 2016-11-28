package botContext;

import org.telegram.telegrambots.api.objects.Update;

public interface ContextInterface {
	

	void abort();
	void work(Update update);

}
