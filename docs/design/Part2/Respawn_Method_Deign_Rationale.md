# class(s) responsibility
- `DeathPublisher`: interface that can be implemented to notify affected subscribers to make corresponding effects(e.g. remove all Enemy except boss).
- `PlayerDeathMessageBus`: implements `DeathPublisher` and focus on the responsibility of holding subscriber list & notify subscribers when player dies. 
- `DeathSubscriber`: self-register to list held by `PlayerDeathMessageBus`; be notified when player dies and make implementation classes take action.
- `BossEnemy`: new class extends from `EnemyActor`. Add capability(Status.Boss) to differentiate boss from regular enemies. `Abxervyer` is refactored to extends `BossEnemy`.
- `EnemyActor`, `Rune`, `Gate`: implements `DeathSubscriber`.
# reason for current design choice
I choose to use Observer design pattern and decouples MessageBus responsibility from Player, because it will be too difficult to refactor a lot of codes(multiple Spawner and EnemyActor constructors). Besides, it improves decoupling and modularity by seperating this functionality from Player.
## extensibility
DeathPublisher and DeathSubscriber provides common API that can be implemented by different classes to achieve different functionality. For example, if we need to make certain Boss's death have effects on enviroment(e.g. locks gate, cow level lol), or dropped Item, etc, we can do this by implementing different publisher and subscribers.
## Pros:
- **Single Responsibility Principle - SRP**: Single Responsibility Principle is adhered by creating a PlayerDeathMessageBus that only takes care about delivering player death message to subscribers.
- **Interface Segregation Principle - ISP**: DeathPublisher and DeathSubscriber defines methods about pushing a certain actor's death to affected subscribers. It is consistent with Interface Segregation Principle because implementations of the 2 interfaces can focus on this functionality and do not need to implement anything they don't need.
- **Dependency Inversion Principle - DIP**: Runes, Gate, EnemyActor, PlayerDeathMessageBus all depend on abstraction(interface) instead of concrete classes.
- **Avoid code smell**: The design of the Observer pattern avoids several common code smells, such as tight coupling and large god classes(Player does not manage Subscriber or push notifications). This is because the pattern promotes loose coupling between classes by using interfaces and events, and it also avoids the need for a central class that manages all of the interactions between publishers and subscribers(PlayerDeathMessageBus only takes care of Player death). As a result, the code is easier to understand, maintain, and extend.
- **Connascence**: Connascence appeared in this code are mostly static connascence. This code does not need to be executed in a specific order, or cares about execution of multiple conponents, and values are not semantically linked. CoI did appear since Player needs to hold a object of PlayerDeathMessageBus, which are in 2 separate packages. But it is acceptable since the PlayerDeathMessageBus object is only referenced in one place inside Player class.
- Subscribers are auto-registered when created, so we don't need to care about finding and registering them.
## Cons:
- It might be challenging to maintain a large subscriber list.
- multiple inheritance: I added BossEnemy which is aadditional layer of inheritance between EnemyActor and Abxervyer, because I believe resetting HP might be a common feature for all bosses.
# Alternative Design Choices:
We don't have to create a new BossEnemy class. We can just override resetEnemy() but I believe resetting HP might be a common boss feature. 