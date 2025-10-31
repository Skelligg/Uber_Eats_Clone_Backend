# KEEP DISHES GOING BACKEND

## Challenges & Accomplishments

I found the backend of this project to be very insighftul to teaching me about DDD
and hexagonal architecture. While scope was very big, I liked the variety of technologies
that were used. I am proud of the fact that I managed to implement most of the user
stories in the time allotted.

I did however think there were too many user stories for the time given since there was
so many new things to learn. On finishing this project, I can already see areas for
improvement and where I wasted time in the beginning, but that is the learning process
after all.

---

## ✅ Finished Features

List of all user stories that were successfully implemented and tested.

| N  | AS A      | USER STORY                                                                                                      |
|----|------------|----------------------------------------------------------------------------------------------------------------|
| 1  | OWNER      | As an owner, I want to sign up/sign in to access my restaurant management area.                               |
| 2  | OWNER      | As an owner, I want to create my restaurant by submitting name, full address, contact email, picture(s) URL, default preparation time, type of cuisine, and opening hours. |
| 3  | OWNER      | As an owner, I want to edit a dish as a draft without affecting the live menu.                                |
| 4  | OWNER      | As an owner, I want to publish a dish so it becomes available to customers.                                   |
| 5  | OWNER      | As an owner, I want to unpublish a dish so it is no longer available to customers.                            |
| 6  | OWNER      | As an owner, I want to apply all pending dish changes in one action.                                          |
| 7  | OWNER      | As an owner, I want to schedule a set of publishes/unpublishes to go live together at a chosen time.          |
| 8  | OWNER      | As an owner, I want to mark a dish out of stock or back in stock immediately.                                 |
| 10 | OWNER      | As an owner, I want to accept or reject new orders and provide a reason on rejection.                         |
| 11 | OWNER      | As an owner, I want orders without a decision within five minutes to be automatically declined.               |
| 12 | OWNER      | As an owner, I want to mark an accepted order ready for pickup.                                               |
| 13 | CUSTOMER   | As a customer, I want the landing page to let me continue as a customer.                                      |
| 14 | CUSTOMER   | As a customer, I want to explore restaurants in a list or on a map.                                           |
| 15 | CUSTOMER   | As a customer, I want to view a restaurant’s details and dishes.                                              |
| 16 | CUSTOMER   | As a customer, I want to filter restaurants by type of cuisine, price range, ~~distance, and guesstimated delivery time.~~ |
| 17 | CUSTOMER   | As a customer, I want to filter dishes by type (e.g., starter or dessert) and by food tags (lactose, gluten, vegan, …). |
| 18 | CUSTOMER   | As a customer, I want sensible sorting options (e.g., price) when viewing dishes.                             |
| 19 | CUSTOMER   | As a customer, I want to build a basket from a single restaurant’s published dishes.                          |
| 20 | CUSTOMER   | As a customer, I want checkout to be blocked if any dish in my basket becomes out of stock or unpublished while I’m browsing. |
| 22 | CUSTOMER   | As a customer, I want to provide my name, delivery address, and contact email at checkout.                    |
| 23 | CUSTOMER   | As a customer, I want to pay using the payment provider during checkout.                                      |
| 24 | CUSTOMER   | As a customer, I want a confirmation with a link so I can return to track my order.                           |
| 25 | CUSTOMER   | As a customer, I want to track the progress of my order as its status changes.                                |
| 27 | KDG        | As KDG, I want no more than 10 dishes to be available to customers at any moment.                             |
| 28 | KDG        | As KDG, I want to publish messages for the delivery service when an order is accepted and when it is ready for pickup. |
| 29 | KDG        | As KDG, I want to consume delivery service messages for picked up, delivered, and courier locations to update orders. |
| 30 | KDG        | As KDG, I want customers to be able to order without signing up or signing in.                                |
| 31 | KDG        | As KDG, I want each owner to manage exactly one restaurant.                                                   |


---

## ❌ Unfinished / Planned Features

List features that are planned, in progress, or not yet implemented.


| N  | AS A      | USER STORY                                                                                                      |
|----|-----------|----------------------------------------------------------------------------------------------------------------|
| 9  | OWNER     | As an owner, I want to set opening hours and manually open/close the restaurant at any moment.                |
| 20 | CUSTOMER  | As a customer, I want to see a guesstimated delivery time based on location, default preparation time, and busyness. |


## COMMAND & EVENT CATALOG

### Events
| Context    | Event Name                 | Description                                            |
| ---------- | -------------------------- | ------------------------------------------------------ |
| Dish       | DishMarkedAvailableEvent   | Fired when a dish is marked as available.              |
| Dish       | DishMarkedOutOfStockEvent  | Fired when a dish is marked out of stock.              |
| Dish       | DishPublishedToMenuEvent   | Fired when a dish is published to the menu.            |
| Dish       | DishUnpublishedToMenuEvent | Fired when a dish is unpublished from the menu.        |
| FoodMenu   | FoodMenuCreatedEvent       | Fired when a new food menu is created.                 |
| Order      | OrderAcceptedEvent         | Fired when an order is accepted by the restaurant.     |
| Order      | OrderCreatedEvent          | Fired when a new order is created.                     |
| Order      | OrderDeliveredEvent        | Fired when an order is delivered.                      |
| Order      | OrderLocationEvent         | Fired when a courier location is updated for an order. |
| Order      | OrderPickedUpEvent         | Fired when an order is picked up by a courier.         |
| Order      | OrderReadyForPickupEvent   | Fired when an order is ready for pickup.               |
| Order      | OrderRejectedEvent         | Fired when an order is rejected.                       |
| Restaurant | RestaurantCreatedEvent     | Fired when a new restaurant is created.                |
### Ordering Commands
| Command Name                     | Description                                          |
| -------------------------------- | ---------------------------------------------------- |
| DishMarkedAvailableCommand       | Command to mark a dish as available.                 |
| DishMarkedOutOfStockCommand      | Command to mark a dish as out of stock.              |
| DishPublishedCommand             | Command to publish a dish to the menu.               |
| DishUnpublishedCommand           | Command to unpublish a dish from the menu.           |
| CreateOrderCommand               | Command to create a new order.                       |
| OrderAcceptedCommand             | Command to accept an order.                          |
| OrderRejectedCommand             | Command to reject an order.                          |
| OrderReadyForPickupCommand       | Command to mark an order ready for pickup.           |
| OrderPickedUpCommand             | Command to mark an order as picked up.               |
| OrderDeliveredCommand            | Command to mark an order as delivered.               |
| OrderLocationUpdatedCommand      | Command to update the courier location for an order. |
| RestaurantAddedProjectionCommand | Command to add a restaurant projection.              |
### Restaurant Commands
| Command Name            | Description                                              |
| ----------------------- | -------------------------------------------------------- |
| CreateDishDraftCommand  | Command to create a new dish draft.                      |
| EditDishCommand         | Command to edit an existing dish draft.                  |
| DishStateChangeCommand  | Command to change a dish state (available/out of stock). |
| ScheduleChangesCommand  | Command to schedule dish changes (publish/unpublish).    |
| OrderPlacedCommand      | Command triggered when an order is placed.               |
| CreateRestaurantCommand | Command to create a new restaurant.                      |

