# mshm
simulates mushroom growth based on the real weather data

Primary purpose of this exercise is to explore how to optimise and manage environment through gamification.

Idea of this purpose is influenced by https://fold.it. Humans can easily solve tasks that are difficult or impossible for machines.
Weather forecast is by itself hard to do, and basing decisions on top of it is even harder.
It is more then likely that this particular usecase is trivial and actually machines could probably do a better job. Main goal is to get acquainted with the notion and play with the idea. Secondary goal is to have fun and learn about the technologies involved.

Step 0 (almost there!):
Users try to find the best suited environment for a given mushroom species by picking a worldwide location for mycelia with just the right temperature and humidity.

Next steps:
Mycelia spreads if conditions are right. If conditions are exactly right, simulate spore release and spread further based on wind state.


Backend is Clojure, cosists of a simulator and a web server.
Data storage is http://docs.basho.com/riak/kv/2.2.0/.
Frontend is Javascript, strongly relying on https://datalens.here.com and https://reactive.io/rxjs

mailto: ivo.kuzmanovic@gmail.com

http://ivo.kuzmanovic.com.hr