SimpleTwitterClient
===================

This is a very simple twitter client

Time spent: 35 hours spent in total

**Completed user stories:**

*Required:*

 - [x] User can sign in to Twitter using OAuth login
 - [x] User can view the tweets from their home timeline
   - [x] User should be displayed the username, name, and body for each tweet
   - [x] User should be displayed the relative timestamp for each tweet "8m", "7h"
   - [x] User can view more tweets as they scroll with infinite pagination
   - [x] *Optional*: Links in tweets are clickable and will launch the web browser 
 - [x] User can compose a new tweet
   - [x] User can click a “Compose” icon in the Action Bar on the top right
   - [x] User can then enter a new tweet and post this to twitter
   - [x] User is taken back to home timeline with new tweet visible in timeline
   - [x] *Optional*: User can see a counter with total number of characters left for tweet
  
*Extra features asked on Moonday night:*
 - [x] When adding a new tweet to timeline, manually insert new tweet, do not refresh timeline
 - [x] When twitter rate limit reached, notify user with a toast message

*Advanced optional:*

 - [x] Improve the user interface and theme the app to feel "twitter branded" - minimal implementation!
 
*Advanced optional features not yet implemented:*

- [ ] User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh)
- [ ] User can open the twitter app offline and see last loaded tweets
   - [x] Tweets are persisted into sqlite and can be displayed from the local DB
- [ ] User can tap a tweet to display a "detailed" view of that tweet
- [ ] User can select "reply" from detail view to respond to a tweet
- [ ] Improve the user interface and theme the app to feel "twitter branded"
- [ ] User can see embedded image media within the tweet detail view
- [ ] Compose activity is replaced with a modal overlay


**Notes:**

* This is my initial submission to be graded. 
* I made the big mistake to not test my app reigorously during the development, especially the oAuth and the saving user credentials in shared preferences functionality. This came back to bite me today! I spent most of the day debugging my app instead of developing advanced features. :(
* The UI could use a lot of work still, I am planning to do those changes after the bootcamp ends to improve my portfolio.

LICEcap demo:
![LICEcapimage](https://github.com/martasmith/GridImageSearch/blob/master/codepath_week2_v1.gif)
