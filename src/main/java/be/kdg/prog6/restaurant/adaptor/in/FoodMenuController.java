package be.kdg.prog6.restaurant.adaptor.in;

import be.kdg.prog6.restaurant.port.in.CreateDishDraftUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foodmenus/{foodMenuId}")
public class FoodMenuController {
    private final CreateDishDraftUseCase createDishDraftUseCase;

    public FoodMenuController(CreateDishDraftUseCase createDishDraftUseCase) {
        this.createDishDraftUseCase = createDishDraftUseCase;
    }
}
