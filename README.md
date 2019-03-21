# miningResourceBehaviour

In this project, we provide a Prom plugin to investigate in more details the human resources behaviours in terms of 
work organization, Queuing behaviour and availability. 

# Getting Started

The following instructions will help you running the project:

An ant build.xml file is provided with the project and can be used to import it into eclipse.

The time the project is implemented, Java 8 is used. 

To launch the plugin, use the file "ProM with UITopia (miningresourcebehaviour).launch".

The file "ProM Package Manager (miningresourcebehaviour).launch" can be used to install other published ProM Plugin (if required).

The plugin takes as input a XES file (event log). For our tests we used the log file available with the following DOI: doi:10.4121/uuid:3926db30-f712-4394-aebc-75976070e91f. 

- The first step to start with after running the plugin is to indicate the connection parameters to the database.
In this project Mysql is used. 
- Import the log file into the database. For this step you can use the plugin to import the data into the database. However, given the size of the log, the importation process may take a time. To do it faster, we provide the dump file of the database.
U need just to create a database with a given name and import the dump file. Further we provide an sql script to create the views to be used later to interogate the database (in the sql script the database name is financial, in case you named it differently, change it the the script with the used name). 
- Once the database is ready, you can start use the plugin to investigate the resource behaviour. 
- For each behaviour type a csv file is generated with all the required information. 
- In its current version the plugin allows to work with log files using the following transcation types: START, SCHEDULE and COMPLETE. 









