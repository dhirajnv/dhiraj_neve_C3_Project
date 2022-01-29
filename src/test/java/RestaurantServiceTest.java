import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    private LocalTime testOpenTime = LocalTime.parse("10:30:00");
    private LocalTime testCloseTime = LocalTime.parse("22:00:00");
    private String testRestaurantName = "Amelie's cafe";
    private String testLocation = "Chennai";
    private String testNameItem1 = "Sweet corn soup";
    private String testNameItem2 = "Vegetable lasagne";
    private int testPriceItem1 = 119;
    private int testPriceItem2 = 269;

    public RestaurantService createDefaultRestaurantServiceForTest() {
        RestaurantService restaurantService = new RestaurantService();
        Restaurant restaurant = restaurantService.addRestaurant(testRestaurantName, testLocation, testOpenTime, testCloseTime);
        restaurant.addToMenu(testNameItem1, testPriceItem1);
        restaurant.addToMenu(testNameItem2, testPriceItem2);
        return restaurantService;
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        RestaurantService service = new RestaurantService();
        Restaurant addedRestaurant = service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        Restaurant searchedRestaurant = service.findRestaurantByName("Pumpkin Tales");
        assertEquals(searchedRestaurant.getName(), addedRestaurant.getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        RestaurantService service = createDefaultRestaurantServiceForTest();
        assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName("Invalid"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        RestaurantService service = createDefaultRestaurantServiceForTest();

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant(testRestaurantName);
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        RestaurantService service = createDefaultRestaurantServiceForTest();

        assertThrows(restaurantNotFoundException.class, () -> service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {
        RestaurantService service = createDefaultRestaurantServiceForTest();

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
}