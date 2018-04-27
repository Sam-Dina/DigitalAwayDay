# DigitalAwayDay

##How to execute

> The application can be execute from the MainApplication.java
> Example: MainApplication input.txt output.txt

>The input.txt will be formatted activities input file
>The output.txt will be path to the output file
>This will print the output in console and it will also write it to the output file

>If the args are not provided then, the default input(activities.txt) and output(output.txt) will be used


##Implementation Approach

> The application is designed using the strategy design pattern
> The activities are given time without conflicting with the event start time/event end time/default activity start time/default activity end time
> The activities are randomly picked up for a team
> The greedy algorithm is applied to ignore conflicting activities and take maximum number of activities from the randomly selected activity

##Unit Test

> The Unit testing is covered for all the possible activity timing

##Note

>The pattern to read the activity from the input file is not configurable hence, it is expected that the activities will the given format
