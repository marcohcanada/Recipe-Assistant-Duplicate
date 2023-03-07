# Recipe-Assistant
The Recipe Assistant is an application that aims to help a user find recipes within the limitations of their intolerances. For users who have many intolerances, attempting to find a recipe online that is possible to be eaten with their large amount of food intolerances is nearly impossible. Within the Recipe Assistant, a user may make a profile of their intolerances where when searching the system will vet and either hide or warn recipes that exceed those rules.
## Getting Started
The API for the Android application is currently offline, though a demo of the project recorded in its intended state with the API being online is linked below.
## Video Demo
https://youtu.be/Uf5z0TqA124
## System Components
### Azure Virtual Machine Server
- We have purchased and configured a Windows Server 2018 environment that is hosting the System Components that need to stay active and accessible. This includes the Microsoft SQL Server, .NET 6 API, and Python Machine learning scripts.
- We have created our own custom API in .NET 6. This API uses a separate Entity Framework project to connect with the Database. Models in the “EFDataAccess” project are 1:1 with the Database and pull into the application. Within the Recipe Assistant API project there are controllers that contain all the methods for endpoints that can be called, and the models contain the objects that received and sent JSON is converted to.
