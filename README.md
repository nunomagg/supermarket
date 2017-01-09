This documents serves as an explanation to the implemented solution. This was achieved by Nuno Maggiolly, on the context of the recruitment process for the company Mirriad.
For this problem, it was proposed that I implemented a supermarket checkout system, considering multiple items and discounts, all enabled for usage. These discounts included (but were not limited to):

   - Buying 3 items of a certain type and paying only 2;
   - Buying 2 items of a certain type for a special price;
   - Buying a specific set of items and receiving the cheapest one for free;
   - After buying a number of items, another set of items being offered.

Considering this problem, I came to the related solution. This solution involves considering the discounts as generic as possible, so it is easy to simply add new ones, while at the same time separating the discount logic and the shopping cart logic.
As such, the shopping cart manages the products and their quantities on the cart, as Item(s). Where an Item manages the quantity for a specific product, along with the notion of a product already being included in a discount or not. 
This is the only deep connection between the shopping cart and the discounts. On the other hand, the discount factory receives a shopping cart object and applies all available discounts to each of the products.

These are a couple of notes worth mentioning:

   - Due to the implementation decisions made, it is fairly easy to include new discounts;
   - Although it was considered, a reordering/prioritizing method for the discounts wasn't implemented. Thus, the discounts are considered in the order they're added;
   - Each unit of a product can't take part in different discounts;
   - This code used multiple unit tests to confirm its validity;
   - There's a method called printCart() which was used during development to see each result verbosely. This method is only used in final release for a visual representation of the final state of the cart.
   - This project uses Java 8 and JUnit 5, since a depedency injector wasn't used, these dependencies must be added manually.

Along with these notes, it's also worth mentioning that even though a number of design patterns were considered, in the end the way this implementation was created was based on the strategy design pattern, considering a rules engine. However, its principles weren't followed rigourously.
On a final note, I hope this readme file is enough to clarify most of the questions that could be related to certain features or limitations found. Any question you have, feel free to contact me through the contacts already shared.


Thank you!

Best regards,

Nuno Maggiolly