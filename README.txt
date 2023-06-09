Fanzone - Yoel Nozar and Arnav Banthia
=================================

1. Changes to Goals

	The main change we made since the midterm report was reducing the supported sports to just be MLB since they are the only Major American sports league still in its regular season. As a result it was the only sport with a reliable and constant source of data points to pull from. No Changes were made to the overall scope of the apps features.

2. 
	Completed Deliverables

		- Real-Time searchable list of teams 
		- User can add teams to their favorite's list which will be stored and persisted locally on the device across shutdown, app updates, etc. without sign-in functionality
		- User can view a current breakdown of their favorite teams at a glance on the main screen.
		- User can view live scores for the current day that can be refreshed from the app without leaving and reopening the app. They can also launch ESPN's live GameCast for a box score.
		

	Uncompleted Deliverables
		
		All major deliverables were completed. We did not implement the in app browser which was a last minute stretch goal. Android does not provide a seamless in-app browser library or API like apple does with Safari.



MLB API
Scores: http://site.api.espn.com/apis/site/v2/sports/baseball/mlb/scoreboard

News: http://site.api.espn.com/apis/site/v2/sports/baseball/mlb/news

All Teams: http://site.api.espn.com/apis/site/v2/sports/baseball/mlb/teams

Specific Team: http://site.api.espn.com/apis/site/v2/sports/baseball/mlb/teams/:team

- for specific team use abbreviation EX atlanta braves -> http://site.api.espn.com/apis/site/v2/sports/baseball/mlb/teams/atl

