
# # Soccer League

Soccer league is an Android application for managing league matches. Selected league teams can be viewed. By using every team in the league, a weekly match fixture will be made. There will be two half of the league. If Team A plays against Team B on the home side for the first half, Team A will be away side at the second half of the league. Every team matched with others exactly once. 
League and team information are taken from https://www.football-data.org/
Note: If BUNDESLIGA_ID (utils/Constants.kt) changed according to the league ids in the Football Data API, league name and teams will be changed automatically.
Debug and release apks are in the app folder.
### Tech

* MVVM architecture
* Single activity multiple fragments
* Coroutine for async tasks
* LiveData
* Timber for logging
* ViewModel
* Navigation
* Retrofit2 & Gson
* GlideToVectorYou
* OkHttp (For logging interceptor)

### In App Showcase:

![](https://github.com/etasdemir/SoccerLeague/blob/main/showcase.gif?raw=true)
