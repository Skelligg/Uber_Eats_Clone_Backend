package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.port.in.CreateDishDraftCommand;
import be.kdg.prog6.restaurant.port.in.CreateDishDraftUseCase;
import be.kdg.prog6.restaurant.port.out.UpdateDishPort;
import org.springframework.stereotype.Service;

@Service
public class CreateDishDraftUseCaseImpl implements CreateDishDraftUseCase {

    private final UpdateDishPort updateDishPort;

    public CreateDishDraftUseCaseImpl(UpdateDishPort updateDishPort) {
        this.updateDishPort = updateDishPort;
    }

    @Override
    public void createDishDraftForFoodMenu(CreateDishDraftCommand command) {

    }
}
