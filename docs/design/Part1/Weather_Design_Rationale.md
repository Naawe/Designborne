# refactor made to current code
- spawnEnemy is modified to spawn actor because I believe it suits the purpose of Spawner better. What type of Actor a Ground will produce depends on the Spawner it receives and there is no reason to assume SpawningGround only spawns Enemy.
- Spawner refactored from interface to abstract class because spawning actors is what Spawners are supposed to do. Inheritance can reduce code repeatition.
- odds and bound of spawning should always be its original to make spawn rate multilier work properly.
# class(s) responsibility
- `AncientWoodWeatherController`: Keep track of playturn and manage weather switching.
- `Weather`: interface of what Weather should do and provides API for AncientWoodWeatherController.
- `SunnyForestWeather`: handles concrete implementation of manipulations to be done.
- `RainyForestWeather`: same as above.
- `ForestGameMap`: an extension to `GameMap` that allows to look up for certain SpawningGround and EnemyActor and modify certain attributes accordingly.
# my design choice
## Weather
I assume a boss will always stay in one battlefield, and weather will only affect limited game maps. Different game maps may have different weather effects combinations. Since GameMap does not provide API essential for certain implementations, e.g. return ArrayList of locations stored in a game map for other client to look up certain Grounds that are affected by weather(in order to implement modifySpawnRate()), I have to implement GameMap extensions to provide necessary methods to achieve weather effects. 
## potential hacky method & justification
- casting is used in 
    - ForestGameMap.modifySpawnRate(), because I need to use spawnGround.updateSpawnRateMultiplier(multiplier) which is a method implemented in SpawningGround(which is one responsibility of SpawningGround, and We are not allowed to modify engine code anyway) Won't cause problem since we are getting SpawningGround from an ArrayList of SpawningGround got from previous method(that gets all SpawningGround instances of a chosen type)
    - weatherEffect() in Weather implementations. Weather is intented to be a general interface. Won't cause problem since we are getting GameMap of casted type when it is passed to weatherEffect().
## Pros
- Highly extensible. Create new GameMap Extension, Weather and WeatherController to add more weather functionality to more GameMaps.
- Separation of Concern: adheres to SRP because Weather functionalities is divided into different components.
- Dependency inversion Principle: Weather implementation depends on Weather interface, which provides API for Controller to call Weather for effects.
- Dependency injection: Concrete execution functions are implemented in GameMap extensions, and can be used by different weathers to perform basic actions(e.g. modify spawnRate, modify enemy damage multiplier)
## Cons
- used some casting since subclasses are needed in some places.
# extensibility
## Weather Effect Functionality
Different GameMap extensions may have different weather effects combinations and implemented accordingly. Same for different weather effects implemented in Weather implementations like SunnyForestWeather.
