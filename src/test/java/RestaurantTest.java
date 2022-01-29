import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    private LocalTime testOpenTime = LocalTime.parse("10:30:00");
    private LocalTime testCloseTime = LocalTime.parse("22:00:00");
    private String testRestaurantName = "Amelie's cafe";
    private String testLocation = "Chennai";
    private String testNameItem1 = "Sweet corn soup";
    private String testNameItem2 = "Vegetable lasagne";
    private int testPriceItem1 = 119;
    private int testPriceItem2 = 269;

    public Restaurant createDefaultRestaurantForTest() {
        Restaurant restaurant = new Restaurant(testRestaurantName, testLocation, testOpenTime, testCloseTime);
        restaurant.addToMenu(testNameItem1, testPriceItem1);
        restaurant.addToMenu(testNameItem2, testPriceItem2);
        return restaurant;
    }

    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        Restaurant restaurant = createDefaultRestaurantForTest();
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("14:00:00"));
        assertTrue(mockedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        Restaurant restaurant = createDefaultRestaurantForTest();
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("04:00:00"));
        assertFalse(mockedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void calculate_cost_should_return_correct_result() {
        Restaurant restaurant = createDefaultRestaurantForTest();
        List<String> itemList = new ArrayList<String>();
        itemList.add(testNameItem1);
        itemList.add(testNameItem2);
        assertEquals(testPriceItem1 + testPriceItem2, restaurant.calculateCost(itemList));
    }

    @Test
    public void calculate_cost_should_zero_for_zero_items() {
        Restaurant restaurant = createDefaultRestaurantForTest();
        List<String> itemList = new ArrayList<String>();
        assertEquals(0, restaurant.calculateCost(itemList));
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        Restaurant restaurant = createDefaultRestaurantForTest();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        Restaurant restaurant = createDefaultRestaurantForTest();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu(testNameItem2);
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        Restaurant restaurant = createDefaultRestaurantForTest();

        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}